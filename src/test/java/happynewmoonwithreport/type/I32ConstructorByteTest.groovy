/*
 *  Copyright 2018 Whole Bean Software, LTD.
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

import spock.lang.Specification

/**
 * Created on 2018-03-03.
 */
class I32ConstructorByteTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Generate I32 with four bytes "() {
		setup: ""
		Byte[] byteAll = new Byte[4];
		byteAll[0] = new Byte((byte) 0x70);
		byteAll[1] = new Byte((byte) 0x01);
		byteAll[2] = new Byte((byte) 0x02);
		byteAll[3] = new Byte((byte) 0x03);


		when: "Construct the I32"
		I32 actual = new I32(byteAll);

		then: ""
		actual.integerValue() == 1879114243;

		// expect: ""

		// cleanup: ""

		// where: ""

	}
}
