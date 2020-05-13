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
import happynewmoonwithreport.type.I32;
import happynewmoonwithreport.type.I64;
import happynewmoonwithreport.type.S64;

/**
 * I64 Greater than or equal to Signed  (i64_ge_s)
 * <p>
 * <b>Note this is the same for all Relative Operations</b>
 * <p>
 * t.relop
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
 * Let c be the result of computing relopt(c1,c2).
 * </li>
 * <li>
 * Push the value i32.const c to the stack.
 * <p>
 * </li>
 * </ol>
 * Source:  <a href="https://webassembly.github.io/spec/core/appendix/index-instructions.html"
 * target="_top"> https://webassembly.github.io/spec/core/appendix/index-instructions.html
 * </a>
 */
public class I64_ge_s {
	private WasmInstanceInterface instance;

	private I64_ge_s() {
		super();
	}

	public I64_ge_s(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}


	/**
	 * Execute the opcode.
	 */
	public void execute() {
		WasmStack<Object> stack = instance.stack();
		if ((stack.peek() instanceof I64) == false) {
			throw new WasmRuntimeException(UUID.fromString("97ef80d8-c0c6-420b-9a05-8b65946a8af5"),
				"I64_ge_s: Value2 type is incorrect");
		}
		I64 value2 = (I64) stack.pop();

		if ((stack.peek() instanceof I64) == false) {
			throw new WasmRuntimeException(UUID.fromString("9a4c917b-b6a1-4d44-99f6-315240f77b89"),
				"I64_ge_s: Value1 type is incorrect");
		}
		I64 value1 = (I64) stack.pop();

		// these values are signed (positive/negative) values
		S64 value2Signed = value2.signedValue();
		S64 value1Signed = value1.signedValue();

		// Do the comparison.
		I32 result = value1Signed.greaterThanEqual(value2Signed);

		stack.push(result);
	}


}
