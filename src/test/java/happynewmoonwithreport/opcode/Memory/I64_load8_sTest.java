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

package happynewmoonwithreport.opcode.Memory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import happynewmoonwithreport.WasmFrame;
import happynewmoonwithreport.WasmModule;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.WasmStore;
import happynewmoonwithreport.type.I32;
import happynewmoonwithreport.type.I64;
import happynewmoonwithreport.type.JavaType.ByteUnsigned;
import happynewmoonwithreport.type.MemoryArgument;
import happynewmoonwithreport.type.MemoryType;
import happynewmoonwithreport.type.U32;
import happynewmoonwithreport.type.WasmVector;

import static org.junit.jupiter.api.Assertions.assertEquals;

class I64_load8_sTest {
	WasmModule module;
	WasmFrame frame;
	I64_load8_s i64Load8_s;

	WasmStack stack;
	MemoryType memory;

	@BeforeEach
	void setUp() {
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
		i64Load8_s = new I64_load8_s(memoryArgument, frame, store, stack);

	}

	@AfterEach
	void tearDown() {
	}

	@DisplayName("Should calculate the correct sum")
	@ParameterizedTest(name = "{index} => input={0}, expected={1}")
	@CsvSource({
		"0           , 0",                //
		"2           , 2",                //
		"4           , 4",                //
		"127         , 127",              //
		"-100 		 , -100",             //
		"-128 		 , -128",             //
		"-1   		 , -1"                //
	})
	void execute(Byte input, Long expected) {
		Integer address = 2;

		stack.push(new I32(address));  // load bytes starting at 2

		memory.set(address, new ByteUnsigned(input));

		// run
		i64Load8_s.execute();

		// verify
		I64 actual = (I64) stack.pop();
		I64 expectedI64 = new I64(expected); // Little Endian!
		assertEquals(actual, expectedI64);


	}
}