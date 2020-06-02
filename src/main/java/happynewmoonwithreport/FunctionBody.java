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

import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.WasmVector;

/**
 * FunctionBody Bodies
 * <p>
 * FunctionBody bodies consist of a sequence of local variable declarations followed by bytecode
 * instructions.
 * Instructions are encoded as an opcode followed by zero or more immediates as defined by the
 * tables below. Each
 * function body must end with the end opcode.
 * </p>
 * <p>
 * Source:  <a href="http://webassembly.org/docs/binary-encoding/#function-bodies" target="_top">
 * http://webassembly.org/docs/binary-encoding/#function-bodies
 * </a>
 */

public class FunctionBody {

	private UInt32 bodySize;
	/**
	 * Number of local entries.
	 * <p>
	 * Number of objects in localEntryAll.
	 */
	private UInt32 localCount;
	/**
	 * Each local entry declares a number of local variables of a given type. It is legal to have
	 * several entries with
	 * the same type.
	 * <p>
	 * This is the types of the local variables not the values of the local variables which is in
	 * WasmFunction.
	 */
	private WasmVector<ValueType> localEntryAll;

	/**
	 * This is the actual code of the function.
	 */
	private byte[] code;

	/**
	 * One byte tha is always <code>0x0b</code>, indicating the end of the body.
	 * <p>
	 * Pretty much useless.  Used mainly for reading the wasm file.
	 */
	private byte end;

	public FunctionBody() {

	}

	public FunctionBody(BytesFile payload) {
		//* Body Size
		bodySize = new VarUInt32(payload);

		final Integer start = payload.getIndex();

		//* Count
		localCount = new VarUInt32(payload);


		//* LocalAll
		localEntryAll = new WasmVector<>(localCount.integerValue());
		for (Integer index = 0; index < localCount.integerValue(); ) {
			LocalEntry localEntry = new LocalEntry(payload);
			for (Integer localIndex = 0; localIndex < localEntry.getCount().integerValue();
				 localIndex++) {
				localEntryAll.add(index, localEntry.getValueType());
				index++;
			}
		}

		final Integer after = payload.getIndex();

		final Integer consumedByLocals = after - start;

		final Integer codeLength =
			bodySize.integerValue() - consumedByLocals - 1;  // minus 1 for end byte.


		//* Code
		code = new byte[codeLength];
		for (Integer i = 0; i < codeLength; i++) {
			code[i] = payload.readByte();
		}

		//* Byte
		end = payload.readByte();
		assert (end == (byte) 0x0B);
	}

	public UInt32 getBodySize() {
		return bodySize;
	}

	public UInt32 getLocalCount() {
		return localCount;
	}

	public WasmVector<ValueType> getLocalEntryAll() {
		return localEntryAll;
	}

	public void setLocalEntryAll(WasmVector<ValueType> localEntryAll) {
		this.localEntryAll = localEntryAll;
	}

	public byte[] getCode() {
		return code;
	}

	public byte getEnd() {
		return end;
	}

	@Override
	public String toString() {
		return "FunctionBody{" + "bodySize=" + bodySize + ", localCount=" + localCount + '}';
	}
}
