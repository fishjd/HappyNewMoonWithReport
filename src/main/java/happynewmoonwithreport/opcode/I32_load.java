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


import happynewmoonwithreport.WasmFrame;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.type.MemoryType;
import happynewmoonwithreport.type.UInt32;

import java.util.UUID;

/**
 * <h1>i32_load</h1> Load an i32 value from memory to the stack.
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/core/exec/instructions.html#exec-load" target="_top">
 * https://webassembly.github.io/spec/core/exec/instructions.html#exec-load
 * </a>
 * <p>
 * <p>
 * <h2>t.load memarg and t.loadN_sx memarg</h2>
 * <ol>
 * <li>
 * Let F be the current frame.
 * </li><li>
 * Assert: due to validation, F.module.memaddrs[0]
 * exists.
 * </li><li>
 * Let a be the memory address F.module.memaddrs[0].
 * </li><li>
 * Assert: due to validation, S.mems[a] exists.
 * </li><li>
 * Let mem be the memory instance S.mems[a].
 * </li><li>
 * Assert: due to validation, a value of value type i32 is on the top of the stack.
 * </li><li>
 * Pop the value i32.const i from the stack.
 * </li><li>
 * Let ea be i+memarg.offset.
 * </li><li>
 * If N is not part of the instruction, then:
 * <ol type="a">
 * <li>
 * Let N be the bit width |t| of value type t .
 * </li> </ol>
 * </li><li>
 * If ea+N/8 is larger than the length of mem.data , then:
 * <ol type="a">
 * <li>
 * Trap.
 * </li></ol>
 * </li><li>
 * Let b∗ be the byte sequence mem.data[ea:N/8].
 * </li><li>
 * If N and sx are part of the instruction, then:
 * <ol type="a">
 * <li>
 * Let n be the integer for which bytesiN(n)=b∗.
 * </li><li>
 * Let c be the result of computing extend_sxN,|t|(n).
 * </li> </ol>
 * </li><li>
 * Else:
 * <ol type="a">
 * <li>
 * Let c be the constant for which bytes<sub>t</sub>(c)=b∗.
 * </li>
 * </ol>
 * <li>
 * Push the value t.const c  to the stack.
 * </li>
 * </ol>
 */
public class I32_load {

	private WasmFrame frame;

	private I32_load() {
		super();
	}

	public I32_load(WasmFrame frame) {
		this();
		this.frame = frame;
	}


	/**
	 * Execute the opcode.
	 */
	public void execute() {
		// 1. Let F be the current frame.
		// Frame set in constructor.

		// 2. Assert: due to validation, F.module.memaddrs[0] exists.
		UInt32 memoryIndex = new UInt32(0);
		final Boolean memoryExists = frame.getModule().memoryExists(memoryIndex);
		if (memoryExists == false) {
			throw new WasmRuntimeException(UUID.fromString("35030ef5-2f4a-496c-8e67-06245e05d56d"),
					"Memory %s does not exists", memoryIndex);
		}

		// 3. Let a be the memory address F.module.memaddrs[0].
		final MemoryType a = frame.getModule().getMemory(memoryIndex);

		// 4. Assert: due to validation, S.mems[a] exists.


	}


}
