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

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.type.JavaType.ByteUnsigned;


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

	public F32() {
		this.value = 0F;
	}

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
		return (double) Float.MAX_VALUE;
	}

	// this words but requires import of nio.*
	//	public byte[] toByteArray() {
	//		byte[] bytes = new byte[4];
	//		ByteBuffer.wrap(bytes).putFloat(value);
	//		return bytes;
	//	}

	/**
	 * Convert the F32 value to an array of ByteUnsigned.
	 * <p>
	 * <b>See:</b>
	 * <p>
	 * <a href="https://www.h-schmidt.net/FloatConverter/IEEE754.html" target="_top">
	 * IEEE-754 Floating Point Converter
	 * </a>
	 * <br>
	 * <a href="http://weitz.de/ieee/" target="_top">
	 * IEEE-754 Floating Point Calculator.  Works for 32 & 64 bits.
	 * </a>
	 * <p>
	 * The complement is F32(ByteUnsigned []) constructor.
	 * <p>
	 * <b>Java implementation</b>
	 * This uses <code>Float.floatToIntBits(float)</code> to covert to an <code>Integer</code>.
	 *
	 * @return an array of ByteUnsigned
	 */
	public ByteUnsigned[] getBytes() {
		// consider using floatToIntBits(value);
		Integer bits = Float.floatToRawIntBits(value);

		// Integer to ByteUnsigned array
		ByteUnsigned[] byteAll = getByteUnsigned(bits);

		return byteAll;
	}

	private ByteUnsigned[] getByteUnsigned(Integer bits) {
		ByteUnsigned[] byteAll = new ByteUnsigned[4];
		// Big Endian
		byteAll[0] = new ByteUnsigned((bits >>> 24) & 0x0000_00FF);  // Most Significant Byte
		byteAll[1] = new ByteUnsigned((bits >>> 16) & 0x0000_00FF);
		byteAll[2] = new ByteUnsigned((bits >>> 8) & 0x0000_00FF);
		byteAll[3] = new ByteUnsigned((bits >>> 0) & 0x0000_00FF);
		return byteAll;
	}

	/**
	 * Create an F32 with an array of four UnsignedBytes.  Bytes are interpreted as unsigned.
	 * <p>
	 * <b>See:</b>
	 * <p>
	 * <a href="https://www.h-schmidt.net/FloatConverter/IEEE754.html" target="_top">
	 * IEEE-754 Floating Point Converter
	 * </a>
	 * <p>
	 *
	 * @param byteAll an array of 4 UnsignedBytes
	 */
	public F32(ByteUnsigned[] byteAll) {
		Integer valueInteger;
		// Big Endian
		valueInteger = byteAll[0].intValue() << 24;    // Most  Significant Byte
		valueInteger += byteAll[1].intValue() << 16;
		valueInteger += byteAll[2].intValue() << 8;
		valueInteger += byteAll[3].intValue() << 0;

		value = Float.intBitsToFloat(valueInteger);
	}

	/**
	 * Create an F32 from the wasm file.
	 *
	 * @param bytesFile The wasm file.
	 * @return an F32
	 * <p>
	 * Floating points are stored in Little Endian.
	 * See:
	 * <a href="https://webassembly.github.io/spec/core/binary/values.html#floating-point"
	 * target="_top">
	 * https://webassembly.github.io/spec/core/binary/values.html#floating-point
	 * </a>
	 * <p>
	 * <a href="https://chortle.ccsu.edu/AssemblyTutorial/Chapter-15/ass15_3.html" target="_top">
	 * Little Endian vs Big Endian
	 * </a>
	 */
	public static F32 convert(BytesFile bytesFile) {
		Integer valueInteger;
		// Little Endian
		valueInteger = (((int) bytesFile.readByte() & 0xFF) << 0); // Least Significant Byte
		valueInteger += (((int) bytesFile.readByte() & 0xFF) << 8);
		valueInteger += (((int) bytesFile.readByte() & 0xFF) << 16);
		valueInteger += (((int) bytesFile.readByte() & 0xFF) << 24); // Most Significant Byte

		F32 result = new F32(Float.intBitsToFloat(valueInteger));
		return result;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("F32{");
		sb.append("value=").append(value);
		if (value != null) {
			Integer bits = Float.floatToIntBits(value);
			ByteUnsigned[] bytesAll = getByteUnsigned(bits);
			sb.append(" hex =  0x");
			sb.append(bytesAll[0]).append(' ');
			sb.append(bytesAll[1]).append(' ');
			sb.append(bytesAll[2]).append(' ');
			sb.append(bytesAll[3]).append(' ');
		}
		sb.append('}');
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		F32 f32 = (F32) o;

		return value.equals(f32.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}
}
