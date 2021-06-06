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
package happynewmoonwithreport.opcode.comparison

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.opcode.comparison.I32_eq
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.I64
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created on 2017-11-14.
 */
class I32_eqTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	@Unroll
	def "Execute I32 Equal to "() {
		setup: " push two values on stack."
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I32(val1));
		instance.stack().push(new I32(val2));

		I32_eq function = new I32_eq(instance);

		when: "run the opcode"
		function.execute();

		then: " a value of expected"
		new I32(expected).equals(instance.stack().pop());


		where: "val1 equals val2 returns #expected"
		val1        | val2        || expected
		4           | 3           || 0
		3           | 4           || 0
		4           | 4           || 1
		0           | 0           || 1
		0x0FFF_FFFF | 0x0FFF_FFFE || 0
		0x0FFF_FFFF | 0x0FFF_FFFF || 1
	}

	def "Execute I32_eq throw exception on incorrect Type on second param "() {
		setup: " a value of I32  value 1  and a value of I64 of value 2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I32(3));  // value 1
		instance.stack().push(new I64(4));  // value 2

		I32_eq function = new I32_eq(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.getUuid().toString().contains("03da0147-6ed0-4039-8f54-a7cab2477b6f");
	}

	def "Execute I32_eq throw exception on incorrect Type on first param "() {
		setup: " a value of I64  value 1  and a value of I32 of value 2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(3));  // value 1
		instance.stack().push(new I32(4));  // value 2

		I32_eq function = new I32_eq(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.getUuid().toString().contains("3d0a4b3c-bf4b-42b0-b594-c57a4cb23143");
	}

}
