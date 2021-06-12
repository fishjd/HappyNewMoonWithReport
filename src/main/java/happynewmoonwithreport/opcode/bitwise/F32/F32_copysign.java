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
package happynewmoonwithreport.opcode.bitwise.F32;


import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.F32;
import java.util.UUID;

/**
 * F32 Copy Sign
 * <p>
 * [f32 f32]→[f32]
 *
 * <ul>
 * 		<li>
 *  		If z1 and z2 have the same sign, then return z1.
 *		</li> <li>
 * 			Else return z1 with negated sign.
 * 		</li>
 * </ul>
 *
 * <br>
 * Source:
 * <a href="https://webassembly.github.io/spec/core/exec/instructions.html#exec-binop" target="_top">
 * Binary Operator
 * </a>
 * <p>
 * Source:
 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-fcopysign" target="_top">
 * F32_copysign
 * </a>
 */
public class F32_copysign {
	private final String opCodeName = getClass().getName();
	private final String t1Type = "F32";
	private final String t2Type = "F32";

	private WasmInstanceInterface instance;

	private F32_copysign() {
		super();
	}

	public F32_copysign(WasmInstanceInterface instance) {
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
			StringBuilder sb = new StringBuilder();
			sb.append(opCodeName + ":");
			sb.append(
				" Value2 type is incorrect. Value should be of type " + "'" + t1Type + "'" + ".");
			sb.append(
				" The input type is " + "'" + stack.peek().getClass().getSimpleName() + "'" + ".");
			sb.append(" The input value is " + "'" + stack.peek().toString() + "'.");

			throw new WasmRuntimeException(UUID.fromString("316c2f0b-0a48-42d9-89a7-d7863bb9af3f"),
				sb.toString());

		}
		// 2. Pop the value t.const c2 from the stack.
		F32 value2 = (F32) stack.pop();

		// 1. Assert: due to validation, two values of value type t are on the top of the stack.
		if ((stack.peek() instanceof F32) == false) {
			StringBuilder sb = new StringBuilder();
			sb.append(opCodeName + ":");
			sb.append(
				" Value1 type is incorrect. Value should be of type " + "'" + t1Type + "'" + ".");
			sb.append(
				" The input type is " + "'" + stack.peek().getClass().getSimpleName() + "'" + ".");
			sb.append(" The input value is " + "'" + stack.peek().toString() + "'.");

			throw new WasmRuntimeException(UUID.fromString("73b380b9-23ed-44cf-9b96-f224922415fd"),
				sb.toString());
		}
		// 3. Pop the value t.const c1 from the stack.
		F32 value1 = (F32) stack.pop();

		// 4. If binopt(c1,c2) is defined, then:

		// 4a. Let c be a possible result of computing binopt(c1,c2).
		F32 c = value1.copysign(value2);

		// 4b. Push the value f32.const c to the stack.
		stack.push(c);

		// 5. Else Trap.
		// No need to trap.
	}


}
