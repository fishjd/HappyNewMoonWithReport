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
package happynewmoonwithreport.opcode.comparison.F32;


import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.F32;
import happynewmoonwithreport.type.I32;
import java.util.UUID;

/**
 * F32 greater than (f32_gt)
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
 * Push the value f32.const c to the stack.
 * <p>
 * </li>
 * </ol>
 * Source:
 * <a href="https://webassembly.github.io/spec/core/exec/instructions.html#exec-relop" target="_top">
 * https://webassembly.github.io/spec/core/exec/instructions.html#exec-relop
 * </a>
 */
public class F32_gt {
	private WasmInstanceInterface instance;

	private F32_gt() {
		super();
	}

	public F32_gt(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}


	/**
	 * Execute the opcode.
	 */
	public void execute() {
		WasmStack<Object> stack = instance.stack();

		// 1. Assert: due to validation, two values of value type t are on the top of the stack.
		if ((stack.peek() instanceof F32) == false) {
			throw new WasmRuntimeException(UUID.fromString("874c9d63-a8ea-483f-85b8-7ab88f44da4c"),
				"f32_ne: Value2 type is incorrect");
		}
		// 2. Pop the value t.const c2 from the stack.
		F32 value2 = (F32) stack.pop();

		// 1. Assert: due to validation, two values of value type t are on the top of the stack.
		if ((stack.peek() instanceof F32) == false) {
			throw new WasmRuntimeException(UUID.fromString("c9dd4e2c-eeb9-471a-ae98-6af1661b705a"),
				"f32_ne: Value1 type is incorrect");
		}
		// 3. Pop the value t.const c1 from the stack.
		F32 value1 = (F32) stack.pop();

		// 4. Let c be the result of computing relopt(c1,c2).
		I32 c = value1.greaterThan(value2);

		// 5. Push the value f32.const c to the stack.
		stack.push(c);
	}


}
