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
import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.I32;
import happynewmoonwithreport.type.I64;

/**
 * Remainder 32 unsigned.
 * <br>
 * <br>
 * <h2>
 * irem_u<sub><i>N</i></sub>(<i>i</i><sub>1</sub>,<i>i</i><sub>2</sub>)
 * </h2>
 * 	<ul>
 * 		<li>
 * 			If i<sub>2</sub> is 0, then the result is undefined.
 * 		</li>
 * 		<li>
 * 			Else, return the remainder of dividing i<sub>1</sub> by i<sub>2</sub>, with the sign
 * 			of the dividend i<sub>1</sub>.
 * 		</li>
 * 	</ul>
 * <br>
 * <br>
 * Note the below is the same for all Binary Operations
 * <br>
 * <br>
 * <h3>t.binop</h3>
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
 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-irem-u"
 * target="_top">
 * 		Remainder Unsigned Operator
 * </a>
 * <br>
 * <a href="https://webassembly.github.io/spec/core/exec/instructions.html#exec-binop" target="_top">
 * 		Binary Operator
 * </a>
 */
public class I64_rem_u {
	private final String opcodeName = getClass().getName();
	private final String t1Type = "I64";
	private final String t2Type = "I64";

	private WasmInstanceInterface instance;

	private I64_rem_u() {
		super();
	}

	public I64_rem_u(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}

	/**
	 * Execute the opcode.
	 */
	public void execute() {
		WasmStack<Object> stack = instance.stack();
		if ((stack.peek() instanceof I64) == false) {
			throw new WasmRuntimeException(UUID.fromString("5a5e465e-a559-4240-a4bf-052cb519ee8b"),
				opcodeName + ": Value2 type is incorrect. Value should be of type " + t1Type);
		}
		I64 value2 = (I64) stack.pop();

		if ((stack.peek() instanceof I64) == false) {
			throw new WasmRuntimeException(UUID.fromString("6c6c8160-c566-461d-9ae9-76e64b81e97b"),
				opcodeName + ": Value1 type is incorrect. Value should be of type " + t2Type);
		}
		I64 value1 = (I64) stack.pop();


		//If j2 is 0, then the result is undefined.
		if (value2.longValue() == 0) {
			throw new WasmDivideByZeroException(
				UUID.fromString("668d8f31-6e66-4226-ba20-4d2dacafe3c9"),
				opcodeName + "Divide by zero is not defined");
		}

		//return the remainder of dividing i1 by i2, with the sign of the dividend i1.
		I64 result =
			new I64(Long.remainderUnsigned(value1.longValue(), value2.longValue()));

		stack.push(result);
	}
}
