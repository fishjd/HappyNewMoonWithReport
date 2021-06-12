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
import happynewmoonwithreport.opcode.math.I64_add
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.I64
import happynewmoonwithreport.type.S64
import spock.lang.Specification

/**
 * Created on 2017-08-25.
 *
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i64.wast#L38">
 *      WebAssembly Test Suite i64.wast
 * </a>
 *
 */
class I64_addTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Execute AddI64"(Long val1, Long val2, Long expected) {
		setup: " a value of 3 and a value of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new S64(val1));
		instance.stack().push(new S64(val2));

		I64_add i64_add = new I64_add(instance);

		when: "run the opcode"
		i64_add.execute();

		then: " a value of expected"

		new I64(expected) == instance.stack().pop()

		where: ""
		val1                  | val2                  || expected
		// Web Assembly Test Suite
		1                     | 1                     || 2
		1                     | 0                     || 1
		-1                    | -1                    || -2
		-1                    | 1                     || 0
		0x7FFF_FFFF_FFFF_FFFF | 1                     || 0x8000_0000_0000_0000
		0x8000_0000_0000_0000 | -1                    || 0x7FFF_FFFF_FFFF_FFFF
		0x8000_0000_0000_0000 | 0x8000_0000_0000_0000 || 0
		0x3FFF_FFFF           | 1                     || 0x4000_0000
		// Happy New Moon With Report  tests
		3                     | 4                     || 7
		4                     | 3                     || 7
		0x7FFF_FFFE           | 0x1                   || 0x7FFF_FFFF
	}


	def "Execute opcode AddI64 throw exception on incorrect Type on second param "() {
		setup: " a value of int32  of 3 and a value of int64 of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(4));
		instance.stack().push(new I32(3)); // wrong type.

		I64_add i64_add = new I64_add(instance);

		when: "run the opcode"
		i64_add.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.getUuid().toString().contains("a846eb0e-2ff2-4ffd-b570-da1f4bea8604");
	}

	def "Execute AddI64 throw exception on incorrect Type on first param "() {
		setup: " a value of int64  of 3  and a value of int32 of 4"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I32(4));  // wrong type
		instance.stack().push(new I64(3));

		I64_add i64_add = new I64_add(instance);

		when: "run the opcode"
		i64_add.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.getUuid().toString().contains("c3b6e630-56be-41c5-b4ca-a869e2012166");
	}
}
