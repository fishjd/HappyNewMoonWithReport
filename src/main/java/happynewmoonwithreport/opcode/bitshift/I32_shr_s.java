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
package happynewmoonwithreport.opcode.bitshift;


import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.I32;
import java.util.UUID;

/**
 * I32 Shr_s opcode.
 * <br>
 * ishr_s<sub>N</sub>(i<sub>1</sub>,i<sub>2</sub>)
 * <ul>
 *     <li>Let <i>k</i> be i<sub>2</sub> modulo N.</li>
 *     <li>Return the result of shifting i<sub>1</sub> right by <i>k</i> bits,
 *     extended with the most significant bit of the original value.
 *
 * </ul>
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
 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-ishr-s" target="_top">
 *   	Shr_s Operator
 * </a>
 * <br>
 * <a href="https://webassembly.github.io/spec/core/exec/instructions.html#exec-binop" target="_top">
 * 		Binary Operator
 * </a>
 */
public class I32_shr_s {
	private final String opCodeName = getClass().getName();
	private final Integer N = 32;   // number of bits
	private final String t1Type = "I32";
	private final String t2Type = "I32";

	private WasmInstanceInterface instance;

	private I32_shr_s() {
		super();
	}

	public I32_shr_s(WasmInstanceInterface instance) {
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
			throw new WasmRuntimeException(UUID.fromString("1337b561-7faa-4e0d-912e-2e377b2fd660"),
				opCodeName + ": Value2 type is incorrect. Value should be of type " + t1Type);
		}
		I32 value2 = (I32) stack.pop();

		//Pop the value t.const value1 from the stack.
		if ((stack.peek() instanceof I32) == false) {
			throw new WasmRuntimeException(UUID.fromString("22db161f-451d-4a06-8562-b8e767632888"),
				opCodeName + ": Value1 type is incorrect. Value should be of type " + t2Type);
		}
		I32 value1 = (I32) stack.pop();

		// Let c(i.e. result) be a possible result of computing binopt(value1,value2).

		// Let k be i2 modulo N.
		Integer k = value2.integerValue() % N;

		// Return the result of shifting i1 right  by k bits, modulo 2^N
		I32 result = new I32(value1.integerValue() >> k);   // Java handles the modulo 2^N

		// Push the value t.const c(i.e. result) to the stack.
		stack.push(result);
	}
}
