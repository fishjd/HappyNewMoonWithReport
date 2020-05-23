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

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.I64
import happynewmoonwithreport.type.S32
import spock.lang.Specification

/**
 *
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i64.wast#L47">
 *      WebAssembly Test Suite i64.wast
 * </a>

 */
class I64_subTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Execute I64_Sub"() {
		setup: "two values"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(val1));
		instance.stack().push(new I64(val2));

		I64_sub i64_sub = new I64_sub(instance);

		when: "run the opcode"
		i64_sub.execute();

		then: "get the result off the stack and verify it matches the expected value"

		new I64(expected) == instance.stack().pop();

		where: "Subtract"
		val1                   | val2                   || expected
		// Web Assembly Tests
		1                      | 1                      || 0
		1                      | 0                      || 1
		-1                     | -1                     || 0
		0x7FFF_FFFF_FFFF_FFFF  | -1                     || 0x8000_0000_0000_0000L
		0x8000_0000_0000_0000L | 1                      || 0x7FFF_FFFF_FFFF_FFFF
		0x8000_0000_0000_0000L | 0x8000_0000_0000_0000L || 0
		0x3FFF_FFFF            | -1                     || 0x4000_0000
		// Happy New Moon with Report Tests
		3                      | 4                      || -1
		4                      | 3                      || 1
		0x7FFF_FFFE            | 0x1                    || 0x7FFF_FFFD
		new S32(0).maxValue()  | 0x1                    || 0x7FFF_FFFE
	}


	def "Execute opcode I64_Sub throw exception on incorrect Type on second param "() {
		setup: " a value of int64  of 3  and a value of int32 of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(4));
		instance.stack().push(new I32(3)); // wrong type

		I64_sub function = new I64_sub(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.getUuid().toString().contains("d2fc139f-1481-4585-a291-0b72ca6fe253");
	}

	def "Execute opcode I64_Sub throw exception on incorrect Type on first param "() {
		setup: " a value of int32  of 3  and a value of int64 of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I32(4)); // wrong type
		instance.stack().push(new I64(3));

		I64_sub function = new I64_sub(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.getUuid().toString().contains("42dc1103-28a5-47ad-bb06-448bb05d8c54");
	}
}
