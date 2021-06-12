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
 * Test I32_shr_uTest opcode.
 * <br>
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i32.wast#L199">
 *      WebAssembly Test Suite i32.wast
 * </a>
 *
 */
class I32_shr_uTest extends Specification {
	WasmInstanceInterface instance;

	void setup() {
		instance = new WasmInstanceStub();
	}

	// @Unroll
	def "I32_shr_u val1 = #val1 val2 = #val2 expected = #expected"(Integer val1, Integer val2, Integer expected) {

		setup: "two values"
		instance.stack().push(new I32(val1));
		instance.stack().push(new I32(val2));

		and: "an opcode"
		I32_shr_u i32_shr_u = new I32_shr_u(instance);

		when: "run the opcode"
		i32_shr_u.execute();

		then: "verify the expected equals the return value"
		new I32(expected) == instance.stack().pop()

		where: ""
		val1        | val2        || expected
		// Web Assembly Test Suite
		1           | 1           || 0
		1           | 0           || 1
		-1          | 1           || 0x7FFF_FFFF
		0x7FFF_FFFF | 1           || 0x3FFF_FFFF
		0x8000_0000 | 1           || 0x4000_0000
		0x4000_0000 | 1           || 0x2000_0000
		1           | 32          || 1
		1           | 33          || 0
		1           | -1          || 0
		1           | 0x7FFF_FFFF || 0
		1           | 0x8000_0000 || 1
		0x8000_0000 | 31          || 1
		-1          | 32          || -1
		-1          | 33          || 0x7FFF_FFFF
		-1          | -1          || 1
		-1          | 0x7FFF_FFFF || 1
		-1          | 0x8000_0000 || -1
	}

	def "I32_shr_u throws exception on incorrect type on second parameter"() {
		setup: "two values, the second one is the wrong type"
		instance.stack().push(new I32(4));
		instance.stack().push(new I64(3));  // wrong type

		and: "an opcode"
		I32_shr_u i32_shr_u = new I32_shr_u(instance);

		when: "run the opcode"
		i32_shr_u.execute();

		then: "Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.message.contains("I32_shr_u");
		exception.message.contains("Value should be of type I32");
		exception.getUuid() == UUID.fromString("42b9e53c-0c4a-4fdc-8f1e-2a6c618d3cef");
	}

	def "I32_shr_u throws exception on incorrect type on first parameter"() {
		setup: "two values, the first one is the wrong type"
		instance.stack().push(new I64(4)); // wrong type
		instance.stack().push(new I32(3));

		and: "an opcode"
		I32_shr_u i32_shr_u = new I32_shr_u(instance);

		when: "run the opcode"
		i32_shr_u.execute();

		then: "Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.message.contains("I32_shr_u");
		exception.message.contains("Value should be of type I32");
		exception.getUuid() == UUID.fromString("dce4370c-c5ba-48ea-9d8e-9699fcf41d43");
	}
}
