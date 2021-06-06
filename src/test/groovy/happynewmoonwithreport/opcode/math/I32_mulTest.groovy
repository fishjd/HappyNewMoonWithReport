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

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.type.S32
import happynewmoonwithreport.type.S64
import spock.lang.Specification

/**
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i32.wast#L54">
 *      WebAssembly Test Suite i32.wast
 * </a>
 */
class I32_mulTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Execute I32_Mul"(Integer val1, Integer val2, Integer expected) {
		setup: " given two values val1 and val2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S32(val1));
		instance.stack().push(new S32(val2));

		I32_mul function = new I32_mul(instance);

		when: "run the opcode"
		function.execute();

		then: " a value of expected"

		new S32(expected) == instance.stack().pop();

		where: ""
		val1        | val2        || expected
		// Web Assembly Test
		1           | 1           || 1
		1           | 0           || 0
		-1          | -1          || 1
		0x1000_0000 | 4096        || 0
		0x8000_0000 | 0           || 0
		0x8000_0000 | -1          || 0x8000_0000
		0x7FFF_FFFF | -1          || 0x8000_0001
		0x0123_4567 | 0x7654_3210 || 0x358e7470
		0x7FFF_FFFF | 0x7FFF_FFFF || 1
		// Happy New Moon tests
		3           | 4           || 12
		3           | 0           || 0
		4           | 3           || 12
		0x7FFF_FFFE | 0x1         || 0x7FFF_FFFE

	}

	def "Execute opcode I32_Mul throw exception on incorrect Type on second param "() {
		setup: " a value of int64  of 3  and a value of int32 of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S32(4));
		instance.stack().push(new S64(3));

		I32_mul function = new I32_mul(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.getUuid().toString().contains("847fe99b-56ea-407c-ac94-1cf13c1936f1");
	}

	def "Execute opcode I64_Mul throw exception on incorrect Type on first param "() {
		setup: " a value of int32  of 3  and a value of int64 of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S64(4));
		instance.stack().push(new S32(3));

		I32_mul function = new I32_mul(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.getUuid().toString().contains("e1433c51-da9f-4c43-a9fe-90ba1d84e56b");
	}
}
