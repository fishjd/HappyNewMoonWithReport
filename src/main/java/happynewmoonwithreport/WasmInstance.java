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

import happynewmoonwithreport.opcode.*;
import happynewmoonwithreport.opcode.Memory.*;
import happynewmoonwithreport.opcode.bitshift.*;
import happynewmoonwithreport.opcode.comparison.*;
import happynewmoonwithreport.opcode.comparison.F32.F32_eq;
import happynewmoonwithreport.opcode.control.Block;
import happynewmoonwithreport.opcode.control.End;
import happynewmoonwithreport.opcode.control.Nop;
import happynewmoonwithreport.opcode.control.Unreachable;
import happynewmoonwithreport.opcode.convert.*;
import happynewmoonwithreport.opcode.countingBits.*;
import happynewmoonwithreport.opcode.logic.*;
import happynewmoonwithreport.opcode.math.*;
import happynewmoonwithreport.type.*;
import happynewmoonwithreport.type.utility.Hex;
import java.util.UUID;


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
			Boolean found =
				exportEntry.getExternalKind().equals(new ExternalKind(ExternalKind.function));
			found &= exportEntry.getFieldName().getValue().equals(name);
			if (found) {
				result = functionAll.get(exportEntry.getIndex().integerValue());
				break;
			}
		}
		return result;

	}


	/**
	 * Execute a function.  The function must be an export.   This is the entry point from Java.
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

		// copy the stack to the returnAll Vector.
		while (stack.isEmpty() == false) {
			returnAll.add((DataTypeNumber) stack.pop());
		}
	}

	/**
	 * Run one opcode in the byte file.
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

			case (byte) 0x0B: { // End Opcode
				End end = new End(this);
				end.execute();
				break;
			}
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
			case (byte) 0x2A: {   // F32_load
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				F32_load f32_load = new F32_load(memoryArgument, currentFrame, store, stack);
				f32_load.execute();
				break;
			}
			case (byte) 0x2B: {   // F64_load
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				F64_load f64_load = new F64_load(memoryArgument, currentFrame, store, stack);
				f64_load.execute();
				break;
			}
			case (byte) 0x2C: {   // I32_load8_s
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I32_load8_s i32_load8_s =
					new I32_load8_s(memoryArgument, currentFrame, store, stack);
				i32_load8_s.execute();
				break;
			}
			case (byte) 0x2D: {   // I32_load8_u
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I32_load8_u i32_load8_u =
					new I32_load8_u(memoryArgument, currentFrame, store, stack);
				i32_load8_u.execute();
				break;
			}
			case (byte) 0x2E: {   // I32_load16_s
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I32_load16_s i32_load16_s =
					new I32_load16_s(memoryArgument, currentFrame, store, stack);
				i32_load16_s.execute();
				break;
			}
			case (byte) 0x2F: {   // I32_load16_u
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I32_load16_u i32_load16_u =
					new I32_load16_u(memoryArgument, currentFrame, store, stack);
				i32_load16_u.execute();
				break;
			}
			case (byte) 0x30: {   // I64_load8_s
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I64_load8_s i64_load8_s =
					new I64_load8_s(memoryArgument, currentFrame, store, stack);
				i64_load8_s.execute();
				break;
			}
			case (byte) 0x31: {   // I64_load8_u
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I64_load8_u i64_load8_u =
					new I64_load8_u(memoryArgument, currentFrame, store, stack);
				i64_load8_u.execute();
				break;
			}
			case (byte) 0x32: {   // I64_load16_s
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I64_load16_s i64_load16_s =
					new I64_load16_s(memoryArgument, currentFrame, store, stack);
				i64_load16_s.execute();
				break;
			}
			case (byte) 0x33: {   // I64_load16_u
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I64_load16_u i64_load16_u =
					new I64_load16_u(memoryArgument, currentFrame, store, stack);
				i64_load16_u.execute();
				break;
			}
			case (byte) 0x34: {   // I64_load32_s
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I64_load32_s i64_load32_s =
					new I64_load32_s(memoryArgument, currentFrame, store, stack);
				i64_load32_s.execute();
				break;
			}
			case (byte) 0x35: {   // I64_load32_u
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I64_load32_u i64_load32_u =
					new I64_load32_u(memoryArgument, currentFrame, store, stack);
				i64_load32_u.execute();
				break;
			}


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
			case (byte) 0x38: {      // F32 store
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				F32_store f32_store = new F32_store(memoryArgument, currentFrame, store, stack);
				f32_store.execute();
				break;
			}
			case (byte) 0x39: {      // F64 store
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				F64_store f64_store = new F64_store(memoryArgument, currentFrame, store, stack);
				f64_store.execute();
				break;
			}
			case (byte) 0x3A: {      // I32 8 store
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I32_store8 i32_store8 = new I32_store8(memoryArgument, currentFrame, store, stack);
				i32_store8.execute();
				break;
			}
			case (byte) 0x3B: {      // I32 16 store
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I32_store16 i32_store16 =
					new I32_store16(memoryArgument, currentFrame, store, stack);
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
				I64_store16 i64_store16 =
					new I64_store16(memoryArgument, currentFrame, store, stack);
				i64_store16.execute();
				break;
			}
			case (byte) 0x3E: {      // I64 32 store
				MemoryArgument memoryArgument = new MemoryArgument(); // Not sure what this is.
				I64_store32 i64_store32 =
					new I64_store32(memoryArgument, currentFrame, store, stack);
				i64_store32.execute();
				break;
			}
			//			case (byte) 0x3F: { break;}  // Memory Size
			//			case (byte) 0x40: { break;}  // Memory Grow

			case (byte) 0x41: {  // I32.const i32
				I32_const i32_const = new I32_const(this);
				i32_const.execute(new VarInt32(code)); // Not sure if this is signed or unsigned
				break;
			}
			case (byte) 0x42: {   // I64 const I64
				I64_const i64_const = new I64_const(this);
				i64_const.execute(new VarInt64(code)); // Not sure if this is signed or unsigned
				break;
			}
			case (byte) 0x43: {  // F32 const F32
				// read the value from the wasm file
				F32 value = F32.convert(code);
				// execute the opcode
				F32_const f32_const = new F32_const(this);
				f32_const.execute(value);
				break;
			}
			case (byte) 0x44: {  // F64 const F64
				// read the value from the wasm file
				F64 value = F64.convert(code);
				// execute the opcode
				F64_const f64_const = new F64_const(this);
				f64_const.execute(value);
				break;
			}
			case (byte) 0x45: { // I32 equals zero
				I32_eqz i32_eqz = new I32_eqz(this);
				i32_eqz.execute();
				break;
			}
			case (byte) 0x46: { // I32 equals
				I32_eq i32_eq = new I32_eq(this);
				i32_eq.execute();
				break;
			}
			case (byte) 0x47: { // I32 not equals
				I32_ne i32_ne = new I32_ne(this);
				i32_ne.execute();
				break;
			}
			case (byte) 0x48: { // I32 less than signed
				I32_lt_s i32_lt_s = new I32_lt_s(this);
				i32_lt_s.execute();
				break;
			}
			case (byte) 0x49: { // I32 less than unsigned
				I32_lt_u i32_lt_u = new I32_lt_u(this);
				i32_lt_u.execute();
				break;
			}
			case (byte) 0x4A: { // I32 greater than signed
				I32_gt_s i32_gt_s = new I32_gt_s(this);
				i32_gt_s.execute();
				break;
			}
			case (byte) 0x4B: { // I32 greater than unsigned
				I32_gt_u i32_gt_u = new I32_gt_u(stack);
				i32_gt_u.execute();
				break;
			}
			case (byte) 0x4C: { // I32 less than or equal to signed
				I32_le_s i32_le_s = new I32_le_s(this);
				i32_le_s.execute();
				break;
			}
			case (byte) 0x4D: { // I32 less than or equal to unsigned
				I32_le_u i32_le_u = new I32_le_u(this);
				i32_le_u.execute();
				break;
			}
			case (byte) 0x4E: { // I32 greater than or equal to signed
				I32_ge_s i32_ge_s = new I32_ge_s(this);
				i32_ge_s.execute();
				break;
			}
			case (byte) 0x4F: { // I32 greater than or equal to unsigned
				I32_ge_u i32_ge_u = new I32_ge_u(this);
				i32_ge_u.execute();
				break;
			}
			case (byte) 0x50: { // I64 equals zero
				I64_eqz i64_eqz = new I64_eqz(this);
				i64_eqz.execute();
				break;
			}
			case (byte) 0x51: { // I64 equals
				I64_eq i64_eq = new I64_eq(this);
				i64_eq.execute();
				break;
			}
			case (byte) 0x52: { // I64 not equals
				I64_ne i64_ne = new I64_ne(this);
				i64_ne.execute();
				break;
			}
			case (byte) 0x53: { // I64 less than signed
				I64_lt_s i64_lt_s = new I64_lt_s(this);
				i64_lt_s.execute();
				break;
			}
			case (byte) 0x54: { // I64 less than unsigned
				I64_lt_u i64_lt_u = new I64_lt_u(this);
				i64_lt_u.execute();
				break;
			}
			case (byte) 0x55: { // I64 greater than signed
				I64_gt_s i64_gt_s = new I64_gt_s(this);
				i64_gt_s.execute();
				break;
			}
			case (byte) 0x56: { // I64 greater than unsigned
				I64_gt_u i64_gt_u = new I64_gt_u(this);
				i64_gt_u.execute();
				break;
			}
			case (byte) 0x57: { // I64 less than equal to signed
				I64_le_s i64_le_s = new I64_le_s(this);
				i64_le_s.execute();
				break;
			}
			case (byte) 0x58: { // I64 less than equal to unsigned
				I64_le_u i64_le_u = new I64_le_u(this);
				i64_le_u.execute();
				break;
			}
			case (byte) 0x59: { // I64 greater than equal to signed
				I64_ge_s i64_ge_s = new I64_ge_s(this);
				i64_ge_s.execute();
				break;
			}
			case (byte) 0x5A: { // I64 greater than equal to unsigned
				I64_ge_u i64_ge_u = new I64_ge_u(this);
				i64_ge_u.execute();
				break;
			}
			case (byte) 0x5B: {	// F32 Equal
				F32_eq f32_eq = new F32_eq(this);
				f32_eq.execute();
				break;
			}
			case (byte) 0x5C: {	// F32 Not Equal
				break;
			}
			case (byte) 0x5D: {	// F32 Less than
				break;
			}
			case (byte) 0x5E: {	// F32 Greater than
				break;
			}
			case (byte) 0x5F: {	// F32 Less than Equal
				break;
			}
			case (byte) 0x60: {	// F32 Greater than Equal
				break;
			}
			case (byte) 0x61: {	// F64 Equal
				break;
			}
			case (byte) 0x62: {	// F64 Not Equal
				break;
			}
			case (byte) 0x63: {	// F64 Less than
				break;
			}
			case (byte) 0x64: {	// F64 Greater than
				break;
			}
			case (byte) 0x65: {	// F64 Less than Equal
				break;
			}
			case (byte) 0x66: {// F64 Greater than Equal
				break;
			}
			case (byte) 0x67: {  // I32 Count Leading Zeros
				// I'm not sure to pass the WasmInstance or the Stack Only?
				I32_clz i32_clz = new I32_clz(this.stack);
				i32_clz.execute();
				break;
			}
			case (byte) 0x68: {  // I32 Count Trailing Zeros
				I32_ctz i32_ctz = new I32_ctz(this.stack);
				i32_ctz.execute();
				break;
			}
			case (byte) 0x69: {  // I32 Population Count,  aka Bit Count
				I32_popcnt i32_popcnt = new I32_popcnt(this.stack);
				i32_popcnt.execute();
				break;
			}
			case (byte) 0x6A: { // I32 add
				I32_add addI32 = new I32_add(this);
				addI32.execute();
				break;
			}
			case (byte) 0x6B: { // I32 subtract
				I32_sub i32_sub = new I32_sub(this);
				i32_sub.execute();
				break;
			}
			case (byte) 0x6C: { // I32 multiply
				I32_mul i32_mul = new I32_mul(this);
				i32_mul.execute();
				break;
			}
			case (byte) 0x6D: { // I32 divide unsigned
				I32_div_s i32_div_s = new I32_div_s(this);
				i32_div_s.execute();
				break;
			}
			case (byte) 0x6E: { // I32 divide unsigned
				I32_div_u i32_div_u = new I32_div_u(this);
				i32_div_u.execute();
				break;
			}
			case (byte) 0x6F: { // I32 remainder signed
				I32_rem_s i32_rem_s = new I32_rem_s(this);
				i32_rem_s.execute();
				break;
			}
			case (byte) 0x70: { // I32 remainder unsigned
				I32_rem_u i32_rem_u = new I32_rem_u(this);
				i32_rem_u.execute();
				break;
			}
			case (byte) 0x71: { // I32 logical and
				I32_and i32_and = new I32_and(this);
				i32_and.execute();
				break;
			}
			case (byte) 0x72: { // I32 logical or
				I32_or i32_or = new I32_or(this);
				i32_or.execute();
				break;
			}
			case (byte) 0x73: { // I32 logical xor
				I32_xor i32_xor = new I32_xor(this);
				i32_xor.execute();
				break;
			}
			case (byte) 0x74: { // I32 bit shift shl
				I32_shl i32_shl = new I32_shl(this);
				i32_shl.execute();
				break;
			}
			case (byte) 0x75: { // I32 bit shift shr_s
				I32_shr_s i32_shr_s = new I32_shr_s(this);
				i32_shr_s.execute();
				break;
			}
			case (byte) 0x76: { // I32 bit shift shr_u
				I32_shr_u i32_shr_u = new I32_shr_u(this);
				i32_shr_u.execute();
				break;
			}
			case (byte) 0x77: { // I32 bit shift rotl
				I32_rotl i32_rotl = new I32_rotl(this);
				i32_rotl.execute();
				break;
			}
			case (byte) 0x78: { // I32 bit shift rotr
				I32_rotr i32_rotr = new I32_rotr(this);
				i32_rotr.execute();
				break;
			}
			case (byte) 0x79: {  // I64 Count Leading Zeros
				I64_clz i64_clz = new I64_clz(this.stack);
				i64_clz.execute();
				break;
			}
			case (byte) 0x7A: {  // I64 Count Trailing Zeros
				I64_ctz i64_ctz = new I64_ctz(this.stack);
				i64_ctz.execute();
				break;
			}
			case (byte) 0x7B: {  // I64 Population Count,  aka Bit Count
				I64_popcnt i64_popcnt = new I64_popcnt(this.stack);
				i64_popcnt.execute();
				break;
			}
			case (byte) 0x7C: { // I64 add
				I64_add i64_add = new I64_add(this);
				i64_add.execute();
				break;
			}
			case (byte) 0x7D: { // I64 subtract
				I64_sub i64_sub = new I64_sub(this);
				i64_sub.execute();
				break;
			}
			case (byte) 0x7E: { // I64 multiply
				I64_mul i64_mul = new I64_mul(this);
				i64_mul.execute();
				break;
			}
			case (byte) 0x7F: { // I64 division signed
				I64_div_s i64_div_s = new I64_div_s(this);
				i64_div_s.execute();
				break;
			}
			case (byte) 0x80: { // I64 division unsigned
				I64_div_u i64_div_u = new I64_div_u(this);
				i64_div_u.execute();
				break;
			}
			case (byte) 0x81: { // I64 remainder signed
				I64_rem_s i64_rem_s = new I64_rem_s(this);
				i64_rem_s.execute();
				break;
			}
			case (byte) 0x82: { // I64 remainder unsigned
				I64_rem_u i64_rem_u = new I64_rem_u(this);
				i64_rem_u.execute();
				break;
			}
			case (byte) 0x83: { // I64 and
				I64_and i64_and = new I64_and(this);
				i64_and.execute();
				break;
			}
			case (byte) 0x84: { // I64 or
				I64_or i64_or = new I64_or(this);
				i64_or.execute();
				break;
			}
			case (byte) 0x85: { // I64 exclusive or
				I64_xor i64_xor = new I64_xor(this);
				i64_xor.execute();
				break;
			}
			case (byte) 0x86: { // I64 shift left
				I64_shl i64_shl = new I64_shl(this);
				i64_shl.execute();
				break;
			}
			case (byte) 0x87: { // I64 shift right signed
				I64_shr_s i64_shr_s = new I64_shr_s(this);
				i64_shr_s.execute();
				break;
			}
			case (byte) 0x88: { // I64 Shift right unsigned
				I64_shr_u i64_shr_u = new I64_shr_u(this);
				i64_shr_u.execute();
				break;
			}
			case (byte) 0x89: { // I64 Rotate Left
				I64_rotl i64_rotl = new I64_rotl(this);
				i64_rotl.execute();
				break;
			}
			case (byte) 0x8A: { // I64 Rotate Right
				I64_rotr i64_rotr = new I64_rotr(this);
				i64_rotr.execute();
				break;
			}
			case (byte) 0xAC: { // I64 Extend I32 Signed
				I64_extend_I32_s i64_extend_i32_s = new I64_extend_I32_s(this);
				i64_extend_i32_s.execute();
				break;
			}
			case (byte) 0xAD: { // I64 Extend I32 Unsigned
				I64_extend_I32_u i64_extend_i32_u = new I64_extend_I32_u(this);
				i64_extend_i32_u.execute();
				break;
			}
			case (byte) 0xC0: { // I32 Extend 8 Signed
				I32_extend8_s i32_extend8_s = new I32_extend8_s(this);
				i32_extend8_s.execute();
				break;
			}
			case (byte) 0xC1: { // I32 Extend 16 Signed
				I32_extend16_s i32_extend16_s = new I32_extend16_s(this);
				i32_extend16_s.execute();
				break;
			}
			case (byte) 0xC2: { // I64 Extend 8 Signed
				I64_extend8_s i64_extend8_s = new I64_extend8_s(this);
				i64_extend8_s.execute();
				break;
			}
			case (byte) 0xC3: { // I64 Extend 16 Signed
				I64_extend16_s i64_extend16_s = new I64_extend16_s(this);
				i64_extend16_s.execute();
				break;
			}
			case (byte) 0xC4: { // I64 Extend 32 Signed
				I64_extend32_s i64_extend32_s = new I64_extend32_s(this);
				i64_extend32_s.execute();
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
