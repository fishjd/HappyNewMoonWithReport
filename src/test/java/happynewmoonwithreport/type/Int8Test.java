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
package happynewmoonwithreport.type;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Int8Test {
	private SInt8 SInt8;

	@BeforeEach
	public void setUp() throws Exception {

	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void maxBits() throws Exception {
		SInt8 = new SInt8((byte) 0);
		assertEquals(new Integer(8), SInt8.maxBits());
	}

	@Test
	public void minValue() throws Exception {
		SInt8 = new SInt8((byte) 0);
		assertEquals(new Long((byte) -128), SInt8.minValue());
	}

	@Test
	public void maxValue() throws Exception {
		SInt8 = new SInt8((byte) 0);
		assertEquals(new Long((byte) 127), SInt8.maxValue());
	}

}