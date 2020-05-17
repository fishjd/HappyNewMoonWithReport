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

package happynewmoonwithreport.opcode.countingBits;

import java.util.UUID;

import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.I32;

/**
 * Return the number of trailing zeros.  CTZ stands for <b>C</b>ount <b>T</b>railing <b>Z</b>eros
 * </p>
 * <h2>Source:</h2>
 * <h3>Operator:</h3>
 * <p>
 * Return the count of trailing zero bits in i; all bits are considered trailing zeros if i is 0.
 * <p>
 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-ictz" target="_top">
 * https://webassembly.github.io/spec/core/exec/numerics.html#op-ictz </a>
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

public class I32_ctz {
	private final String opCodeName = getClass().getName();
	private final String t1Type = "I32";
	private final String t2Type = "I32";

	private WasmStack<Object> stack;

	private I32_ctz() {
		super();
	}

	public I32_ctz(WasmStack<Object> stack ) {
		this();
		this.stack = stack;
	}

	/**
	 * Execute the opcode.
	 */
	public void execute() {

		// Assert: due to validation, a value of value type t1 is on the top of the stack.
		if ((stack.peek() instanceof I32) == false) {
			throw new WasmRuntimeException(UUID.fromString("6132922f-a968-42a0-bef5-236f486448d5"),
				opCodeName + ": Value type is incorrect. Value should be of type " + t1Type);
		}
		// Pop the value t1.const c1 from the stack.
		I32 c1 = (I32) stack.pop();

		//Let c be a possible result of computing unopt(c1).
		I32 c = c1.countTrailingZeros();

		// Push the value t.const c to the stack.
		stack.push(c);

		// No need to trap as I32 may always be converted to I32.
	}
}
