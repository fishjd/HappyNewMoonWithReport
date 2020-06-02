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
package happynewmoonwithreport.opcode.math

import happynewmoonwithreport.WasmDivideByZeroException
import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.I64
import happynewmoonwithreport.type.S32
import happynewmoonwithreport.type.S64
import spock.lang.Specification

/**
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i64.wast#L124">
 *      WebAssembly Test Suite i64.wast
 * </a>
 */
class I64_rem_uTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	// @Unroll
	def "Execute I64_rem_u val1 = #val1 val2 = #val2 expected = #expected "(Long val1, Long val2, Long expected) {
//		println(formatInteger('val1', val1))
//		println(formatInteger('val2', val2))
//		println(formatInteger('expected', expected))
//		println();

		setup: " given two values val1 and val2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S64(val1));
		instance.stack().push(new S64(val2));

		and: "an opcode"
		I64_rem_u i64_rem_u = new I64_rem_u(instance);

		when: "run the opcode"
		i64_rem_u.execute();

		then: "verify the result equals the expected value"
		new I64(expected) == instance.stack().pop();

		where: ""
		val1                  | val2                  || expected
		// Web Assembly Test
		1                     | 1                     || 0
		0                     | 1                     || 0
		-1                    | -1                    || 0
		0x8000_0000_0000_0000 | -1                    || 0x8000_0000_0000_0000
		0x8000_0000_0000_0000 | 2                     || 0
		0x8FF0_0FF0_0FF0_0FF0 | 0x1_0000_0001         || 0x80000001
		0x8000_0000_0000_0001 | 1000                  || 809
		5                     | 2                     || 1
		-5                    | 2                     || 1
		5                     | -2                    || 5
		-5                    | -2                    || -5
		7                     | 3                     || 1
		11                    | 5                     || 1
		17                    | 7                     || 3
		// Happy New Moon tests
		1                     | 1                     || 0   // Not divide overflow
		1                     | 0xFFFF_FFFF_FFFF_FFFF || 1   // Not divide overflow

	}

	def "Execute I64_rem throws divide by zero exception"(Long val1, Long val2) {
		setup: " given two values val1 and val2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S64(val1));
		instance.stack().push(new S64(val2));

		I64_rem_u i64_rem_u = new I64_rem_u(instance);

		when: "run the opcode"
		i64_rem_u.execute();

		then: " a value of expected"
		WasmDivideByZeroException exception = thrown();
		exception.getUuid().toString().contains("668d8f31-6e66-4226-ba20-4d2dacafe3c9");

		where: ""
		val1              | val2
		// Web Assembly Test
		1                 | 0
		0                 | 0
		// Happy New Moon with Report Tests
		0x8000_0000_0000_0000 | 0

	}

	def "Execute opcode I64_rem_u throw exception on incorrect Type on second param "() {
		setup: " a value of int64  of 3  and a value of int64 of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(4));
		instance.stack().push(new I32(3));  // wrong type

		I64_rem_u i64_rem_u = new I64_rem_u(instance);

		when: "run the opcode"
		i64_rem_u.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.getUuid().toString().contains("5a5e465e-a559-4240-a4bf-052cb519ee8b");
	}

	def "Execute opcode I64_rem_s throw exception on incorrect Type on first param "() {
		setup: " a value of int64  of 3  and a value of int64 of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S32(4));  // wrong type
		instance.stack().push(new S64(3));

		I64_rem_u i64_rem_u = new I64_rem_u(instance);

		when: "run the opcode"
		i64_rem_u.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.getUuid().toString().contains("6c6c8160-c566-461d-9ae9-76e64b81e97b");
	}
}
