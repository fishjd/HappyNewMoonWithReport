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

import happynewmoonwithreport.type.JavaType.ByteUnsigned
import spock.lang.Specification
import spock.lang.Unroll

class F32Test extends Specification {
	def setup() {
	}

	def cleanup() {
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
}
