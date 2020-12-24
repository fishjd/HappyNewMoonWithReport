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

package happynewmoonwithreport.type

import happynewmoonwithreport.BytesFile
import happynewmoonwithreport.type.JavaType.ByteUnsigned
import spock.lang.Specification

class F64GroovyTest extends Specification {
	def setup() {
	}

	def cleanup() {
	}

	def "test F64 ValueOf(Double) ##count -> #input | #expected"(Integer count, Double input, String expected) {
		given: "A Double input "

		when: "covert to F64"
		F64 result = F64.valueOf(input);

		then:
		F64 expectedF64 = F64.valueOf(expected);

		expectedF64 == result;
		// Math.abs(expectedF64.value - result.value) < 0.1F;

		where:
		count | input || expected
		// Groovy/Spock strips out the sign bit of -0.0F so this test does not work.
		// 1     | -0D   || "-0"
		// 2     | -0D   || "-0x0p+0"
		3     | 0D    || "0"
		4     | 0.0D  || "+0x0p+0"
		5     | 1D    || "1"
	}


	//@Unroll
	def "test F64 ValueOf(String) #input, #expected"(String input, Double expected) {
		given: "an string input "

		when: "covert to F64"
		F64 result = F64.valueOf(input);

		F64 expectedF64 = new F64(expected);
		Long numOfBits = Double.doubleToLongBits(expected);
		then:
		// expectedF64 == result;
		Math.abs(expected - result.value) < 0.1F;

		where:
		input       || expected
		"0"         || 0F
		"+0x0p+0"   || 0.0F
		//"-0x0p+0" || -0.0F    // Groovy/Spock strips out the sign bit of -0.0F so this test does not work.
		//                      // This valueOf("-0x0p+0") is tested in "F64 Constants"() method below.
		"-0x1p-149" || -1.4E-45 // This is the value the Double.valueOf(String) returns.
		//                      // I don't know if this is what WASM is trying to express.
		//                      // 1E-149 is not a valid Double.
	}

	def "test convert"(Byte[] input, Double expected) {
		given: "A Bytes file from input"
		BytesFile bytesFile = new BytesFile(input);

		when: "Convert bytesFile to an F64"
		F64 output = F64.convert(bytesFile);

		then:
		new F64(expected) == output
		expected == output.value

		where:
		input                                            || expected
		[0x33, 0x33, 0x33, 0x33, 0x33, 0x33, 0xF3, 0x3F] || 1.2D
		[0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00] || 0D
		[0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xF8, 0x7F] || Double.NaN
		[0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xF0, 0xFF] || Double.NEGATIVE_INFINITY
		[0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xF0, 0x7F] || Double.POSITIVE_INFINITY
		[0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00] || Double.MIN_VALUE
		[0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xEF, 0x7F] || Double.MAX_VALUE
	}


	def "test getBytes"(Double valueDouble, Byte[] expected) {
		given:
		F64 value = new F64(valueDouble)

		when: "Covert to Byte Array"
		Byte[] byteAll = value.getBytes();

		then:

		expected == byteAll

		where:
		valueDouble              || expected
		1.2                      || [0x3F, 0xF3, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33]
		0                        || [0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00]
		Double.NaN               || [0x7F, 0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00]
		Double.NEGATIVE_INFINITY || [0xFF, 0xF0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00]
		Double.POSITIVE_INFINITY || [0x7F, 0xF0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00]
		Double.MIN_VALUE         || [0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01]
		Double.MAX_VALUE         || [0x7F, 0xEF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF]
	}

	public def "getSignTest  #valueDouble || #expected"(Double valueDouble, Boolean expected) {
		given:
		F64 value = new F64(valueDouble)

		when: "Get the Sign"
		Boolean sign = value.isPositive()

		then:
		expected == sign

		where:
		valueDouble              || expected
		1.2                      || true
		0F                       || true
		// Groovy / Spock do not handle -0F  Happy New Moon with Report does.
		// -0F                   || false
		Double.NaN               || true
		Double.NEGATIVE_INFINITY || false
		Double.POSITIVE_INFINITY || true
		Double.MIN_VALUE         || true
		Double.MAX_VALUE         || true

	}

