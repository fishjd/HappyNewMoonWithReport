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
package happynewmoonwithreport.opcode.comparison;


import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.I32;
import java.util.UUID;

/**
 * I32 equals zero (i32_eqz)
 * <br>
 * <b>Note this is the same for all Relative Operations</b>
 * <br>
 * t.relop <ol> <li> Assert: due to validation, two values of value type t are on the top of the
 * stack. </li> <li> Pop the value t.const c1 from the stack. </li> <li> Let c be the result of
 * computing relopt(c1). </li> <li> Push the value i32.const c to the stack.
 * <br>
 * </li> </ol>
 * <br>
 * Source:  <a href="https://webassembly.github.io/spec/core/exec/instructions.html#exec-relop"
 * target="_top"> https://webassembly.github.io/spec/core/exec/instructions.html#exec-relop </a>
 */
public class I32_eqz {
	private WasmInstanceInterface instance;

	private I32_eqz() {
		super();
	}

	public I32_eqz(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}


	/**
	 * Execute the opcode.
	 */
	public void execute() {
		WasmStack<Object> stack = instance.stack();
		if ((stack.peek() instanceof I32) == false) {
			throw new WasmRuntimeException(UUID.fromString("2278f5a2-debe-4e0d-a1ff-9a040297359c"),
				"I32_eqz: Value1 type is incorrect");
		}
		I32 value1 = (I32) stack.pop();

		Integer iResult;
		if (new I32(0).equals(value1)) {
			iResult = 1;
		} else {
			iResult = 0;
		}
		I32 result = new I32(iResult);

		stack.push(result);
	}


}
