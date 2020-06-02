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

import java.util.ArrayList;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.FunctionBody;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;

/**
 * <h1>Code section</h1>
 * <p>
 * The code section contains a body for every function in the Module. The count of function
 * declared in the function
 * section and function bodies defined in this section must be the same and the ith declaration
 * corresponds to the ith
 * function body.
 * </p>
 * <p>
 * Source:  <a href="http://webassembly.org/docs/binary-encoding/#code-section" target="_top">
 * http://webassembly.org/docs/binary-encoding/#code-section
 * </a>
 * <p>
 * Source:  <a href="http://webassembly.org/docs/modules/#function-and-code-sections" target="_top">
 * http://webassembly.org/docs/modules/#function-and-code-sections
 * </a>
 */
public class SectionCode implements Section {

	private UInt32 count;
	private ArrayList<FunctionBody> functionAll;


	/**
	 * @param payload the input BytesFile.
	 */
	@Override
	public void instantiate(BytesFile payload) {

		//* FunctionBody Count
		count = new VarUInt32(payload);

		//* Functions
		functionAll = new ArrayList<>(count.integerValue());
		for (Integer index = 0; index < count.integerValue(); index++) {
			FunctionBody function = new FunctionBody(payload);
			functionAll.add(index, function);
		}
	}

	public UInt32 getCount() {
		return count;
	}

	public ArrayList<FunctionBody> getFunctionAll() {
		return functionAll;
	}
}
