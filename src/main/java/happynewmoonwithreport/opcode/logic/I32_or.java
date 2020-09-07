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
package happynewmoonwithreport.opcode.logic;


import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.I32;
import java.util.UUID;

/**
 * Return the bitwise disjunction of i<sub>1</sub> and i<sub>2</sub>.
 * <h2>I32 or</h2>
 * <br>
 * <h3>Formal Definition</h3>
 * iand<sub>N</sub>(i<sub>1</sub>,i<sub>2</sub>)  =
 * ibits<sub>N</sub><sup>−1</sup>
 * (ibits<sub>N</sub>(i<sub>1</sub>) ∨
 * ibits<sub>N</sub>(i<sub>2</sub>))
 * <br>
 * ∨ = bitwise 'or'
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
 * <br>
 * Source:
 * <br>
 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-ior" target="_top">
 *   	OR Operator
 * </a>
 * <br>
 * <a href="https://webassembly.github.io/spec/core/exec/instructions.html#exec-binop" target="_top">
 * 		Binary Operator
 * </a>
 */
public class I32_or {
	private final String opCodeName = getClass().getName();
	private final String t1Type = "I32";
	private final String t2Type = "I32";

	private WasmInstanceInterface instance;

	private I32_or() {
		super();
	}

	public I32_or(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}

	/**
	 * Execute the opcode.
	 */
	public void execute() {
		WasmStack<Object> stack = instance.stack();

		//Pop the value t.const value2 from the stack.
		if ((stack.peek() instanceof I32) == false) {
			throw new WasmRuntimeException(UUID.fromString("bb037b9f-32a7-448a-a594-618e7f22c0e3"),
				opCodeName + ": Value2 type is incorrect. Value should be of type " + t1Type);
		}
		I32 value2 = (I32) stack.pop();

		//Pop the value t.const value1 from the stack.
		if ((stack.peek() instanceof I32) == false) {
			throw new WasmRuntimeException(UUID.fromString("678d01a3-cf7d-4492-9dec-04e931e4b574"),
				opCodeName + ": Value1 type is incorrect. Value should be of type " + t2Type);
		}
		I32 value1 = (I32) stack.pop();

		// Let c(i.e. result) be a possible result of computing binopt(value1,value2).

		// Note the Wasm Specification dictates the uses of the bitwise or '|' operator in the
		// formal definition, not the logical or "||" operator.
		I32 result = new I32(value1.integerValue() | value2.integerValue());

		// Push the value t.const c(i.e. result) to the stack.
		stack.push(result);
	}
}
