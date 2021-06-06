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
package happynewmoonwithreport.type


import happynewmoonwithreport.type.JavaType.ByteUnsigned
import spock.lang.Specification

/**
 * Created on 2018-011-26.
 */
class I64GetBytes_Test extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Get Bytes "() {
		setup: "Create an I64"
		I64 val = new I64(val1);

		ByteUnsigned[] expected = new ByteUnsigned[8];
		expected[0] = new ByteUnsigned(expected0) // high byte
		expected[1] = new ByteUnsigned(expected1)
		expected[2] = new ByteUnsigned(expected2)
		expected[3] = new ByteUnsigned(expected3)
		expected[4] = new ByteUnsigned(expected4)
		expected[5] = new ByteUnsigned(expected5)
		expected[6] = new ByteUnsigned(expected6)
		expected[7] = new ByteUnsigned(expected7) // low byte


		when: "convert to to Unsigned byte array"
		ByteUnsigned[] actual = val.getBytes();

		then: "Test"
		actual == expected;

		where: ""
		// value  high Byte  ... low Byte
		val1                  || expected0 || expected1 || expected2 || expected3 || expected4 || expected5 || expected6 || expected7
		127                   || 0         || 0         || 0         || 0         || 0         || 0         || 0         || 127
		0                     || 0         || 0         || 0         || 0         || 0         || 0         || 0         || 0
		1                     || 0         || 0         || 0         || 0         || 0         || 0         || 0         || 1
		134480385             || 0         || 0         || 0         || 0         || 0x8       || 0x4       || 0x2       || 0x1
		2004318071            || 0         || 0         || 0         || 0         || 0x77      || 0x77      || 0x77      || 0x77
		2147483647            || 0         || 0         || 0         || 0         || 0x7F      || 0xFF      || 0xFF      || 0xFF
		1887469696            || 0         || 0         || 0         || 0         || 0x70      || 0x80      || 0x80      || 0x80
		1                     || 0         || 0         || 0         || 0         || 0         || 0         || 0         || 1
		0x01                  || 0         || 0         || 0         || 0         || 0         || 0         || 0         || 1
		9186918263483431288   || 0x7F      || 0x7E      || 0x7D      || 0x7C      || 0x7B      || 0x7A      || 0x79      || 0x78
		0x7F7E_7D7C_7B7A_7978 || 0x7F      || 0x7E      || 0x7D      || 0x7C      || 0x7B      || 0x7A      || 0x79      || 0x78

	}

}
