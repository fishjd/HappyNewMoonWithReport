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


import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;

import java.util.UUID;

/**
 * Drop (Drop) - pop a value off the stack.   The value is discarded.
 * <p>
 * <ol>
 * <li>Assert: due to validation, a value is on the top of the stack.</li
 * <li>Pop the value from the stack.</li>
 * </ol>
 * Source:  <a href="https://webassembly.github.io/spec/core/exec/instructions.html#exec-drop" target="_top">
 * https://webassembly.github.io/spec/core/exec/instructions.html#exec-drop
 * </a>
 */
public class Drop {
	private WasmInstanceInterface instance;

	private Drop() {
		super();
	}

	public Drop(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}


	/**
	 * Execute the opcode.
	 */
	public void execute() {
		// 1 Assert: due to validation, a value is on the top of the stack.
		WasmStack<Object> stack = instance.stack();
		if (stack.empty() == true) {
			throw new WasmRuntimeException(UUID.fromString("f42f4399-988b-46ce-b73b-4dcdccae576f"),
					"Stack must have at least one value");
		}

		// 2. Pop the value from the stack
		stack.pop();

	}


}
