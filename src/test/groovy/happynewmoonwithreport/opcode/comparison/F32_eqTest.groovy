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
import happynewmoonwithreport.type.F32
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.I64
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created on 2020-09-13
 */
class F32_eqTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Execute F32 Equal to "(Float val1, Float val2, Integer expected) {
		setup: " push two values on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F32(val1));
		instance.stack().push(new F32(val2));

		F32_eq function = new F32_eq(instance);

		when: "run the opcode"
		function.execute();
		I32 result = instance.stack().pop();

		then: " verify result equals value of expected"
		new I32(expected) == result

		where: "val1 equals val2 returns #expected"
		val1 | val2 || expected
		4    | 3    || 0
		3    | 4    || 0
		4    | 4    || 1
		0    | 0    || 1

	}

	def "Execute F32_eq throw exception on incorrect Type on second param "() {
		setup: " a value of F32  value 1  and a value of I64 of value 2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F32(3));  // value 1
		instance.stack().push(new I64(4));  // value 2

		F32_eq function = new F32_eq(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.getUuid().toString().contains("bf008255-1d76-4620-aaa2-62f14afa93db");
	}

	def "Execute F32_eq throw exception on incorrect Type on first param "() {
		setup: " a value of I64  value 1  and a value of F32 of value 2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(3));  // value 1
		instance.stack().push(new F32(4));  // value 2

		F32_eq function = new F32_eq(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.getUuid().toString().contains("adf0c7ba-231e-40d4-a1b7-4fe10c75723c");
	}

}
