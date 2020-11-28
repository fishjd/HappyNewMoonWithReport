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

package happynewmoonwithreport.opcode.bitwise.F32;

import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.F32;
import java.util.UUID;

/**
 * Return the absolute value of the input.
 * </p>
 * <h2>Source:</h2>
 * <h3>Operator:</h3>
 * <p>
 * Return the absolute value of the input.
 * <p>
 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-fabs" target="_top">
 * https://webassembly.github.io/spec/core/exec/numerics.html#op-fabs
 * </a>
 *
 *
 * <p>Note: the documentation below is the same for all unary operators.</p>
 * <h3>Execution:</h3>
 * <a href="https://webassembly.github.io/spec/core/exec/instructions.html#exec-unop"
 * target="_top"> https://webassembly.github.io/spec/core/exec/instructions.html#exec-unop
 * </a>
 * <h2>t.unop - Unary Operator</h2>
 *
 * <ol>
 * 		<li>
 * 			Assert: due to validation, a value of value type t is on the top of the stack.
 * 		</li>
 * 		<li>
 * 			Pop the value t.const c1 from the stack.
 * 		</li>
 * 		<li>
 * 			If unopt(c1) is defined, then:
 * 			<ol type="a">
 * 				<li>
 * 					Let c be a possible result of computing unopt(c1).
 * 				</li>
 * 				<li>
 * 					Push the value t.const c to the stack.
 * 				</li>
 * 			</ol>
 * 		</li>
 * 		<li>
 * 			Else:
 * 			<ol type="a">
 * 				<li>
 * 					Trap.
 * 				</li>
 * 			</ol>
 * 		</li>
 * </ol>
 */
public class F32_abs {
	private final String opCodeName = getClass().getName();
	private final String t1Type = "F32";

	private WasmInstanceInterface instance;

	private F32_abs() {
		super();
	}

	public F32_abs(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}

	/**
	 * Execute the opcode.
	 */
	public void execute() {
		WasmStack<Object> stack = instance.stack();

		// Assert: due to validation, a value of value type t is on the top of the stack.
		if ((stack.peek() instanceof F32) == false) {
			throw new WasmRuntimeException(UUID.fromString("316c2f0b-0a48-42d9-89a7-d7863bb9af3f"),
				opCodeName + ":"
				+ " Value type is incorrect. Value should be of type " + "'" +t1Type  + "'" +"."
				+ " The input type is " + "'" + stack.peek().getClass().getSimpleName() + "'."
				+ " The input value is " + "'" + stack.peek().toString() + "'.");

		}
		// Pop the value t1.const c1 from the stack.
		F32 c1 = (F32) stack.pop();

		//Let c be a possible result of computing unopt(c1).
		F32 c = c1.abs();

		// Push the value t.const c to the stack.
		stack.push(c);

		// No need to trap.
	}
}
