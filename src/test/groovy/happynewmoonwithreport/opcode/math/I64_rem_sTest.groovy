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
import happynewmoonwithreport.type.S64
import spock.lang.Specification
import spock.lang.Unroll

import static happynewmoonwithreport.UtilHappy.formatInteger

/**
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i64.wast#L103">
 *      WebAssembly Test Suite i64.wast
 * </a>
 */
class I64_rem_sTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	//@Unroll   // @Unroll is good for debugging.
	def "Execute I64_rem_s val1 = #val1 val2 = #val2 expected = #expected "(Long val1, Long val2, Long expected) {
		// println(formatInteger('val1', val1))
		// println(formatInteger('val2', val2))
		// println(formatInteger('expected', expected))

		setup: " given two values val1 and val2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S64(val1));
		instance.stack().push(new S64(val2));

		and: "an opcode"
		I64_rem_s i64_rem_s = new I64_rem_s(instance);

		when: "run the opcode"
		i64_rem_s.execute();

		then: "verify the return value vs the expected"
		new I64(expected) == instance.stack().pop();

		where: ""
		val1                  | val2                  || expected
		// Web Assembly Test
		0x7FFF_FFFF_FFFF_FFFF | -1                    || 0
		1                     | 1                     || 0
		0                     | 1                     || 0
		0                     | -1                    || 0
		-1                    | -1                    || 0
		0x8000_0000_0000_0000 | -1                    || 0
		0x8000_0000_0000_0000 | 2                     || 0
		0x8000_0000_0000_0001 | 1000                  || -807
		5                     | 2                     || 1
		-5                    | 2                     || -1
		5                     | -2                    || 1
		-5                    | -2                    || -1
		7                     | 3                     || 1
		-7                    | 3                     || -1
		7                     | -3                    || 1
		-7                    | -3                    || -1
		11                    | 5                     || 1
		17                    | 7                     || 3
		// Happy New Moon tests
		1                     | 0xFFFF_FFFF_FFFF_FFFF || 0   // Not divide overflow
		1                     | 1                     || 0   // Not divide overflow

	}

	def "Execute I64_rem throws divide by zero exception"() {
		setup: " given two values val1 and val2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S64(val1));
		instance.stack().push(new S64(val2));

		I64_rem_s i64_rem_s = new I64_rem_s(instance);

		when: "run the opcode"
		i64_rem_s.execute();

		then: " a value of expected"
		WasmDivideByZeroException exception = thrown();
		exception.getUuid().toString().contains("d4fee389-19bc-4c46-9de6-765490991d78");

		where: ""
		val1        | val2
		// Web Assembly Test
		1           | 0
		0           | 0
		// Happy New Moon with Report Tests
		0x8000_0000 | 0

	}

	def "Execute opcode I64_rem_s throw exception on incorrect Type on second param "() {
		setup: " a value of int64  of 3  and a value of int64 of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(4));
		instance.stack().push(new I32(3));  // wrong type

		I64_rem_s function = new I64_rem_s(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.getUuid().toString().contains("b02e2bbb-0127-4969-a61d-39fb9734a2e1");
	}

	def "Execute opcode I64_rem_s throw exception on incorrect Type on first param "() {
		setup: " a value of int64  of 3  and a value of int64 of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I32(4));  // wrong type
		instance.stack().push(new I64(3));

		I64_rem_s function = new I64_rem_s(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.getUuid().toString().contains("b18fe95a-f745-4277-b0b0-703080bda5d6");
	}
}
