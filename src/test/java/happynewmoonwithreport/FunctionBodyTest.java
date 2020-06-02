/*
 *  Copyright 2017 - 2020 Whole Bean Software, LTD.
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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import happynewmoonwithreport.type.UInt32;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class FunctionBodyTest {
	private FunctionBody functionBody;

	@BeforeEach
	public void setUp() throws Exception {

	}

	@AfterEach
	public void tearDown() throws Exception {
	}


	/**
	 * Run instantiate on the add32.wasm bytes.
	 */
	@Test
	public void instantiateFull() {
		// I made this one up.  This is not in Add32.wasm
		byte[] byteAll = {
			//Body Size
			(byte) 0x09,
			// Local Count
			(byte) 0x02,
			// Local Entry
			(byte) 0x02,    // count
			(byte) 0x7F,    // int 32
			// Code
			(byte) 0x20, (byte) 0x01, (byte) 0x20, (byte) 0x00, (byte) 0x6A,
			// End Byte
			(byte) 0x0B     // always 0x0B
		};
		BytesFile payload = new BytesFile(byteAll);

		// run
		functionBody = new FunctionBody(payload);

		//* verify
		//** Body Size
		assertEquals(new UInt32(9L), functionBody.getBodySize());

		//** Local Count
		assertEquals(new UInt32(2L), functionBody.getLocalCount());

		//** Local Variables
		assertEquals(2, functionBody.getLocalEntryAll().size());
		assertEquals(new ValueType("int32"), functionBody.getLocalEntryAll().get(0));
		assertEquals(new ValueType("int32"), functionBody.getLocalEntryAll().get(1));

		//** Code
		byte[] expectedCode = {(byte) 0x20, (byte) 0x01, (byte) 0x20, (byte) 0x00, (byte) 0x6A};
		assertArrayEquals(expectedCode, functionBody.getCode());

		//** End Byte
		assertEquals(0x0B, functionBody.getEnd());
	}

}