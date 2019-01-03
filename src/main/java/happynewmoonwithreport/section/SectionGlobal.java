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
import happynewmoonwithreport.GlobalVariableType;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.WasmVector;


/**
 * The encoding of the Global section:
 * <p>
 * Source <a href="http://webassembly.org/docs/binary-encoding/#memory-section" target="_top">
 * http://webassembly.org/docs/binary-encoding/#memory-section
 * </a>
 * <p>
 * Source:  <a href="http://webassembly.org/docs/modules/#global-section" target="_top">
 * http://webassembly.org/docs/modules/#global-section
 * </a>
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/core/binary/modules.html#global-section" target="_top">
 * https://webassembly.github.io/spec/binary/modules.html#global-section
 * </a>
 */
public class SectionGlobal implements Section {

	private UInt32 count;
	private WasmVector<GlobalVariableType> globals;

	public SectionGlobal() {
		count = new UInt32(0);
		globals = new WasmVector<>();
	}

	/**
	 * @param payload the input BytesFile.
	 */
	@Override
	public void instantiate(BytesFile payload) {

		//* Count
		count = new VarUInt32(payload);

		//* Entries of Global Variables.
		globals = new WasmVector<>(count.integerValue());
		for (Integer index = 0; index < count.integerValue(); index++) {
			GlobalVariableType globalVariable = new GlobalVariableType(payload);
			globals.add(index, globalVariable);
		}
	}

	public UInt32 getCount() {
		return count;
	}

	public WasmVector<GlobalVariableType> getGlobals() {
		return globals;
	}
}
