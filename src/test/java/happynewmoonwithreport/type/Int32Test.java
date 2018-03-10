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

public class Int32Test {
	private S32 s32;

	@BeforeEach
	public void setUp() throws Exception {

	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void maxBits() throws Exception {
		s32 = new S32(0);
		assertEquals(new Integer(32), s32.maxBits());
	}

	@Test
	public void minValue() throws Exception {
		s32 = new S32(0);
		assertEquals(new Long(-2_147_483_648), s32.minValue());
	}

	@Test
	public void maxValue() throws Exception {
		s32 = new S32(0);
		assertEquals(new Long(2_147_483_647), s32.maxValue());

		assertEquals((1 << 31) - 1, new Double(Math.pow(2, 31)).intValue());
	}

}