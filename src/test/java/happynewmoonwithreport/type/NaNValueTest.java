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

import static org.assertj.core.api.Assertions.assertThat;

import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.opcode.WasmInstanceStub;
import happynewmoonwithreport.opcode.comparison.I32_eq;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Created on 2018-01-20.
 */
public class NaNValueTest {

	private WasmInstanceInterface instance;
	private I32_eq function;


	@BeforeEach
	void setUp() {
		instance = new WasmInstanceStub();
		function = new I32_eq(instance);
	}

	@AfterEach
	void tearDown() {
	}

	@ParameterizedTest
	// @Formatter:off
	@ValueSource(ints =
		{
			0x7fa00000,   // nan:0x200000
			0x7fc00000    // nan:canonical  nan:0x400000
		}
	)
	// @Formatter:on
	/**
	 *  Test Float to covet to int.  Does not test F32.
	 */
	void testFloatConical (Integer input) {
		// convert input to float and test
		Float canonicalFloat = Float.intBitsToFloat(input);
		Boolean isNan = canonicalFloat.isNaN();
		assertThat(isNan).isTrue();

		// convert back to integer and test
		Integer canonical = Float.floatToRawIntBits(canonicalFloat);
		assertThat(input).isEqualTo(canonical);
	}


}