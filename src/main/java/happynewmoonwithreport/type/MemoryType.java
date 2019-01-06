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
package happynewmoonwithreport.type;


import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.Validation;
import happynewmoonwithreport.type.JavaType.ByteUnsigned;

/**
 * Memory Type,
 * <br>
 * <br>
 * Source:  <a href="http://webassembly.org/docs/binary-encoding/#memory_type" target="_top">
 * http://webassembly.org/docs/binary-encoding/#memory_type
 * </a>
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/syntax/types.html#memory-types" target="_top">
 * https://webassembly.github.io/spec/syntax/types.html#memory-types
 * </a>
 * Source:  <a href="https://webassembly.github.io/spec/core/exec/runtime.html#memory-instances" target="_top">
 * https://webassembly.github.io/spec/core/exec/runtime.html#memory-instances
 * </a>
 */
public class MemoryType implements Validation {

	private LimitType limit;
	/**
	 * a vector of bytes;
	 *
	 * @TODO change to WasmVector
	 */
	private ByteUnsigned[] byteAll;

	public static final Integer _64Ki = 65536;
	public static final Integer pageSize = _64Ki;

	public MemoryType(UInt8 hasMaximum, UInt32 minimum, UInt32 maximum) {
		limit = new LimitType(hasMaximum, minimum, maximum);
		byteAll = new ByteUnsigned[pageSize.intValue() * limit.minimum().integerValue()];
	}

	public MemoryType(UInt8 hasMaximum, UInt32 minimum) {
		limit = new LimitType(hasMaximum, minimum);
		byteAll = new ByteUnsigned[pageSize.intValue() * limit.minimum().integerValue()];
	}

	public MemoryType(U32 hasMaximum, U32 minimum) {
		limit = new LimitType(hasMaximum, minimum);
		byteAll = new ByteUnsigned[pageSize.intValue() * limit.minimum().integerValue()];
	}

	public MemoryType(BytesFile payload) {
		limit = new LimitType(payload);
		byteAll = new ByteUnsigned[pageSize.intValue() * limit.minimum().integerValue()];
	}

	/**
	 * The limits must be valid.
	 * <p>
	 * source:  <a href="https://webassembly.github.io/spec/valid/types.html#memory-types"
	 * target="_top"> https://webassembly.github.io/spec/valid/types.html#memory-types
	 * </a>
	 *
	 * @return true if valid.
	 */
	@Override
	public Boolean valid() {
		return limit.valid();
	}


	/**
	 * minimum  of the memory in Page Size
	 *
	 * @return min
	 */
	public U32 minimum() {
		return limit.minimum();
	}

	/**
	 * maximum of the memory in Page Size
	 * <p>
	 * Usage :
	 * <code>
	 * if (hasMaximum()) { max = maximum(); }
	 * </code>
	 * <p>
	 * Throws RuntimeException is maximum is not set.
	 *
	 * @return maximum
	 */
	public U32 maximum() {
		return limit.maximum();
	}

	/**
	 * Does Memory contain a maximum value 1 = has max 0 = does not have max.
	 *
	 * @return hasMaximum
	 */
	public UInt8 hasMaximum() {
		return limit.hasMaximum();
	}

	public Boolean hasMaximumBoolean() {
		return hasMaximum().value != 0;
	}

	public ByteUnsigned get(Integer address) {
		return byteAll[address];
	}

	public void set(Integer address, ByteUnsigned value) {
		byteAll[address] = value;
	}


	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("MemoryType{");
		sb.append(" hasMaximum=").append(hasMaximum());
		sb.append(", minimum=").append(minimum());
		if (limit.hasMaximum().booleanValue()) {
			sb.append(", maximum=").append(maximum());
		}
		sb.append('}');
		return sb.toString();
	}
}
