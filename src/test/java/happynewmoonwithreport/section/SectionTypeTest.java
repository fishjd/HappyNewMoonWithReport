/*
 *  Copyright 2017 - 2019 Whole Bean Software, LTD.
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

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.FunctionType;
import happynewmoonwithreport.type.UInt32;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SectionTypeTest {

	private SectionType sectionType;

	@BeforeEach
	public void setUp() throws Exception {
		sectionType = new SectionType();
		assertNotNull(sectionType);
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void testInstantiate() {
		byte[] byteAll = {(byte) 0x01, (byte) 0x60, (byte) 0x02, (byte) 0x7F, (byte) 0x7F, (byte) 0x01, (byte) 0x7F};
		BytesFile payload = new BytesFile(byteAll);
		// run
		sectionType.instantiate(payload);

		// verify
		assertEquals(1, sectionType.getFunctionSignatures().size());
		final ArrayList<FunctionType> functionSignatureAll = sectionType.getFunctionSignatures();
		FunctionType functionType = functionSignatureAll.get(0);
		assertEquals("func", functionType.getForm().getValue());
		assertEquals(new UInt32(2L), functionType.getParamCount());
		assertEquals(new UInt32(1), functionType.getReturnCount());

		assertEquals(new UInt32(2L), functionType.getParamCount());
		assertEquals(new UInt32(1), functionType.getReturnCount());

		assertEquals(2, functionType.getParamTypeAll().size());
		assertEquals(1, functionType.getReturnTypeAll().size());

		assertEquals("int32", functionType.getParamTypeAll().get(0).getValue());
		assertEquals("int32", functionType.getParamTypeAll().get(1).getValue());
		assertEquals("int32", functionType.getReturnTypeAll().get(0).getValue());


	}

}
