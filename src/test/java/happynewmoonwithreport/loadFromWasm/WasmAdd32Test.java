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
package happynewmoonwithreport.loadFromWasm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import happynewmoonwithreport.*;
import happynewmoonwithreport.type.DataTypeNumber;
import happynewmoonwithreport.type.S32;
import happynewmoonwithreport.type.WasmVector;
import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * A low level test to run a module.  In this test our module only has one function add32 which
 * simply adds two integers.
 *
 * A api for a simpler, easier, more high level is in the works.
 */
public class WasmAdd32Test {
	private Wasm wasm;
	private WasmModule module;

	@BeforeEach
	public void setUp() throws Exception {

		// load the *.wasm file as a "plain old" java file.
		String path = "./src/test/resources/add32/add32.wasm";
		File wasmFile = new File(path);
		assertTrue(wasmFile.exists());

		// Create the Wasm object
		wasm = new Wasm(path);
		// Read the file,  create all wasm sections Memory, Export, Functions
		// Check the Magic Number.  And a lot more.
		module = wasm.instantiate();
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	/**
	 * This an end to end test
	 */
	@Test
	public void wasmTest() {

		// Run the wasm code to validate the *.wasm file.
		assertTrue(wasm.validate());

		// Verify there is only one function.
		assertEquals(new Integer(1), wasm.getFunctionSignatures().getCount().integerValue());

		// Verify there are two exports.
		assertEquals(2, wasm.exports().size());

		// Verify exports[0] is 'memory' type
		Assertions.assertEquals(new ExternalKind(ExternalKind.memory),
			wasm.exports().get(0).getExternalKind());
		assertEquals("memory", wasm.exports().get(0).getFieldName().getValue());

		// Verify exports[1] is the add32 function
		assertEquals(new ExternalKind(ExternalKind.function),
			wasm.exports().get(1).getExternalKind());
		assertEquals("add32", wasm.exports().get(1).getFieldName().getValue());

		// create a Wasm instance from the Module.  This is like calling new();
		WasmInstance instance = new WasmInstance(module);
		assertNotNull(instance.stack());

		// find the add 32 function from the instance.
		WasmFunction functionAdd32 = instance.exportFunction("add32");

		// load up the variable to send to the opcode.
		WasmVector<DataTypeNumber> returnAll = new WasmVector<>(1);
		WasmVector<DataTypeNumber> paramAll = new WasmVector<>(2);
		paramAll.add(new S32(3));
		paramAll.add(new S32(4));

		// Runs all the code in the function add32
		instance.call(functionAdd32, returnAll, paramAll);

		// Verify the results.
		assertNotNull(returnAll);
		assertEquals(1, returnAll.size());
		assertEquals(new S32(7), returnAll.get(0));


	}

}
