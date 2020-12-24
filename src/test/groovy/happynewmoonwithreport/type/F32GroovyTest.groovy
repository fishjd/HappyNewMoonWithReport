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

class F32GroovyTest extends Specification {
	def setup() {
	}

	def cleanup() {
	}

	def "test F32 ValueOf(Float) ##count -> #input | #expected"(Integer count, Float input, String expected) {
		given: "A Float input "

		when: "covert to F32"
		F32 result = F32.valueOf(input);

		then:
		F32 expectedF32 = F32.valueOf(expected);

		expectedF32 == result;
		// Math.abs(expectedF32.value - result.value) < 0.1F;

		where:
		count | input || expected
		// Groovy/Spock strips out the sign bit of -0.0F so this test does not work.
		// 1     | -0F   || "-0"
		// 2     | -0F   || "-0x0p+0"
		3     | 0F    || "0"
		4     | 0.0F  || "+0x0p+0"
		5     | 1F    || "1"
	}


	//@Unroll
	def "test F32 ValueOf(String) #input, #expected"(String input, Float expected) {
		given: "an string input "

		when: "covert to F32"
		F32 result = F32.valueOf(input);

		F32 expectedF32 = new F32(expected);
		int numOfBits = Float.floatToRawIntBits(expected);
		then:
		expectedF32 == result;
		Math.abs(expected - result.value) < 0.1F;

		where:
		input       || expected
		"0"         || 0F
		"+0x0p+0"   || 0.0F
		//"-0x0p+0" || -0.0F    // Groovy/Spock strips out the sign bit of -0.0F so this test does not work.
		//                      // This valueOf("-0x0p+0") is tested in "F32 Constants"() method below.
		"-0x1p-149" || -1.4E-45 // This is the value the Float.valueOf(String) returns.
		//                      // I don't know if this is what WASM is trying to express.
		//                      // 1E-149 is not a valid Float.
	}

	def "test convert"(Byte[] input, Float expected) {
		given: "A Bytes file from input"
		BytesFile bytesFile = new BytesFile(input);

		when: "Convert bytesFile to an F32"
		F32 output = F32.convert(bytesFile);

		then:
		new F32(expected) == output
		expected == output.value

		where:
		input                    || expected
		[0x9A, 0x99, 0x99, 0x3F] || 1.2
		[0x00, 0x00, 0x00, 0x00] || 0
		[0x00, 0x00, 0xC0, 0x7F] || Float.NaN
		[0x00, 0x00, 0x80, 0xFF] || Float.NEGATIVE_INFINITY
		[0x00, 0x00, 0x80, 0x7F] || Float.POSITIVE_INFINITY
		[0x01, 0x00, 0x00, 0x00] || Float.MIN_VALUE
		[0xFF, 0xFF, 0x7F, 0x7F] || Float.MAX_VALUE
	}


	def "test getBytes"(Float valueFloat, Byte[] expected) {
		given:
		F32 value = new F32(valueFloat)

		when: "Covert to Byte Array"
		Byte[] byteAll = value.getBytes();
		then:

		expected == byteAll

		where:
		valueFloat              || expected
		1.2                     || [0x3F, 0x99, 0x99, 0x9A]
		0                       || [0x00, 0x00, 0x00, 0x00]
		Float.NaN               || [0x7F, 0xC0, 0x00, 0x00]
		Float.NEGATIVE_INFINITY || [0xFF, 0x80, 0x00, 0x00]
		Float.POSITIVE_INFINITY || [0x7F, 0x80, 0x00, 0x00]
		Float.MIN_VALUE         || [0x00, 0x00, 0x00, 0x01]
		Float.MAX_VALUE         || [0x7F, 0x7F, 0xFF, 0xFF]
	}

	public def "getSignTest  #valueFloat || #expected"(Float valueFloat, Boolean expected) {
		given:
		F32 value = new F32(valueFloat)

		when: "Get the Sign"
		Boolean sign = value.isPositive()

		then:
		expected == sign

		where:
		valueFloat              || expected
		1.2                     || true
		0F                      || true
		// Groovy / Spock do not handle -0F  Happy New Moon with Report does.
		// -0F                     || false
		Float.NaN               || true
		Float.NEGATIVE_INFINITY || false
		Float.POSITIVE_INFINITY || true
		Float.MIN_VALUE         || true
		Float.MAX_VALUE         || true

	}

	public def "getSignTestNegativeZero"(F32 value, Boolean expected) {

		when: "Get the Sign"
		Boolean sign = value.isPositive()

		then:
		expected == sign

		where:
		value                 || expected
		F32.ZERO_NEGATIVE     || false
		F32.ZERO_POSITIVE     || true
		F32.NEGATIVE_INFINITY || false
		F32.POSITIVE_INFINITY || true
		F32.NAN               || true // ????


	}


