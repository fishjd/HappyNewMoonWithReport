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
import happynewmoonwithreport.type.S32
import happynewmoonwithreport.type.S64
import spock.lang.Specification

/**
 * Created on 2018-08-04.
 *
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i32.wast#L46">
 *      WebAssembly Test Suite i32.wast
 * </a>

 */
class I32_subTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Execute I32_Sub"() {
		setup: "two values"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S32(val1));
		instance.stack().push(new S32(val2));

		I32_sub i32_sub = new I32_sub(instance);

		when: "run the opcode"
		i32_sub.execute();

		then: "get the result off the stack and verify it matches the expected value"

		new I32(expected) == instance.stack().pop();

		where: "Subtract"
		val1                  | val2              || expected
		// Web Assembly Tests
		1                     | 1                 || 0
		1                     | 0                 || 1
		-1                    | -1                || 0
		0x7FFF_FFFF           | -1                || (int) 0x8000_0000
		(int) 0x8000_0000     | 1                 || 0x7FFF_FFFF
		(int) 0x8000_0000     | (int) 0x8000_0000 || 0
		0x3FFF_FFFF           | -1                || 0x4000_0000
		// Happy New Moon with Report Tests
		3                     | 4                 || -1
		4                     | 3                 || 1
		0x7FFF_FFFE           | 0x1               || 0x7FFF_FFFD
		new S32(0).maxValue() | 0x1               || 0x7FFF_FFFE
	}


	def "Execute opcode I32_Sub throw exception on incorrect Type on second param "() {
		setup: " a value of int64  of 3  and a value of int32 of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S32(4));
		instance.stack().push(new S64(3));

		I32_sub function = new I32_sub(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");  // not sure if this is the Wasm Spec. Maybe it should be "Value1"
		exception.getUuid().toString().contains("ed5b6703-894c-4d1e-8ddc-4aab7ed1f4dd");
	}

	def "Execute I32_Sub throw exception on incorrect Type on first param "() {
		setup: " a value of int32  of 3  and a value of int64 of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S64(4));
		instance.stack().push(new S32(3));

		I32_sub function = new I32_sub(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");  // not sure if this is the Wasm Spec. Maybe it should be "Value1"
		exception.getUuid().toString().contains("d259488c-e394-4c0c-9246-1882923fb352");
	}
}
