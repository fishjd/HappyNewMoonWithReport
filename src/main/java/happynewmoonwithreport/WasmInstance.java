package happynewmoonwithreport;

import happynewmoonwithreport.type.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.UUID;

/**
 * A WebAssembly.Instance object is a stateful, executable instance of a WebAssembly.Module.
 * <p>
 * Source:  <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/WebAssembly/Instance"
 * target="_top">
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/WebAssembly/Instance
 * </a>
 */
public class WasmInstance {
    private WasmModule module;

    /* TODO Change DataTypeNumber to Object or create a new "Stackable" type. */
    private WasmStack<DataTypeNumber> stack;

    private WasmInstance() {
        stack = new WasmStack();

    }

    public WasmInstance(WasmModule module) {
        this();
        this.module = module;
    }

    /**
     * Not Implemented.
     *
     * @param module
     * @param wasmImport
     */
    public WasmInstance(WasmModule module, WasmImport wasmImport) {
        this();
        throw new NotImplementedException();
    }

    /**
     * <p>
     * Source:  <a href="https://developer.mozilla.org/en-US/docs/WebAssembly/Exported_functions" target="_top">
     * https://developer.mozilla.org/en-US/docs/WebAssembly/Exported_functions
     * </a>
     *
     * @param name the function name
     *
     * @return WasmFunction
     */
    public WasmFunction exportFunction(String name) {
        WasmFunction result = null;
        WasmVector<WasmFunction> functionAll = module.getFunctionAll();

        for (ExportEntry exportEntry : module.getExportAll()) {
            Boolean found = exportEntry.getExternalKind().equals(new ExternalKind(ExternalKind.function));
            found &= exportEntry.getFieldName().getValue().equals(name);
            if (found) {
                result = functionAll.get(exportEntry.getIndex().integerValue());
                break;
            }
        }
        return result;

    }

    WasmFunction wasmFunction;

    /**
     * @param wasmFunction
     * @param returnAll
     * @param paramAll
     */
    public void call(WasmFunction wasmFunction, WasmVector<DataTypeNumber> returnAll, WasmVector<DataTypeNumber> paramAll) {
        this.wasmFunction = wasmFunction;
        wasmFunction.setLocals(paramAll);
        // TODO verify paramAll with LocalEntryAll

        BytesFile code = new BytesFile(wasmFunction.getCode());
        while (code.atEndOfFile() == false) {
            execute(code);
        }

        while (stack.isEmpty() == false) {  // ??? ¿¿¿
            returnAll.add(stack.pop());
        }
    }


    private void execute(BytesFile code) {
        byte opcode = code.readByte();
        switch (opcode) {
            case (byte) 0x20: {
                getLocal(new VarUInt32(code));
                break;
            }
            case (byte) 0x6a: {
                addI32();
                break;
            }
            default:
                throwUnknownOpcodeException(opcode);
                return;
        }
    }

    private void throwUnknownOpcodeException(byte opcode) {
        String message = "Wasm tried to run an opcode that was not defined. Unknown Opcode = " + opcode;
        String possibleSolutions = "Verify the wasm file is valid.  Recompile Wasm File.  Contact support.";
        throw new WasmRuntimeException(UUID.fromString("6b5700ee-9642-4544-8850-22794071e848"), message, possibleSolutions);
    }

    /**
     * Get Local Opcode
     * <p>
     * <ol>
     * <li>
     * Let F be the current frame.
     * </li>
     * <li>
     * Assert: due to validation, F.locals[x] exists.
     * </li>
     * <li>
     * Let val be the value F.locals[x]
     * </li>
     * <li>
     * Push the value val to the stack.
     * </ol>
     * <p>
     * Source:  <a href="https://webassembly.github.io/spec/exec/instructions.html#variable-instructions" target="_top">
     * https://webassembly.github.io/spec/exec/instructions.html#variable-instructions
     * </a>
     * <p>
     * Source:  <a href="http://webassembly.org/docs/binary-encoding/#variable-access-described-here" target="_top">
     * http://webassembly.org/docs/binary-encoding/#variable-access-described-here
     * </a>
     *
     * @param index
     */
    private void getLocal(UInt32 index) {
        if ((index.integerValue() <= wasmFunction.getLocals().size()) == false) {
            throw new WasmRuntimeException(UUID.fromString("dcbf3c1d-334a-451d-9010-e32bdc876e9d"),
                    "getLocal: Local variable " + index.integerValue() + " does not exist");
        }
        DataTypeNumber value = wasmFunction.getLocals().get(index.integerValue());
        stack.push(value);
    }

    /**
     * Sign-agnostic addition
     * <p>
     * Source:  <a href="https://webassembly.github.io/spec/exec/instructions.html#numeric-instructions" target="_top">
     * https://webassembly.github.io/spec/exec/instructions.html#numeric-instructions  t.binop
     * </a>
     */
    private void addI32() {
        if ((stack.peek() instanceof Int32) == false) {
            throw new WasmRuntimeException(UUID.fromString("22500212-e077-4507-a27a-3a08039da2b7"),
                    "addI32: Value1 type is incorrect");
        }
        Int32 value1 = (Int32) stack.pop();
        if ((stack.peek() instanceof Int32) == false) {
            throw new WasmRuntimeException(UUID.fromString("59c20edb-690b-4260-b5cf-704cd509ac07"),
                    "addI32: Value2 type is incorrect");
        }
        Int32 value2 = (Int32) stack.pop();

        Int32 result = new Int32(value1.integerValue() + value2.integerValue());

        stack.push(result);
    }
}
