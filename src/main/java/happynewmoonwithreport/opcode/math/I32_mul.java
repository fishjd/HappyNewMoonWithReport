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
package happynewmoonwithreport.opcode.math;


import java.util.UUID;

import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.I32;

/**
 * Return the result of multiplying i<sub>1</sub> from i<sub>2</sub> modulo 2<sup>N</sup>.
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
 * Pop the value t.const c<sub>2</sub> from the stack.
 * </li>
 * <li>
 * Pop the value t.const c<sub>1</sub> from the stack.
 * </li>
 * <li>
 * If binop<sub>t</sub>(c<sub>1</sub>,c<sub>2</sub>) is defined, then:
 * 		<ol type="a">
 * 			<li>
 * 			    Let c be a possible result of computing binopt(c<sub>1</sub>,c<sub>2</sub>).
 * 			</li>
 * 			<li>
 * 		    	Push the value t.const c to the stack.
 * 			</li>
 * 		</ol>
 * </li>
 * <li>
 * Else:
 * 		<ol type="a">
 * 			<li>
 * 			 	Trap.
 * 			</li>
 * 		</ol>
 * </li>
 * </ol>
 * Source:
 * <br>
 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-imul"
 * target="_top">
 * 		Multiply Operator
 * </a>
 * <br>
 * <a href="https://webassembly.github.io/spec/core/exec/instructions.html#exec-binop" target="_top">
 *		Binary Operator
 * </a>
 */
public class I32_mul<ParameterType, ReturnType> {
	private final String opCodeName = getClass().getName();
	private final String t1Type = "I32";
	private final String t2Type = "I32";

	private WasmInstanceInterface instance;

	private I32_mul() {
		super();
	}

	public I32_mul(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}

	/**
	 * Execute the opcode.
	 */
	public void execute() {
		WasmStack<Object> stack = instance.stack();
		if ((stack.peek() instanceof I32) == false) {
			throw new WasmRuntimeException(UUID.fromString("847fe99b-56ea-407c-ac94-1cf13c1936f1"),
				opcodeName + ": Value2 type is incorrect. Value should be of type " + t1Type);
		}
		I32 value2 = (I32) stack.pop();

		if ((stack.peek() instanceof I32) == false) {
			throw new WasmRuntimeException(UUID.fromString("e1433c51-da9f-4c43-a9fe-90ba1d84e56b"),
				opcodeName + ": Value1 type is incorrect. Value should be of type " + t2Type);
		}
		I32 value1 = (I32) stack.pop();

		I32 result = new I32(value1.integerValue() * value2.integerValue());

		stack.push(result);
	}
}
