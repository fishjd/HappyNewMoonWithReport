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
package happynewmoonwithreport.type;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UInt64Test {

	@BeforeEach
	public void setUp() throws Exception {
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	private UInt64 uInt64;

	@Test
	public void maxBits() throws Exception {
		uInt64 = new UInt64(0);
		assertEquals(new Integer(63), uInt64.maxBits());
	}

	@Test
	public void minValue() throws Exception {
		uInt64 = new UInt64(0);
		assertEquals(new Long(0), uInt64.minValue());
	}

	@Test
	public void maxValue() throws Exception {
		uInt64 = new UInt64(0);
		// If we had the full 64 bits.
		// assertEquals(new Long( 18_446_744_073_709_551_616L), uInt64.maxValue());
		assertEquals(new Long(9_223_372_036_854_775_807L), uInt64.maxValue());
	}


	private void assertEqualHex(Long expected, Long result) {
		assertEquals(new Long(expected), result, "i = " + expected.toString() + " hex = " + Long.toHexString(expected));
	}


}
