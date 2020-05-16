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

package happynewmoonwithreport.opcode.convert

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.type.I64
import spock.lang.Specification

/**
 * Test I64 extend 32 signed.
 *
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i64.wast#LC287">
 *      WebAssembly Test Suite i64.wast
 * </a> */
class I64_extend32_s_Test extends Specification {
	// CUT  Component/Class/Code under test
	I64_extend32_s i64_extend32_s;

	WasmInstanceInterface instance;

	void setup() {
		instance = new WasmInstanceStub();

		// create class to test.
		i64_extend32_s = new I64_extend32_s(instance);
	}

	void cleanup() {
	}

	def "test execute"() {
		given: // An I64 value
		I64 value = new I64(input0);

		// push on stack
		instance.stack().push(value);

		when:  // convert
		// execute
		i64_extend32_s.execute();

		then:
		// verify
		I64 result = instance.stack().pop();
		new I64(expected) == result;

		where:
		input0              || expected
		-100                || -100
		0                   || 0
		100                 || 100
		0x7FFF              || 32767
		0x8000              || 32768
		0xFFFF              || 65535
		0x8000_0000         || -2147483648
		0x8000_0000         || 0xFFFF_FFFF_8000_0000L
		0x7FFF_FFFF         || 2147483647
		0x7FFF_FFFF         || 0x0000_0000_7FFF_FFFFL
		0xFFFF_FFFF         || -1L
		0x01234567_00000000 || 0
		0xfedcba98_80000000 || -0x80000000
		-1                  || -1
		1                   || 1
		Integer.MIN_VALUE   || -2147483648
		Integer.MAX_VALUE   || 2147483647


	}
}
