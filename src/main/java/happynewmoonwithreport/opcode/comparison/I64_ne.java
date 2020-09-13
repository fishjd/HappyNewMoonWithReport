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
package happynewmoonwithreport.opcode.comparison;


import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.I32;
import happynewmoonwithreport.type.I64;
import java.util.UUID;

/**
 * I64 equals zero (i64_eqz)
 * <br>
 * <b>Note this is the same for all Relative Operations</b>
 * <br>
 * t.relop <ol> <li> Assert: due to validation, two values of value type t are on the top of the
 * stack. </li> <li> Pop the value t.const c1 from the stack. </li> <li> Let c be the result of
 * computing relopt(c1). </li> <li> Push the value i32.const c to the stack.
 * <br>
 * </li> </ol>
 * <br>
 * Source:  <a href="https://webassembly.github.io/spec/core/appendix/index-instructions.html"
 * target="_top"> https://webassembly.github.io/spec/core/appendix/index-instructions.html </a>
 */
public class I64_ne {
	private WasmInstanceInterface instance;

	private I64_ne() {
		super();
	}

	public I64_ne(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}


	/**
	 * Execute the opcode.
	 */

	public void execute() {
		WasmStack<Object> stack = instance.stack();
		if ((stack.peek() instanceof I64) == false) {
			throw new WasmRuntimeException(UUID.fromString("35101325-10e5-41c3-86e1-b79dd7eac7c6"),
				"I64_ne: Value2 type is incorrect");
		}
		I64 value2 = (I64) stack.pop();

		if ((stack.peek() instanceof I64) == false) {
			throw new WasmRuntimeException(UUID.fromString("1f3ea0f6-6b0d-4aa0-9065-8f35e3218af2"),
				"I64_ne: Value1 type is incorrect");
		}
		I64 value1 = (I64) stack.pop();

		Integer iResult;
		if (value1.equals(value2) == false) {
			iResult = 1;
		} else {
			iResult = 0;
		}
		I32 result = new I32(iResult);

		stack.push(result);
	}


}
