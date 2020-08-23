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


import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmLabel;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.I32;
import java.util.UUID;

/**
 * Block opcode.
 * <ol>
 * <li>
 * Assert: due to validation expand<sub>F</sub>(Blocktype) is defined.
 * </li><li>
 * Let [t<sup>m</sup><sub>1</sub>] -> [t<sup>n</sup><sub>2</sub>] be the function type
 * expand<sub>F</sub>(bloctype).
 * </li><li>
 * Let L be the label whose arity is n and whose continuation is the end of the block.
 * </li><li>
 * Assert: due to validation, there are at least m values on top of the stack.
 * </li><li>
 * Pop the values val<sup>m</sup> from the stack.
 * </li><li>
 * Enter the block val<sup>m</sup> instr∗ with label L.
 * </li>
 * </ol>
 * <p>
 * Source:
 * <a href="https://webassembly.github.io/spec/core/exec/instructions.html#exec-block" target="_top">
 *     https://webassembly.github.io/spec/core/exec/instructions.html#exec-block
 * </a>
 */
public class Block {
	private WasmInstanceInterface instance;
	private final String opCodeName = getClass().getName();

	private Block() {
		super();
	}

	public Block(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}

	/**
	 * Execute the opcode.
	 */
	public void execute() {
		// Assert: due to validation expand<sub>F</sub>(Blocktype) is defined.
		// I am not sure what this means.  @TODO

		// Let [t<sup>m</sup><sub>1</sub>] -> [t<sup>n</sup><sub>2</sub>] be the function type
		// expand<sub>F</sub>(blocktype).

		I32 m = new I32(0);
		// For now we are going to set m to zero.  I don't understand where
		// to determine the value of m.  // TODO
		m.setValue(0);
		// Assert: due to validation, there are at least m values on top of the stack.
		WasmStack<Object> stack = instance.stack();
		if (stack.size() < m.integerValue()) {
			throw new WasmRuntimeException(UUID.fromString("ef6d31d8-301c-4c6c-8d86-ed7402fc5dad"),
				opCodeName + ": Not enough values on the stack.  The stack must contain "
				+ m.toString() + " values");
		}

		// Pop the values val<sup>m</sup> from the stack.
		for (Integer index = 0; index < m.integerValue(); index++) {
			// The way I read the specification these values popped off the stack are lost.
			stack.pop();
		}

		BytesFile code = instance.getCode();

		WasmLabel label = new WasmLabel(code);

		// Entering Blocks
		// See:  https://webassembly.github.io/spec/core/exec/instructions.html#blocks
		instance.stack().push(label);
	}
}
