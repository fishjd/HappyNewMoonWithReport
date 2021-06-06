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
package happynewmoonwithreport.util.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import happynewmoonwithreport.type.JavaType.ByteUnsigned;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
		ByteUnsigned[] result = (ByteUnsigned[]) converter.convert("CDFE70", byte[].class);

		// verify
		assertEquals(new ByteUnsigned((byte) 0x70), result[2]);
		assertEquals(new ByteUnsigned((byte) 0xFE), result[1]);
		assertEquals(new ByteUnsigned((byte) 0xCD), result[0]);

	}

	@Test
	void convert00() {
		// run
		ByteUnsigned[] result = (ByteUnsigned[]) converter.convert("00CDFE70", byte[].class);

		// verify
		assertEquals(new ByteUnsigned((byte) 0x70), result[3]);
		assertEquals(new ByteUnsigned((byte) 0xFE), result[2]);
		assertEquals(new ByteUnsigned((byte) 0xCD), result[1]);
		assertEquals(new ByteUnsigned((byte) 0x00), result[0]);

	}

	@Test
	void convertFF() {
		// run
		ByteUnsigned[] result = (ByteUnsigned[]) converter.convert("FF", byte[].class);

		// verify
		assertEquals(new ByteUnsigned((byte) 0xFF), result[0]);
	}
}