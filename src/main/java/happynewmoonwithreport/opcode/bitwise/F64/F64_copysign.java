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
package happynewmoonwithreport.opcode.bitwise.F64;


import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.F64;
import happynewmoonwithreport.type.F64;
import java.util.UUID;

/**
 *
 * F64 Copy Sign
 * <p>
 *[F64 F64]→[F64]
 *
 *
 * <br>
 * Source:
 * <a href="https://webassembly.github.io/spec/core/exec/instructions.html#exec-binop" target="_top">
 *     Binary Operator
 * </a>
 *
 * Source:
 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-fcopysign" target="_top">
 * F64_copysign
 * </a>
 */
public class F64_copysign {
	private final String opCodeName = getClass().getName();
	private final String t1Type = "F64";
	private final String t2Type = "F64";

	private WasmInstanceInterface instance;

	private F64_copysign() {
		super();
	}

	public F64_copysign(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}


	/**
	 * Execute the opcode.
	 */
	public void execute() {
		WasmStack<Object> stack = instance.stack();

		// Assert: due to validation, a value of value type t is on the top of the stack.
		if ((stack.peek() instanceof F64) == false) {
			throw new WasmRuntimeException(UUID.fromString("1233deff-d390-4d90-baab-7759d0db3a98"),
				opCodeName + ":"
				+ " Value2 type is incorrect. Value should be of type " + "'" + t2Type  + "'" +"."
				+ " The input type is " + "'" + stack.peek().getClass().getSimpleName() + "'."
				+ " The input value is " + "'" + stack.peek().toString() + "'.");

		}
		// 2. Pop the value t.const c2 from the stack.
		F64 value2 = (F64) stack.pop();

		// 1. Assert: due to validation, two values of value type t are on the top of the stack.
		if ((stack.peek() instanceof F64) == false) {
			throw new WasmRuntimeException(UUID.fromString("4161f0bb-2542-4996-bf91-243318bff833"),
				opCodeName + ":"
				+ " Value1 type is incorrect. Value should be of type " + "'" + t1Type  + "'" +"."
				+ " The input type is " + "'" + stack.peek().getClass().getSimpleName() + "'."
				+ " The input value is " + "'" + stack.peek().toString() + "'.");
		}
		// 3. Pop the value t.const c1 from the stack.
		F64 value1 = (F64) stack.pop();

		// 4. If binopt(c1,c2) is defined, then:

		// 4a. Let c be a possible result of computing binopt(c1,c2).
		F64 c =  value1.copysign(value2);

		// 4b. Push the value F64.const c to the stack.
		stack.push(c);

		// 5. Else Trap.
		// No need to trap.
	}


}
