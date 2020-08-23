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

import happynewmoonwithreport.type.JavaType.ByteUnsigned;

/**
 * A number read from the *.wasm file. It is usually create with a collection of bytes.
 */
public interface DataTypeNumberFloat {

	/**
	 * The value converted to a byte type.
	 *
	 * @return value as a byte
	 */
	Byte byteValue();


	/**
	 * The value converted to a Integer type.
	 *
	 * @return value as a Integer.
	 */
	Integer integerValue();

	/**
	 * The value converted to a Long type.
	 *
	 * @return value as a Long.
	 */
	Long longValue();

	/**
	 * Does the <code>value</code> lay between Integer.minValue and Integer.maxValue.  i.e
	 * <code>Integer.minValue &lt;= value &lt;= Integer.maxValue;</code>
	 * <p>
	 * This will <b>always</b> be true for class where the ValueType is Integer, Byte. But may be
	 * false when ValueType is Long.
	 *
	 * @return true if between Integer.minValue and Integer.maxValue.
	 **/
	// Boolean isBoundByInteger();

	/**
	 * The minimum number of bytes this number may be represented by. For UInt this is fixed and is
	 * the same as maxBytes(). For VarUInt it may be less tha maxBytes().
	 *
	 * @return minimum number of bytes.
	 */
	Integer minBytes();

	/**
	 * The maximum number of bytes this number may be represented by. For UInt this is fixed. For
	 * VarUInt if may vary.
	 * <p>
	 * <code> maxBytes = ceiling(maxBits/8); </code>
	 *
	 * @return maximum number of bytes.
	 */
	Integer maxBytes();

	/**
	 * The minimum number of bites this number is represented by. It is constant. Usually in the
	 * class name VarUInt32 sets maxBits to 32.
	 *
	 * @return maximum number of bits
	 */
	Integer maxBits();

	/**
	 * The minimum value that may be held. For Unsigned it is zero.
	 *
	 * @return minimum number of bits
	 */
	Double minValue();

	/**
	 * The maximum value that may be held. For Unsigned it is zero.
	 *
	 * @return maximum value
	 */
	Double maxValue();

	/**
	 * Get an array of the bytes.  Big Endian.
	 *
	 * @return array of bytes.
	 */
	ByteUnsigned[] getBytes();

}
