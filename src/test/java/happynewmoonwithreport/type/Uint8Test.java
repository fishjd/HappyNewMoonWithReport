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
package happynewmoonwithreport.type;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import happynewmoonwithreport.BytesFile;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Uint8Test {
	private UInt8 uInt8;

	@BeforeEach
	public void setUp() throws Exception {
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void maxBits() throws Exception {
		uInt8 = new UInt8(0);
		assertEquals(new Integer(8), uInt8.maxBits());
	}

	@Test
	public void minValue() throws Exception {
		uInt8 = new UInt8(0);
		assertEquals(new Long(0), uInt8.minValue());
	}

	@Test
	public void maxValue() throws Exception {
		uInt8 = new UInt8(0);
		assertEquals(new Long(256), uInt8.maxValue());
	}


	@Test
	public void testReadUnsigned2() throws Exception {
		for (Integer i = 0; i < 256; i++) {
			byte[] bytesAll = new byte[]{new Integer(i).byteValue()};
			BytesFile bytesFile = new BytesFile(bytesAll);
			UInt8 uInt8 = new UInt8(bytesFile);
			Integer result = uInt8.integerValue();
			assertEquals(i, result, "i = " + i.toString());
		}
	}


}
