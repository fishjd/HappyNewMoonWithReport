/*
 *  Copyright 2017 Whole Bean Software, LTD.
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
package happynewmoonwithreport.type;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WasmVectorTest {
	private WasmVector<String> vector;

	@BeforeEach
	public void setUp() throws Exception {
		vector = new WasmVector<>();
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void addTest() {
		vector.add("111");
		assertEquals("111", vector.get(0));
	}

	@Test
	public void addAtTest() {
		vector.add(0, "111");
		assertEquals("111", vector.get(0));
	}
}