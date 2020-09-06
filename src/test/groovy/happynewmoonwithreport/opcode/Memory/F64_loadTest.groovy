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
package happynewmoonwithreport.opcode.Memory

import happynewmoonwithreport.WasmFrame
import happynewmoonwithreport.WasmModule
import happynewmoonwithreport.WasmStack
import happynewmoonwithreport.WasmStore
import happynewmoonwithreport.opcode.Memory.F64_load
import happynewmoonwithreport.type.*
import happynewmoonwithreport.type.JavaType.ByteUnsigned
import spock.lang.Specification

/**
 * Created on 2020-08-30
 */
class F64_loadTest extends Specification {
	WasmModule module;
	WasmFrame frame;
	F64_load f64_load;

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
		memory.set(7, new ByteUnsigned(0x07));
		memory.set(8, new ByteUnsigned(0x08));
		memory.set(9, new ByteUnsigned(0x09));
		memory.set(10, new ByteUnsigned(0x0A));
		memory.set(11, new ByteUnsigned(0x0B));
		memory.set(12, new ByteUnsigned(0x0C));
		memory.set(13, new ByteUnsigned(0x0D));
		memory.set(14, new ByteUnsigned(0x0E));
		memory.set(15, new ByteUnsigned(0x0F));
		memory.set(16, new ByteUnsigned(0x10));
		memory.set(17, new ByteUnsigned(0x11));
		memory.set(18, new ByteUnsigned(0x12));

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
		f64_load = new F64_load(memoryArgument, frame, store, stack);
	}

	void cleanup() {
	}

	def "Execute Golden Path"(Double input, Double expected) {
		Integer address = 2;

		setup: ""
		stack.push(new I32(address));  // load bytes starting at 2

		F64 inputF64 = new F64(input);
		ByteUnsigned[] byteAll = inputF64.getBytes();

		// Store bytes in memory starting at 'address'
		memory.set(address + 0, byteAll[0]);
		memory.set(address + 1, byteAll[1]);
		memory.set(address + 2, byteAll[2]);
		memory.set(address + 3, byteAll[3]);

		memory.set(address + 4, byteAll[4]);
		memory.set(address + 5, byteAll[5]);
		memory.set(address + 6, byteAll[6]);
		memory.set(address + 7, byteAll[7]);

		when: ""
		f64_load.execute();

		then: ""
		F64 actual = (F64) stack.pop();
		F64 expectedF64 = new F64(expected);
		actual == expectedF64;

		// expect: ""

		// cleanup: ""

		where: ""
		input                    || expected
		0                        || 0
		1                        || 1
		-1                       || -1
		Double.NaN               || Double.NaN
		Double.NEGATIVE_INFINITY || Double.NEGATIVE_INFINITY
		Double.POSITIVE_INFINITY || Double.POSITIVE_INFINITY
		Double.MIN_VALUE         || Double.MIN_VALUE
		Double.MAX_VALUE         || Double.MAX_VALUE
	}


}
