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

package happynewmoonwithreport.opcode.convert;

import java.util.UUID;

import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.I32;

/**
 * <h1>I64_extend8_s</h1>
 * <p>
 * Extend an I32 Extend 8 signed.  Extend the 8 least significant bits to 32 bits.  The 8
 * least significant bits are interpreted as a signed value.
 * </p>
 * <h2>Source:</h2>
 *
 * <p>Note: the documentation below is the same for all unary operators.</p>
 * <h2>Execution:</h2>
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

public class I32_extend8_s {
	private final String opCodeName = getClass().getName();
	private final String t1Type = "I32";
	private final String t2Type = "I32";

	private WasmInstanceInterface instance;

	private I32_extend8_s() {
		super();
	}

	public I32_extend8_s(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}

	/**
	 * Execute the opcode.
	 */
	public void execute() {
		WasmStack<Object> stack = instance.stack();

		// Assert: due to validation, a value of value type t1 is on the top of the stack.
		if ((stack.peek() instanceof I32) == false) {
			throw new WasmRuntimeException(UUID.fromString("633e1fa4-806e-485f-ad05-9529cf6289e1"),
				opCodeName + ": Value type is incorrect. Value should be of type " + t1Type);
		}
		// Pop the value t1.const c1 from the stack.
		I32 c1 = (I32) stack.pop();

		//Let c be a possible result of computing unopt(c1).
		I32 c = c1.extend8To32Signed();

		// Push the value t.const c to the stack.
		stack.push(c);

		// No need to trap as I32 may always be converted to I32.
	}
}
