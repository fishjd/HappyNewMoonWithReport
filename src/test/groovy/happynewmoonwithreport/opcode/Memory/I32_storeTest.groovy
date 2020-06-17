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
package happynewmoonwithreport.opcode.memory

import happynewmoonwithreport.WasmFrame
import happynewmoonwithreport.WasmModule
import happynewmoonwithreport.WasmStack
import happynewmoonwithreport.WasmStore
import happynewmoonwithreport.type.*
import happynewmoonwithreport.type.JavaType.ByteUnsigned
import spock.lang.Specification

/**
 * Created on 2018-02-12.
 */
class I32_storeTest extends Specification {
	WasmModule module;
	WasmFrame frame;
	I32_store i32Store;

	WasmStack stack;
	WasmStore store;

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
		MemoryArgument memoryArgument = new MemoryArgument(new U32(0), new U32(0));

		// create stack
		stack = new WasmStack();
		stack.push(new I32(2));  // load bytes starting at 2

		// create a value to store
		ByteUnsigned[] baStoreThis = new ByteUnsigned[4];
		baStoreThis[0] = new ByteUnsigned(0xFC);
		baStoreThis[1] = new ByteUnsigned(0xFD);
		baStoreThis[2] = new ByteUnsigned(0xFE);
		baStoreThis[3] = new ByteUnsigned(0xFF);
		I32 storeThis = new I32(baStoreThis);

		// add to the stack
		stack.push(storeThis);

		// create class to test.
		i32Store = new I32_store(memoryArgument, frame, store, stack);
	}

	void cleanup() {
	}

	def "Execute Golden Path"() {
		// setup: ""

		when: ""
		i32Store.execute();

		then: ""
		new ByteUnsigned(0xFC) == store.memoryAll.get(0).get(2);
		new ByteUnsigned(0xFD) == store.memoryAll.get(0).get(3);
		new ByteUnsigned(0xFE) == store.memoryAll.get(0).get(4);
		new ByteUnsigned(0xFF) == store.memoryAll.get(0).get(5);

		// expect: ""

		// cleanup: ""

		// where: ""

	}


}
