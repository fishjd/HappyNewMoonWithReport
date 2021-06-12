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
package happynewmoonwithreport.opcode.math

import happynewmoonwithreport.WasmDivideByZeroException
import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.S32
import happynewmoonwithreport.type.S64
import spock.lang.Specification

/**
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i32.wast#L102">
 *      WebAssembly Test Suite i32.wast
 * </a>
 */
class I32_rem_sTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	//@Unroll
	def "Execute I32_rem_s val1 = #val1 val2 = #val2 expected = #expected "(Integer val1, Integer val2, Integer expected) {
//		println(formatInteger('val1', val1))
//		println(formatInteger('val2', val2))
//		println(formatInteger('expected', expected))
//		println();

		setup: " given two values val1 and val2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S32(val1));
		instance.stack().push(new S32(val2));

		Integer.toHexString(val1)

		I32_rem_s i32_rem_s = new I32_rem_s(instance);

		when: "run the opcode"
		i32_rem_s.execute();

		then: " a value of expected"
		new I32(expected) == instance.stack().pop();

		where: ""
		val1        | val2        || expected
		// Web Assembly Test
		0x7FFF_FFFF | -1          || 0
		1           | 1           || 0
		0           | 1           || 0
		0           | -1          || 0
		-1          | -1          || 0
		0x8000_0000 | -1          || 0
		0x8000_0000 | 2           || 0
		0x8000_0001 | 1000        || -647
		5           | 2           || 1
		-5          | 2           || -1
		5           | -2          || 1
		-5          | -2          || -1
		7           | 3           || 1
		-7          | 3           || -1
		7           | -3          || 1
		-7          | -3          || -1
		11          | 5           || 1
		17          | 7           || 3
		// Happy New Moon tests
		0x8000_0000 | 0xFFFF_FFFE || 0   // Not divide overflow
		0x8000_0001 | 0xFFFF_FFFF || 0   // Not divide overflow

	}

	def "Execute I32_rem throws divide by zero exception"(Integer val1, Integer val2) {
		setup: " given two values val1 and val2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S32(val1));
		instance.stack().push(new S32(val2));

		I32_rem_s i32_rem_s = new I32_rem_s(instance);

		when: "run the opcode"
		i32_rem_s.execute();

		then: " a value of expected"
		WasmDivideByZeroException exception = thrown();
		exception.getUuid().toString().contains("5b00bedc-f56e-4026-aa70-8ad526c71faa");

		where: ""
		val1        | val2
		// Web Assembly Test
		1           | 0
		0           | 0
		// Happy New Moon with Report Tests
		0x8000_0000 | 0

	}

	def "Execute opcode I32_rem_s throw exception on incorrect Type on second param "() {
		setup: " a value of int64  of 3  and a value of int32 of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S32(4));
		instance.stack().push(new S64(3));  // wrong type

		I32_rem_s function = new I32_rem_s(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.getUuid().toString().contains("48441ff5-2e20-4e67-bbed-365b251b19e7");
	}

	def "Execute opcode I64_rem_s throw exception on incorrect Type on first param "() {
		setup: " a value of int32  of 3  and a value of int64 of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S64(4));  // wrong type
		instance.stack().push(new S32(3));

		I32_rem_s function = new I32_rem_s(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.getUuid().toString().contains("625d6b4e-3586-4d51-8534-62cfa8df058b");
	}
}
