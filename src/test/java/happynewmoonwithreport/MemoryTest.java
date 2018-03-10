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
package happynewmoonwithreport;

import happynewmoonwithreport.type.S32;
import happynewmoonwithreport.type.UInt32;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MemoryTest {
	private Memory memory;
	private UInt32 size;  // in pages

	@BeforeEach
	public void setUp() throws Exception {
		size = new UInt32(1L);
		memory = new Memory(size);
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void setAndGetRoundTrip() throws Exception {
		UInt32 index = new UInt32(3L);
		byte expectedValue = (byte) 0xC6;

		// set
		memory.set(index, expectedValue);

		// get
		byte actual = memory.get(index);

		//verify
		assertEquals(expectedValue, actual);

	}


	/**
	 * Test the memory Grow function.
	 *
	 * @throws Exception
	 */
	@Test
	public void growTest() throws Exception {
		UInt32 index = new UInt32(3L);
		byte expectedValue = (byte) 0xC6;

		// set
		memory.set(index, expectedValue);

		// run
		final S32 previousSize = memory.grow(new UInt32(3));

		// get
		byte actual = memory.get(index);


		//verify
		assertEquals(new S32(1), previousSize);
		assertEquals(expectedValue, actual);

	}


}