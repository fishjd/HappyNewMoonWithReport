/*
 *  Copyright 2017 Whole Bean Software, LTD.
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

import happynewmoonwithreport.opcode.*;
import happynewmoonwithreport.type.*;
import happynewmoonwithreport.type.utility.Hex;
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
public class WasmInstance implements WasmInstanceInterface {
    private WasmModule module;
    private WasmFunction wasmFunction;
    private WasmFrame currentFrame;

    /**
     * the local variables
     **/
    private WasmVector<DataTypeNumber> localAll;

    private WasmStack<Object> stack;
    private BytesFile code;

    private WasmInstance() {
        stack = new WasmStack();
        currentFrame = new WasmFrame(this);

    }

    /**
     * @param module Web Assembly Module
     */
    public WasmInstance(WasmModule module) {
        this();
        this.module = module;
    }

    /**
     * Not Implemented.
     *
     * @param module Web Assembly Module
     * @param wasmImport Web Assembly Import
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


    /**
     * Execute an function.  The function must be and export.   This is the entry point from Java.
     *
     * @param wasmFunction The function to execute/run.
     * @param returnAll The output parameters.  May be zero on one.  Future versions of Wasm may return more tha one.
     * @param paramAll The input parameters.
     */
    public void call(WasmFunction wasmFunction, WasmVector<DataTypeNumber> returnAll, WasmVector<DataTypeNumber> paramAll) {
        this.wasmFunction = wasmFunction;
        currentFrame.setLocalAll(paramAll);
        // TODO verify paramAll with LocalEntryAll

        for ( Integer i = 0 ; i < wasmFunction.getLocalEntryAll().size() ;  i++) {
            currentFrame.localAll().add(new Int32(0));
        }

        BytesFile code = new BytesFile(wasmFunction.getCode());
        while (code.atEndOfFile() == false) {
            execute(code);
        }

        while (stack.isEmpty() == false) {  // ??? ¿¿¿
            returnAll.add((DataTypeNumber) stack.pop());
        }
    }

    /**
     * Source:  <a href="https://webassembly.github.io/spec/appendix/index-instructions.html" target="_top">
     * https://webassembly.github.io/spec/appendix/index-instructions.html
     * </a>
     */
    private void execute(BytesFile code) {
        this.code = code;
        byte opcode = code.readByte();
        switch (opcode) {
            case (byte) 0x00: {
                Unreachable unreachable = new Unreachable(this);
                unreachable.execute();
                break;
            }
            case (byte) 0x01: {
                Nop nop = new Nop(this);
                nop.execute();
                break;
            }
            case (byte) 0x02: {
                Block block = new Block(this);
                block.execute();
                break;
            }
            case (byte) 0x20: {
                GetLocal getLocal = new GetLocal(currentFrame);
                getLocal.execute(new VarUInt32(code));
                break;
            }
            case (byte) 0x21: {
                SetLocal setLocal = new SetLocal(currentFrame);
                setLocal.execute(new VarUInt32(code));
                break;
            }
            case (byte) 0x40: {
                break;
            }
            case (byte) 0x41: {  // i32.const i32
                ConstantInt32 constantInt32 = new ConstantInt32(this);
                constantInt32.execute(new VarInt32(code));
                break;
            }
            case (byte) 0x6a: {
                AddI32 addI32 = new AddI32(this);
                addI32.execute();
                break;
            }
            default:
                throwUnknownOpcodeException(opcode, code.getIndex());
                return;
        }
    }

    private void throwUnknownOpcodeException(byte opcode, Integer index) {
        String message = "Wasm tried to run an opcode that was not defined. Unknown Opcode = " + Hex.byteToHex(opcode) +" (0d" + opcode +")" ;
        message += " at byte number = " + index + ". ";
        String possibleSolutions = "Verify the wasm file is valid.  Recompile Wasm File.  Contact support.";
        throw new WasmRuntimeException(UUID.fromString("6b5700ee-9642-4544-8850-22794071e848"), message, possibleSolutions);
    }


    @Override
    public WasmStack<Object> stack() {
        return stack;
    }

    @Override
    public WasmVector<DataTypeNumber> localAll() {
        return localAll;
    }

    public BytesFile getCode() {
        return code;
    }
}
