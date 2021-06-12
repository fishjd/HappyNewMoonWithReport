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
package happynewmoonwithreport.type;

import static org.junit.jupiter.api.Assertions.assertEquals;

import happynewmoonwithreport.BytesFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WasmStringTest {
	@BeforeEach
	public void setUp() throws Exception {
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void testReadUnsigned() {
		byte[] fox =
			new byte[]{0x54, (byte) 0x68, (byte) 0x65, (byte) 0x20, (byte) 0x71, (byte) 0x75,
				(byte) 0x69, (byte) 0x63, (byte) 0x6B, (byte) 0x20, (byte) 0x62, (byte) 0x72,
				(byte) 0x6F, (byte) 0x77, (byte) 0x6E};
		BytesFile payload = new BytesFile(fox);
		WasmString wasmFox = new WasmString(payload, new UInt32(fox.length));
		assertEquals(new WasmString("The quick brown"), wasmFox);

	}
}