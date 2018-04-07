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
 * Web Assembly Source:  <a href="https://webassembly.github.io/spec/core/exec/runtime.html#module-instances" target="_top">
 * https://webassembly.github.io/spec/core/exec/runtime.html#module-instances
 * </a>
 * <p>
 * JavaScript Source:  <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/WebAssembly/Instance"
 * target="_top">
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/WebAssembly/Instance
 * </a>
 */
public class WasmInstance implements WasmInstanceInterface {
	private WasmModule module;
	private WasmFunction wasmFunction;
	private WasmFrame currentFrame;
	private WasmStore store;

	/**
	 * the local variables
	 **/
	private WasmVector<DataTypeNumber> localAll;

	private WasmStack<Object> stack;
	private BytesFile code;

	private WasmInstance() {
		stack = new WasmStack();
		currentFrame = new WasmFrame(module);

	}

	/**
	 * @param module Web Assembly Module
	 */
	public WasmInstance(WasmModule module) {
		this();
		this.module = module;
		this.store = module.getStore();
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

		for (Integer i = 0; i < wasmFunction.getLocalEntryAll().size(); i++) {
			currentFrame.localAll().add(new S32(0));
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
			case (byte) 0x00: {  // Unreachable
				Unreachable unreachable = new Unreachable(this);
				unreachable.execute();
				break;
			}
			case (byte) 0x01: {  // nop,  no operation.
				Nop nop = new Nop(this);
				nop.execute();
				break;
			}
			case (byte) 0x02: {
				Block block = new Block(this);
				block.execute();
				break;
			}
			case (byte) 0x1A: { // drop
				Drop drop = new Drop(this);
				drop.execute();
				break;
			}
			case (byte) 0x1B: { // select
				Select select = new Select(this);
				select.execute();
				break;
			}
			case (byte) 0x20: {  // get local
				GetLocal getLocal = new GetLocal(currentFrame, stack);
				getLocal.execute(new VarUInt32(code));
				break;
			}
			case (byte) 0x21: {
				SetLocal setLocal = new SetLocal(currentFrame, stack);
				setLocal.execute(new VarUInt32(code));
				break;
			}
			case (byte) 0x28: {  // I32_load
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I32_load i32_load = new I32_load(memoryArgument, currentFrame, store, stack);
				i32_load.execute();
				break;
			}
			case (byte) 0x40: {
				break;
			}
			case (byte) 0x41: {  // i32.const i32
				ConstantInt32 constantInt32 = new ConstantInt32(this);
				constantInt32.execute(new VarUInt32(code));
				break;
			}
			case (byte) 0x45: { // i32 equals zero
				I32_eqz i32_eqz = new I32_eqz(this);
				i32_eqz.execute();
				break;
			}
			case (byte) 0x46: { // i32 equals
				I32_eq i32_eq = new I32_eq(this);
				i32_eq.execute();
				break;
			}
			case (byte) 0x47: { // i32 not equals
				I32_ne i32_ne = new I32_ne(this);
				i32_ne.execute();
				break;
			}
			case (byte) 0x48: { // i32 less than signed
				I32_lt_s i32_lt_s = new I32_lt_s(this);
				i32_lt_s.execute();
				break;
			}
			case (byte) 0x49: { // i32 less than unsigned
				I32_lt_u i32_lt_u = new I32_lt_u(this);
				i32_lt_u.execute();
				break;
			}
			case (byte) 0x4A: { // i32 greater than signed
				I32_gt_s i32_gt_s = new I32_gt_s(this);
				i32_gt_s.execute();
				break;
			}
			case (byte) 0x4B: { // i32 greater than unsigned
				I32_gt_u i32_gt_u = new I32_gt_u(stack);
				i32_gt_u.execute();
				break;
			}
			case (byte) 0x4C: { // i32 less than or equal to signed
				I32_le_s i32_le_s = new I32_le_s(this);
				i32_le_s.execute();
				break;
			}
			case (byte) 0x4D: { // i32 less than or equal to unsigned
				I32_le_u i32_le_u = new I32_le_u(this);
				i32_le_u.execute();
				break;
			}
			case (byte) 0x4E: { // i32 greater than or equal to signed
				I32_ge_s i32_ge_s = new I32_ge_s(this);
				i32_ge_s.execute();
				break;
			}
			case (byte) 0x4F: { // i32 greater than or equal to unsigned
				I32_ge_u i32_ge_u = new I32_ge_u(this);
				i32_ge_u.execute();
				break;
			}
			case (byte) 0x55: { // i64 greater than signed
				I64_gt_s i64_gt_s = new I64_gt_s(this);
				i64_gt_s.execute();
				break;
			}
            case (byte) 0x6A: {
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
		String message = "Wasm tried to run an opcode that was not defined. Unknown Opcode = " + Hex.byteToHex(opcode) + " (0d" + opcode + ")";
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

	@Override
	public BytesFile getCode() {
		return code;
	}
}
