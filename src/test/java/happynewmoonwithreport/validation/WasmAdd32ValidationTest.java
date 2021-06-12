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
package happynewmoonwithreport.validation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import happynewmoonwithreport.Wasm;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class WasmAdd32ValidationTest {
	private Wasm wasm;

	@BeforeEach
	public void setUp() throws Exception {

		String path = "./src/test/resources/add32/add32.wasm";
		File wasmFile = new File(path);
		assertTrue(wasmFile.exists());

		// C:\Users\James\Documents\Programming
		// 2017\HappyNewMoonWithReport\src\test\resources\add32
		wasm = new Wasm(path);
		wasm.instantiate();
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void isValidTest() {

		// run
		Boolean valid = wasm.validate();

		// validate
		assertTrue(valid);

	}

}
