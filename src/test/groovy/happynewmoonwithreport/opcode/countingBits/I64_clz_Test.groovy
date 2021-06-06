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

package happynewmoonwithreport.opcode.countingBits

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.I64
import spock.lang.Specification

/**
 * Test I64 count leading zeros.
 *
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i64.wast#LC246">
 *      WebAssembly Test Suite i64.wast
 * </a>
 */
class I64_clz_Test extends Specification {
	// CUT  Component/Class/Code under test
	I64_clz i64_clz;

	WasmInstanceInterface instance;

	void setup() {
		instance = new WasmInstanceStub();

		// create class to test.
		i64_clz = new I64_clz(instance.stack());
	}

	void cleanup() {
	}

	def "test execute"() {
		given: // An I64 value
		I64 value = new I64(input);

		// push on stack
		instance.stack().push(value);


		when:  // convert
		// execute
		i64_clz.execute();

		then:
		// verify
		I32 result = instance.stack().pop();
		new I32(expected) == result;

		where:
		input  /* Long **/     || expected  /* Integer **/
		0xFFFF_FFFF_FFFF_FFFFL || 0
		0                      || 64
		0x0000_8000L           || 48
		0x0000_00FFL           || 56
		0x8000_0000_0000_0000L || 0
		1                      || 63
		2                      || 62
		0x7FFF_FFFF_FFFF_FFFFL || 1
	}
}
