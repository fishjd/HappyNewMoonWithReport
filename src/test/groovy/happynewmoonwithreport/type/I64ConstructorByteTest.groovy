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
 * Created on 2018-011-26.
 */
class I64ConstructorByteTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Generate I64 with four bytes "() {
		setup: ""
		ByteUnsigned[] byteAll = new ByteUnsigned[8];
		byteAll[0] = new ByteUnsigned((byte) 0x00);
		byteAll[1] = new ByteUnsigned((byte) 0x01);
		byteAll[2] = new ByteUnsigned((byte) 0x02);
		byteAll[3] = new ByteUnsigned((byte) 0x03);
		byteAll[4] = new ByteUnsigned((byte) 0x04);
		byteAll[5] = new ByteUnsigned((byte) 0x05);
		byteAll[6] = new ByteUnsigned((byte) 0x06);
		byteAll[7] = new ByteUnsigned((byte) 0x07);


		when: "Construct the I64"
		I64 actual = new I64(byteAll);

		then: ""
		actual.longValue() == 0x01020304050607

		// expect: ""

		// cleanup: ""

		// where: ""

	}


}
