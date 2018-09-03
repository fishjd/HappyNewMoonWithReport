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
package happynewmoonwithreport.util.converter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created on 2018-03-10.
 */
class StringToByteArrayConverterTest {

	StringToByteArrayConverter converter;

	@BeforeEach
	void setUp() {
		converter = new StringToByteArrayConverter();
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void convert() {
		// run
		byte[] result = (byte[]) converter.convert("CDFE70", byte[].class);

		// verify
		assertEquals((byte) 0x70, result[0]);
		assertEquals((byte) 0xFE, result[1]);
		assertEquals((byte) 0xCD, result[2]);

	}

	@Test
	void convertFF() {
		// run
		byte[] result = (byte[]) converter.convert("FF", byte[].class);

		// verify
		assertEquals((byte) 0xFF, result[0]);
	}
}