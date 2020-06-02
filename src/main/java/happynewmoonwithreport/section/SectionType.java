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
import happynewmoonwithreport.FunctionType;
import happynewmoonwithreport.ValueType;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt1;
import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.WasmVector;


/**
 * The type section declares all function signatures that will be used in the module.
 * <p>
 * Source: <a href = "http://webassembly.org/docs/binary-encoding/#type-section" target="_top">
 * http://webassembly.org/docs/binary-encoding/#type-section
 * </a>
 * <p>
 * Source:
 * <a href="https://webassembly.github.io/spec/core/binary/modules.html#type-section" target="_top">
 * https://webassembly.github.io/spec/core/binary/modules.html#type-section
 * </a>
 */
public class SectionType implements Section {

	public SectionType() {
		functionSignatures = new WasmVector<>();
	}

	// all the Function Types.
	private UInt32 count;
	private WasmVector<FunctionType> functionSignatures;


	@Override
	public void instantiate(BytesFile payload) {

		ValueType form;
		UInt32 paramCount;
		VarUInt1 varReturnCount;

		// Type Count
		count = new VarUInt32(payload);

		functionSignatures = new WasmVector<>(count.integerValue());

		FunctionType functionType;
		for (Integer countFT = 0; countFT < count.integerValue(); countFT++) {
			functionType = new FunctionType(payload);
			functionSignatures.add(countFT, functionType);
		}
	}

	public WasmVector<FunctionType> getFunctionSignatures() {
		return functionSignatures;
	}


	public UInt32 getCount() {
		return count;
	}

}
