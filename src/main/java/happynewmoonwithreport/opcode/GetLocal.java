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

import happynewmoonwithreport.WasmFrame;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.DataTypeNumber;
import happynewmoonwithreport.type.I32;

/**
 * Get Local Opcode
 * <ol>
 * <li>
 * Let F be the current frame.
 * </li>
 * <li>
 * Assert: due to validation, F.locals[x] exists.
 * </li>
 * <li>
 * Let val be the value F.locals[x]
 * </li>
 * <li>
 * Push the value val to the stack.
 * </ol>
 * <p>
 * Source:
 * <a href="https://webassembly.github.io/spec/core/exec/instructions.html#variable-instructions" target="_top">
 * https://webassembly.github.io/spec/core/exec/instructions.html#variable-instructions
 * </a>
 * <p>
 * Source:
 * <a href="http://webassembly.org/docs/binary-encoding/#variable-access-described-here" target="_top">
 * http://webassembly.org/docs/binary-encoding/#variable-access-described-here
 * </a>
 */
public class GetLocal {

	private WasmFrame frame;
	private WasmStack stack;

	private GetLocal() {
		super();
	}

	public GetLocal(WasmFrame frame, WasmStack stack) {
		this();
		this.frame = frame;
		this.stack = stack;
	}

	/**
	 * Execute the opcode
	 *
	 * @param index index in to the vector that contains the local variable.
	 */
	public void execute(I32 index) {
		// 1 Frame set in constructor.

		// 2 validate.
		if ((index.integerValue() < frame.localAll().size()) == false) {
			throw new WasmRuntimeException(UUID.fromString("dcbf3c1d-334a-451d-9010-e32bdc876e9d"),
				"getLocal: Local variable " + index.integerValue() + " does not exist");
		}

		// 3. value
		DataTypeNumber value = frame.localAll().get(index.integerValue());

		// 4. Push
		stack.push(value);
	}


}
