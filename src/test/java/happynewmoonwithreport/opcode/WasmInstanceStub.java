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
package happynewmoonwithreport.opcode;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.DataTypeNumber;
import happynewmoonwithreport.type.WasmVector;

public class WasmInstanceStub implements WasmInstanceInterface {

	private WasmStack<Object> stack;
	private WasmVector<DataTypeNumber> localAll;
	private BytesFile code;

	public WasmInstanceStub() {
		stack = new WasmStack<Object>();
		localAll = new WasmVector<DataTypeNumber>();
	}

	@Override
	public WasmStack<Object> stack() {
		return stack;
	}

	public void setStack(WasmStack<Object> stack) {
		this.stack = stack;
	}


	@Override
	public WasmVector<DataTypeNumber> localAll() {
		return localAll;
	}

	public WasmVector<DataTypeNumber> getLocalAll() {
		return localAll;
	}

	@Override
	public BytesFile getCode() {
		return code;
	}

	public void setCode(BytesFile code) {
		this.code = code;
	}
}
