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

package happynewmoonwithreport.opcode.convert

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.type.I64
import spock.lang.Specification

/**
 * Test I64 extend 16 signed.
 *
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i64.wast#LC278">
 *      WebAssembly Test Suite i64.wast
 * </a> */
class I64_extend16_s_Test extends Specification {
	// CUT  Component/Class/Code under test
	I64_extend16_s i64_extend16_s;

	WasmInstanceInterface instance;

	void setup() {
		instance = new WasmInstanceStub();

		// create class to test.
		i64_extend16_s = new I64_extend16_s(instance);
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
		i64_extend16_s.execute();

		then:
		// verify
		I64 result = instance.stack().pop();
		new I64(expected) == result;

		where:
		input0            || expected
		0                 || 0
		1                 || 1
		0x0000_7FFF       || 32767
		(int) 0x0000_8000 || -32768
		0x0000_FFFF       || -1
		0x0123_0000       || 0
		(int) 0xfedc_8000 || -0x8000
		-1                || -1
		100               || 100
		-100              || -100
	}
}
