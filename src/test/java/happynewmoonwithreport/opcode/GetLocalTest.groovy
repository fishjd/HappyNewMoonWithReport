/*
 *  Copyright 2017 Whole Bean Software, LTD.
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

import happynewmoonwithreport.WasmFrame
import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.type.I32
import spock.lang.Specification

/**
 * Created on 2017-08-25.
 */
class GetLocalTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Execute"() {
		setup: " an instance with one local variable "
		WasmInstanceInterface instance = new WasmInstanceStub();

		WasmFrame frame = new WasmFrame(instance);
		frame.localAll().add(new I32(3));

		GetLocal function = new GetLocal(frame);

		when: "run the opcode"
		function.execute(new I32(0));

		then: " the local value should be on the stack"
		new I32(3) == instance.stack().pop();
	}

	def "Execute with exception thrown"() {
		setup: " an instance with zero local variable "
		WasmInstanceInterface instance = new WasmInstanceStub();

		WasmFrame frame = new WasmFrame(instance);
		// frame.localAll().add(new Int32(3));

		GetLocal function = new GetLocal(frame);

		when: "run the opcode"
		function.execute(new I32(0));

		then: " the local value should be on the stack"
		thrown WasmRuntimeException;
	}
}