	public def "getSignTestNegativeZero"(F64 value, Boolean expected) {

		when: "Get the Sign"
		Boolean sign = value.isPositive()

		then:
		expected == sign

		where:
		value                 || expected
		F64.ZERO_NEGATIVE     || false
		F64.ZERO_POSITIVE     || true
		F64.NEGATIVE_INFINITY || false
		F64.POSITIVE_INFINITY || true
		F64.NAN               || true // ????


	}


	def "Construction UnsignedByte Array"(ByteUnsigned[] input, Double expected) {

		when: "Covert to Byte Array"
		F64 value = new F64(input)
		F64 expectedF64 = new F64(expected)
		then:
		expectedF64 == value

		where:
		input                                                                                            || expected
		[new ByteUnsigned(0x3F), new ByteUnsigned(0xF3), new ByteUnsigned(0x33), new ByteUnsigned(0x33),
		 new ByteUnsigned(0x33), new ByteUnsigned(0x33), new ByteUnsigned(0x33), new ByteUnsigned(0x33)] || 1.2

		[new ByteUnsigned(0x00), new ByteUnsigned(0x00), new ByteUnsigned(0x00), new ByteUnsigned(0x00),
		 new ByteUnsigned(0x00), new ByteUnsigned(0x00), new ByteUnsigned(0x00), new ByteUnsigned(0x00)] || 0

		[new ByteUnsigned(0x7F), new ByteUnsigned(0xF8), new ByteUnsigned(0x00), new ByteUnsigned(0x00),
		 new ByteUnsigned(0x00), new ByteUnsigned(0x00), new ByteUnsigned(0x00), new ByteUnsigned(0x00)] || Double.NaN

		[new ByteUnsigned(0xFF), new ByteUnsigned(0xF0), new ByteUnsigned(0x00), new ByteUnsigned(0x00),
		 new ByteUnsigned(0x00), new ByteUnsigned(0x00), new ByteUnsigned(0x00), new ByteUnsigned(0x00)] || Double.NEGATIVE_INFINITY

		[new ByteUnsigned(0x7F), new ByteUnsigned(0xF0), new ByteUnsigned(0x00), new ByteUnsigned(0x00),
		 new ByteUnsigned(0x00), new ByteUnsigned(0x00), new ByteUnsigned(0x00), new ByteUnsigned(0x00)] || Double.POSITIVE_INFINITY

		[new ByteUnsigned(0x00), new ByteUnsigned(0x00), new ByteUnsigned(0x00), new ByteUnsigned(0x00),
		 new ByteUnsigned(0x00), new ByteUnsigned(0x00), new ByteUnsigned(0x00), new ByteUnsigned(0x01)] || Double.MIN_VALUE

		[new ByteUnsigned(0x7F), new ByteUnsigned(0xEF), new ByteUnsigned(0xFF), new ByteUnsigned(0xFF),
		 new ByteUnsigned(0xFF), new ByteUnsigned(0xFF), new ByteUnsigned(0xFF), new ByteUnsigned(0xFF)] || Double.MAX_VALUE
	}

	def "F64 constants"() {
		when:
		int i = 1;

		then:
		// valueOf(String)
		F64.ZERO_POSITIVE == F64.valueOf("0x0p0D");
		F64.ZERO_NEGATIVE == F64.valueOf("-0x0p+0")

		// valueOf(Double)
//		Double nan_f = Double.longBitsToDouble(0x7fc0_0000);
		Double nan_d = Double.longBitsToDouble(0x7ff8000000000000L);
		F64.NAN == F64.valueOf(nan_d);
//		Double nan_neg_f = Double.intBitsToDouble(0xffc0_0000);
//		F64.NAN_NEGATIVE == F64.valueOf(nan_neg_f);
	}

