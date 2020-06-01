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
package happynewmoonwithreport.opcode.bitshift

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.I64
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Test I64_shlTest opcode.
 * <br>
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i64.wast#L170">
 *      WebAssembly Test Suite i64.wast
 * </a>
 *
 */
class I64_shlTest extends Specification {
	WasmInstanceInterface instance;

	void setup() {
		instance = new WasmInstanceStub();
	}

	//@Unroll
	def "I64_shl val1 = #val1 val2 = #val2 expected = #expected"(Long val1, Long val2, Long expected) {

		setup: "two values"
		instance.stack().push(new I64(val1));
		instance.stack().push(new I64(val2));

		and: "an opcode"
		I64_shl i64_shl = new I64_shl(instance);

		when: "run the opcode"
		i64_shl.execute();

		then: "verify the expected equals the return value"
		new I64(expected) == instance.stack().pop()

		where: "I64_shl"
		val1                  | val2                  || expected
		// Web Assembly Test Suite
		1                     | 1                     || 2
		1                     | 0                     || 1
		0x7FFF_FFFF_FFFF_FFFF | 1                     || 0xFFFF_FFFF_FFFF_FFFE
		0xFFFF_FFFF_FFFF_FFFF | 1                     || 0xFFFF_FFFF_FFFF_FFFE
		0x8000_0000_0000_0000 | 1                     || 0
		0x4000_0000_0000_0000 | 1                     || 0x8000_0000_0000_0000
		1                     | 63                    || 0x8000_0000_0000_0000
		1                     | 64                    || 1
		1                     | 65                    || 2
		1                     | -1                    || 0x8000_0000_0000_0000
		1                     | 0x7FFF_FFFF_FFFF_FFFF || 0x8000_0000_0000_0000
	}

	def "I64_shl throws exception on incorrect type on second parameter"() {
		setup: "two values, the second one is the wrong type"
		instance.stack().push(new I64(4));
		instance.stack().push(new I32(3));  // wrong type

		and: "an opcode"
		I64_shl i64_shl = new I64_shl(instance);

		when: "run the opcode"
		i64_shl.execute();

		then: "Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.message.contains("I64_shl");
		exception.message.contains("Value should be of type I64");
		exception.getUuid() == UUID.fromString("83d4883d-751b-4a04-a137-1b010d98a880");
	}

	def "I64_shl throws exception on incorrect type on first parameter"() {
		setup: "two values, the first one is the wrong type"
		instance.stack().push(new I32(4)); // wrong type
		instance.stack().push(new I64(3));

		and: "an opcode"
		I64_shl i64_shl = new I64_shl(instance);

		when: "run the opcode"
		i64_shl.execute();

		then: "Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.message.contains("I64_shl");
		exception.message.contains("Value should be of type I64");
		exception.getUuid() == UUID.fromString("c3f8cbf7-820f-49de-9297-2c85c8709ee0");
	}
}
