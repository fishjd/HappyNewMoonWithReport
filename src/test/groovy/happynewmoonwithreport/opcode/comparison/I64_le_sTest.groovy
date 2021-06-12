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
import happynewmoonwithreport.opcode.comparison.I64_le_s
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.I64
import spock.lang.Specification

/**
 * Created on 2017-08-25.
 */
class I64_le_sTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Execute I64 Less than equal signed "() {
		setup: " push two values on stack."
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(val1));
		instance.stack().push(new I64(val2));

		I64_le_s function = new I64_le_s(instance);

		when: "run the opcode"
		function.execute();

		then: " a value of expected"
		new I32(expected) == instance.stack().pop();


		where: ""
		val1 | val2 || expected
		3    | 4    || 1
		4    | 3    || 0
		4    | 4    || 1
		0    | 0    || 1
//        0x7FFF_FFFF | 0x7FFF_FFFF || 0
//        0x7FFF_FFFE | 0x7FFF_FFFF || 1
	}

	def "Execute AddI32 throw exception on incorrect Type on second param "() {
		setup: " a value of I64  value 1  and a value of I32 of value 2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(3));  // value 1
		instance.stack().push(new I32(4));  // value 2

		I64_le_s function = new I64_le_s(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.getUuid().toString().contains("b130e5a2-3cbd-4ebc-a2be-d36bb40297b6");
	}

	def "Execute AddI32 throw exception on incorrect Type on first param "() {
		setup: " a value of I32  value 1  and a value of I64 of value 2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I32(3));  // value 1
		instance.stack().push(new I64(4));  // value 2

		I64_le_s function = new I64_le_s(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.getUuid().toString().contains("1c5c6417-2756-4e24-aa09-05c0d67deed4");
	}

}
