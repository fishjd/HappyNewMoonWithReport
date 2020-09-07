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

import happynewmoonwithreport.type.MemoryType;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.WasmVector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Web Assembly Module
 * <p>
 * WebAssembly programs are organized into modules, which are the unit of deployment, loading,
 * and compilation. A module
 * collects definitions for types, functions, tables, memories, and globals. In addition, it can
 * declare imports and
 * exports and provide initialization logic in the form of data and element segments or a start
 * function.
 * <p>
 * Source: <a href="https://webassembly.github.io/spec/core/syntax/modules.html#" target="_top">
 * Modules
 * </a>
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/core/binary/modules.html#" target="_top">
 * https://webassembly.github.io/spec/core/binary/modules.html#
 * </a>
 */
public class WasmModule {

	// https://webassembly.github.io/spec/core/syntax/modules.html#indices
	private UInt32 typeIndex;
	private UInt32 functionIndex;
	private UInt32 tableIndex;
	private UInt32 memoryIndex;
	private UInt32 globalIndex;
	private UInt32 localIndex;
	private UInt32 labelIndex;

	private WasmVector<FunctionType> types;
	private WasmVector<WasmFunction> functionAll; // aka funcs
	private WasmVector<TableType> tables;
	private WasmVector<MemoryType> memoryAll;  // aka mems
	private WasmVector<GlobalVariableType> globals;
	// private WasmVector<> elementAll;  // todo  // aka elem
	// private WasmVector<> dataAll // todo  // aka data
	/**
	 * index to start function. Optional
	 **/
	private UInt32 start;
	// todo
	// private WasmVector<ExportEntry> importAll;    // aka imports
	private WasmVector<ExportEntry> exportAll;    // aka exports


	public WasmModule() {

		constructIndexAll();

		types = new WasmVector<>();
		functionAll = new WasmVector<>();
		tables = new WasmVector<>();
		memoryAll = new WasmVector<>();
		globals = new WasmVector<>();
		start = new UInt32(0); // todo ?
		exportAll = new WasmVector<>();
		store = new WasmStore(functionAll, tables, memoryAll, globals);
	}

	public WasmModule(WasmVector<FunctionType> types, WasmVector<WasmFunction> functions,
					  WasmVector<TableType> tables, WasmVector<MemoryType> memoryAll,
					  WasmVector<GlobalVariableType> globals,
					  // to do element
					  // to do data
					  UInt32 start, WasmVector<ExportEntry> exportAll

	) {
		constructIndexAll();
		this.types = types;
		this.functionAll = functions;
		this.tables = tables;
		this.memoryAll = memoryAll;
		this.globals = globals;
		this.start = start;
		this.exportAll = exportAll;
		store = new WasmStore(functionAll, tables, memoryAll, globals);
	}

	private void constructIndexAll() {
		typeIndex = new UInt32(0);
		functionIndex = new UInt32(0);
		tableIndex = new UInt32(0);
		memoryIndex = new UInt32(0);
		globalIndex = new UInt32(0);
		localIndex = new UInt32(0);
		labelIndex = new UInt32(0);
	}

	/**
	 * Execute all the validity checks
	 * <p>
	 * Source:  <a href="https://webassembly.github.io/spec/core/valid/index.html" target="_top">
	 * https://webassembly.github.io/spec/core/valid/index.html
	 * </a>
	 *
	 * @return true if all validity checks pass.
	 */
	public Boolean validation() {
		Boolean isValid = true;

		for (FunctionType functionType : types) {
			Boolean valid = functionType.valid();
			if (valid == false) {
				Logger.getLogger(WasmModule.class.getName())
					.log(Level.SEVERE,
						"Function Type not valid! Function Type = " + functionType.toString());
			}
			isValid &= valid;
		}

		for (TableType tableType : tables) {
			Boolean valid = tableType.valid();
			if (valid == false) {
				Logger.getLogger(WasmModule.class.getName())
					.log(Level.SEVERE,
						"Table Type not valid! Table Type = " + tableType.toString());
			}
			isValid &= valid;
		}

		for (MemoryType memoryType : memoryAll) {
			Boolean valid = memoryType.valid();
			if (valid == false) {
				Logger.getLogger(WasmModule.class.getName())
					.log(Level.SEVERE,
						"Memory Type not valid! Memory Type = " + memoryType.toString());
			}
			isValid &= valid;
		}

		isValid &= validateGlobals();


		return isValid;
	}

	public Boolean validateGlobals() {
		Boolean isValid = true;
		for (GlobalVariableType globalVariable : globals) {
			Boolean valid = globalVariable.valid();
			if (valid == false) {
				Logger.getLogger(WasmModule.class.getName())
					.log(Level.SEVERE, "Global Variable not valid! Global Variable  = "
									   + globalVariable.toString());
			}
			isValid &= valid;
		}
		return isValid;
	}

	WasmStore store;

	public WasmStore getStore() {
		return store;
	}

	// boring getters and setters

	public void setTypeIndex(UInt32 typeIndex) {
		this.typeIndex = typeIndex;
	}

	public void setFunctionIndex(UInt32 functionIndex) {
		this.functionIndex = functionIndex;
	}

	public void setTableIndex(UInt32 tableIndex) {
		this.tableIndex = tableIndex;
	}

	/**
	 * Add a memory.   Used for testing.
	 *
	 * @param memoryToAdd memory to add to the module.
	 */
	public void addMemory(MemoryType memoryToAdd) {
		memoryAll.add(memoryToAdd);
	}

	/**
	 * Note the 'private' access.  MemoryIndex is zero for the MVP.
	 *
	 * @param memoryIndex
	 */
	private void setMemoryIndex(UInt32 memoryIndex) {
		if (memoryIndex.integerValue() != 0) {
			throw new RuntimeException("Memory Index may only be zero");
		}
		// this.memoryIndex = memoryIndex;
	}

	public void setGlobalIndex(UInt32 globalIndex) {
		this.globalIndex = globalIndex;
	}

	public void setLocalIndex(UInt32 localIndex) {
		this.localIndex = localIndex;
	}

	public void setLabelIndex(UInt32 labelIndex) {
		this.labelIndex = labelIndex;
	}

	public WasmVector<FunctionType> getTypes() {
		return types;
	}

	public UInt32 getStart() {
		return start;
	}

	public void setStart(UInt32 start) {
		this.start = start;
	}

	public WasmVector<WasmFunction> getFunctionAll() {
		return functionAll;
	}

	public WasmVector<ExportEntry> getExportAll() {
		return exportAll;
	}

	public WasmVector<MemoryType> getMemoryAll() {
		return memoryAll;
	}

	/**
	 * <i>Assert: due to validation, F.module.memaddrs[index] exists.</i>
	 *
	 * @param index only zero is valid in version 1.0
	 * @return true if memory[index] exists.
	 */
	public Boolean memoryExists(UInt32 index) {
		Boolean result;
		result = memoryAll.exists(index);
		return result;
	}

	public MemoryType getMemory(UInt32 index) {
		return memoryAll.get(index);
	}

}
