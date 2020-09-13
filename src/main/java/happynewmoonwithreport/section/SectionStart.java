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

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;

/**
 * The start section declares the start function.
 * <br>
 * If the module has a start node defined, the function it refers should be called by the loader
 * after the instance is
 * initialized, including its Memory and Table though Data and Element sections, and before the
 * exported functions are
 * callable.
 * <br>
 * Source:  <a href="http://webassembly.org/docs/binary-encoding/#start-section" target="_top">
 * http://webassembly.org/docs/binary-encoding/#start-section
 * </a>
 * <br>
 * Source:  <a href="http://webassembly.org/docs/modules/#module-start-function" target="_top">
 * http://webassembly.org/docs/modules/#module-start-function
 * </a>
 * <br>
 * Source:
 * <a href="https://webassembly.github.io/spec/core/binary/modules.html#start-section" target="_top">
 * https://webassembly.github.io/spec/core/binary/modules.html#start-section
 * </a>
 */
public class SectionStart implements Section {

	protected UInt32 index;

	/**
	 * @param payload the input BytesFile.
	 */
	@Override
	public void instantiate(BytesFile payload) {

		//* Index
		index = new VarUInt32(payload);

	}

	public UInt32 getIndex() {
		return index;
	}
}
