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
import happynewmoonwithreport.type.I64
import happynewmoonwithreport.type.S64
import spock.lang.Specification

/**
 * Created on 2020-5-16.
 */
class ConstantInt64Test extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Execute Constant Int64"() {

		// This test has very little value.  All it does is add a value to the stack.

		setup: " a value of ex : 3 "
		WasmInstanceInterface instance = new WasmInstanceStub();
		I64 value = new I64(val1);

		ConstantInt64 function = new ConstantInt64(instance);

		when: "run the opcode"
		function.execute(value);

		then: " value is placed on the stack "

		new I64(expected) == instance.stack().pop();

		where: ""
		val1                  || expected
		3                     || 3
		4                     || 4
		0x7FFF_FFFE           || 0x7FFF_FFFE
		0x7FFF_FFFF_FFFF_FFFF || new S64(0).maxValue()
		new S64(0).minValue() || new S64(0).minValue()
	}

}
