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
import happynewmoonwithreport.opcode.comparison.I32_ge_u
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.I64
import spock.lang.Specification

/**
 * Created on 2017-08-25.
 */
class I32_ge_uTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Execute I32 Greater than or equal to unsigned "() {
		setup: " push two values on stack."
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I32(val1));
		instance.stack().push(new I32(val2));

		I32_ge_u function = new I32_ge_u(instance);

		when: "run the opcode"
		function.execute();

		then: " a value of expected"
		new I32(expected) == instance.stack().pop();


		where: ""
		val1        | val2        || expected
		4           | 3           || 1
		3           | 4           || 0
		4           | 4           || 1
		0           | 0           || 1
		0x7FFF_FFFF | 0x7FFF_FFFF || 1
		0x7FFF_FFFF | 0x7FFF_FFFE || 1
		0x7FFF_FFFF | 0x0FFF_FFFF || 1
		0x0FFF_FFFF | 0x7FFF_FFFF || 0
	}

	def "Execute I32_ge_u throw exception on incorrect Type on second param "() {
		setup: " a value of I32  value 1  and a value of I64 of value 2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I32(3));  // value 1
		instance.stack().push(new I64(4));  // value 2

		I32_ge_u function = new I32_ge_u(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.getUuid().toString().contains("bf28b297-121c-46aa-a5a7-3223507247ce");
	}

	def "Execute I32_ge_u throw exception on incorrect Type on first param "() {
		setup: " a value of I64  value 1  and a value of I32 of value 2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(3));  // value 1
		instance.stack().push(new I32(4));  // value 2

		I32_ge_u function = new I32_ge_u(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.getUuid().toString().contains("7c39b2ff-57ac-4106-b1dd-a08373e47cfd");
	}

}
