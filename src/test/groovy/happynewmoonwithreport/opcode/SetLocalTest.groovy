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
package happynewmoonwithreport.opcode


import happynewmoonwithreport.WasmFrame
import happynewmoonwithreport.WasmModule
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.WasmStack
import happynewmoonwithreport.type.I32
import spock.lang.Specification

/**
 * Created on 2017-08-25.
 */
class SetLocalTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "Execute"() {
		setup: "A Frame with two local variables and a Int32 on the stack.  "
		WasmModule module = new WasmModule();
		WasmFrame frame = new WasmFrame(module);
		WasmStack stack = new WasmStack();
		// locals must be initialized.
		frame.localAll().add(0);
		frame.localAll().add(0);
		stack.push(new I32(3));

		SetLocal function = new SetLocal(frame, stack);

		when: "run the opcode"
		I32 index = new I32(0);
		function.execute(index);

		then: " the local value should be set"
		new I32(3) == frame.localAll().get(index);
	}

	def "Execute with exception thrown when there are zero local variables "() {
		setup: " an instance with zero local variables "
		WasmModule module = new WasmModule();
		WasmFrame frame = new WasmFrame(module);
		WasmStack stack = new WasmStack();
		stack.push(new I32(3));

		SetLocal function = new SetLocal(frame, stack);

		expect: "no local variables"
		0 == frame.localAll().size()


		when: "run the opcode"
		function.execute(new I32(0));

		then: "Exception Thrown"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Local variable")
		exception.message.contains("SetLocal")
	}

	def "Execute with exception thrown when stack is empty"() {
		setup: " an instance with zero local variables "
		WasmModule module = new WasmModule();
		WasmFrame frame = new WasmFrame(module);
		WasmStack stack = new WasmStack();

		frame.localAll().add(0);

		SetLocal function = new SetLocal(frame, stack);

		expect: "nothing on the stack! "
		0 == stack.size();

		when: "run the opcode"
		function.execute(new I32(0));

		then: "Exception Thrown "
		WasmRuntimeException exception = thrown();
		exception.message.contains("stack")
		exception.message.contains("SetLocal")
	}
}
