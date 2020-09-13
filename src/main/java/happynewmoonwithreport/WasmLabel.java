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
package happynewmoonwithreport;

import happynewmoonwithreport.type.WasmVector;

/**
 * Labels carry an argument arity n and their associated branch target, which is expressed
 * syntactically as an instruction sequence:
 * <br>
 * Intuitively, instr∗ is the continuation to execute when the branch is taken, in place of the
 * original control construct.
 * <br>
 * <h3>Source:</h3>
 * <a href="https://webassembly.github.io/spec/core/exec/runtime.html#labels" target="_top">
 * Wasm Label Documentation
 * </a>
 * <br>
 * <a href="https://github.com/sunfishcode/wasm-reference-manual/blob/467fc534e5b546b8d22363abf3668aa7f988ecfa/WebAssembly.md#labels" target="_top">
 * Sunfish Label documentation
 * </a>
 */
public class WasmLabel {

	public WasmLabel(BytesFile code) {
		returnTypeAll = new WasmVector<>(1);
		ValueType resultType = new ValueType(code);
		returnTypeAll.add(resultType);
	}

	/**
	 * The arity,  The types of the return values
	 */
	private WasmVector<ValueType> returnTypeAll;


	public WasmVector<ValueType> getReturnTypeAll() {
		return returnTypeAll;
	}

	public void setReturnTypeAll(WasmVector<ValueType> returnTypeAll) {
		this.returnTypeAll = returnTypeAll;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("WasmLabel{");
		sb.append("returnTypeAll=").append(returnTypeAll);
		sb.append('}');
		return sb.toString();
	}
}
