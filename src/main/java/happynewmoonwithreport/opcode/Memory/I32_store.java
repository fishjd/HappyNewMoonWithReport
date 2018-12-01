/*
 *  Copyright 2018 Whole Bean Software, LTD.
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
 * <b>Source:</b>  <a href="https://webassembly.github.io/spec/core/exec/instructions.html#exec-storen" target="_top">
 * https://webassembly.github.io/spec/core/exec/instructions.html#exec-storen
 * </a>
 * <p>
 * <h2>t.store memarg and t.storeN memarg</h2>
 * <ol>
 * <li>
 * Let F  be the current frame.
 * </li>
 * <li>
 * Assert: due to validation, F.module.memaddrs[0]
 * exists.
 * </li>
 * <li>Let a be the memory address F.module.memaddrs[0]
 * </li>
 * <li>
 * Assert: due to validation, S.mems[a] exists.
 * </li>
 * <li>
 * Let mem
 * be the memory instance S.mems[a]..
 * </li>
 * <li>
 * Assert: due to validation, a value of value type t
 * is on the top of the stack.
 * </li>
 * <li>
 * Pop the value t.const c
 * from the stack.
 * </li>
 * <li>
 * Assert: due to validation, a value of value type i32
 * is on the top of the stack.
 * </li>
 * <li>
 * Pop the value i32.const i
 * from the stack.
 * </li>
 * <li>
 * Let ea
 * be the integer i+memarg.offset
 * </li>
 * <li>
 * If N
 * is not part of the instruction, then:
 * <ul>
 * <li>
 * Let N be the bit width |t| of value type t
 * </li>
 * </ul>
 * </li>
 * <li>
 * If ea+N/8
 * is larger than the length of mem.data
 * , then:
 * <ul>
 * <li>
 * Trap.
 * </li>
 * </ul>
 * </li>
 * <li>
 * If N
 * is part of the instruction, then:
 * <ul>
 * <li>
 * Let n
 * <p>
 * be the result of computing wrap|t|,N(c)
 * </li>
 * <li>.
 * Let b*
 * be the byte sequence bytesiN(n)
 * </li>
 * </li>
 * <li>
 * Else:
 * <ul>
 * <li>
 * Let b* be the byte sequence bytest(c)
 * </li>
 * </ul>
 * </li>
 * <li>
 * <p>
 * Replace the bytes mem.data[ea:N/8]
 * with b*.
 * </li>
 * </ol>
 */
public class I32_store extends LoadBase {

	private MemoryArgument memoryArgument;
	private WasmFrame frame;
	private WasmStore store;
	private WasmStack stack;

	private I32_store() {
		super();
	}

	public I32_store(MemoryArgument memoryArgument, WasmFrame frame, WasmStore store, WasmStack stack) {
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
		// N = <Not Set>
		// type T = I32
		Object T = new I32(0);

		// 1. Let F be the current frame.
		// Frame set in constructor.

		// 2. Assert: due to validation, F.module.memaddrs[0] exists.
		UInt32 memoryIndex = new UInt32(0);
		final Boolean memoryExists = frame.getModule().memoryExists(memoryIndex);
		if (memoryExists == false) {
			throw new WasmRuntimeException(UUID.fromString("d8a21153-4018-4f0d-bdd1-0827eedb52a2"),
					"Memory %s does not exists", memoryIndex);
		}

		// 3. Let a be the memory address F.module.memaddrs[0].
		final MemoryType a = frame.getModule().getMemory(memoryIndex);

		// 4. Assert: due to validation, S.mems[a] exists.
		final Boolean memoryTypeExists = store.getMemoryAll().contains(a);
		if (memoryTypeExists == false) {
			throw new WasmRuntimeException(UUID.fromString("421da893-e7ba-4050-86b3-2352e9b00d0c"),
					"Memory type %s does not exists", a);

		}

		// 5. Let mem be the memory instance S.mems[a].
		final MemoryType mem = store.getMemoryAll().get(a);

		// 6. Assert: due to validation, a value of value type t is on the top of the stack.
		if ((stack.peek() instanceof I32) == false) {
			throw new WasmRuntimeException(UUID.fromString("a5fae44f-b2c4-496b-8869-7281bf074396"),
					"I32_Store: Step 6: Value type on stack is incorrect.  Expected I32 but type was " + stack.peek().toString());
		}

		// 7. Pop the value t.const c from the stack
		I32 c = (I32) stack.pop();

		// 8. Assert: due to validation, a value of value type I32 is on the top of the stack.
		if ((stack.peek() instanceof I32) == false) {
			throw new WasmRuntimeException(UUID.fromString("4e2ce874-f853-463e-b998-fe5b9ede7f05"),
					"I32_Store: Step 8: Value type on stack is incorrect.  Expected I32 but type was " + stack.peek().toString());
		}

		// 9. Pop the value t.const i from the stack
		I32 i = (I32) stack.pop();

		// 10. Let ea be i+memarg.offset.
		Long eaValue = i.longValue() + memoryArgument.getOffest().longValue();
		U32 ea = new U32(eaValue);   // ¿should this be U33?


		// 11. If N is NOT part of the instruction, then:
		//        a: Let N be the bit width |t| of value type t .
		U32 N = new U32(32L);

		// 12. If ea+N/8 is larger than the length of mem.data , then:
		//        a: Trap.
		Long length = ea.longValue() + (N.longValue() / 8);
		if (mem.hasMaximum().integerValue() == 1) {  // not in the webassembly specification.  This may line may be incorrect.
			Long memLength = mem.maximum().longValue();
			if (length > memLength) {
				throw new WasmRuntimeException(UUID.fromString("2ea707a2-d7b1-483c-9ca5-b8da56628716"),
						"I32_load: Step12: Trap.  Address  + size is too large. length = " + length + " memoryLength = " + memLength);
			}
		}

		// 13. If N is part of the instruction, then:
		//    a. Let n be the result of computing wrap|t|,N(c)
		//    b. Let b∗ be the byte sequence bytesiN(n).

		// Does Not apply as N is not part of the instruction

		// 14.  Else
		//  a. Let b∗ be the byte sequence bytes t (c).
		ByteUnsigned[] bytes = c.getBytes();

		// 15. Replace the bytes mem.data[ea:N/8] with b*.
		mem.set(ea.integerValue() + 0, bytes[0]);
		mem.set(ea.integerValue() + 1, bytes[1]);
		mem.set(ea.integerValue() + 2, bytes[2]);
		mem.set(ea.integerValue() + 3, bytes[3]);

	}


}
