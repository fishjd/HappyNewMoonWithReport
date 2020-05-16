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
package happynewmoonwithreport.opcode;


import java.util.UUID;

import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.I32;
import happynewmoonwithreport.type.S32;

/**
 * I32 Greater than Signed  (i32_gt_s)
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
 * Source:  <a href="https://webassembly.github.io/spec/core/exec/instructions.html#exec-relop"
 * target="_top"> https://webassembly.github.io/spec/core/exec/instructions.html#exec-relop
 * </a>
 */
public class I32_gt_s {
	private WasmInstanceInterface instance;

	private I32_gt_s() {
		super();
	}

	public I32_gt_s(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}


	/**
	 * Execute the opcode.
	 */
	public void execute() {
		WasmStack<Object> stack = instance.stack();
		if ((stack.peek() instanceof I32) == false) {
			throw new WasmRuntimeException(UUID.fromString("a379bcda-2089-496e-9994-29d32f46882b"),
				"I32_gt_s: Value2 type is incorrect");
		}
		I32 value2 = (I32) stack.pop();

		if ((stack.peek() instanceof I32) == false) {
			throw new WasmRuntimeException(UUID.fromString("2f6fc2a8-6e0a-44e2-bfce-ed776703d2f6"),
				"I32_gt_s: Value1 type is incorrect");
		}
		I32 value1 = (I32) stack.pop();

		// these values are signed (positive/negative) values
		S32 value2Signed = value2.signedValue();
		S32 value1Signed = value1.signedValue();

		// Do the comparison.
		I32 result = value1Signed.greaterThan(value2Signed);

		stack.push(result);
	}


}
