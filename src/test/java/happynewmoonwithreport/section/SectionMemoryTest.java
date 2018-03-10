/*
 *  Copyright 2017 Whole Bean Software, LTD.
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
import happynewmoonwithreport.type.MemoryType;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt1;
import happynewmoonwithreport.type.WasmVector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class SectionMemoryTest {
	private SectionMemory sectionMemory;

	@BeforeEach
	public void setUp() throws Exception {
		sectionMemory = new SectionMemory();
		assertNotNull(sectionMemory);
	}

	@AfterEach
	public void tearDown() throws Exception {
	}


	/**
	 * Run instantiate on the add32.wasm bytes.
	 */
	@Test
	public void instantiateAdd32() {
		byte[] byteAll = {
				(byte) 0x01,    // count
				(byte) 0x00,    // has Maximum
				(byte) 0x01     // minimum
		};
		BytesFile payload = new BytesFile(byteAll);

		// run
		sectionMemory.instantiate(payload);

		// verify
		// the count is 1
		assertEquals(new UInt32(1), sectionMemory.getCount());

		WasmVector<MemoryType> limitAll = sectionMemory.getMemoryTypeAll();
		assertEquals(1, limitAll.size());

		MemoryType limits = limitAll.get(0);
		assertEquals(new VarUInt1(0), limits.hasMaximum());
		assertEquals(new UInt32(1), limits.minimum());
		try {
			limits.maximum();
			fail("maximum Failure");
		} catch (RuntimeException rte) {
			// ok ;
		}

	}

	/**
	 * Run instantiate on the add32.wasm bytes.
	 */
	@Test
	public void instantiateHasMax() {
		byte[] byteAll = {
				(byte) 0x01,    // count
				(byte) 0x01,    // has Maximum
				(byte) 0x03,    // minimum
				(byte) 0x06     // maximum
		};
		BytesFile payload = new BytesFile(byteAll);

		// run
		sectionMemory.instantiate(payload);

		// verify
		// the count is 1
		assertEquals(new UInt32(1), sectionMemory.getCount());

		WasmVector<MemoryType> memoryTypeAll = sectionMemory.getMemoryTypeAll();
		assertEquals(1, memoryTypeAll.size());

		MemoryType memoryType = memoryTypeAll.get(0);
		assertEquals(new UInt32(1), memoryType.hasMaximum());
		assertEquals(new UInt32(3), memoryType.minimum());
		assertEquals(new UInt32(6), memoryType.maximum());

	}

}