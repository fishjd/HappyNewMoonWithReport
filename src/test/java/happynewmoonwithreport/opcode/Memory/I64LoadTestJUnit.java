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

import static org.junit.jupiter.api.Assertions.assertEquals;

import happynewmoonwithreport.WasmFrame;
import happynewmoonwithreport.WasmModule;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.WasmStore;
import happynewmoonwithreport.type.*;
import happynewmoonwithreport.type.JavaType.ByteUnsigned;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/*  I can't get Spock/Groovy working.  It may be my 'new' computer.   So, to move forward were
stuck with JUnit 5.

 ~~ James
 */
class I64LoadTestJUnit {
	WasmModule module;
	WasmFrame frame;
	I64_load i64Load;

	WasmStack stack;

	@BeforeEach
	void setUp() {
		// create a module.
		module = new WasmModule();

		// create a memory if we are going to load from memory we need a memory.
		U32 hasMaximum = new U32(0);
		U32 minimum = new U32(1);
		MemoryType memory = new MemoryType(hasMaximum, minimum);
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
		memory.set(11, new ByteUnsigned(0xFF));

		// add memory to module
		module.addMemory(memory);

		// create a frame
		frame = new WasmFrame(module);

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
		stack.push(new I32(2));  // load bytes starting at 2

		// create class to test.
		i64Load = new I64_load(memoryArgument, frame, store, stack);

	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void execute() {
		i64Load.execute();

		I64 actual = (I64) stack.pop();
		I64 expected = new I64(0x02_03_04_05_06_07_08_09L); // Little Endian!
		assertEquals(actual, expected);
	}
}