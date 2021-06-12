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
package happynewmoonwithreport.opcode.bitshift

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.I64
import spock.lang.Specification

/**
 * Test I32_rotrTest opcode.
 * <br>
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i32.wast#L231">
 *      WebAssembly Test Suite i32.wast
 * </a>
 *
 */
class I32_rotrTest extends Specification {
	WasmInstanceInterface instance;

	void setup() {
		instance = new WasmInstanceStub();
	}

	// @Unroll
	def "I32_rotr val1 = #val1 val2 = #val2 expected = #expected"(Integer val1, Integer val2, Integer expected) {

		setup: "two values"
		instance.stack().push(new I32(val1));
		instance.stack().push(new I32(val2));

		and: "an opcode"
		I32_rotr i32_rotr = new I32_rotr(instance);

		when: "run the opcode"
		i32_rotr.execute();

		then: "verify the expected equals the return value"
		new I32(expected) == instance.stack().pop()

		where: ""
		val1        | val2        || expected
		// Web Assembly Test Suite
		1           | 1           || 0x8000_0000
		1           | 0           || 1
		-1          | 1           || -1
		1           | 32          || 1
		0xFF00_CC00 | 1           || 0x7F8_06600
		0x0008_0000 | 4           || 0x000_08000
		0xB0C1_D2E3 | 5           || 0x1D8_60E97
		0x0000_8000 | 37          || 0x000_00400
		0xB0C1_D2E3 | 0xFF05      || 0x1D8_60E97
		0x769A_BCDF | 0xFFFF_FFED || 0xE6F_BB4D5
		0x769A_BCDF | 0x8000_000D || 0xE6F_BB4D5
		1           | 31          || 2
		0x80000000  | 31          || 1
	}

	def "I32_rotr throws exception on incorrect type on second parameter"() {
		setup: "two values, the second one is the wrong type"
		instance.stack().push(new I32(4));
		instance.stack().push(new I64(-1));  // wrong type

		and: "an opcode"
		I32_rotr i32_rotr = new I32_rotr(instance);

		when: "run the opcode"
		i32_rotr.execute();

		then: "Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.message.contains("I32_rotr");
		exception.message.contains("Value should be of type I32");
		exception.getUuid() == UUID.fromString("e6b10f7c-c8bf-44fa-9ed5-6fd4af11f8e4");
	}

	def "I32_rotr throws exception on incorrect type on first parameter"() {
		setup: "two values, the first one is the wrong type"
		instance.stack().push(new I64(-2)); // wrong type
		instance.stack().push(new I32(8));

		and: "an opcode"
		I32_rotr i32_rotr = new I32_rotr(instance);

		when: "run the opcode"
		i32_rotr.execute();

		then: "Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.message.contains("I32_rotr");
		exception.message.contains("Value should be of type I32");
		exception.getUuid() == UUID.fromString("15a589e5-8ca4-4d9e-a664-0bf4cf22fbf9");
	}
}
