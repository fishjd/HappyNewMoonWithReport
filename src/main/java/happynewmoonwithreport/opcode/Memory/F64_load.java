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
package happynewmoonwithreport.opcode.Memory;


import happynewmoonwithreport.WasmFrame;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.WasmStore;
import happynewmoonwithreport.type.F64;
import happynewmoonwithreport.type.JavaType.ByteUnsigned;
import happynewmoonwithreport.type.MemoryArgument;
import happynewmoonwithreport.type.MemoryType;
import happynewmoonwithreport.type.U32;


/**
 * <h1>F64_load</h1> Load an F64 value from memory to the stack.
 * <br>
 * Memory Instructions<br>
 * <br>
 * <b>Source:</b>
 * <a href="https://webassembly.github.io/spec/core/syntax/instructions.html#memory-instructions"
 * target="_top"> https://webassembly.github.io/spec/core/syntax/instructions
 * .html#memory-instructions
 * </a>
 * <br>
 * <br>
 * Exec:
 * <br>
 * <b>Source:</b>
 * <a href="https://webassembly.github.io/spec/core/exec/instructions.html#exec-load" target="_top">
 * https://webassembly.github.io/spec/core/exec/instructions.html#exec-load
 * </a>
 * <br>
 * <h2>t.load memarg and t.loadN_sx memarg</h2>
 * <ol>
 * <li>
 * Let F be the current frame.
 * </li><li>
 * Assert: due to validation, F.module.memaddrs[0] exists.
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
public class F64_load extends LoadBase {


	private F64_load() {
		super();
	}

	/**
	 * Load an F32 value from Memory to the Stack.
	 *
	 * @param memoryArgument memory Argument.  I am still not sure what this is.
	 * @param frame          The current Frame.
	 * @param store          The current Store.
	 * @param stack          The stack to place the value.
	 */
	public F64_load(MemoryArgument memoryArgument, WasmFrame frame, WasmStore store,
					WasmStack stack) {
		this();
		this.memoryArgument = memoryArgument;
		this.frame = frame;
		this.store = store;
		this.stack = stack;
	}

	/* package-private */
	@Override
	U32 getBitWithOfN() {
		return new U32(64);
	}

	/* package-private */
	@Override
	ByteUnsigned[] getBytesFromMemory(MemoryType mem, U32 ea) {
		ByteUnsigned[] bytes = new ByteUnsigned[8];
		Integer eaIntegerValue = ea.integerValue();
		bytes[0] = mem.get(eaIntegerValue + 0);
		bytes[1] = mem.get(eaIntegerValue + 1);
		bytes[2] = mem.get(eaIntegerValue + 2);
		bytes[3] = mem.get(eaIntegerValue + 3);

		bytes[4] = mem.get(eaIntegerValue + 4);
		bytes[5] = mem.get(eaIntegerValue + 5);
		bytes[6] = mem.get(eaIntegerValue + 6);
		bytes[7] = mem.get(eaIntegerValue + 7);

		return bytes;
	}

	/* package-private */
	@Override
	F64 convertToType(ByteUnsigned[] bytes) {
		F64 c = new F64(bytes);
		return c;
	}
}
