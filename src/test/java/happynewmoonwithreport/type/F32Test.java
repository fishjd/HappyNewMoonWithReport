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

package happynewmoonwithreport.type;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class F32Test {


	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void testValueOfFloatInputFloat() {
		// Groovy/Spock does not handle -0F, Java/jUnit does. So we moved this test to jUnit.
		assertEquals(F32.ZERO_POSITIVE,F32.valueOf(0F));
		assertEquals(F32.ZERO_NEGATIVE,F32.valueOf(-0F));

		assertEquals(F32.ZERO_NEGATIVE,F32.valueOf(-0F));


		assertEquals(F32.NAN,F32.valueOf(Float.NaN));
		assertEquals(F32.NEGATIVE_INFINITY,F32.valueOf(Float.NEGATIVE_INFINITY));
		assertEquals(F32.POSITIVE_INFINITY,F32.valueOf(Float.POSITIVE_INFINITY));
	}

	@Test
	void testValueOfFloatInputString() {
		assertEquals(F32.ZERO_POSITIVE,F32.valueOf("+0x0p+0"));
		assertEquals(F32.ZERO_NEGATIVE,F32.valueOf("-0x0p+0"));
	}

	@Test
	void valueOf() {
	}
}