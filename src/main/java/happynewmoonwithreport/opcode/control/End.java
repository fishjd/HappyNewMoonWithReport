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

package happynewmoonwithreport.opcode.control;

import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmLabel;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import java.util.ArrayList;
import java.util.UUID;

/**
 * This is the end.
 * <br>
 * // Exiting Blocks
 * // See:  https://webassembly.github.io/spec/core/exec/instructions.html#blocks
 */
public class End {
	private WasmInstanceInterface instance;
	private final String opCodeName = getClass().getName();


	private End() {
		super();
	}

	public End(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}

	/**
	 * Execute the opcode.
	 */
	public void execute() {
		WasmStack<Object> stack = instance.stack();
		// Exiting Blocks
		// See:  https://webassembly.github.io/spec/core/exec/instructions.html#blocks
		// Let m be the number of values on the top of the stack
		// Note:  This implementation counts the number items on the stack that are not Labels.
		// The specification is not clear here.
		Integer m = 0;
		for (Integer index = stack.size() - 1; 0 <= index; index--) {
			if (!(stack.peek(index) instanceof WasmLabel)) {
				m++;
			} else {
				break;
			}
		}

		//Pop the values val<sup>m<sup> from the stack.
		ArrayList<Object> stackTemp = new ArrayList<>(m);
		for (Integer index = 0; index < m; index++) {
			stackTemp.add(index, stack.pop());
		}

		//Assert: due to validation, the label L is now on the top of the stack.
		if ((stack.peek() instanceof WasmLabel) == false) {
			throw new WasmRuntimeException(UUID.fromString("f1ac0e85-8c64-4d54-b9c1-2c0b424f6709"),
				opCodeName + ": Type is incorrect on stack. Value should be of type WasmLabel");
		}
		//Pop the label from the stack.
		WasmLabel label = (WasmLabel) stack.pop();

		//Push val<sup>m</sup> back to the stack.
		for (Integer index = stackTemp.size() - 1; 0 <= index; index--) {
			stack.push(stackTemp.get(index));
		}

		//Jump to the position after the end of the structured control instruction
		// associated with the label L.
	}
}
