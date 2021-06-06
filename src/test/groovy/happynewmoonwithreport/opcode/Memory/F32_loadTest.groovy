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
package happynewmoonwithreport.opcode.Memory

import happynewmoonwithreport.WasmFrame
import happynewmoonwithreport.WasmModule
import happynewmoonwithreport.WasmStack
import happynewmoonwithreport.WasmStore
import happynewmoonwithreport.opcode.Memory.F32_load
import happynewmoonwithreport.type.*
import happynewmoonwithreport.type.JavaType.ByteUnsigned
import spock.lang.Specification

/**
 * Created on 2020-5-11
 */
class F32_loadTest extends Specification {
	WasmModule module;
	WasmFrame frame;
	F32_load f32_load;

	WasmStack stack;
	MemoryType memory;

	void setup() {
		// create a module.
		module = new WasmModule();

		// create a memory if we are going to load from memory we need a memory.
		U32 hasMaximum = new U32(0);
		U32 minimum = new U32(1);

		memory = new MemoryType(hasMaximum, minimum);
		memory.set(0, new ByteUnsigned(0x70));
		memory.set(1, new ByteUnsigned(0x01));
		memory.set(2, new ByteUnsigned(0x02));
		memory.set(3, new ByteUnsigned(0x03));
		memory.set(4, new ByteUnsigned(0x04));
		memory.set(5, new ByteUnsigned(0x05));
		memory.set(6, new ByteUnsigned(0x06));

		// add memory to module
		module.addMemory(memory);

		// create a frame
		frame = new WasmFrame(module)

		//
		WasmVector<MemoryType> memoryAll = new WasmVector<>();
		memoryAll.add(memory);

		// create Store
		WasmStore store = new WasmStore();
		store.setMemoryAll(memoryAll);

		// create memoryArgument
		MemoryArgument memoryArgument = new MemoryArgument(new U32(0), new U32(0));

		// create stack
		stack = new WasmStack();
		stack.push(new I64(2));  // load bytes starting at 2

		// create class to test.
		f32_load = new F32_load(memoryArgument, frame, store, stack);
	}

	void cleanup() {
	}

	def "Execute Golden Path"(Float input, Float expected) {
		Integer address = 2;

		setup: ""
		stack.push(new I32(address));  // load bytes starting at 2

		F32 inputF32 = new F32(input);
		ByteUnsigned[] byteAll = inputF32.getBytes();

		// Store bytes in memory starting at 'address'
		memory.set(address + 0, byteAll[0]);
		memory.set(address + 1, byteAll[1]);
		memory.set(address + 2, byteAll[2]);
		memory.set(address + 3, byteAll[3]);

		when: ""
		f32_load.execute();

		then: ""
		F32 actual = (F32) stack.pop();
		F32 expectedF32 = new F32(expected);
		actual == expectedF32;

		// expect: ""

		// cleanup: ""

		where: ""
		input                   || expected
		0                       || 0
		1                       || 1
		-1                      || -1
		Float.NaN               || Float.NaN
		Float.NEGATIVE_INFINITY || Float.NEGATIVE_INFINITY
		Float.POSITIVE_INFINITY || Float.POSITIVE_INFINITY
		Float.MIN_VALUE         || Float.MIN_VALUE
		Float.MAX_VALUE         || Float.MAX_VALUE
	}


}
