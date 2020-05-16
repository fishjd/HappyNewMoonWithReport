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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import happynewmoonwithreport.type.JavaType.ByteUnsigned;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Int64WasmTest {
	ByteUnsigned input;

	@BeforeEach
	void setUp() {

	}

	@AfterEach
	void tearDown() {
	}

	@DisplayName("Sign extend 8 to 64")
	@ParameterizedTest(name = "{index} => val1={0}, expected={1}")
	@CsvSource({"0, 0",  //
		"1, 1",  //
		"2, 2",  //
		"127, 127",  //
		"-2, -2",  //
		"-128, -128"  //
	})
	void signExtend8To64(byte val1, Long expected) {
		// setup
		input = new ByteUnsigned(val1);

		// run
		Long actual = IntWasm.signExtend8To64(input);

		// verify
		assertEquals(expected, actual);

	}

	@Test
	void signExtend8To64Loop() {
		for (Byte val1 = Byte.MIN_VALUE; val1 < Byte.MAX_VALUE; val1++) {
			// setup
			input = new ByteUnsigned(val1);

			// run
			Long actual = IntWasm.signExtend8To64(input);

			// verify
			Long expected = Long.valueOf(val1);
			assertEquals(expected, actual);
		}
	}

	@Test
	void twoComplement() {
		// setup

		// run
		assertEquals(-1L, IntWasm.twoComplement(1));
		assertEquals(0L, IntWasm.twoComplement(0));
		assertEquals(0L, IntWasm.twoComplement(0));
	}
}