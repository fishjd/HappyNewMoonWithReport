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
package happynewmoonwithreport.opcode.Memory;


import happynewmoonwithreport.WasmFrame;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.WasmStore;
import happynewmoonwithreport.type.*;
import happynewmoonwithreport.type.JavaType.ByteUnsigned;

import java.util.UUID;

/**
 * <h1>i32_load</h1> Load an i32 value from memory to the stack.
 * <p>
 * <p>
 * Memory Instutions<br>
 * <p>
 * <b>Source:</b>  <a href="https://webassembly.github.io/spec/core/syntax/instructions.html#memory-instructions" target="_top">
 * https://webassembly.github.io/spec/core/syntax/instructions.html#memory-instructions
 * </a>
 * <br>
 * <p>
 * Memory Overview<br>
 * <b>Source:</b>  <a href="https://webassembly.github.io/spec/core/syntax/instructions.html#syntax-instr-memory" target="_top">
 * https://webassembly.github.io/spec/core/syntax/instructions.html#syntax-instr-memory
 * </a>
 * </p>
 * <br>
 * Exec:
 * <p>
 * <b>Source:</b>  <a href="https://webassembly.github.io/spec/core/exec/instructions.html#exec-load" target="_top">
 * https://webassembly.github.io/spec/core/exec/instructions.html#exec-load
 * </a>
 * <p>
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
 * Let N be the bit width |t| of value type t.
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
public class I32_load extends LoadBase {

	private MemoryArgument memoryArgument;
	private WasmFrame frame;
	private WasmStore store;
	private WasmStack stack;

	private I32_load() {
		super();
	}

	public I32_load(MemoryArgument memoryArgument, WasmFrame frame, WasmStore store, WasmStack stack) {
		this();
		this.memoryArgument = memoryArgument;
		this.frame = frame;
		this.store = store;
		this.stack = stack;
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
		final Boolean memoryTypeExists = store.getMemoryAll().contains(a);
		if (memoryTypeExists == false) {
			throw new WasmRuntimeException(UUID.fromString("3e1eac11-9acd-46e4-ab62-08e34f3e3f2b"),
					"Memory type %s does not exists", a);

		}

		// 5. Let mem be the memory instance S.mems[a].
		final MemoryType mem = store.getMemoryAll().get(a);

		// 6. Assert: due to validation, a value of value type i32 is on the top of the stack.
		if ((stack.peek() instanceof I32) == false) {
			throw new WasmRuntimeException(UUID.fromString("edba1731-664f-4756-9374-1365e8b19a7a"),
					"I32_load: Step 6: Value type  on stack is incorrect.  Expected I32 but type was " + stack.peek().toString());
		}

		// 7. Pop the value i32.const i from the stack.
		I32 i = (I32) stack.pop();

		// 8. Let ea be i+memarg.offset.
		Long eaValue = i.longValue() + memoryArgument.getOffest().longValue();
		U32 ea = new U32(eaValue);   // ¿should this be U33?

		// 9. If N is not part of the instruction, then:
		//        a: Let N be the bit width |t| of value type t .
		U32 N = new U32(32L);

		// 10. If ea+N/8 is larger than the length of mem.data , then:
		//        a: Trap.
		Long length = ea.longValue() + (N.longValue() / 8);
		if (mem.hasMaximum().integerValue() == 1) {  // not is spec.  This may line may be incorrect.
			Long memLength = mem.maximum().longValue();
			if (length > memLength) {
				throw new WasmRuntimeException(UUID.fromString("518fe904-05b5-492f-9a78-d89b30bb6551"),
						"I32_load: Step 10: Trap.  Address  + size is too large. length = " + length + " memoryLength = " + memLength);
			}
		}

		// 11. Let b∗ be the byte sequence mem.data[ea:N/8].
		ByteUnsigned[] bytes = new ByteUnsigned[4];
		Integer eaIntegerValue = ea.integerValue();
		bytes[0] = mem.get(eaIntegerValue + 0);
		bytes[1] = mem.get(eaIntegerValue + 1);
		bytes[2] = mem.get(eaIntegerValue + 2);
		bytes[3] = mem.get(eaIntegerValue + 3);

		// 12. If N and sx are part of the instruction, then:
		//        a: Let n be the integer for which bytesiN(n)=b∗.
		//        b: Let c be the result of computing extend_sxN,|t|(n).

		/* does not apply for i32_load does not contain sx **/

		// 13. Else:
		//        a: Let c be the constant for which bytes<sub>t</sub>(c)=b∗.
		I32 c = new I32(bytes);

		// 14. Push the value t.const c  to the stack.
		stack.push(c);


	}


}
