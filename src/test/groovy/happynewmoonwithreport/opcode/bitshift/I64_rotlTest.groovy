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
package happynewmoonwithreport.opcode.bitshift

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.I64
import spock.lang.Specification

/**
 * Test I64_rotlTest opcode.
 * <br>
 * Some test cases are from:
 * <a href="https://github.com/WebAssembly/testsuite/blob/c17cd7f4e379b814055c82fcc0fc1f6202ba9e2a/i64.wast#L218">
 *      WebAssembly Test Suite i64.wast
 * </a>
 *
 */
class I64_rotlTest extends Specification {
	WasmInstanceInterface instance;

	void setup() {
		instance = new WasmInstanceStub();
	}

	// @Unroll
	def "I64_rotl val1 = #val1 val2 = #val2 expected = #expected"(Long val1, Long val2, Long expected) {

		setup: "two values"
		instance.stack().push(new I64(val1));
		instance.stack().push(new I64(val2));

		and: "an opcode"
		I64_rotl i64_rotl = new I64_rotl(instance);

		when: "run the opcode"
		i64_rotl.execute();

		then: "verify the expected equals the return value"
		new I64(expected) == instance.stack().pop()

		where: ""
		val1                  | val2                 || expected
		// Web Assembly Test Suite
		1                     | 1                     | 2
		1                     | 0                     | 1
		-1                    | 1                     | -1
		1                     | 64                    | 1
		0xABCD_9876_0246_8ACE | 1                     | 0x579B_30EC_048D_159D
		0xFE00_0000_DC00_0000 | 4                     | 0xE000_000D_C000_000F
		0xABCD_1234_EF56_7809 | 53                    | 0x0135_79A2_469D_EACF
		0xABD1_234E_F567_809C | 63                    | 0x55E8_91A7_7AB3_C04E
		0xABCD_1234_EF56_7809 | 0xF5                  | 0x0135_79A2_469D_EACF
		0xABCD_7294_EF56_7809 | 0xFFFF_FFFF_FFFF_FFED | 0xCF01_3579_AE52_9DEA
		0xABD1_234E_F567_809C | 0x8000_0000_0000_003F | 0x55E8_91A7_7AB3_C04E
		1                     | 63                    | 0x8000_0000_0000_0000
		0x8000_0000_0000_0000 | 1                     | 1
	}

	def "I64_rotl throws exception on incorrect type on second parameter"() {
		setup: "two values, the second one is the wrong type"
		instance.stack().push(new I64(4));
		instance.stack().push(new I32(3));  // wrong type

		and: "an opcode"
		I64_rotl i64_rotl = new I64_rotl(instance);

		when: "run the opcode"
		i64_rotl.execute();

		then: "Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.message.contains("I64_rotl");
		exception.message.contains("Value should be of type I64");
		exception.getUuid() == UUID.fromString("a45465fe-4ab1-474b-b702-a1526b292d42");
	}

	def "I64_rotl throws exception on incorrect type on first parameter"() {
		setup: "two values, the first one is the wrong type"
		instance.stack().push(new I32(4)); // wrong type
		instance.stack().push(new I64(3));

		and: "an opcode"
		I64_rotl i64_rotl = new I64_rotl(instance);

		when: "run the opcode"
		i64_rotl.execute();

		then: "Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.message.contains("I64_rotl");
		exception.message.contains("Value should be of type I64");
		exception.getUuid() == UUID.fromString("2bee7bd5-2dcf-40e7-8c27-118fea841bf9");
	}
}
