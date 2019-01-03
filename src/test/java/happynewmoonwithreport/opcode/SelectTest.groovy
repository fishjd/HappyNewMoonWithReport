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
import happynewmoonwithreport.type.I64
import spock.lang.Specification

/**
 * Created on 2017-08-25.
 */
class SelectTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Select  Golden Test"() {
		setup: " push three  values on stack."
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I32(val1));
		instance.stack().push(new I32(val2));
		instance.stack().push(new I32(con))
		Select function = new Select(instance);

		when: "run the opcode"
		function.execute();

		then: " a value of expected"
		I32 actual = (I32) instance.stack().pop();
		actual.equals(new I32(expected));

		where: ""
		val1 | val2 | con | expected
		4    | 7    | 1   | 4
		4    | 7    | 0   | 7
	}


	def "Throw exception when constant incorrect type"() {

		setup: " push three  values on stack. the const value is the incorrect type. "
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I32(val1));
		instance.stack().push(new I32(val2));
		instance.stack().push(new I64(con))  // incorrect, should be I32

		Select function = new Select(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.getUuid().toString().equals("78c61ec8-a580-40b0-ad97-bd40d6d55739");

		where: ""
		val1 | val2 | con
		4    | 7    | 1
		4    | 7    | 0
	}


	def "Throw exception when val1 and val2 are not the same type"() {

		setup: " push three  values on stack. the val1 and val2 are not the same type. "
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I32(val1));
		instance.stack().push(new I64(val2));// incorrect, should be I32
		instance.stack().push(new I32(con))

		Select function = new Select(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.getUuid().toString().equals("bd046bf9-6aea-42a4-b1bf-31e74d64f95c");

		where: ""
		val1 | val2 | con
		4    | 7    | 1
		4    | 7    | 0
	}

}
