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

public class Uint16Test {
	private UInt16 uInt16;

	@BeforeEach
	public void setUp() throws Exception {
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void maxBits() throws Exception {
		uInt16 = new UInt16(0);
		assertEquals(new Integer(16), uInt16.maxBits());
	}

	@Test
	public void minValue() throws Exception {
		uInt16 = new UInt16(0);
		assertEquals(new Long(0), uInt16.minValue());
	}

	@Test
	public void maxValue() throws Exception {
		uInt16 = new UInt16(0);
		assertEquals(new Long(65_536), uInt16.maxValue());
	}

	@Test
	public void testReadUnsigned() throws Exception {
		for (Integer i = 0; i < Math.pow(2, 16); i++) {
			byte b1 = (byte) (i & 0xFF);
			byte b2 = (byte) ((i >> 8) & 0xFF);
			BytesFile bytesFile = new BytesFile(new byte[]{b1, b2});
			UInt16 uint16 = new UInt16(bytesFile);
			Integer result = uint16.integerValue();
			assertEquals(i, result);
		}
	}

}
