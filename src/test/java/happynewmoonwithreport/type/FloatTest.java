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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testing the Java Float class.
 */
class FloatTest {


	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void FloatIsNanTest() {
		assertTrue(F32.NanCanonicalPos.value.isNaN());
		assertTrue(F32.NanCanonicalNeg.value.isNaN());
		assertTrue(F32.NanArithmeticPos.value.isNaN());
		assertTrue(F32.NanArithmeticNeg.value.isNaN());
		assertTrue(F32.Nan0x20_0000Pos.value.isNaN());
		assertTrue(F32.Nan0x20_0000Neg.value.isNaN());
	}


}