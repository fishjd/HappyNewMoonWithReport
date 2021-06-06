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

import happynewmoonwithreport.type.*;
import java.util.ArrayList;

/**
 * The description of a function signature
 * <br>
 * Source: <a href = "http://webassembly.org/docs/binary-encoding/#func_type" target="_top">
 * http://webassembly.org/docs/binary-encoding/#func_type
 * </a>
 * <br>
 * <br>
 * <a href = "https://webassembly.github.io/spec/core/binary/types.html#function-types" target="_top">
 * https://webassembly.github.io/spec/core/binary/types.html#function-types</a>
 * <br>
 */
public class FunctionType implements Validation {

	/**
	 * Always 0x60  ValueType("func")
	 */
	private ValueType form;

	/**
	 * The number of parameters
	 */
	private UInt32 paramCount;

	/**
	 * Array of the types of parameters
	 */
	private WasmVector<ValueType> paramTypeAll;

	/**
	 * The number of return values Currently only zero or one value
	 */
	private UInt8 returnCount;

	/**
	 * Array of the types of of the return values.
	 */
	private WasmVector<ValueType> returnTypeAll;

	/**
	 * Create a FunctionType from a BytesFile.
	 *
	 * @param payload the input BytesFile.
	 */
	public FunctionType(BytesFile payload) {
		//* form
		form = new ValueType(payload);
		assert (form.getValue().equals("func"));

		//* Parameter Count
		paramCount = new VarUInt32(payload);

		//* Parameters Types
		paramTypeAll = new WasmVector<>(paramCount.integerValue());
		for (Integer count = 0; count < paramCount.integerValue(); count++) {
			ValueType paramType = new ValueType(payload);
			paramTypeAll.add(count, paramType);
		}

		//* Return Count
		returnCount = new VarUInt1(payload);
		// current version only allows 0 or 1
		assert (returnCount.integerValue() <= 1);

		//* Return Types.
		returnTypeAll = new WasmVector<>(returnCount.integerValue());
		for (Integer count = 0; count < returnCount.integerValue(); count++) {
			ValueType returnType = new ValueType(payload);
			returnTypeAll.add(count, returnType);
		}

	}


	public FunctionType(UInt32 paramCount, WasmVector<ValueType> paramTypeAll, UInt8 returnCount,
						WasmVector<ValueType> returnTypeAll) {
		this(new ValueType("func"), paramCount, paramTypeAll, returnCount, returnTypeAll);
	}


	public FunctionType(ValueType form, UInt32 paramCount, WasmVector<ValueType> paramTypeAll,
						UInt8 returnCount, WasmVector<ValueType> returnTypeAll) {
		super();
		this.form = form;
		this.paramCount = paramCount;
		this.paramTypeAll = paramTypeAll;
		this.returnCount = returnCount;
		this.returnTypeAll = returnTypeAll;
	}

	/**
	 * Function types may not specify more than one result.
	 * <br>
	 * source:
	 * <a href="https://webassembly.github.io/spec/core/valid/types.html#function-types" target="_top">
	 * https://webassembly.github.io/spec/core/valid/types.html#function-types</a>
	 * <br>
	 * Note:
	 * This restriction may be removed in future versions of WebAssembly.
	 *
	 * @return true if valid.
	 */
	@Override
	public Boolean valid() {
		Boolean isValid;

		isValid = returnCount.integerValue() <= 1;
		isValid &= 0 <= returnCount.integerValue();

		return isValid;
	}


	public ValueType getForm() {
		return form;
	}


	public UInt32 getParamCount() {
		return paramCount;
	}


	public ArrayList<ValueType> getParamTypeAll() {
		return paramTypeAll;
	}


	public UInt8 getReturnCount() {
		return returnCount;
	}

	public ArrayList<ValueType> getReturnTypeAll() {
		return returnTypeAll;
	}

	@Override
	public String toString() {
		return "FunctionType{" + "form=" + form + ", paramCount=" + paramCount + ", paramTypeAll"
			   + "=" + paramTypeAll + ", returnCount=" + returnCount + ", returnTypeAll="
			   + returnTypeAll + '}';
	}
}
