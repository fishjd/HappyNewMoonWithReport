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
package happynewmoonwithreport.opcode.logic

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.I64
import spock.lang.Specification

/**
 * Test I64_and opcode.
 * <br>
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i64.wast#L141">
 *      WebAssembly Test Suite i64.wast
 * </a>
 *
 */
class I64_andTest extends Specification {
	WasmInstanceInterface instance;

	void setup() {
		instance = new WasmInstanceStub();
	}

	// @Unroll
	def "I64_and val1 = #val1 val2 = #val2 expected = #expected"(Long val1, Long val2, Long expected) {

		setup: "two values"
		instance.stack().push(new I64(val1));
		instance.stack().push(new I64(val2));

		and: "an opcode"
		I64_and i64_and = new I64_and(instance);

		when: "run the opcode"
		i64_and.execute();

		then: "verify the expected equals the return value"
		new I64(expected) == instance.stack().pop()

		where: ""
		val1                  | val2                  || expected
		// Web Assembly Test Suite
		1                     | 0                     || 0
		0                     | 1                     || 0
		1                     | 1                     || 1
		0                     | 0                     || 0
		0x7FFF_FFFF_FFFF_FFFF | 0x8000_0000_0000_0000 || 0
		0x7FFF_FFFF_FFFF_FFFF | -1                    || 0x7FFF_FFFF_FFFF_FFFF
		0xF0F0_FFFF           | 0xFFFF_F0F0           || 0xF0F0_F0F0
		0xFFFF_FFFF_FFFF_FFFF | 0xFFFF_FFFF_FFFF_FFFF || 0xFFFF_FFFF_FFFF_FFFF
	}

	def "I64_and throws exception on incorrect type on second parameter"() {
		setup: "two values, the second one is the wrong type"
		instance.stack().push(new I64(4));
		instance.stack().push(new I32(3));  // wrong type

		and: "an opcode"
		I64_and i64_and = new I64_and(instance);

		when: "run the opcode"
		i64_and.execute();

		then: "Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.message.contains("I64_and");
		exception.message.contains("Value should be of type I64");
		exception.getUuid() == UUID.fromString("c5fd26a6-54fe-44e9-b8d8-b7ac53223f49");
	}

	def "I64_and throws exception on incorrect type on first parameter"() {
		setup: "two values, the first one is the wrong type"
		instance.stack().push(new I32(4)); // wrong type
		instance.stack().push(new I64(3));

		and: "an opcode"
		I64_and i64_and = new I64_and(instance);

		when: "run the opcode"
		i64_and.execute();

		then: "Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.message.contains("I64_and");
		exception.message.contains("Value should be of type I64");
		exception.getUuid() == UUID.fromString("6215c6ee-fdd1-42b6-9539-8cefd137f77c");
	}
}
