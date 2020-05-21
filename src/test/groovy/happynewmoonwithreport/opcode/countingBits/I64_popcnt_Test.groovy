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
import happynewmoonwithreport.type.I64
import spock.lang.Specification

/**
 * Test I64 count trailing zeros.
 *
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i64.wast#LC262">
 *      WebAssembly Test Suite i64.wast
 * </a>
 */
class I64_popcnt_Test extends Specification {
	// CUT  Component/Class/Code under test
	I64_popcnt i64_popcnt;

	WasmInstanceInterface instance;

	void setup() {
		instance = new WasmInstanceStub();

		// create class to test.
		i64_popcnt = new I64_popcnt(instance.stack());
	}

	void cleanup() {
	}

	def "test execute"() {
		// println("Input = " + input + " expected = " + expected);

		given: // An I64 value
		I64 value = new I64(input);

		// push on stack
		instance.stack().push(value);


		when:  // convert
		// execute
		i64_popcnt.execute();

		then:
		// verify
		I32 result = instance.stack().pop();
		result.integerValue() == expected;
		result == new I32(expected);

		where:
		input                  || expected
		// Tests from the WebAssembly test suite
		-1                     || 64
		0                      || 0
		0x0000_8000            || 1
		0x8000_8000_8000_8000L || 4
		0x7FFF_FFFF_FFFF_FFFF  || 63
		0xAAAA_AAAA_5555_5555L || 32
		0x9999_9999_AAAA_AAAAL || 32
		0xDEAD_BEEF_DEAD_BEEFL || 48  // lol
		// Additional tests
		0x0000_00FF            || 8
		1                      || 1
		2                      || 1
		0x0001_0000            || 1
		100                    || 3


	}
}
