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
import happynewmoonwithreport.opcode.math.I32_add
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.S32
import happynewmoonwithreport.type.S64
import spock.lang.Specification

/**
 * Created on 2017-08-25.
 *
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i32.wast#L37">
 *      WebAssembly Test Suite i32.wast
 * </a>
 *
 */
class I32_addTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Execute AddI32"(Integer val1, Integer val2, Integer expected) {
		setup: " a value of 3 and a value of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S32(val1));
		instance.stack().push(new S32(val2));

		I32_add i32_add = new I32_add(instance);

		when: "run the opcode"
		i32_add.execute();

		then: " a value of expected"

		new I32(expected) == instance.stack().pop()

		where: ""
		val1        | val2       || expected
		// Web Assembly Test Suite
		1           | 1           | 2
		1           | 0           | 1
		-1          | -1          | -2
		-1          | 1           | 0
		0x7FFF_FFFF | 1           | 0x8000_0000
		0x8000_0000 | -1          | 0x7FFF_FFFF
		0x8000_0000 | 0x8000_0000 | 0
		0x3FFF_FFFF | 1           | 0x4000_0000
		// Happy New Moon With Report tests
		3           | 4          || 7
		4           | 3          || 7
		0x7FFF_FFFE | 0x1        || 0x7FFF_FFFF
		0x7FFF_FFFE | 0x1        || new S32(0).maxValue();
	}


	def "Execute opcode AddI32 throw exception on incorrect Type on second param "() {
		setup: " a value of int64  of 3  and a value of int32 of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S32(4));
		instance.stack().push(new S64(3));

		I32_add i32_add = new I32_add(instance);

		when: "run the opcode"
		i32_add.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");  // not sure if this is the Wasm Spec. Maybe it should be "Value1"
		exception.getUuid().toString().contains("59c20edb-690b-4260-b5cf-704cd509ac07");
	}

	def "Execute AddI32 throw exception on incorrect Type on first param "() {
		setup: " a value of int32  of 3  and a value of int64 of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S64(4));
		instance.stack().push(new S32(3));

		I32_add i32_add = new I32_add(instance);

		when: "run the opcode"
		i32_add.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");  // not sure if this is the Wasm Spec. Maybe it should be "Value1"
		exception.getUuid().toString().contains("22500212-e077-4507-a27a-3a08039da2b7");
	}
}
