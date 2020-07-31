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

package happynewmoonwithreport.type;

/**
 * A F32 data type.
 * A class that implements an F32 data type.
 *
 * <b>Java Implementation</b>
 * This uses a <code>Float</code> type.  Floats and F32 are the same IEEE 754.
 * <p>
 * See:
 * <p><a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html">
 * Java Documentation on types.
 * </a>
 *
 * <p><a href="https://webassembly.github.io/spec/core/syntax/types.html#value-types">
 * WASM documentation on types.
 * </a>
 */
public class F32 implements DataTypeNumberFloat {
	protected Float value;

	public F32(Float value) {
		this.value = value;
	}

	/**
	 * The value converted to a byte type.
	 *
	 * @return value as a byte
	 */
	@Override
	public Byte byteValue() {
		return value.byteValue();
	}

	/**
	 * The value converted to a Integer type.
	 *
	 * @return value as a Integer.
	 */
	@Override
	public Integer integerValue() {
		return value.intValue();
	}

	/**
	 * The value converted to a Long type.
	 *
	 * @return value as a Long.
	 */
	@Override
	public Long longValue() {
		return value.longValue();
	}

	/**
	 * The minimum number of bytes this number may be represented by. For UInt this is fixed and is
	 * the same as maxBytes(). For VarUInt it may be less tha maxBytes().
	 *
	 * @return minimum number of bytes.
	 */
	@Override
	public Integer minBytes() {
		return 4;
	}

	/**
	 * The maximum number of bytes this number may be represented by. For UInt this is fixed. For
	 * VarUInt if may vary.
	 * <p>
	 * <code> maxBytes = ceiling(maxBits/8); </code>
	 *
	 * @return maximum number of bytes.
	 */
	@Override
	public Integer maxBytes() {
		return 4;
	}

	/**
	 * The minimum number of bites this number is represented by. It is constant. Usually in the
	 * class name VarUInt32 sets maxBits to 32.
	 *
	 * @return maximum number of bits
	 */
	@Override
	public Integer maxBits() {
		return 32;
	}

	/**
	 * The minimum value that may be held. For Unsigned it is zero.
	 *
	 * @return minimum number of bits
	 */
	@Override
	public Double minValue() {
		return (double) Float.MIN_VALUE;
	}

	/**
	 * The maximum value that may be held. For Unsigned it is zero.
	 *
	 * @return maximum value
	 */
	@Override
	public Double maxValue() {
		return (double) Float.MIN_VALUE;
	}
}
