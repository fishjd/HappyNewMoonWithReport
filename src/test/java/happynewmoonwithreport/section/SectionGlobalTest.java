/*
 *  Copyright 2017 - 2021 Whole Bean Software, LTD.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package happynewmoonwithreport.section;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.GlobalType;
import happynewmoonwithreport.GlobalVariableType;
import happynewmoonwithreport.ValueType;
import happynewmoonwithreport.type.UInt32;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class SectionGlobalTest {
	private SectionGlobal sectionGlobal;

	@BeforeEach
	public void setUp() throws Exception {
		sectionGlobal = new SectionGlobal();
		assertNotNull(sectionGlobal);
	}

	@AfterEach
	public void tearDown() throws Exception {
	}


	/**
	 * Run instantiate on the add32.wasm bytes.
	 */
	@Test
	public void instantiateFull() {
		// I made this one up.  This is not in Add32
		byte[] byteAll = {(byte) 0x01,//
			(byte) 0x7F, // -0x01 i32
			(byte) 0x00 // mutability = 0
		};
		BytesFile payload = new BytesFile(byteAll);

		// run
		sectionGlobal.instantiate(payload);

		// verify
		// the count is 1
		assertEquals(new UInt32(1L), sectionGlobal.getCount());

		ArrayList<GlobalVariableType> globals = sectionGlobal.getGlobals();
		assertEquals(1, globals.size());

		GlobalVariableType globalVariable = globals.get(0);
		GlobalType type = globalVariable.getType();
		assertEquals(new ValueType(-0x01), type.getContentType());
		assertEquals(0, type.getMutability().getType().intValue());


	}

}