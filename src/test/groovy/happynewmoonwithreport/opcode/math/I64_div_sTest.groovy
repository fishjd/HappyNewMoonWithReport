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
import happynewmoonwithreport.WasmDivideOverflowException
import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.I64
import happynewmoonwithreport.type.S64
import spock.lang.Specification

/**
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i64.wast#L65">
 *      WebAssembly Test Suite i64.wast
 * </a>
 */
class I64_div_sTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	//@Unroll
	def "Execute I64_div_s val1 = #val1 val2 = #val2 expected = #expected"(Long val1, Long val2, Long expected) {
		// println(formatInteger('val1', val1))
		// println(formatInteger('val2', val2))
		// println(formatInteger('expected', expected))
		// println();

		setup: " given two values val1 and val2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S64(val1));
		instance.stack().push(new S64(val2));

		and: "an opcode"
		I64_div_s i64_div_s = new I64_div_s(instance);

		when: "run the opcode"
		i64_div_s.execute();

		then: "test the result to the expected value"
		new I64(expected) == instance.stack().pop();

		where: ""
		val1                  | val2                  || expected
		// Web Assembly Test
		1                     | 1                     || 1
		0                     | 1                     || 0
		0                     | -1                    || 0
		-1                    | -1                    || 1
		0x8000_0000_0000_0000 | 2                     || 0xC000_0000_0000_0000
		0x8000_0000_0000_0001 | 1000                  || 0xFFDF_3B64_5A1C_AC09
		5                     | 2                     || 2
		-5                    | 2                     || -2
		5                     | -2                    || -2
		-5                    | -2                    || 2
		7                     | 3                     || 2
		-7                    | 3                     || -2
		7                     | -3                    || -2
		-7                    | -3                    || 2
		11                    | 5                     || 2
		17                    | 7                     || 2
		// Happy New Moon tests
		0x8000_0000_0000_0000 | 0xFFFF_FFFF_FFFF_FFFE || 0x4000_0000_0000_0000   // Not divide overflow
		0x8000_0000_0000_0001 | 0xFFFF_FFFF_FFFF_FFFF || 0x7FFF_FFFF_FFFF_FFFF   // Not divide overflow

	}

	def "Execute I64_Div throws divide by zero exception"(Long val1, Long val2) {
		setup: " given two values val1 and val2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S64(val1));
		instance.stack().push(new S64(val2));

		and: "an opcode"
		I64_div_s i64_div_s = new I64_div_s(instance);

		when: "run the opcode"
		i64_div_s.execute();

		then: "Divide by zero exception "
		WasmDivideByZeroException exception = thrown();
		exception.getUuid().toString().contains("f2d3464a-f45d-4dbc-836a-6cd9020e138d");

		where: ""
		val1                  | val2
		// Web Assembly Test
		1                     | 0
		0                     | 0
		0x8000_0000_0000_0000 | 0

	}

	def "Execute I64_Div throws divide overflow exception"(Long val1, Long val2) {
		setup: " given two values val1 and val2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(val1));
		instance.stack().push(new I64(val2));

		I64_div_s i64_div_s = new I64_div_s(instance);

		when: "run the opcode"
		i64_div_s.execute()

		then: " a value of expected"
		WasmDivideOverflowException exception = thrown();
		exception.getUuid().toString().contains("3160d190-0508-4a79-91aa-f0f290f2f254");

		where: ""
		val1                  | val2
		// Web Assembly Test
		0x8000_0000_0000_0000 | -1                      // mixed hexadecimal | decimal
		0x8000_0000_0000_0000 | 0xFFFF_FFFF_FFFF_FFFF   // in hexadecimal

	}


	def "Execute opcode I64_div_s throw exception on incorrect Type on second param "() {
		setup: " a value of int64  of 3  and a value of int64 of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(4));
		instance.stack().push(new I32(3));  // wrong type

		I64_div_s function = new I64_div_s(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.getUuid() == UUID.fromString("a3dd4634-f7fa-41ca-a355-0611ed0bfca7")
	}

	def "Execute opcode I64_div_s throw exception on incorrect Type on first param "() {
		setup: " a value of int64  of 3  and a value of int64 of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I32(4));  // wrong type
		instance.stack().push(new I64(3));

		I64_div_s function = new I64_div_s(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.getUuid() == UUID.fromString("b92dc38f-c903-4eb4-a1ae-3e248181f23c")
	}
}
