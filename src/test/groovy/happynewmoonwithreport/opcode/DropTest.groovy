/*
 *  Copyright 2017 - 2019 Whole Bean Software, LTD.
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
package happynewmoonwithreport.opcode

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.type.I32
import spock.lang.Specification

/**
 * Created on 2017-08-25.
 */
class DropTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Drop Golden Test"() {
		setup: " push two values on stack."
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I32(val1));
		instance.stack().push(new I32(val2));

		Drop function = new Drop(instance);

		when: "run the opcode"
		function.execute();

		then: " a value of expected"
		instance.stack().empty() == false;
		instance.stack().pop();
		instance.stack().empty() == true;


		where: ""
		val1        | val2        || expected
		4           | 3           || 1
		3           | 4           || 0
		4           | 4           || 1
		0           | 0           || 1
		0x7FFF_FFFF | 0x7FFF_FFFF || 1
		0x7FFF_FFFF | 0x7FFF_FFFE || 1
	}

	def "Drop Throw Exception when empty"() {
		setup: "stack with zero elements on it."

		WasmInstanceInterface instance = new WasmInstanceStub();

		Drop function = new Drop(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.getUuid().toString().contains("f42f4399-988b-46ce-b73b-4dcdccae576f");
	}

}
