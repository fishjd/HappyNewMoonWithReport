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

package happynewmoonwithreport.opcode.math.f32;

import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.F32;
import java.util.UUID;

/**
 * Return the Minimum value of the inputs.
 * </p>
 * <h2>Source:</h2>
 * <h3>Operator:</h3>
 * <p>
 * Return the Minimum value of the inputs.
 * <p>
 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-fmin" target="_top">
 * 		floating point Minimum
 * </a>
 *
 *
 * <p>Note: the documentation below is the same for all Binary operators.</p>
 *
 * <h3>Execution:</h3>
 * <a href="https://webassembly.github.io/spec/core/exec/instructions.html#exec-binop" target="_top">
 * Binary Operator
 * </a>
 *
 * <h2>t.binop - Binary Operator</h2>
 * <ol>
 * 		<li>
 * 			Assert: due to validation, two values of value type t are on the top of the stack.
 * 		</li>
 * 		<li>
 * 			Pop the value t.const c<sub>2</sub> from the stack.
 * 		</li>
 * 		<li>
 * 			Pop the value t.const c<sub>1</sub> from the stack.
 * 		</li>
 * 		<li>
 * 			If binop<sub>t</sub>(c<sub>1</sub>,c<sub>2</sub>) is defined, then:
 * 			<ol type="a">
 * 				<li>
 * 				    Let c be a possible result of computing binopt(c<sub>1</sub>,c<sub>2</sub>).
 * 				</li>
 * 				<li>
 * 			    	Push the value t.const c to the stack.
 * 				</li>
 * 			</ol>
 * 		</li>
 * 		<li>
 * 			Else:
 * 			<ol type="a">
 * 				<li>
 * 				 	Trap.
 * 				</li>
 * 			</ol>
 * 		</li>
 * 	</ol>
 */
public class F32_min {
	private final String opCodeName = getClass().getName();
	private final String t1Type = "F32";
	private final String t2Type = "F32";

	private WasmInstanceInterface instance;

	private F32_min() {
		super();
	}

	public F32_min(WasmInstanceInterface instance) {
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
			throw new WasmRuntimeException(UUID.fromString("79294f81-eb3a-4a18-8830-efa4c06ad8f6"),
				opCodeName + ":"                                                                    //
				+ " Value2 type is incorrect. Value should be of type " + "'" + t2Type + "'" + "."  //
				+ " The input type is " + "'" + stack.peek().getClass().getSimpleName() + "'."      //
				+ " The input value is " + "'" + stack.peek().toString() + "'."                     //
			);

		}

		// Pop the value t1.const c2 from the stack.
		F32 c2 = (F32) stack.pop();

		// Assert: due to validation, a value of value type t is on the top of the stack.
		if ((stack.peek() instanceof F32) == false) {
			throw new WasmRuntimeException(UUID.fromString("6e8d4073-f0b4-41ef-968e-ff86e13852ef"),
				opCodeName + ":"                                                                    //
				+ " Value1 type is incorrect. Value should be of type " + "'" + t1Type + "'" + "."  //
				+ " The input type is " + "'" + stack.peek().getClass().getSimpleName() + "'."      //
				+ " The input value is " + "'" + stack.peek().toString() + "'."                     //
			);

		}

		// Pop the value t1.const c1 from the stack.
		F32 c1 = (F32) stack.pop();

		// Let c be a possible result of computing binopt(c1,c2).
		F32 c = c1.minWasm(c2);

		// Push the value t.const c to the stack.
		stack.push(c);

		// No need to trap.
	}
}
