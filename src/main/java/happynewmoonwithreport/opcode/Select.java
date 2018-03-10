/*
 *  Copyright 2017 Whole Bean Software, LTD.
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


import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.I32;

import java.util.UUID;

/**
 * <h1>Select</h1>  - Select one of two values on the stack.
 * <ol>
 * <li>
 * Assert: due to validation, a value of value type i32 is on the top of the stack.
 * </li>
 * <li>
 * Pop the value i32.const c from the stack.
 * </li>
 * <li>
 * Assert: due to validation, two more values (of the same value type) are on the top of the stack.
 * </li>
 * <li>
 * Pop the value val2 from the stack.
 * </li>
 * <li>
 * Pop the value val1 from the stack.
 * </li>
 * <li>
 * If c  is not 0, then:
 * <p>
 * Push the value val1 back to the stack.
 * </li>
 * <li>
 * Else:
 * <p>
 * Push the value val2 back to the stack.
 * </li>
 * </ol>
 */
public class Select {
	private WasmInstanceInterface instance;

	private Select() {
		super();
	}

	public Select(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}


	/**
	 * Execute the opcode.
	 */
	public void execute() {
		// 1 Assert: due to validation, a value of value type i32 is on the top of the stack.
		WasmStack<Object> stack = instance.stack();
		Object value = stack.peek();
		if ((value instanceof I32) == false) {
			throw new WasmRuntimeException(UUID.fromString("78c61ec8-a580-40b0-ad97-bd40d6d55739"),
					"Stack value must be of type I32");
		}
		// 2 Pop the value i32.const c from the stack.
		I32 c = (I32) stack.pop();

		// 3 Assert: due to validation, two more values (of the same value type) are on the top of the stack.
		Object val_1 = stack.peek(1);
		Object val_2 = stack.peek(0);
		String val_1_type = val_1.getClass().getTypeName();
		String val_2_type = val_2.getClass().getTypeName();
		if (val_1_type.equals(val_2_type) == false) {
			throw new WasmRuntimeException(UUID.fromString("bd046bf9-6aea-42a4-b1bf-31e74d64f95c"),
					"The two types must be the equals.  type of value 1 = " + val_1_type + " type of val2 = " + val_2_type);
		}

		// 4 Pop the value val2 from the stack.
		val_2 = stack.pop();

		// 5 Pop the value val1 from the stack.
		val_1 = stack.pop();

		// 6  If c  is not 0, then Push the value val1 back to the stack.
		if (c.equals(new I32(0)) == false) {
			stack.push(val_1);
		} else {
			// 7 else then push val2 back on the stack
			stack.push(val_2);
		}

	}


}
