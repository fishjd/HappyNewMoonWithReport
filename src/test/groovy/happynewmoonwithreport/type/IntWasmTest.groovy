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

class IntWasmTest extends Specification {

	/**
	 * <p>
	 *
	 * Cool Site
	 * <b>Source of test data:</b>  <a href="https://github.com/WebAssembly/testsuite/blob/master/i64.wast" target="_top">
	 * https://github.com/WebAssembly/testsuite/blob/master/64.wast
	 * </a>
	 *
	 */
	def "SignExtend8To32"() {
		setup:

		ByteUnsigned input = new ByteUnsigned(input0);

		when: " extend to signed I32"
		int actual = IntWasm.signExtend8To32(input);


		then: "Test"
		expected == actual;


		where: ""
		input0      || expected
		0           || 0
		100         || 100
		0xFF        || -1
		0XEF        || -17
		(byte) -100 || -100


	}

	/**
	 * <p>
	 *
	 * Cool Site:  <b>Web Assembly Test Suite</b></br>
	 * <b>Source of test data:</b>  <a href="https://github.com/WebAssembly/testsuite/blob/master/i64.wast" target="_top">
	 * https://github.com/WebAssembly/testsuite/blob/master/i64.wast
	 * </a>
	 *
	 */
	def "SignExtend16To64"() {
		setup:

		Long input = new Long(input0);

		when: " extend to signed I32"
		int actual = IntWasm.signExtend16To64(input);


		then: "Test"
		expected == actual;


		where: ""
		input0                       || expected
		0                            || 0
		100                          || 100
		0x7FFF                       || 32767
		0x8000                       || -32768
		0xFFFF                       || -1
		(Long) 0x1234_5678_9abc_0000 || 0
		(Long) 0xfedc_ba98_7654_8000 || -0x8000
		-1                           || -1
	}
}
