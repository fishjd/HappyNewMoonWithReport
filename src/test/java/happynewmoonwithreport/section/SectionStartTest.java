/*
 *  Copyright 2017 - 2020 Whole Bean Software, LTD.
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
package happynewmoonwithreport.section;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.type.UInt32;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class SectionStartTest {
	private SectionStart sectionStart;

	@BeforeEach
	public void setUp() throws Exception {
		sectionStart = new SectionStart();
		assertNotNull(sectionStart);
	}

	@AfterEach
	public void tearDown() throws Exception {
	}


	/**
	 * Run instantiate on the add32.wasm bytes.
	 */
	@Test
	public void instantiateFull() {
		// I made this one up.  This is not in Add32
		byte[] byteAll = {(byte) 0x02};
		BytesFile payload = new BytesFile(byteAll);

		// run
		sectionStart.instantiate(payload);

		// verify
		// the count is 2
		assertEquals(new UInt32(2L), sectionStart.getIndex());


	}

}