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
package happynewmoonwithreport;

import happynewmoonwithreport.type.DataTypeNumber;
import happynewmoonwithreport.type.MemoryType;
import happynewmoonwithreport.type.WasmVector;

/**
 * The store represents all global state that can be manipulated by WebAssembly programs. It
 * consists of the runtime
 * representation of all instances of functions, tables, memories, and globals that have been
 * allocated during the life
 * time of the abstract machine.
 * <br>
 * The Store is represented by capital <b><i>S</i></b> in wasm documentation.
 * <br>
 * <br>
 * Source:  <a href="https://webassembly.github.io/spec/core/exec/runtime.html#store" target="_top">
 * https://webassembly.github.io/spec/core/exec/runtime.html#store
 * </a>
 */

public class WasmStore {

	private WasmVector<WasmFunction> functionAll; // aka funcs
	private WasmVector<TableType> tableAll; // aka tables
	private WasmVector<MemoryType> memoryAll;  // aka mems
	private WasmVector<GlobalVariableType> globals;

	/**
	 * For Unit Testing
	 */
	public WasmStore() {
		this.functionAll = null;
		this.tableAll = null;
		this.memoryAll = null;
		this.globals = null;
	}

	public WasmStore(WasmVector<WasmFunction> functionAll, WasmVector<TableType> tableAll,
					 WasmVector<MemoryType> memoryAll, WasmVector<GlobalVariableType> globals) {
		this.functionAll = functionAll;
		this.tableAll = tableAll;
		this.memoryAll = memoryAll;
		this.globals = globals;
	}

	public WasmVector<TableType> getTableAll() {
		return tableAll;
	}

	public void setTableAll(WasmVector<TableType> tableAll) {
		this.tableAll = tableAll;
	}

	public WasmVector<WasmFunction> getFunctionAll() {
		return functionAll;
	}

	public void setFunctionAll(WasmVector<WasmFunction> functionAll) {
		this.functionAll = functionAll;
	}

	public WasmVector<MemoryType> getMemoryAll() {
		return memoryAll;
	}

	public void setMemoryAll(WasmVector<MemoryType> memoryAll) {
		this.memoryAll = memoryAll;
	}

	public MemoryType getMemory(DataTypeNumber index) {
		return memoryAll.get(index);
	}

	public WasmVector<GlobalVariableType> getGlobals() {
		return globals;
	}

	public void setGlobals(WasmVector<GlobalVariableType> globals) {
		this.globals = globals;
	}
}
