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
package happynewmoonwithreport.opcode.comparison;


import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.I32;
import happynewmoonwithreport.type.I64;
import happynewmoonwithreport.type.U64;
import java.util.UUID;

/**
 * I32 Less than Unsigned  (i32_le_u)
 * <br>
 * <b>Note this is the same for all Relative Operations</b>
 * <br>
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
 * <br>
 * </li>
 * </ol>
 * Source:  <a href="https://webassembly.github.io/spec/core/appendix/index-instructions.html"
 * target="_top"> https://webassembly.github.io/spec/core/appendix/index-instructions.html
 * </a>
 */
public class I64_le_u {
	private WasmInstanceInterface instance;

	private I64_le_u() {
		super();
	}

	public I64_le_u(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}


	/**
	 * Execute the opcode.
	 */
	public void execute() {
		WasmStack<Object> stack = instance.stack();
		if ((stack.peek() instanceof I64) == false) {
			throw new WasmRuntimeException(UUID.fromString("bb35e895-dc00-45d9-a5fa-9e19032569ab"),
				"I64_le_u: Value2 type is incorrect");
		}
		I64 value2 = (I64) stack.pop();

		if ((stack.peek() instanceof I64) == false) {
			throw new WasmRuntimeException(UUID.fromString("5a702a2b-6bd8-43c4-9183-a9431b1dcb2c"),
				"I64_le_u: Value1 type is incorrect");
		}
		I64 value1 = (I64) stack.pop();

		// these values are unsigned (positive/negative) values
		U64 value2Unsigned = value2.unsignedValue();
		U64 value1Unsigned = value1.unsignedValue();

		// Do the comparison.
		I32 result = value1Unsigned.lessThanEqual(value2Unsigned);

		stack.push(result);
	}


}
