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
package happynewmoonwithreport.opcode.comparison.F64;


import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.F64;
import happynewmoonwithreport.type.I32;
import java.util.UUID;

/**
 * F64 greater than or equal (f64_ge)  >=
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
public class F64_ge {
	private WasmInstanceInterface instance;

	private F64_ge() {
		super();
	}

	public F64_ge(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}


	/**
	 * Execute the opcode.
	 */
	public void execute() {
		WasmStack<Object> stack = instance.stack();

		// 1. Assert: due to validation, two values of value type t are on the top of the stack.
		if ((stack.peek() instanceof F64) == false) {
			throw new WasmRuntimeException(UUID.fromString("76d88e00-c903-487e-aed8-7035e6e5a062"),
				"f64_ne: Value2 type is incorrect");
		}
		// 2. Pop the value t.const c2 from the stack.
		F64 value2 = (F64) stack.pop();

		// 1. Assert: due to validation, two values of value type t are on the top of the stack.
		if ((stack.peek() instanceof F64) == false) {
			throw new WasmRuntimeException(UUID.fromString("ef1980dc-d622-4733-8486-b8ee543136cc"),
				"f64_ne: Value1 type is incorrect");
		}
		// 3. Pop the value t.const c1 from the stack.
		F64 value1 = (F64) stack.pop();

		// 4. Let c be the result of computing relopt(c1,c2).
		I32 c = value1.greaterThaEqualnWasm(value2);

		// 5. Push the value f64.const c to the stack.
		stack.push(c);
	}


}
