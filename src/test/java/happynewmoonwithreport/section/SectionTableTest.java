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
import happynewmoonwithreport.ElementType;
import happynewmoonwithreport.TableType;
import happynewmoonwithreport.type.UInt32;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class SectionTableTest {
	private SectionTable sectionTable;

	@BeforeEach
	public void setUp() throws Exception {
		sectionTable = new SectionTable();
		assertNotNull(sectionTable);
	}

	@AfterEach
	public void tearDown() throws Exception {
	}


	/**
	 * Run instantiate on the add32.wasm bytes.
	 */
	@Test
	public void instantiateAdd32() {
		byte[] byteAll = {(byte) 0x01, (byte) 0x70, (byte) 0x00, (byte) 0x00};
		BytesFile payload = new BytesFile(byteAll);

		// run
		sectionTable.instantiate(payload);

		// verify
		// the count is 1
		assertEquals(new UInt32(1L), sectionTable.getCount());

		ArrayList<TableType> typeAll = sectionTable.getTables();
		assertEquals(1, typeAll.size());

		TableType table = typeAll.get(0);
		assertNotNull(table);

		assertEquals(new ElementType("anyFunc"), table.getElementType());

		assertEquals(new UInt32(0), table.hasMaximum());
		assertEquals(new UInt32(0L), table.minimum());
		try {
			table.maximum();
			fail("maximum Failure");
		} catch (RuntimeException rte) {
			// ok ;
		}

	}

}