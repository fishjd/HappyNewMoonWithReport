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

package happynewmoonwithreport.opcode.countingBits;

import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.I32;
import happynewmoonwithreport.type.I64;
import java.util.UUID;

/**
 * Return the number of leading zeros.  CLZ stands for <b>C</b>ount <b>L</b>eading <b>Z</b>eros
 * <br>
 * <h2>Source:</h2>
 * <h3>Operator:</h3>
 * <br>
 * Return the count of leading zero bits in i; all bits are considered leading zeros if i is 0.
 * <br>
 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-iclz" target="_top">
 * https://webassembly.github.io/spec/core/exec/numerics.html#op-iclz </a>
 *
 *
 * <br>Note: the documentation below is the same for all unary operators.<br>
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

public class I64_clz {
	private final String opCodeName = getClass().getName();
	private final String t1Type = "I64";

	private WasmStack<Object> stack;

	private I64_clz() {
		super();
	}

	public I64_clz(WasmStack<Object> stack) {
		this();
		this.stack = stack;
	}

	/**
	 * Execute the opcode.
	 */
	public void execute() {

		// Assert: due to validation, a value of value type t1 is on the top of the stack.
		if ((stack.peek() instanceof I64) == false) {
			throw new WasmRuntimeException(UUID.fromString("e3dfb90a-6745-42d4-9ade-be472248f199"),
				opCodeName + ": Value type is incorrect. Value should be of type " + t1Type);
		}
		// Pop the value t1.const c1 from the stack.
		I64 c1 = (I64) stack.pop();

		//Let c be a possible result of computing unopt(c1).
		I32 c = c1.countLeadingZeros();

		// Push the value t.const c to the stack.
		stack.push(c);

		// No need to trap as I64 may always be converted to I64.
	}
}
