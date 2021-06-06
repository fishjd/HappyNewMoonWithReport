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
package happynewmoonwithreport.opcode;

import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.type.I64;

/**
 * Constant Int64
 * <br>
 * <b>Note this is the same for all Constant Operations</b>
 * <br>
 * t.constant c
 * <ol>
 * <li>
 * Push the value t.const c to the stack.   t = Int64
 * </li>
 * </ol>
 * <br>
 * Source:
 * <a href="https://webassembly.github.io/spec/core/exec/instructions.html#exec-const" target="_top">
 * https://webassembly.github.io/spec/core/exec/instructions.html#exec-const
 * </a>
 */
public class I64_const {
	private WasmInstanceInterface instance;

	private I64_const() {
		super();
	}

	public I64_const(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}

	/**
	 * Execute the opcode.
	 *
	 * @param value value to push on stack.
	 */
	public void execute(I64 value) {
		instance.stack().push(value);

	}
}
