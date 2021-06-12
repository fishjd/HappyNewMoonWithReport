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
import happynewmoonwithreport.type.*
import happynewmoonwithreport.type.JavaType.ByteUnsigned
import spock.lang.Specification

/**
 * Created on 2020-5-12
 */
class I64_load32_u_Test extends Specification {
	WasmModule module;
	WasmFrame frame;
	I64_load32_u I64Load32_u;

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
		I64Load32_u = new I64_load32_u(memoryArgument, frame, store, stack);
	}

	void cleanup() {
	}

	def "Execute Golden Path"() {
		Integer address = 2;

		setup: ""
		stack.push(new I32(address));  // load bytes starting at 2

		memory.set(address + 0, new ByteUnsigned((input >> 24) & 0xFF)); // most significant byte
		memory.set(address + 1, new ByteUnsigned((input >> 16) & 0xFF));
		memory.set(address + 2, new ByteUnsigned((input >> 8) & 0xFF));
		memory.set(address + 3, new ByteUnsigned((input >> 0) & 0xFF)); // most significant byte

		when: ""
		I64Load32_u.execute();

		then: ""
		I64 actual = (I64) stack.pop();
		I64 expectedI64 = new I64(expected); // Little Endian!
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
		0x7FFF_FFFF           || 0x7FFF_FFFF
		-100                  || 0xFFFF_FF9C
		-1                    || 0xFFFF_FFFF
		0xFFFF_FFFF           || 0xFFFF_FFFF
		// test only two right bytes are used.
		0x7FFF_FFFF_FFFF_FFFF || 0xFFFF_FFFF
	}


}
