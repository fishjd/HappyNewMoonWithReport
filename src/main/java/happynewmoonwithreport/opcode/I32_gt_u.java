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
package happynewmoonwithreport.opcode;


import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.I32;
import happynewmoonwithreport.type.U32;

import java.util.UUID;

/**
 * I32 Greater than Unsigned  (i32_gt_u)
 * <p>
 * <b>Note this is the same for all Relative Operations</b>
 * <p>
 * t.relop
 * <ol>
 * <li>
 * Assert: due to validation, two values of value type t are on the top of the stack.
 * </li>
 * <li>
 * Pop the value t.const c2 from the stack.
 * </li>
 * <li>
 * Pop the value t.const c1 from the stack.
 * </li>
 * <li>
 * Let c be the result of computing relopt(c1,c2).
 * </li>
 * <li>
 * Push the value i32.const c to the stack.
 * <p>
 * </li>
 * </ol>
 * <p>
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/exec/instructions.html#exec-relop" target="_top">
 * https://webassembly.github.io/spec/exec/instructions.html#exec-relop
 * </a>
 */
public class I32_gt_u {

	private WasmStack<Object> stack;

	private I32_gt_u() {
		super();
	}

	public I32_gt_u(WasmStack<Object> stack) {
		this();
		this.stack = stack;
	}


	/**
	 * Execute the opcode.
	 */
	public void execute() {
		if ((stack.peek() instanceof I32) == false) {
			throw new WasmRuntimeException(UUID.fromString("b422e802-6418-4b81-9eba-cff76bcdefb2"),
					"i32_gt_u: Value2 type is incorrect");
		}
		I32 value2 = (I32) stack.pop();

		if ((stack.peek() instanceof I32) == false) {
			throw new WasmRuntimeException(UUID.fromString("16c20d8f-cf3d-499f-b758-7e48fa9fec0f"),
					"i32_gt_u: Value1 type is incorrect");
		}
		I32 value1 = (I32) stack.pop();

		// these values are unsigned values
		U32 value2Unsigned = value2.unsignedValue();
		U32 value1Unsigned = value1.unsignedValue();

		// Do the comparison.
		I32 result = value1Unsigned.greaterThan(value2Unsigned);


		stack.push(result);
	}


}
