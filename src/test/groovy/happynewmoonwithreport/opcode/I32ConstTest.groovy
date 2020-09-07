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
package happynewmoonwithreport.opcode

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.S32
import spock.lang.Specification

/**
 * Created on 2017-08-25.
 */
class I32ConstTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Execute Constant Int32"() {
		setup: " a value of ex : 3 "
		WasmInstanceInterface instance = new WasmInstanceStub();
		I32 value = new I32(val1);

		I32_const function = new I32_const(instance);

		when: "run the opcode"
		function.execute(value);

		then: " value is placed on the stack "

		new I32(expected) == instance.stack().pop();

		where: ""
		val1                  || expected
		3                     || 3
		4                     || 4
		0x7FFF_FFFE           || 0x7FFF_FFFE
		0x7FFF_FFFF           || new S32(0).maxValue()
		new S32(0).minValue() || new S32(0).minValue()
	}

}
