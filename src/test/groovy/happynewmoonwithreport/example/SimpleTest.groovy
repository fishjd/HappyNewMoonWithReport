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
package happynewmoonwithreport.example

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.opcode.math.f32.F32_ceil
import happynewmoonwithreport.type.F32
import spock.lang.Specification

class SimpleTest extends Specification {

	void setup() {
	}

	void cleanup() {
	}

	def "Name"() {
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F32(val1));

		F32_ceil opcode = new F32_ceil(instance);
	}

	def "Simple_Test"() {
		setup: ""
		Integer a = 54;
		Integer b = 98;

		when: ""
		Integer c = a + b;

		then: " verify results"
		c == 152
	}


}
