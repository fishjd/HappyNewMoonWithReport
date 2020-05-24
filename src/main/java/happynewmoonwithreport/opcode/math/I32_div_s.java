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

import happynewmoonwithreport.WasmDivideByZeroException;
import happynewmoonwithreport.WasmDivideOverflowException;
import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.I32;
import happynewmoonwithreport.type.S32;

/**
 * Divide 32 signed
 * 	<ul>
 * 		<li>
 * 			Let j<sub>1</sub> be the signed interpretation of i<sub>1</sub>.
 * 		</li>
 * 		<li>
 * 			Let j<sub>2</sub> be the signed interpretation of i<sub>2</sub>.
 * 		</li>
 * 		<li>
 * 			If j<sub>2</sub> is 0, then the result is undefined.
 * 		</li>
 * 		<li>
 * 			Else if j<sub>1</sub> divided by j<sub>2</sub> is 2<sup>N-1</sup>, then the result is
 * 			undefined.
 * 		</li>
 * 		<li>
 * 			Else, return the result of dividing j<sub>1</sub> by j<sub>2</sub>, truncated toward
 * 			zero.
 * 		</li>
 * 	</ul>
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
 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-idiv-s"
 * target="_top">
 * 		Divide Signed Operator
 * </a>
 * <br>
 * <a href="https://webassembly.github.io/spec/core/exec/instructions.html#exec-binop" target="_top">
 * 		Binary Operator
 * </a>
 */
public class I32_div_s<ParameterType, ReturnType> {
	private final String opcodeName = getClass().getName();
	private final String t1Type = "I32";
	private final String t2Type = "I32";

	private WasmInstanceInterface instance;

	private I32_div_s() {
		super();
	}

	public I32_div_s(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}

	/**
	 * Execute the opcode.
	 */
	public void execute() {
		WasmStack<Object> stack = instance.stack();
		if ((stack.peek() instanceof I32) == false) {
			throw new WasmRuntimeException(UUID.fromString("12a6126e-d632-4486-94a5-20f9c974c1de"),
				opcodeName + ": Value2 type is incorrect. Value should be of type " + t1Type);
		}
		I32 value2 = (I32) stack.pop();

		if ((stack.peek() instanceof I32) == false) {
			throw new WasmRuntimeException(UUID.fromString("099049ec-f319-4d6b-9e6f-64e66467bc45"),
				opcodeName + ": Value1 type is incorrect. Value should be of type " + t2Type);
		}
		I32 value1 = (I32) stack.pop();


		// Let j1 be the signed interpretation of i1.
		S32 j1 = new S32(value1);
		// Let j2 be the signed interpretation of i2.
		S32 j2 = new S32(value2);

		//If j2 is 0, then the result is undefined.
		if (j2.integerValue() == 0) {
			throw new WasmDivideByZeroException(
				UUID.fromString("2c6526b2-6f08-45c4-8343-579fc6cfe515"),
				opcodeName + "Divide by zero is not defined");
		}

		// Else if j1 divided by j2 is 2N-1, then the result is undefined.
		// Hackers Delight Section 2-13 states this is the only overflow condition.
		if (j1.integerValue() == 0x8000_0000 && j2.integerValue() ==  0xFFFF_FFFF) {
			throw new WasmDivideOverflowException(
				UUID.fromString("4acf4694-fe77-4678-b37b-15b8391ee5fa"),
				opcodeName + "Integer divide Overflow");
		}

		//return the result of dividing j1 by j2, truncated toward zero.
		I32 result = new I32(j1.integerValue() / j2.integerValue());

		stack.push(result);
	}
}
