/*
 *  Copyright 2017 - 2019 Whole Bean Software, LTD.
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
package happynewmoonwithreport.opcode;


import java.util.UUID;

import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.I64;

/**
 * Return the result of adding i1 and i2 modulo 2N.
 * <br>
 * <br>
 * Note the below is the same for all Binary Operations
 * <br>
 * <br>
 * t.binop
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
 * If binopt(c1,c2) is defined, then: Let c be a possible result of computing binopt(c1,c2). Push
 * the value t.const
 * c to the stack.
 * </li>
 * <li>
 * Else:
 * Trap.
 * <br>
 * </li>
 * </ol>
 * <br>
 * Source:
 * <br>
 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-iadd" target="_top">
 *   https://webassembly.github.io/spec/core/exec/numerics.html#op-iadd
 *   </a>
 * <br>
 * <a href="https://webassembly.github.io/spec/core/exec/instructions.html#numeric-instructions" target="_top">
 * https://webassembly.github.io/spec/core/exec/instructions.html#numeric-instructions  t.binop
 * </a>
 */
public class I64_add<ParameterType, ReturnType> {
	private final String opCodeName = getClass().getName();
	private final String t1Type = "I64";
	private final String t2Type = "I64";

	private WasmInstanceInterface instance;

	private I64_add() {
		super();
	}

	public I64_add(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}

	/**
	 * Execute the opcode.
	 */
	public void execute() {
		WasmStack<Object> stack = instance.stack();
		if ((stack.peek() instanceof I64) == false) {
			throw new WasmRuntimeException(UUID.fromString("a846eb0e-2ff2-4ffd-b570-da1f4bea8604"),
				opCodeName + ": Value2 type is incorrect. Value should be of type " + t1Type);
		}
		I64 value2 = (I64) stack.pop();

		if ((stack.peek() instanceof I64) == false) {
			throw new WasmRuntimeException(UUID.fromString("c3b6e630-56be-41c5-b4ca-a869e2012166"),
				opCodeName + ": Value1 type is incorrect. Value should be of type " + t2Type);
		}
		I64 value1 = (I64) stack.pop();


		I64 result = new I64(value1.longValue() + value2.longValue());

		stack.push(result);
	}
}
