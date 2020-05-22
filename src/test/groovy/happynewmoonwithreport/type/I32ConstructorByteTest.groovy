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

/**
 * Created on 2018-03-03.
 */
class I32ConstructorByteTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Generate I32 with 4 bytes size = 8 "() {
		setup: ""
		ByteUnsigned[] byteAll = new Byte[4];
		byteAll[0] = new ByteUnsigned((byte) 0x70);
		byteAll[1] = new ByteUnsigned((byte) 0x01);
		byteAll[2] = new ByteUnsigned((byte) 0x02);
		byteAll[3] = new ByteUnsigned((byte) 0x03);


		when: "Construct the I32 size 8 "
		I32 actual = new I32(byteAll, 8, false);

		then: ""
		actual.integerValue() == 0x00000070;

		// expect: ""

		// cleanup: ""

		// where: ""

	}

	def "Generate I32 with four bytes "() {
		setup: ""
		ByteUnsigned[] byteAll = new Byte[4];
		byteAll[0] = new ByteUnsigned(byte0);
		byteAll[1] = new ByteUnsigned(byte1);
		byteAll[2] = new ByteUnsigned(byte2);
		byteAll[3] = new ByteUnsigned(byte3);


		when: "Construct the I32"
		I32 actual = new I32(byteAll);

		then: ""
//		actual.integerValue() == 1879114243;
//		actual.integerValue() == 0x70010203;
		new I32(val1) == actual;

		// expect: ""

		// cleanup: ""

		where: ""
		val1       || byte0 || byte1 || byte2 || byte3
		127        || 0     || 0     || 0     || 127
		0          || 0     || 0     || 0     || 0
		1          || 0     || 0     || 0     || 1
		134480385  || 0x8   || 0x4   || 0x2   || 0x1
		2004318071 || 0x77  || 0x77  || 0x77  || 0x77
		2147483647 || 0x7F  || 0xFF  || 0xFF  || 0xFF
		1887469696 || 0x70  || 0x80  || 0x80  || 0x80

	}

	def "Generate I32 with four bytes Length and SignExtension"() {
		setup: ""
		ByteUnsigned[] byteAll = new ByteUnsigned[4];
		byteAll[0] = new ByteUnsigned((byte) 0x70);
		byteAll[1] = new ByteUnsigned((byte) 0x01);
		byteAll[2] = new ByteUnsigned((byte) 0x02);
		byteAll[3] = new ByteUnsigned((byte) 0x03);


		when: "Construct the I32"
		I32 actual = new I32(byteAll, 32, false);

		then: ""
		// actual.integerValue() == 1879114243;
		actual.integerValue() == 0x70010203;

		// expect: ""

		// cleanup: ""

		// where: ""

	}


}
