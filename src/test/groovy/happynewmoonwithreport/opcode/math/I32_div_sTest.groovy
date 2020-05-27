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
import happynewmoonwithreport.type.S32
import happynewmoonwithreport.type.S64
import spock.lang.Specification

/**
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i32.wast#L64">
 *      WebAssembly Test Suite i32.wast
 * </a>
 */
class I32_div_sTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Execute I32_div_s"() {
		setup: " given two values val1 and val2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S32(val1));
		instance.stack().push(new S32(val2));

		I32_div_s function = new I32_div_s(instance);

		when: "run the opcode"
		function.execute();

		then: " a value of expected"

		new I32(expected) == instance.stack().pop();

		where: ""
		val1              | val2              || expected
		// Web Assembly Test
		1                 | 1                 || 1
		0                 | 1                 || 0
		0                 | -1                || 0
		-1                | -1                || 1
		(int) 0x8000_0000 | 2                 || (int) 0xC000_0000
		(int) 0x8000_0001 | 1000              || (int) 0xFFDF3B65
		5                 | 2                 || 2
		-5                | 2                 || -2
		5                 | -2                || -2
		-5                | -2                || 2
		7                 | 3                 || 2
		-7                | 3                 || -2
		7                 | -3                || -2
		-7                | -3                || 2
		11                | 5                 || 2
		17                | 7                 || 2
		// Happy New Moon tests
		(int) 0x8000_0000 | (int) 0xFFFF_FFFE || 0x4000_0000   // Not divide overflow
		(int) 0x8000_0001 | (int) 0xFFFF_FFFF || 0x7FFF_FFFF   // Not divide overflow

	}

	def "Execute I32_Div throws divide by zero exception"() {
		setup: " given two values val1 and val2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S32(val1));
		instance.stack().push(new S32(val2));

		I32_div_s function = new I32_div_s(instance);

		when: "run the opcode"
		function.execute();

		then: " a value of expected"
		thrown(WasmDivideByZeroException)

		where: ""
		val1              | val2
		// Web Assembly Test
		1                 | 0
		0                 | 0
		(int) 0x8000_0000 | 0

	}

	def "Execute I32_Div throws divide overflow exception"() {
		setup: " given two values val1 and val2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I32(val1));
		instance.stack().push(new I32(val2));

		I32_div_s i32_div_s = new I32_div_s(instance);

		when: "run the opcode"
		i32_div_s.execute()

		then: " a value of expected"
		thrown(WasmDivideOverflowException)

		where: ""
		val1              | val2
		// Web Assembly Test
		(int) 0x8000_0000 | -1                  // mixed hexadecimal | decimal
		(int) -2147483648 | -1                  // in decimal
		(int) 0x8000_0000 | (int) 0xFFFF_FFFF   // in hexadecimal

	}


	def "Execute opcode I32_div_s throw exception on incorrect Type on second param "() {
		setup: " a value of int64  of 3  and a value of int32 of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S32(4));
		instance.stack().push(new S64(3));  // wrong type

		I32_div_s function = new I32_div_s(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.getUuid().toString().contains("12a6126e-d632-4486-94a5-20f9c974c1de");
	}

	def "Execute opcode I64_div_s throw exception on incorrect Type on first param "() {
		setup: " a value of int32  of 3  and a value of int64 of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S64(4));  // wrong type
		instance.stack().push(new S32(3));

		I32_div_s function = new I32_div_s(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.getUuid().toString().contains("099049ec-f319-4d6b-9e6f-64e66467bc45");
	}
}
