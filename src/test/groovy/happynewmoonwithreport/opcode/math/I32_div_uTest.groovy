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
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i32.wast#L85">
 *      WebAssembly Test Suite i32.wast
 * </a>
 */
class I32_div_uTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Execute I32_div_u"(Integer val1, Integer val2, Integer expected) {
		setup: " given two values val1 and val2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S32(val1));
		instance.stack().push(new S32(val2));

		I32_div_u function = new I32_div_u(instance);

		when: "run the opcode"
		function.execute();

		then: " a value of expected"

		new I32(expected) == instance.stack().pop();

		where: ""
		val1        | val2    || expected
		// Web Assembly Test
		1           | 1       || 1
		0           | 1       || 0
		-1          | -1      || 1
		0x8000_0000 | -1      || 0
		0x8000_0000 | 2       || 0x4000_0000
		0x8FF00FF0  | 0x10001 || 0x8FEF
		0x8000_0001 | 1000    || 0x20_C49B
		5           | 2       || 2
		-5          | 2       || 0x7FFFFFFD
		5           | -2      || 0
		-5          | -2      || 0
		7           | 3       || 2
		11          | 5       || 2
		17          | 7       || 2
		// Happy New Moon tests
		0           | -1      || 0

	}

	def "Execute I32_Div throws divide by zero exception"(Integer val1, Integer val2) {
		setup: " given two values val1 and val2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S32(val1));
		instance.stack().push(new S32(val2));

		I32_div_u function = new I32_div_u(instance);

		when: "run the opcode"
		function.execute();

		then: "a divide by zero exception is thrown "
		WasmDivideByZeroException exception = thrown();
		exception.getUuid().toString().contains("900174aa-3a9e-4a3a-b43e-3f5342aa867f");

		where: ""
		val1        | val2
		// Web Assembly Test
		1           | 0
		0           | 0
		0x8000_0000 | 0

	}

	def "Execute opcode I32_div_u throw exception on incorrect Type on second param "() {
		setup: " a value of int64  of 3  and a value of int32 of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S32(4));
		instance.stack().push(new S64(3));  // wrong type

		I32_div_u function = new I32_div_u(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.getUuid().toString().contains("019f337f-8297-4228-a81f-be816ae3de34");
	}

	def "Execute opcode I64_div_u throw exception on incorrect Type on first param "() {
		setup: " a value of int32  of 3  and a value of int64 of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S64(4));  // wrong type
		instance.stack().push(new S32(3));

		I32_div_u function = new I32_div_u(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.getUuid().toString().contains("aea63625-b6e3-41da-8d02-c1a39d559d0f");
	}
}
