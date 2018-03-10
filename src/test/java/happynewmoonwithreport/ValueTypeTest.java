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
package happynewmoonwithreport;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by James Haring on 2017-07-18.
 */
public class ValueTypeTest {


	@Test
	public void constructorPayloadIndexModifiedConstrutorBytesFile() throws Exception {
		byte[] bytesAll = new byte[]{0x7F};
		Integer index = 0;
		BytesFile payload = new BytesFile(bytesAll, 0);
		// run
		ValueType vtc = new ValueType(payload);

		// verify
		assertContains(vtc.toString(), "int32");
		assertEquals(new Integer(1), payload.getIndex());
	}

	/**
	 * Test constructor(Integer);
	 *
	 * @throws Exception
	 */
	@Test
	public void constructorInteger() throws Exception {
		ValueType vtc = new ValueType(-0x01);
		assertContains(vtc.toString(), "int32");
	}

	/**
	 * Test constructor(String);
	 *
	 * @throws Exception
	 */
	@Test
	public void constructorString() throws Exception {
		// run
		ValueType vtc = new ValueType("int32");

		// test
		assertContains(vtc.toString(), "int32");
		assertEquals(new ValueType(-0x01), vtc);
	}

	@Test
	public void constructorPayload() throws Exception {
		byte[] bytesAll = new byte[]{0x7F};
		BytesFile bytesFile = new BytesFile(bytesAll);
		ValueType vtc = new ValueType(bytesFile);
		assertContains(vtc.toString(), "int32");
		assertEquals(new ValueType(-0x01), vtc);
	}


	@Test
	public void valueOf() throws Exception {
		ValueType vtc = new ValueType(-0x01);
		assertContains(vtc.getValue(), "int32");

		vtc = new ValueType(-0x20);
		assertContains(vtc.getValue(), "func");

	}

	private void assertContains(String aaa, String bbb) {
		assertTrue(aaa.contains(bbb));
	}

}