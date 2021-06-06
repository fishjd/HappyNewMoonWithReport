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
package happynewmoonwithreport.loadFromWasm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import happynewmoonwithreport.ExternalKind;
import happynewmoonwithreport.Wasm;
import happynewmoonwithreport.WasmFunction;
import happynewmoonwithreport.WasmInstance;
import happynewmoonwithreport.WasmModule;
import happynewmoonwithreport.type.DataTypeNumber;
import happynewmoonwithreport.type.S32;
import happynewmoonwithreport.type.WasmVector;
import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Call a module to validate the 'Block' opcode.
 */
class WasmBlockSingularTest {
	private Wasm wasm;
	private WasmModule module;

	@BeforeEach
	public void setUp() throws Exception {

		// validate the file exists.  This *.wasm was created on https://webassembly.studio/ .
		String path = "./src/test/resources/wasm-project Block-Singular/out/main.wasm";
		File wasmFile = new File(path);
		assertTrue(wasmFile.exists());

		// given a file then instantiate the WASM.
		// Instantiate load all the sections of a module.  Exports, Type, Function...
		wasm = new Wasm(path);
		module = wasm.instantiate();
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	/**
	 * This an end to end test.  It loads the module and executes a function on the module.
	 */
	@Test
	void wasmTest() {

		// Assert the module is validated.  Must be after instantiate().
		assertTrue(wasm.validate());

		// *** various test to ensure we ha the correct module.  Carried over from previous code.

		// Validate there is one function.
		assertEquals(Integer.valueOf(1), wasm.getFunctionSignatures().getCount().integerValue());

		// Validate there is one export
		assertEquals(1, wasm.exports().size());

		// Validate the external is named 'singular'
		assertEquals(new ExternalKind(ExternalKind.function),
			wasm.exports().get(0).getExternalKind());
		assertEquals("singular", wasm.exports().get(0).getFieldName().getValue());

		// *** now we can get some work done.

		// Create an instance of the module
		WasmInstance instance = new WasmInstance(module);
		assertNotNull(instance.stack());

		// retrieve the function named  "singular"
		WasmFunction functionSingular = instance.exportFunction("singular");

		// Set up input and return vectors.
		WasmVector<DataTypeNumber> returnAll = new WasmVector<>(1);
		WasmVector<DataTypeNumber> paramAll = new WasmVector<>(0);

		// call the function named "singular".   This is the actual 'Code Under Test'
		instance.call(functionSingular, returnAll, paramAll);

		// verify the return value is 15.
		assertNotNull(returnAll);
		assertEquals(1, returnAll.size());
		assertEquals(new S32(15), returnAll.get(0));
	}

}
