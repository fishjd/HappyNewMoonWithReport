/*
 *  Copyright 2017 - 2019 Whole Bean Software, LTD.
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

import java.util.UUID;

import happynewmoonwithreport.opcode.AddI32;
import happynewmoonwithreport.opcode.Block;
import happynewmoonwithreport.opcode.ConstantInt32;
import happynewmoonwithreport.opcode.Drop;
import happynewmoonwithreport.opcode.GetLocal;
import happynewmoonwithreport.opcode.I32_Mul;
import happynewmoonwithreport.opcode.I32_Sub;
import happynewmoonwithreport.opcode.I32_eq;
import happynewmoonwithreport.opcode.I32_eqz;
import happynewmoonwithreport.opcode.I32_ge_s;
import happynewmoonwithreport.opcode.I32_ge_u;
import happynewmoonwithreport.opcode.I32_gt_s;
import happynewmoonwithreport.opcode.I32_gt_u;
import happynewmoonwithreport.opcode.I32_le_s;
import happynewmoonwithreport.opcode.I32_le_u;
import happynewmoonwithreport.opcode.I32_lt_s;
import happynewmoonwithreport.opcode.I32_lt_u;
import happynewmoonwithreport.opcode.I32_ne;
import happynewmoonwithreport.opcode.I64_eq;
import happynewmoonwithreport.opcode.I64_eqz;
import happynewmoonwithreport.opcode.I64_ge_s;
import happynewmoonwithreport.opcode.I64_ge_u;
import happynewmoonwithreport.opcode.I64_gt_s;
import happynewmoonwithreport.opcode.I64_le_s;
import happynewmoonwithreport.opcode.I64_le_u;
import happynewmoonwithreport.opcode.I64_lt_s;
import happynewmoonwithreport.opcode.I64_lt_u;
import happynewmoonwithreport.opcode.I64_ne;
import happynewmoonwithreport.opcode.Nop;
import happynewmoonwithreport.opcode.Select;
import happynewmoonwithreport.opcode.SetLocal;
import happynewmoonwithreport.opcode.Unreachable;
import happynewmoonwithreport.opcode.memory.I32_load;
import happynewmoonwithreport.opcode.memory.I32_load16_s;
import happynewmoonwithreport.opcode.memory.I32_load16_u;
import happynewmoonwithreport.opcode.memory.I32_load8_s;
import happynewmoonwithreport.opcode.memory.I32_load8_u;
import happynewmoonwithreport.opcode.memory.I32_store;
import happynewmoonwithreport.opcode.memory.I32_store16;
import happynewmoonwithreport.opcode.memory.I32_store8;
import happynewmoonwithreport.opcode.memory.I64_load;
import happynewmoonwithreport.opcode.memory.I64_load16_s;
import happynewmoonwithreport.opcode.memory.I64_load16_u;
import happynewmoonwithreport.opcode.memory.I64_load32_s;
import happynewmoonwithreport.opcode.memory.I64_load8_s;
import happynewmoonwithreport.opcode.memory.I64_load8_u;
import happynewmoonwithreport.opcode.memory.I64_store;
import happynewmoonwithreport.opcode.memory.I64_store16;
import happynewmoonwithreport.opcode.memory.I64_store32;
import happynewmoonwithreport.opcode.memory.I64_store8;
import happynewmoonwithreport.type.DataTypeNumber;
import happynewmoonwithreport.type.MemoryArgument;
import happynewmoonwithreport.type.S32;
import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.WasmVector;
import happynewmoonwithreport.type.utility.Hex;

/**
 * A WebAssembly.Instance object is a stateful, executable instance of a WebAssembly.Module.
 * <p>
 * Web Assembly Source:
 * <a href="https://webassembly.github.io/spec/core/exec/runtime.html#module-instances"
 * target="_top"> https://webassembly.github.io/spec/core/exec/runtime.html#module-instances
 * </a>
 * <p>
 * JavaScript Source:
 * <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/WebAssembly/Instance"
 * target="_top"> https://developer.mozilla
 * .org/en-US/docs/Web/JavaScript/Reference/Global_Objects/WebAssembly/Instance
 * </a>
 */
