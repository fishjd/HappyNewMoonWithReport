/*
 *  Copyright 2018 Whole Bean Software, LTD.
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

import happynewmoonwithreport.*;
import happynewmoonwithreport.type.DataTypeNumber;
import happynewmoonwithreport.type.S32;
import happynewmoonwithreport.type.WasmVector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;


public class WasmAdd32Test {
	private Wasm wasm;
	private WasmModule module;

	@BeforeEach
	public void setUp() throws Exception {

		String path = "./src/test/resources/add32/add32.wasm";
		File wasmFile = new File(path);
		assertTrue(wasmFile.exists());

		wasm = new Wasm(path);
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

		assertTrue(wasm.validate());

		assertEquals(new Integer(1), wasm.getFunctionSignatures().getSize());

		assertEquals(2, wasm.exports().size());
		Assertions.assertEquals(new ExternalKind(ExternalKind.memory), wasm.exports().get(0).getExternalKind());
		assertEquals("memory", wasm.exports().get(0).getFieldName().getValue());

		assertEquals(new ExternalKind(ExternalKind.function), wasm.exports().get(1).getExternalKind());
		assertEquals("add32", wasm.exports().get(1).getFieldName().getValue());

		WasmInstance instance = new WasmInstance(module);
		assertNotNull(instance.stack());
		WasmFunction functionAdd32 = instance.exportFunction("add32");

		WasmVector<DataTypeNumber> returnAll = new WasmVector<>(1);
		WasmVector<DataTypeNumber> paramAll = new WasmVector<>(2);
		paramAll.add(new S32(3));
		paramAll.add(new S32(4));

		instance.call(functionAdd32, returnAll, paramAll);

		assertNotNull(returnAll);
		assertEquals(1, returnAll.size());
		assertEquals(new S32(7), returnAll.get(0));


	}

}
