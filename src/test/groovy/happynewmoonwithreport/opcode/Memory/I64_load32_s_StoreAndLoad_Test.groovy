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
package happynewmoonwithreport.opcode.memory

import happynewmoonwithreport.WasmFrame
import happynewmoonwithreport.WasmModule
import happynewmoonwithreport.WasmStack
import happynewmoonwithreport.WasmStore
import happynewmoonwithreport.type.*
import happynewmoonwithreport.type.JavaType.ByteUnsigned
import spock.lang.Specification

/**
 * Created on 2018-05-05
 */
class I64_load32_s_StoreAndLoad_Test extends Specification {
	WasmModule module;
	WasmFrame frame;
	I64_store32 i64Store32;
	I64_load32_s i64Load32_s;

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

		// create class to test.
		i64Load32_s = new I64_load32_s(memoryArgument, frame, store, stack);
		// create class to test.
		i64Store32 = new I64_store32(memoryArgument, frame, store, stack);
	}

	void cleanup() {
	}

	def "Execute Golden Path"() {
		Integer address = 2;

		setup: ""


		when: ""

		// Store on memory
		stack.push(new I32(address));  // store bytes starting at 2
		stack.push(new I64(input));
		i64Store32.execute();

		// Load from memory
		stack.push(new I32(address));  // load bytes starting at 2
		i64Load32_s.execute();

		then: ""
		I64 actual = (I64) stack.pop();
		I64 expectedI64 = new I64(expected);
		actual == expectedI64;

		// expect: ""

		// cleanup: ""

		where: ""
		input                 || expected
		0                     || 0
		2                     || 2
		4                     || 4
		127                   || 127
		0x7F                  || 0x7F
		0xFF                  || 0xFF
		0x7FFF                || 0x7FFF
		0x8000                || 0x8000
		0xFFFF                || 0xFFFF
		0x33CC_55AA           || 0x33CC_55AA
		0x8000_0000           || 0xFFFF_FFFF_8000_0000
		0xFFFF_FFFF           || 0xFFFF_FFFF_FFFF_FFFF
		0xAAAA_AAAA_FFFF_FFFF || 0xFFFF_FFFF_FFFF_FFFF
	}


}