public class WasmInstance implements WasmInstanceInterface {
	private WasmModule module;
	private WasmFunction wasmFunction;
	private WasmFrame currentFrame;
	private WasmStore store;
	private WasmVector<DataTypeNumber> localAll;
	private WasmStack<Object> stack;
	private BytesFile code;

	private WasmInstance() {
		stack = new WasmStack();
		currentFrame = new WasmFrame(module);

	}

	/**
	 * Construct a WasmInstance with a Web Assembly Module.
	 *
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
	 * @param module     Web Assembly Module
	 * @param wasmImport Web Assembly Import
	 */
	public WasmInstance(WasmModule module, WasmImport wasmImport) {
		this();
		throw new RuntimeException("Not Implemented");
	}

	/**
	 * Given a function name then return the Wasm Function.  The Wasm Funciton is definced in the
	 * Wasm Module.
	 * <p>
	 * Source:  <a href="https://developer.mozilla.org/en-US/docs/WebAssembly/Exported_functions"
	 * target="_top"> https://developer.mozilla.org/en-US/docs/WebAssembly/Exported_functions
	 * </a>
	 *
	 * @param name the function name
	 * @return WasmFunction
	 */
	public WasmFunction exportFunction(String name) {
		WasmFunction result = null;
		WasmVector<WasmFunction> functionAll = module.getFunctionAll();

		for (ExportEntry exportEntry : module.getExportAll()) {
			Boolean found = exportEntry.getExternalKind().equals(
				new ExternalKind(ExternalKind.function));
			found &= exportEntry.getFieldName().getValue().equals(name);
			if (found) {
				result = functionAll.get(exportEntry.getIndex().integerValue());
				break;
			}
		}
		return result;

	}


	/**
	 * Execute an function.  The function must be an export.   This is the entry point from Java.
	 *
	 * @param wasmFunction The function to execute/run.
	 * @param returnAll    The output parameters.  May be zero on one.  Future versions of Wasm may
	 *                     return more tha one.
	 * @param paramAll     The input parameters.
	 */
	public void call(WasmFunction wasmFunction, WasmVector<DataTypeNumber> returnAll,
		WasmVector<DataTypeNumber> paramAll) {
		this.wasmFunction = wasmFunction;
		currentFrame.setLocalAll(paramAll);
		// TODO verify paramAll with LocalEntryAll

		for (Integer i = 0; i < wasmFunction.getLocalEntryAll().size(); i++) {
			currentFrame.localAll().add(new S32(0));
		}

		BytesFile bfCode = new BytesFile(wasmFunction.getCode());
		while (bfCode.atEndOfFile() == false) {
			execute(bfCode);
		}

		while (stack.isEmpty() == false) {  // ??? ¿¿¿
			returnAll.add((DataTypeNumber) stack.pop());
		}
	}

