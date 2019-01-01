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


import java.util.UUID;

import happynewmoonwithreport.WasmFrame;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.WasmStore;
import happynewmoonwithreport.type.I32;
import happynewmoonwithreport.type.JavaType.ByteUnsigned;
import happynewmoonwithreport.type.MemoryArgument;
import happynewmoonwithreport.type.MemoryType;
import happynewmoonwithreport.type.U32;
import happynewmoonwithreport.type.UInt32;

/**
 * <h1>i32_storeN </h1> Store N bits from the stack to memory.
 * <p>
 * <p>
 * Memory Instructions<br>
 * <p>
 * <b>Source:</b>
 * <a href="https://webassembly.github.io/spec/core/syntax/instructions.html#memory-instructions"
 * target="_top"> https://webassembly.github.io/spec/core/syntax/instructions
 * .html#memory-instructions
 * </a>
 * <br>
 * <p>
 * Memory Overview<br>
 * <b>Source:</b>
 * <a href="https://webassembly.github.io/spec/core/syntax/instructions.html#syntax-instr-memory"
 * target="_top"> https://webassembly.github.io/spec/core/syntax/instructions
 * .html#syntax-instr-memory
 * </a>
 * </p>
 * <br>
 * Exec:
 * <p>
 * <b>Source:</b>
 * <a href="https://webassembly.github.io/spec/core/exec/instructions.html#exec-storen"
 * target="_top"> https://webassembly.github.io/spec/core/exec/instructions.html#exec-storen
 * </a>
 * <p>
 * <h2>t.store memarg and t.storeN memarg</h2>
 * <ol>
 * <li>
 * Let F  be the current frame.
 * </li>
 * <li>
 * Assert: due to validation, F.module.memaddrs[0] exists.
 * </li>
 * <li>Let a be the memory address F.module.memaddrs[0]
 * </li>
 * <li>
 * Assert: due to validation, S.mems[a] exists.
 * </li>
 * <li>
 * Let mem be the memory instance S.mems[a]..
 * </li>
 * <li>
 * Assert: due to validation, a value of value type t is on the top of the stack.
 * </li>
 * <li>
 * Pop the value t.const c from the stack.
 * </li>
 * <li>
 * Assert: due to validation, a value of value type i32 is on the top of the stack.
 * </li>
 * <li>
 * Pop the value i32.const i from the stack.
 * </li>
 * <li>
 * Let ea be the integer i+memarg.offset
 * </li>
 * <li>
 * If N is not part of the instruction, then:
 * <ul>
 * <li>
 * Let N be the bit width |t| of value type t
 * </li>
 * </ul>
 * </li>
 * <li>
 * If ea+N/8 is larger than the length of mem.data , then:
 * <ul>
 * <li>
 * Trap.
 * </li>
 * </ul>
 * </li>
 * <li>
 * If N is part of the instruction, then:
 * <ul>
 * <li>
 * Let n
 * <p>
 * be the result of computing wrap|t|,N(c)
 * </li>
 * <li>.
 * Let b* be the byte sequence bytesiN(n)
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
 * Replace the bytes mem.data[ea:N/8] with b*.
 * </li>
 * </ol>
 */
public abstract class StoreBase {

	MemoryArgument memoryArgument;
	WasmFrame frame;
	WasmStore store;
	WasmStack stack;

	/* package_private */ StoreBase() {
		super();
	}

	public StoreBase(MemoryArgument memoryArgument, WasmFrame frame, WasmStore store,
		WasmStack stack) {
		this();
		this.memoryArgument = memoryArgument;
		this.frame = frame;
		this.store = store;
		this.stack = stack;
	}

	/* package_private */ UInt32 memoryIndex;
	/* package_private */ U32 N;

