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
package happynewmoonwithreport.opcode.logic

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.I64
import spock.lang.Specification

/**
 * Test I32_or opcode.
 * <br>
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i32.wast#L149">
 *      WebAssembly Test Suite i32.wast
 * </a>
 *
 */
class I32_orTest extends Specification {
	WasmInstanceInterface instance;

	void setup() {
		instance = new WasmInstanceStub();
	}

	// @Unroll
	def "I32_or val1 = #val1 val2 = #val2 expected = #expected"(Integer val1, Integer val2, Integer expected) {

		setup: "two values"
		instance.stack().push(new I32(val1));
		instance.stack().push(new I32(val2));

		and: "an opcode"
		I32_or i32_or = new I32_or(instance);

		when: "run the opcode"
		i32_or.execute();

		then: "verify the expected equals the return value"
		new I32(expected) == instance.stack().pop()

		where: ""
		val1        | val2        || expected
		// Web Assembly Test Suite
		1           | 0           || 1
		0           | 1           || 1
		1           | 1           || 1
		0           | 0           || 0
		0x7FFF_FFFF | 0x8000_0000 || -1
		0x8000_0000 | 0           || 0x8000_0000
		0xF0F0_FFFF | 0xFFFF_F0F0 || 0xFFFF_FFFF
		0xFFFF_FFFF | 0xFFFF_FFFF || 0xFFFF_FFFF
	}

	def "I32_or throws exception on incorrect type on second parameter"() {
		setup: "two values, the second one is the wrong type"
		instance.stack().push(new I32(4));
		instance.stack().push(new I64(3));  // wrong type

		and: "an opcode"
		I32_or i32_or = new I32_or(instance);

		when: "run the opcode"
		i32_or.execute();

		then: "Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.message.contains("I32_or");
		exception.message.contains("Value should be of type I32");
		exception.getUuid() == UUID.fromString("bb037b9f-32a7-448a-a594-618e7f22c0e3");
	}

	def "I32_or throws exception on incorrect type on first parameter"() {
		setup: "two values, the first one is the wrong type"
		instance.stack().push(new I64(4)); // wrong type
		instance.stack().push(new I32(3));

		and: "an opcode"
		I32_or i32_or = new I32_or(instance);

		when: "run the opcode"
		i32_or.execute();

		then: "Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.message.contains("I32_or");
		exception.message.contains("Value should be of type I32");
		exception.getUuid() == UUID.fromString("678d01a3-cf7d-4492-9dec-04e931e4b574");
	}
}