	/**
	 * Run a file of Web Assembly byte codes.
	 * <p>
	 * Source: <a href="https://webassembly.github.io/spec/core/appendix/index-instructions.html"
	 * target="_top"> https://webassembly.github.io/spec/core/appendix/index-instructions.html
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
			//			case (byte) 0x03: { break;}  // Loop
			//			case (byte) 0x04: { break;}  // If
			//			case (byte) 0x05: { break;}  // Else

			//			case (byte) 0x0B: { break;}  // End
			//			case (byte) 0x0C: { break;}  // Branch lable
			//			case (byte) 0x0D: { break;}  // Branch If lable
			//			case (byte) 0x0E: { break;}  // Branch Table
			//			case (byte) 0x0F: { break;}  // Return
			//			case (byte) 0x10: { break;}  // Call x

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

			//			case (byte) 0x22: { break;}  // Tee Local x
			//			case (byte) 0x23: { break;}  // Get Global x
			//			case (byte) 0x24: { break;}  // Set Global x


			case (byte) 0x28: {  // I32_load
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I32_load i32_load = new I32_load(memoryArgument, currentFrame, store, stack);
				i32_load.execute();
				break;
			}
			case (byte) 0x29: {   // I64_load
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I64_load i64_load = new I64_load(memoryArgument, currentFrame, store, stack);
				i64_load.execute();
				break;
			}
			//			case (byte) 0x2A: {   // F32_load
			//			case (byte) 0x2B: {   // F64_load
			case (byte) 0x2C: {   // I32_load8_s
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I32_load8_s i32_load8_s = new I32_load8_s(memoryArgument, currentFrame, store,
														  stack);
				i32_load8_s.execute();
				break;
			}
			case (byte) 0x2D: {   // I32_load8_u
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I32_load8_u i32_load8_u = new I32_load8_u(memoryArgument, currentFrame, store,
														  stack);
				i32_load8_u.execute();
				break;
			}
			case (byte) 0x2E: {   // I32_load16_s
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I32_load16_s i32_load16_s = new I32_load16_s(memoryArgument, currentFrame, store,
															 stack);
				i32_load16_s.execute();
				break;
			}
			case (byte) 0x2F: {   // I32_load16_u
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I32_load16_u i32_load16_u = new I32_load16_u(memoryArgument, currentFrame, store,
															 stack);
				i32_load16_u.execute();
				break;
			}
			case (byte) 0x30: {   // I64_load8_s
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I64_load8_s i64_load8_s = new I64_load8_s(memoryArgument, currentFrame, store,
														  stack);
				i64_load8_s.execute();
				break;
			}
			case (byte) 0x31: {   // I64_load8_u
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I64_load8_u i64_load8_u = new I64_load8_u(memoryArgument, currentFrame, store,
														  stack);
				i64_load8_u.execute();
				break;
			}
			case (byte) 0x32: {   // I64_load16_s
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I64_load16_s i64_load16_s = new I64_load16_s(memoryArgument, currentFrame, store,
															 stack);
				i64_load16_s.execute();
				break;
			}
			case (byte) 0x33: {   // I64_load16_u
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I64_load16_u i64_load16_u = new I64_load16_u(memoryArgument, currentFrame, store,
															 stack);
				i64_load16_u.execute();
				break;
			}
			case (byte) 0x34: {   // I64_load32_s
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I64_load32_s i64_load32_s = new I64_load32_s(memoryArgument, currentFrame, store,
															 stack);
				i64_load32_s.execute();
				break;
			}

			//			case (byte) 0x35: {   // I64_load32_u


			case (byte) 0x36: {    // I32_store
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I32_store i32_store = new I32_store(memoryArgument, currentFrame, store, stack);
				i32_store.execute();
				break;
			}
			case (byte) 0x37: {      // I64 store
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I64_store i64_store = new I64_store(memoryArgument, currentFrame, store, stack);
				i64_store.execute();
				break;
			}
			//			case (byte) 0x38: {      // F32 store
			//			case (byte) 0x39: {      // F64 store
			case (byte) 0x3A: {      // I32 8 store
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I32_store8 i32_store8 = new I32_store8(memoryArgument, currentFrame, store, stack);
				i32_store8.execute();
				break;
			}
			case (byte) 0x3B: {      // I32 16 store
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I32_store16 i32_store16 = new I32_store16(memoryArgument, currentFrame, store,
														  stack);
				i32_store16.execute();
				break;
			}
			case (byte) 0x3C: {      // I64 8 store
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I64_store8 i64_store8 = new I64_store8(memoryArgument, currentFrame, store, stack);
				i64_store8.execute();
				break;
			}
			case (byte) 0x3D: {      // I64 16 store
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I64_store16 i64_store16 = new I64_store16(memoryArgument, currentFrame, store,
														  stack);
				i64_store16.execute();
				break;
			}
			case (byte) 0x3E: {      // I64 32 store
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I64_store32 i64_store32 = new I64_store32(memoryArgument, currentFrame, store,
														  stack);
				i64_store32.execute();
				break;
			}
			//			case (byte) 0x3F: { break;}  // Memory Size
			//			case (byte) 0x40: { break;}  // Memory Grow

			case (byte) 0x41: {  // i32.const i32
				ConstantInt32 constantInt32 = new ConstantInt32(this);
				constantInt32.execute(new VarUInt32(code));
				break;
			}
			//			case (byte) 0x42: { break;}  // I64 const I64
			//			case (byte) 0x43: { break;}  // F32 const F32
			//			case (byte) 0x44: { break;}  // F64 const F64

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
			case (byte) 0x50: { // i64 equals zero
				I64_eqz i64_eqz = new I64_eqz(this);
				i64_eqz.execute();
				break;
			}
			case (byte) 0x51: { // i64 equals
				I64_eq i64_eq = new I64_eq(this);
				i64_eq.execute();
				break;
			}
			case (byte) 0x52: { // i64 not equals
				I64_ne i64_ne = new I64_ne(this);
				i64_ne.execute();
				break;
			}
			case (byte) 0x53: { // i64 less than signed
				I64_lt_s i64_lt_s = new I64_lt_s(this);
				i64_lt_s.execute();
				break;
			}
			case (byte) 0x54: { // i64 less than unsigned
				I64_lt_u i64_lt_u = new I64_lt_u(this);
				i64_lt_u.execute();
				break;
			}
			case (byte) 0x55: { // i64 greater than signed
				I64_gt_s i64_gt_s = new I64_gt_s(this);
				i64_gt_s.execute();
				break;
			}
			case (byte) 0x56: { // i64 greater than signed
				I64_gt_s i64_gt_s = new I64_gt_s(this);
				i64_gt_s.execute();
				break;
			}
			case (byte) 0x57: { // i64 less than equal to signed
				I64_le_s i64_le_s = new I64_le_s(this);
				i64_le_s.execute();
				break;
			}
			case (byte) 0x58: { // i64 less than equal to unsigned
				I64_le_u i64_le_u = new I64_le_u(this);
				i64_le_u.execute();
				break;
			}
			case (byte) 0x59: { // i64 greater than equal to signed
				I64_ge_s i64_ge_s = new I64_ge_s(this);
				i64_ge_s.execute();
				break;
			}
			case (byte) 0x5A: { // i64 greater than equal to unsigned
				I64_ge_u i64_ge_u = new I64_ge_u(this);
				i64_ge_u.execute();
				break;
			}
			case (byte) 0x6A: {
				AddI32 addI32 = new AddI32(this);
				addI32.execute();
				break;
			}
			case (byte) 0x6B: {
				I32_Sub i32_sub = new I32_Sub(this);
				i32_sub.execute();
				break;
			}
			case (byte) 0x6C: { // i32 multiply
				I32_Mul i32_mul = new I32_Mul(this);
				i32_mul.execute();
				break;
			}
			default:
				throwUnknownOpcodeException(opcode, code.getIndex());
				return;
		}
	}

	private void throwUnknownOpcodeException(byte opcode, Integer index) {
		String message =
			"Wasm tried to run an opcode that was not defined. Unknown Opcode = " + Hex.byteToHex(
				opcode) + " (0d" + opcode + ")";
		message += " at byte number = " + index + ". ";
		String possibleSolutions =
			"Verify the wasm file is valid.  Recompile Wasm File.  Contact " + "support.";
		throw new WasmRuntimeException(UUID.fromString("6b5700ee-9642-4544-8850-22794071e848"),
									   message, possibleSolutions);
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
