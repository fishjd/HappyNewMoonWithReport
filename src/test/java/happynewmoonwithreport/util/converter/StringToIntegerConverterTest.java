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
package happynewmoonwithreport.util.converter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created on 2018-01-21.
 */
class StringToIntegerConverterTest {
	private StringToIntegerConverter converter;

	@BeforeEach
	void setUp() {
		converter = new StringToIntegerConverter();

	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void convert_1() {

		// run
		Integer result = (Integer) converter.convert("1", Integer.class);

		// verify
		assertEquals(1, result.intValue());
	}

	@Test
	void convert_100_000() {

		// run
		Integer result = (Integer) converter.convert("100_000", Integer.class);

		// verify
		assertEquals(100_000, result.intValue());
	}

	@Test
	void convert_0FFF_FFFF() {

		// run
		Integer result = (Integer) converter.convert("0x0FFF_FFFF", Integer.class);

		// verify
		assertEquals(0x0FFF_FFFF, result.intValue());
	}

	@Test
	void convert_7FFF_FFFF() {

		// run
		Integer result = (Integer) converter.convert("0x7FFF_FFFF", Integer.class);

		// verify
		assertEquals(0x7FFF_FFFF, result.intValue());
	}

	@Test
	void convert_Neg_1F() {

		// run
		Integer result = (Integer) converter.convert("-0x1F", Integer.class);

		// verify
		assertEquals(-0x1F, result.intValue());
	}


}