	/**
	 * Execute the opcode.
	 */
	public void execute() {
		// 1. Let F be the current frame.
		// Frame set in constructor.

		// 2. Assert: due to validation, F.module.memaddrs[0] exists.
		memoryIndex = new UInt32(0);
		final Boolean memoryExists = frame.getModule().memoryExists(memoryIndex);
		if (memoryExists == false) {
			throw new WasmRuntimeException(UUID.fromString("1b1ccfdc-892c-4d12-b6a8-f15e5986f0a4"),
										   "Memory %s does not exists", memoryIndex);
		}

		// 3. Let a be the memory address F.module.memaddrs[0].
		final MemoryType a = frame.getModule().getMemory(memoryIndex);

		// 4. Assert: due to validation, S.mems[a] exists.
		final Boolean memoryTypeExists = store.getMemoryAll().contains(a);
		if (memoryTypeExists == false) {
			throw new WasmRuntimeException(UUID.fromString("c2ceaaf8-3872-4050-aa20-c503053c9a29"),
										   "Memory type %s does not exists", a);

		}

		// 5. Let mem be the memory instance S.mems[a].
		final MemoryType mem = store.getMemoryAll().get(a);

		// 6. Assert: due to validation, a value of value type t is on the top of the stack.
		if ((stack.peek() instanceof I32) == false) {
			throw new WasmRuntimeException(UUID.fromString("4302d8c6-79cb-40df-a776-516b5e1e3f9d"),
										   "I32_Store: Step 6: Value type on stack is incorrect.  "
											   + "Expected I32 but type was " + stack.peek()
																					 .toString());
		}

		// 7. Pop the value t.const c from the stack
		I32 c = (I32) stack.pop();

		// 8. Assert: due to validation, a value of value type I32 is on the top of the stack.
		if ((stack.peek() instanceof I32) == false) {
			throw new WasmRuntimeException(UUID.fromString("09a2e693-ea94-4040-8e53-02f4cf54cdb6"),
										   "I32_Store: Step 8: Value type on stack is incorrect.  "
											   + "Expected I32 but type was " + stack.peek()
																					 .toString());
		}

		// 9. Pop the value t.const i from the stack
		I32 i = (I32) stack.pop();

		// 10. Let ea be i+memarg.offset.
		Long eaValue = i.longValue() + memoryArgument.getOffest().longValue();
		U32 ea = new U32(eaValue);   // ¿should this be U33?


		// 11. If N is NOT part of the instruction, then:
		//        a: Let N be the bit width |t| of value type t .
		if (N == null) {
			N = new U32(32L);
		}

		// 12. If ea+N/8 is larger than the length of mem.data , then:
		//        a: Trap.
		Long length = ea.longValue() + (N.longValue() / 8);
		if (mem.hasMaximum().integerValue()
			== 1) {  // not in the webassembly specification.  This may line may be incorrect.
			Long memLength = mem.maximum().longValue();
			if (memLength < length) {
				throw new WasmRuntimeException(
					UUID.fromString("8486a6d2-31b4-4035-bf27-1d76739bf309"),
					"I32_Store: Step12: Trap.  Address  + size is too large. length = " + length
						+ " memoryLength = " + memLength);
			}
		}

		ByteUnsigned[] bytes;
		// 13. If N is part of the instruction, then:
		if (N != null) {
			//    a. Let n be the result of computing wrap|t|,N(c)
			I32 n = new I32(wrap(N.integerValue(), c.integerValue()));


			//    b. Let b∗ be the byte sequence bytesiN(n).
			bytes = n.getBytes();
		} else {
			// 14.  Else
			//  a. Let b∗ be the byte sequence bytes t (c).
			bytes = c.getBytes();
		}

		// 15. Replace the bytes mem.data[ea:N/8] with b*.
		step15_ReplaceBytes(mem, ea, bytes);

	}

	/* package_private */
	abstract void step15_ReplaceBytes(MemoryType mem, U32 ea, ByteUnsigned[] bytes);

	/**
	 * wrap<sub>M,N</sub>(i)
	 * <p>
	 * Return i modulo 2<sup>N</sup>
	 * <p>
	 * <b>Source:</b>  <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-wrap"
	 * target="_top"> https://webassembly.github.io/spec/core/exec/numerics.html#op-wrap
	 * </a>
	 *
	 * @return
	 */
	public static Integer wrap(// Integer M,
		Integer N, Integer i) {
		Integer result;
		Double pow = Math.pow(2, N);
		result = (i % pow.intValue());
		return result;

	}

}
