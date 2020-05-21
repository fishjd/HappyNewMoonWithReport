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

package happynewmoonwithreport.opcode.countingBits

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.type.I32
import spock.lang.Specification

/**
 * Test I32 count trailing zeros.
 *
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i32.wast#LC261">
 *      WebAssembly Test Suite i32.wast
 * </a>
 */
class I32_popcnt_Test extends Specification {
	// CUT  Component/Class/Code under test
	I32_popcnt i32_popcnt;

	WasmInstanceInterface instance;

	void setup() {
		instance = new WasmInstanceStub();

		// create class to test.
		i32_popcnt = new I32_popcnt(instance.stack());
	}

	void cleanup() {
	}

	def "test execute"() {
		// println("Input = " + input + " expected = " + expected);

		given: // An I32 value
		I32 value = new I32(input);

		// push on stack
		instance.stack().push(value);


		when:  // convert
		// execute
		i32_popcnt.execute();

		then:
		// verify
		I32 result = instance.stack().pop();
		result.integerValue() == expected;
		result == new I32(expected);

		where:
		input             || expected
		// Tests from the WebAssembly test suite
		-1                || 32
		0                 || 0
		(int) 0x0000_8000 || 1
		(int) 0x8000_8000 || 2
		0x7FFF_FFFF       || 31
		(int) 0xAAAA_AAAA || 16
		0x5555_5555       || 16
		(int) 0xDEAD_BEEF || 24  // lol
		// Additional tests
		0x0000_00FF       || 8
		1                 || 1
		2                 || 1
		0x0001_0000       || 1
		100               || 3


	}
}
