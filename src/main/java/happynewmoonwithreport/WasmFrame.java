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

import happynewmoonwithreport.type.DataTypeNumber;
import happynewmoonwithreport.type.WasmVector;

/**
 * Activation frames carry the return arity of the respective function, hold the values of its
 * locals (including arguments) in the order corresponding to their static local indices, and a
 * reference to the function’s own module instance:
 * <p>
 * Source:
 * <a href="https://webassembly.github.io/spec/core/exec/runtime.html#activations-and-frames" target="_top">
 * Frames
 * </a>
 * Frame contains:  locals, module, return arity
 */
public class WasmFrame {
	private WasmModule module;

	/**
	 * The arity,  The types of the return values
	 */
	private WasmVector<DataTypeNumber> returnTypeAll;

	/**
	 * Local Variables including arguments
	 */
	private WasmVector<DataTypeNumber> localAll;

	private WasmFrame() {
		super();
		localAll = new WasmVector<>();
		returnTypeAll = new WasmVector<>();
	}

	public WasmFrame(WasmModule module) {
		this();
		this.module = module;
	}

	public WasmModule getModule() {
		return module;
	}

	public WasmVector<DataTypeNumber> returnTypeAll() {
		return returnTypeAll;
	}

	public WasmVector<DataTypeNumber> localAll() {
		return localAll;
	}

	public void setLocalAll(WasmVector<DataTypeNumber> localAll) {
		this.localAll = localAll;
	}
}
