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
package happynewmoonwithreport.opcode.comparison

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.opcode.comparison.I64_eq
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.I64
import spock.lang.Specification

/**
 * Created on 2018-01-23.
 */
class I64_eqTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Execute I64 Equals to "() {
		setup: " push two values on stack."
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(val1));
		instance.stack().push(new I64(val2));

		I64_eq function = new I64_eq(instance);

		when: "run the opcode"
		function.execute();

		then: " a value of expected"
		instance.stack().pop() == new I32(expected);

		where: ""
		val1                  | val2                  || expected
		4                     | 3                     || 0
		3                     | 4                     || 0
		4                     | 4                     || 1
		0                     | 0                     || 1
		0xFFFF_FFFF_FFFF_FFFF | 0xFFFF_FFFF_FFFF_FFFF || 1
		0xFFFF_FFFF_FFFF_FFFE | 0xFFFF_FFFF_FFFF_FFFF || 0
	}

	def "Execute I64_eq throw exception on incorrect Type on second param "() {
		setup: " a value of I64 value 1 and a value of I32 of value 2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(3));  // value 1
		instance.stack().push(new I32(4));  // value 2 is an incorrect type

		I64_eq function = new I64_eq(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.getUuid().toString().contains("e9b2cccf-1977-4a6b-9cb2-00d101c1203c");
	}

	def "Execute I64_eq throw exception on incorrect Type on first param "() {
		setup: " a value of I32 value 1 and a value of I64 of value 2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I32(3));  // value 1 is an incorrect type
		instance.stack().push(new I64(4));  // value 2

		I64_eq function = new I64_eq(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.getUuid().toString().contains("b7d4d9bd-742c-4a78-9d90-2d4e1f3292b0");
	}
}
