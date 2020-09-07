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

import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.I64;
import java.util.UUID;

/**
 * <h1>I64_extend32_s</h1>
 * <p>
 * Extend the least significant 32 bits to 64 bits signed.  The 32 least significant bits are
 * interpreted as a signed value.
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

public class I64_extend32_s {
	private final String opCodeName = getClass().getName();
	private final String t1Type = "I64";
	private final String t2Type = "I64";

	private WasmInstanceInterface instance;

	private I64_extend32_s() {
		super();
	}

	public I64_extend32_s(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}

	/**
	 * Execute the opcode.
	 */
	public void execute() {
		WasmStack<Object> stack = instance.stack();

		// Assert: due to validation, a value of value type t1 is on the top of the stack.
		if ((stack.peek() instanceof I64) == false) {
			throw new WasmRuntimeException(UUID.fromString("c9e751fb-0e2d-4c3c-bdeb-b1a894227abb"),
				opCodeName + ": Value type is incorrect. Value should be of type " + t1Type);
		}
		// Pop the value t1.const c1 from the stack.
		I64 c1 = (I64) stack.pop();

		//Let c be a possible result of computing unopt(c1).
		I64 c = c1.extend32To64Signed();

		// Push the value t.const c to the stack.
		stack.push(c);

		// No need to trap as I64 may always be converted to I64.
	}
}
