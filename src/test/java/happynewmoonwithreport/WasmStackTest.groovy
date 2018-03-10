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
		I32 val1Actual = stack.peek(1);
		I32 val2Actual = stack.peek(0);


		then: ""
		val1Actual == val1;
		val2Actual == val2;
		stack.size() == 2;

		then: "Stack() is the same as stack(0) "
		stack.peek() == stack.peek(0);


	}
}
