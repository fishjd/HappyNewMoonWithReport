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
package happynewmoonwithreport.opcode.bitshift

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.I64
import spock.lang.Specification

/**
 * Test I32_rotlTest opcode.
 * <br>
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i32.wast#L217">
 *      WebAssembly Test Suite i32.wast
 * </a>
 *
 */
class I32_rotlTest extends Specification {
	WasmInstanceInterface instance;

	void setup() {
		instance = new WasmInstanceStub();
	}

	// @Unroll
	def "I32_rotl val1 = #val1 val2 = #val2 expected = #expected"(Integer val1, Integer val2, Integer expected) {

		setup: "two values"
		instance.stack().push(new I32(val1));
		instance.stack().push(new I32(val2));

		and: "an opcode"
		I32_rotl i32_rotl = new I32_rotl(instance);

		when: "run the opcode"
		i32_rotl.execute();

		then: "verify the expected equals the return value"
		new I32(expected) == instance.stack().pop()

		where: ""
		val1        | val2       || expected
		// Web Assembly Test Suite
		1           | 1          || 2
		1           | 0          || 1
		-1          | 1          || -1
		0xABCD_9876 | 1          || 0x579b_30ed
		0xFE00_DC00 | 4          || 0xe00d_c00f
		0xB0C1_D2E3 | 5          || 0x183a_5c76
		0x0000_8000 | 37         || 0x0010_0000
		0xB0C1_D2E3 | 0xff05     || 0x183a_5c76
		0x769A_BCDF | 0xffffffed || 0x579b_eed3
		0x769A_BCDF | 0x8000000d || 0x579b_eed3
		1           | 31         || 0x8000_0000
		0x8000_0000 | 1          || 1
	}

	def "I32_rotl throws exception on incorrect type on second parameter"() {
		setup: "two values, the second one is the wrong type"
		instance.stack().push(new I32(4));
		instance.stack().push(new I64(3));  // wrong type

		and: "an opcode"
		I32_rotl i32_rotl = new I32_rotl(instance);

		when: "run the opcode"
		i32_rotl.execute();

		then: "Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.message.contains("I32_rotl");
		exception.message.contains("Value should be of type I32");
		exception.getUuid() == UUID.fromString("7632477d-f6fe-4a19-86f2-3a11c10e4a34");
	}

	def "I32_rotl throws exception on incorrect type on first parameter"() {
		setup: "two values, the first one is the wrong type"
		instance.stack().push(new I64(4)); // wrong type
		instance.stack().push(new I32(3));

		and: "an opcode"
		I32_rotl i32_rotl = new I32_rotl(instance);

		when: "run the opcode"
		i32_rotl.execute();

		then: "Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.message.contains("I32_rotl");
		exception.message.contains("Value should be of type I32");
		exception.getUuid() == UUID.fromString("1c59edee-2bd2-4655-b7e4-97cf0b67b99c");
	}
}
