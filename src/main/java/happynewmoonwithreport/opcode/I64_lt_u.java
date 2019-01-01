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
import happynewmoonwithreport.type.U64;

/**
 * I32 Less than Signed  (i32_lt_u)
 * <p>
 * <b>Note this is the same for all Relative Operations</b>
 * <p>
 * t.relop <ol> <li> Assert: due to validation, two values of value type t are on the top of the
 * stack. </li> <li> Pop the value t.const c2 from the stack. </li> <li> Pop the value t.const c1
 * from the stack. </li> <li> Let c be the result of computing relopt(c1,c2). </li> <li> Push the
 * value i32.const c to the stack.
 * <p>
 * </li> </ol>
 * <p>
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/core/appendix/index-instructions.html"
 * target="_top"> https://webassembly.github.io/spec/core/appendix/index-instructions.html </a>
 * </a>
 */
public class I64_lt_u {
	private WasmInstanceInterface instance;

	private I64_lt_u() {
		super();
	}

	public I64_lt_u(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}


	/**
	 * Execute the opcode.
	 */
	public void execute() {
		WasmStack<Object> stack = instance.stack();
		if ((stack.peek() instanceof I64) == false) {
			throw new WasmRuntimeException(UUID.fromString("513578cf-b2c6-4c15-9aeb-c10e6201298f"),
										   "I64_lt_u: Value2 type is incorrect");
		}
		I64 value2 = (I64) stack.pop();

		if ((stack.peek() instanceof I64) == false) {
			throw new WasmRuntimeException(UUID.fromString("f58b9de7-de1e-4da9-8816-492c3ab14be3"),
										   "I64_lt_u: Value1 type is incorrect");
		}
		I64 value1 = (I64) stack.pop();

		// these values are signed (positive/negative) values
		U64 value2Unsigned = value2.unsignedValue();
		U64 value1Unsigned = value1.unsignedValue();

		// Do the comparison.
		I32 result = value1Unsigned.lessThan(value2Unsigned);

		stack.push(result);
	}


}
