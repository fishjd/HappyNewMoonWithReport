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
package happynewmoonwithreport.type;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import happynewmoonwithreport.BytesFile;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VarInt7LoopTest {

	private Map<Integer, byte[]> problemChildren;
	private Random random;

	@BeforeEach
	public void setUp() throws Exception {
		problemChildren = new HashMap<>();
		setupProblemChildren();
		random = new Random(System.currentTimeMillis());

	}

	public void setupProblemChildren() {
		problemChildren.put(0, new byte[]{0x00});
		problemChildren.put(1, new byte[]{0x01});
		problemChildren.put(2, new byte[]{0x02});
		problemChildren.put(-1, new byte[]{0x7F});
		problemChildren.put(-0x20, new byte[]{0x60});

	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void testProblemChildren() throws Exception {
		for (Entry<Integer, byte[]> child : problemChildren.entrySet()) {
			BytesFile bytesFile = new BytesFile(child.getValue());
			VarInt32 varInt32 = new VarInt32(bytesFile);
			Integer result = varInt32.integerValue();

			assertEqualHex(child.getKey(), result);
		}
	}

	@Test
	public void testProblemChildrenWrite() throws Exception {
		for (Entry<Integer, byte[]> child : problemChildren.entrySet()) {

			VarInt32 varInt32 = new VarInt32(child.getKey());
			ByteOutput result = varInt32.convert();

			assertArrayEqualsJDH(child.getValue(), result.bytes());
		}
	}

	public void assertArrayEqualsJDH(byte[] expected, byte[] actual) {
		Integer length = Math.min(expected.length, actual.length);
		Boolean equal = true;
		for (int i = 0; i < length; i++) {
			if (expected[i] == 0 || actual[i] == 0) {
				break;
			}
			if (expected[i] != actual[i]) {
				equal = false;
				throw new AssertionError(
					"Array not equals" + "expected " + expected.toString() + " actual = " +
					actual.toString());
			}
		}
	}

	private void assertEqualHex(Integer expected, Integer result) {
		assertEquals(expected, result,
			"expected = " + expected.toString() + " hex = " + Integer.toHexString(expected));
	}

	private Integer maxCount = 1_000_000;

	@Test
	public void testReadUnsignedConstructor2() throws Exception {
		VarInt7 dummy = new VarInt7(0);
		for (Long j = dummy.maxValue(); j < dummy.maxValue(); j++) {
			Long i = j;

			VarInt32 expected = new VarInt32(i);
			ByteArrayByteOutput out = (ByteArrayByteOutput) expected.convert();

			BytesFile bytesFile = new BytesFile(out.bytes());
			VarInt32 varInt32_b = new VarInt32(bytesFile);
			Integer result_b = varInt32_b.integerValue();

			assertEqualHex(i.intValue(), result_b);
		}
	}

}
