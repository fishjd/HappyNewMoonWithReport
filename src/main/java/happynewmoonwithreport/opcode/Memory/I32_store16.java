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
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.WasmStore;
import happynewmoonwithreport.type.JavaType.ByteUnsigned;
import happynewmoonwithreport.type.MemoryArgument;
import happynewmoonwithreport.type.MemoryType;
import happynewmoonwithreport.type.U32;

/**
 * <h1>i32_store16</h1> Store 16 bits from the stack to memory.
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
 * Let n be the result of computing wrap|t|,N(c)
 * </li>
 * <li>
 * Let b* be the byte sequence bytesiN(n)
 * </li>
 * </ul>
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
public class I32_store16 extends StoreBase {

	private I32_store16() {
		super();
	}

	public I32_store16(MemoryArgument memoryArgument, WasmFrame frame, WasmStore store,
		WasmStack stack) {
		super(memoryArgument, frame, store, stack);

		N = new U32(16);
	}

	/* package_private */ void step15_ReplaceBytes(MemoryType mem, U32 ea, ByteUnsigned[] bytes) {
		mem.set(ea.integerValue() + 0, bytes[2]);
		mem.set(ea.integerValue() + 1, bytes[3]);
	}

}
