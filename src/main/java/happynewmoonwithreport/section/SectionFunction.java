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
package happynewmoonwithreport.section;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;

import java.util.ArrayList;

/**
 * Function section
 * <p>
 * The function section declares the signatures of all functions in the module (their definitions appear in the code
 * section).
 * <p>
 * Source:  <a href="http://webassembly.org/docs/binary-encoding/#function-section" target="_top">
 * http://webassembly.org/docs/binary-encoding/#function-section
 * </a>
 * <p>
 * Source:  <a href="http://webassembly.org/docs/modules/#function-and-code-sections" target="_top">
 * http://webassembly.org/docs/modules/#function-and-code-sections
 * </a>
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/binary/modules.html#function-section" target="_top">
 * https://webassembly.github.io/spec/binary/modules.html#function-section
 * </a>
 */
public class SectionFunction implements Section {

	private UInt32 count;
	private ArrayList<UInt32> types;

	public SectionFunction() {
		count = new UInt32(0);
		types = new ArrayList<>();
	}

	/**
	 * @param payload the input BytesFile.
	 */
	@Override
	public void instantiate(BytesFile payload) {

		//* Parameter Count
		count = new VarUInt32(payload);

		//* Parameters Types
		types = new ArrayList<>(count.integerValue());
		for (Integer index = 0; index < count.integerValue(); index++) {
			UInt32 type = new VarUInt32(payload);
			types.add(index, type);
		}
	}

	public UInt32 getCount() {
		return count;
	}

	public ArrayList<UInt32> getTypes() {
		return types;
	}
}
