/*
 *  Copyright 2017 - 2019 Whole Bean Software, LTD.
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
class I32GetBytes_Test extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Get Bytes "() {
		setup: "Create an I32"
		I32 val = new I32(val1);

		ByteUnsigned[] expected = new ByteUnsigned[4];
		expected[0] = new ByteUnsigned(expected0)
		expected[1] = new ByteUnsigned(expected1)
		expected[2] = new ByteUnsigned(expected2)
		expected[3] = new ByteUnsigned(expected3)


		when: "convert to to Unsigned byte array"
		ByteUnsigned[] actual = val.getBytes();

		then: "Test"
		actual == expected;

		where: ""
		val1       || expected0 || expected1 || expected2 || expected3
		127        || 0         || 0         || 0         || 127
		0          || 0         || 0         || 0         || 0
		1          || 0         || 0         || 0         || 1
		134480385  || 0x8       || 0x4       || 0x2       || 0x1
		2004318071 || 0x77      || 0x77      || 0x77      || 0x77
		2147483647 || 0x7F      || 0xFF      || 0xFF      || 0xFF
		1887469696 || 0x70      || 0x80      || 0x80      || 0x80

//		-1L                        || -1L
//		0x7FFF_FFFF_FFFF_FFFFL     || Long.MAX_VALUE
//		9_223_372_036_854_775_807L || Long.MAX_VALUE
//		0x8000_0000_0000_0000L     || Long.MIN_VALUE
//		Long.MIN_VALUE             || Long.MIN_VALUE
//		// May be a bug in Spock or groovy?
//		// -9_223_372_036_854_775_808L || Long.MIN_VALUE
	}

}
