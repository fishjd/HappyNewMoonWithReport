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
import happynewmoonwithreport.opcode.Memory.F64_store
import happynewmoonwithreport.type.*
import happynewmoonwithreport.type.JavaType.ByteUnsigned
import spock.lang.Specification

/**
 * Created on 2020-08-30
 */
class F64_storeTest extends Specification {
	WasmModule module;
	WasmFrame frame;
	F64_store f64Store;

	WasmStack stack;
	WasmStore store;
	MemoryArgument memoryArgument;

	void setup() {
		// create a module.
		module = new WasmModule();

		// create a memory. if we are going to load from memory we need a memory.
		U32 hasMaximum = new U32(0);
		U32 minimum = new U32(1);
		MemoryType memory = new MemoryType(hasMaximum, minimum);
		memory.set(0, new ByteUnsigned(0x00));
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
		store = new WasmStore();
		store.setMemoryAll(memoryAll);

		// create memoryArgument
		memoryArgument = new MemoryArgument(new U32(0), new U32(0));

		// create stack
		stack = new WasmStack();
		stack.push(new I32(2));  // load bytes starting at 2
	}

	void cleanup() {
	}

	def "Execute Golden Path"() {
		setup: "Create a value to store in to memory"

		// Create a value to store.  We create a value from Byte Unsigned to make it easier to verify.
		ByteUnsigned[] baStoreThis = new ByteUnsigned[8];
		baStoreThis[0] = new ByteUnsigned(0xCC);
		baStoreThis[1] = new ByteUnsigned(0xCD);
		baStoreThis[2] = new ByteUnsigned(0xCE);
		baStoreThis[3] = new ByteUnsigned(0xCF);
		baStoreThis[4] = new ByteUnsigned(0xDC);
		baStoreThis[5] = new ByteUnsigned(0xDD);
		baStoreThis[6] = new ByteUnsigned(0xDE);
		baStoreThis[7] = new ByteUnsigned(0xDF);
		F64 storeThis = new F64(baStoreThis);

		// add the value to the stack
		stack.push(storeThis);

		when: "Instantiate the class and execute"
		f64Store = new F64_store(memoryArgument, frame, store, stack);
		f64Store.execute();

		then: "Verify the memory contains the correct bytes."
		// Get the first memory.  Wasm has a concept of multiple memories.
		MemoryType memoryResult = store.getMemory(new I32(0));
		new ByteUnsigned(0xCC) == memoryResult.get(2);
		new ByteUnsigned(0xCD) == memoryResult.get(3);
		new ByteUnsigned(0xCE) == memoryResult.get(4);
		new ByteUnsigned(0xCF) == memoryResult.get(5);
		new ByteUnsigned(0xDC) == memoryResult.get(6);
		new ByteUnsigned(0xDD) == memoryResult.get(7);
		new ByteUnsigned(0xDE) == memoryResult.get(8);
		new ByteUnsigned(0xDF) == memoryResult.get(9);

		// expect: ""

		// cleanup: ""

		// where: ""

	}


}
