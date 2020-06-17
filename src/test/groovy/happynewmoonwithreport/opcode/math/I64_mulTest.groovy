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
import spock.lang.Specification

/**
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i64.wast#L55">
 *      WebAssembly Test Suite i64.wast
 * </a>
 */
class I64_mulTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Execute I64_Mul"(Long val1, Long val2, Long expected) {
		setup: " given two values val1 and val2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(val1));
		instance.stack().push(new I64(val2));

		I64_mul function = new I64_mul(instance);

		when: "run the opcode"
		function.execute();

		then: " a value of expected"

		new I64(expected) == instance.stack().pop();

		where: ""
		val1                  | val2                  || expected
		// Web Assembly Test
		1                     | 1                     || 1
		1                     | 0                     || 0
		-1                    | -1                    || 1
		0x1000_0000_0000_0000 | 4096                  || 0
		0x8000_0000_0000_0000 | 0                     || 0
		0x8000_0000_0000_0000 | -1                    || 0x8000_0000_0000_0000
		0x7FFF_FFFF_FFFF_FFFF | -1                    || 0x8000_0000_0000_0001
		0x0123_4567_89AB_CDEF | 0xFEDC_BA98_7654_3210 || 0x2236_D88F_E561_8CF0
		0x7FFF_FFFF_FFFF_FFFF | 0x7FFF_FFFF_FFFF_FFFF || 1
		// Happy New Moon tests
		3                     | 4                     || 12
		3                     | 0                     || 0
		4                     | 3                     || 12
		0x7FFF_FFFE           | 0x1                   || 0x7FFF_FFFE

	}

	def "Execute opcode I64_Mul throw exception on incorrect Type on second param "() {
		setup: ""
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(4));
		instance.stack().push(new I32(3));  // wrong type

		I64_mul function = new I64_mul(instance);

		when: "run the opcode"
		function.execute();

		then: "Throw Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.getUuid().toString().contains("81a80536-eb28-4910-bc07-1b5123173882");
	}

	def "Execute opcode I64_Mul throw exception on incorrect Type on first param "() {
		setup: ""
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I32(4)); // wrong type
		instance.stack().push(new I64(3));

		I64_mul function = new I64_mul(instance);

		when: "run the opcode"
		function.execute();

		then: "Throw Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.getUuid().toString().contains("2e92b30a-2dc6-4697-a1cb-d9121b9ad135");
	}
}
