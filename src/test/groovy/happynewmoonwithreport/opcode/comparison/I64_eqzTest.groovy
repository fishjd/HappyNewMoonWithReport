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
import happynewmoonwithreport.opcode.comparison.I64_eqz
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.I64
import spock.lang.Specification

/**
 * Created on 2017-11-14.
 */
class I64_eqzTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Execute I64 Equals zero "() {
		setup: " push one value on stack."
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(val1));

		I64_eqz function = new I64_eqz(instance);

		when: "run the opcode"
		function.execute();

		then: " a value of expected"
		instance.stack().pop() == new I32(expected);


		where: ""
		val1                  || expected
		4                     || 0
		0                     || 1
		0xFFFF_FFFF           || 0
		0xFFFF_FFFE           || 0
		0x0000_0000           || 1
		0x0FFF_FFFF           || 0
		0xFFFF_FFFF_FFFF_FFFF || 0
		0xFFFF_FFFF_FFFF_FFFE || 0
		0x0000_0000_0000_0000 || 1

	}

	def "Execute I64_eqz throw exception on incorrect Type on first param "() {
		setup: " a value of I32  value 1"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I32(3));  // value 1 is an incorrect type

		I64_eqz function = new I64_eqz(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.getUuid().toString().contains("d33cbf32-66c8-4b8c-9fa5-81e8e195d1bc");
	}

}
