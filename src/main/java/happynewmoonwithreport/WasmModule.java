package happynewmoonwithreport;

import happynewmoonwithreport.type.MemoryType;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.WasmVector;

/**
 * A Web Assembly Module
 * <p>
 * WebAssembly programs are organized into modules, which are the unit of deployment, loading, and compilation. A module
 * collects definitions for types, functions, tables, memories, and globals. In addition, it can declare imports and
 * exports and provide initialization logic in the form of data and element segments or a start function.
 * <p>
 * <p>
 * Source: <a href="https://webassembly.github.io/spec/syntax/modules.html#">Modules</a>
 */

public class WasmModule {

    private UInt32 typeIndex;
    private UInt32 functionIndex;
    private UInt32 tableIndex;
    private UInt32 memoryIndex;
    private UInt32 globalIndex;
    private UInt32 localIndex;
    private UInt32 labelIndex;

    private WasmVector<FunctionType> types;
    private WasmVector<WasmFunction> functions;
    private WasmVector<TableType> tables;
    private WasmVector<MemoryType> memoryAll;  // aka mems
    private WasmVector<GlobalVariableType> globals;

    public WasmModule() {

        constructIndexAll();

        types = new WasmVector<>();
        functions = new WasmVector<>();
        tables = new WasmVector<>();
        memoryAll = new WasmVector<>();
        globals = new WasmVector<>();

    }

    public WasmModule(WasmVector<FunctionType> types,
                      WasmVector<WasmFunction> functions,
                      WasmVector<TableType> tables,
                      WasmVector<MemoryType> memoryAll,
                      WasmVector<GlobalVariableType> globals) {
        constructIndexAll();
        this.types = types;
        this.functions = functions;
        this.tables = tables;
        this.memoryAll = memoryAll;
        this.globals = globals;
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
}