	def "F64 EqualsWasm #leftInput | #rightInput || #expectedInput"(Double leftInput, Double rightInput, Integer expectedInput) {
		F64 left = new F64(leftInput);
		F64 right = new F64(rightInput);
		I32 expected = new I32(expectedInput);

		when: "Compare "
		I32 result = left.equalsWasm(right);
		then:
		expected == result

		where:
		leftInput                | rightInput               || expectedInput
		Double.NaN               | Double.NaN               || 0
		Double.NaN               | 1F                       || 0
		1F                       | Double.NaN               || 0
		Double.NaN               | 1F                       || 0
		1F                       | 1F                       || 1
		0F                       | 0F                       || 1
		Double.MAX_VALUE         | Double.MAX_VALUE         || 1
		Double.MIN_VALUE         | Double.MIN_VALUE         || 1
		Double.NEGATIVE_INFINITY | Double.NEGATIVE_INFINITY || 1
		Double.POSITIVE_INFINITY | Double.POSITIVE_INFINITY || 1
		0F                       | 1F                       || 0
		1F                       | 0F                       || 0
		0F                       | 0F                       || 1
		0F                       | -0F                      || 1  // -0F does not render a zero_negative.
		F64.ZERO_POSITIVE.value  | F64.ZERO_POSITIVE.value  || 1
		F64.ZERO_NEGATIVE.value  | F64.ZERO_NEGATIVE.value  || 1
		F64.ZERO_NEGATIVE.value  | F64.ZERO_POSITIVE.value  || 1
		F64.ZERO_POSITIVE.value  | F64.ZERO_NEGATIVE.value  || 1
	}

	def "F64 getSign #leftInput | #rightInput || #expectedInput"(Double leftInput, Double rightInput, Integer expectedInput) {
		F64 left = new F64(leftInput);
		F64 right = new F64(rightInput);
		I32 expected = new I32(expectedInput);

		when: "Compare "
		I32 result = left.equalsWasm(right);
		then:
		expected == result

		where:
		leftInput                | rightInput               || expectedInput
		Double.NaN               | Double.NaN               || 0
		Double.NaN               | 1F                       || 0
		1F                       | Double.NaN               || 0
		Double.NaN               | 1F                       || 0
		1F                       | 1F                       || 1
		0F                       | 0F                       || 1
		Double.MAX_VALUE         | Double.MAX_VALUE         || 1
		Double.MIN_VALUE         | Double.MIN_VALUE         || 1
		Double.NEGATIVE_INFINITY | Double.NEGATIVE_INFINITY || 1
		Double.POSITIVE_INFINITY | Double.POSITIVE_INFINITY || 1
		0F                       | 1F                       || 0
		1F                       | 0F                       || 0
		0F                       | 0F                       || 1
		0F                       | -0F                      || 1  // -0F does not render a zero_negative.
		F64.ZERO_POSITIVE.value  | F64.ZERO_POSITIVE.value  || 1
		F64.ZERO_NEGATIVE.value  | F64.ZERO_NEGATIVE.value  || 1
		F64.ZERO_NEGATIVE.value  | F64.ZERO_POSITIVE.value  || 1
		F64.ZERO_POSITIVE.value  | F64.ZERO_NEGATIVE.value  || 1
	}

	// @Unroll
	def "F64 EqualsWasm negative and positive zero #leftInput_s | #rightInput_s || #expectedInput"(String leftInput_s, String rightInput_s, Integer expectedInput) {
		F64 left = F64.valueOf(leftInput_s);
		F64 right = F64.valueOf(rightInput_s);

		I32 expected = new I32(expectedInput);

		when: "Compare "
		I32 result = left.equalsWasm(right);
		then:
		expected == result

		where:
		leftInput_s | rightInput_s || expectedInput
		"0x0p0F"    | "0x0p0F"     || 1
		"-0x0p+0"   | "-0x0p+0"    || 1
		"-0x0p+0"   | "0x0p+0"     || 1
		"0x0p+0"    | "-0x0p+0"    || 1
	}
}
