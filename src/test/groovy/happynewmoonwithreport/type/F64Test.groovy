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

class F64Test extends Specification {
	def setup() {
	}

	def cleanup() {
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
}
