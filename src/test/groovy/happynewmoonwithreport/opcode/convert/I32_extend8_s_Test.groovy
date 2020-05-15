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
import happynewmoonwithreport.type.I32
import spock.lang.Specification

/**
 * Test I32 extend 8 signed.
 *
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/master/i32.wast#LC270">
 *     https://github.com/WebAssembly/testsuite/blob/master/i32.wast
 * </a>
 */
class I32_extend8_s_Test extends Specification {
	// CUT  Component/Class/Code under test
	I32_extend8_s i64_extend8_s;

	WasmInstanceInterface instance;

	void setup() {
		instance = new WasmInstanceStub();

		// create class to test.
		i64_extend8_s = new I32_extend8_s(instance);
	}

	void cleanup() {
	}

	def "test execute"() {
		given: // An I32 value
		I32 value = new I32(input0);

		// push on stack
		instance.stack().push(value);


		when:  // convert
		// execute
		i64_extend8_s.execute();

		then:
		// verify
		I32 result = instance.stack().pop();
		new I32(expected) == result;

		where:
		input0            || expected
		0                 || 0
		1                 || 1
		0x0000_007F       || 127
		0x0000_0080       || -128
		0x0000_00FF       || -1
		0x0123_4500       || 0
		(int) 0xfedc_ba80 || -0x80
		-1                || -1
		100               || 100
		-100              || -100
	}
}