	def "Construction UnsignedByte Array"(ByteUnsigned[] input, Float expected) {

		when: "Covert to Byte Array"
		F32 value = new F32(input)
		F32 expectedF32 = new F32(expected)
		then:
		expectedF32 == value

		where:
		input                                                                                            || expected
		[new ByteUnsigned(0x3F), new ByteUnsigned(0x99), new ByteUnsigned(0x99), new ByteUnsigned(0x9A)] || 1.2
		[new ByteUnsigned(0x00), new ByteUnsigned(0x00), new ByteUnsigned(0x00), new ByteUnsigned(0x00)] || 0
		[new ByteUnsigned(0x7F), new ByteUnsigned(0xC0), new ByteUnsigned(0x00), new ByteUnsigned(0x00)] || Float.NaN
		[new ByteUnsigned(0xFF), new ByteUnsigned(0x80), new ByteUnsigned(0x00), new ByteUnsigned(0x00)] || Float.NEGATIVE_INFINITY
		[new ByteUnsigned(0x7F), new ByteUnsigned(0x80), new ByteUnsigned(0x00), new ByteUnsigned(0x00)] || Float.POSITIVE_INFINITY
		[new ByteUnsigned(0x00), new ByteUnsigned(0x00), new ByteUnsigned(0x00), new ByteUnsigned(0x01)] || Float.MIN_VALUE
		[new ByteUnsigned(0x7F), new ByteUnsigned(0x7F), new ByteUnsigned(0xFF), new ByteUnsigned(0xFF)] || Float.MAX_VALUE
	}

	def "F32 constants"() {


		when:
		int i = 1;

		then:
		F32.ZERO_POSITIVE == F32.valueOf("0x0p0F");
		F32.ZERO_NEGATIVE == F32.valueOf("-0x0p+0")
		Float nan_f = Float.intBitsToFloat(0x7fc0_0000);
		F32.NAN == F32.valueOf(nan_f);
//		Float nan_neg_f = Float.intBitsToFloat(0xffc0_0000);
//		F32.NAN_NEGATIVE == F32.valueOf(nan_neg_f);
	}

	def "F32 EqualsWasm #leftInput | #rightInput || #expectedInput"(Float leftInput, Float rightInput, Integer expectedInput) {
		F32 left = new F32(leftInput);
		F32 right = new F32(rightInput);
		I32 expected = new I32(expectedInput);

		when: "Compare "
		I32 result = left.equalsWasm(right);
		then:
		expected == result

		where:
		leftInput               | rightInput              || expectedInput
		Float.NaN               | Float.NaN               || 0
		Float.NaN               | 1F                      || 0
		1F                      | Float.NaN               || 0
		Float.NaN               | 1F                      || 0
		1F                      | 1F                      || 1
		0F                      | 0F                      || 1
		Float.MAX_VALUE         | Float.MAX_VALUE         || 1
		Float.MIN_VALUE         | Float.MIN_VALUE         || 1
		Float.NEGATIVE_INFINITY | Float.NEGATIVE_INFINITY || 1
		Float.POSITIVE_INFINITY | Float.POSITIVE_INFINITY || 1
		0F                      | 1F                      || 0
		1F                      | 0F                      || 0
		0F                      | 0F                      || 1
		0F                      | -0F                     || 1  // -0F does not render a zero_negative.
		F32.ZERO_POSITIVE.value | F32.ZERO_POSITIVE.value || 1
		F32.ZERO_NEGATIVE.value | F32.ZERO_NEGATIVE.value || 1
		F32.ZERO_NEGATIVE.value | F32.ZERO_POSITIVE.value || 1
		F32.ZERO_POSITIVE.value | F32.ZERO_NEGATIVE.value || 1
	}

	def "F32 getSign #leftInput | #rightInput || #expectedInput"(Float leftInput, Float rightInput, Integer expectedInput) {
		F32 left = new F32(leftInput);
		F32 right = new F32(rightInput);
		I32 expected = new I32(expectedInput);

		when: "Compare "
		I32 result = left.equalsWasm(right);
		then:
		expected == result

		where:
		leftInput               | rightInput              || expectedInput
		Float.NaN               | Float.NaN               || 0
		Float.NaN               | 1F                      || 0
		1F                      | Float.NaN               || 0
		Float.NaN               | 1F                      || 0
		1F                      | 1F                      || 1
		0F                      | 0F                      || 1
		Float.MAX_VALUE         | Float.MAX_VALUE         || 1
		Float.MIN_VALUE         | Float.MIN_VALUE         || 1
		Float.NEGATIVE_INFINITY | Float.NEGATIVE_INFINITY || 1
		Float.POSITIVE_INFINITY | Float.POSITIVE_INFINITY || 1
		0F                      | 1F                      || 0
		1F                      | 0F                      || 0
		0F                      | 0F                      || 1
		0F                      | -0F                     || 1  // -0F does not render a zero_negative.
		F32.ZERO_POSITIVE.value | F32.ZERO_POSITIVE.value || 1
		F32.ZERO_NEGATIVE.value | F32.ZERO_NEGATIVE.value || 1
		F32.ZERO_NEGATIVE.value | F32.ZERO_POSITIVE.value || 1
		F32.ZERO_POSITIVE.value | F32.ZERO_NEGATIVE.value || 1
	}

	// @Unroll
	def "F32 EqualsWasm negative and positive zero #leftInput_s | #rightInput_s || #expectedInput"(String leftInput_s, String rightInput_s, Integer expectedInput) {
		F32 left = F32.valueOf(leftInput_s);
		F32 right = F32.valueOf(rightInput_s);

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