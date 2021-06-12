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
package happynewmoonwithreport

import happynewmoonwithreport.type.I32
import spock.lang.Specification

/**
 * Created on 2017-12-12.
 */
class WasmStackTest extends Specification {
	def "Peek"() {
		setup: "set up stack with two elements."
		WasmStack<I32> stack = new Stack<>();
		I32 val1 = new I32(4);
		stack.push(val1);
		I32 val2 = new I32(7);
		stack.push(val2);

		expect:
		stack.size() == 2

		when: "peek at index 0 "
		I32 val1Actual = stack.peek(0);  // bottom of the stack.
		I32 val2Actual = stack.peek(1);  // top of the stack.


		then: ""
		val1Actual == val1;
		val2Actual == val2;
		val2 == stack.peek();
		val2 == stack.peek(stack.size() - 1)

		stack.size() == 2;

		//then: "Stack.peak(), which views the top of the stack, is the same as stack(stack.size()-1) "
		stack.peek() == stack.peek(stack.size() - 1);

		then:
		val2 == stack.pop();
		val1 == stack.pop();

	}
}
