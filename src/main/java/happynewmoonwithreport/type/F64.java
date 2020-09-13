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
 * A F64 data type.
 * A class that implements an F64 data type.
 *
 * <b>Java Implementation</b>
 * This uses a <code>Float</code> type.  Floats and F64 are the same IEEE 754.
 * <br>
 * See:
 * <br><a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html">
 * Java Documentation on types.
 * </a>
 *
 * <br><a href="https://webassembly.github.io/spec/core/syntax/types.html#value-types">
 * WASM documentation on types.
 * </a>
 */
public class F64 implements DataTypeNumberFloat {
	protected Double value;

	public F64() {
		this.value = 0D;
	}

	public F64(Double value) {
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
		return 8;
	}

	/**
	 * The maximum number of bytes this number may be represented by. For UInt this is fixed. For
	 * VarUInt if may vary.
	 * <br>
	 * <code> maxBytes = ceiling(maxBits/8); </code>
	 *
	 * @return maximum number of bytes.
	 */
	@Override
	public Integer maxBytes() {
		return 8;
	}

	/**
	 * The minimum number of bites this number is represented by. It is constant. Usually in the
	 * class name VarUInt32 sets maxBits to 32.
	 *
	 * @return maximum number of bits
	 */
	@Override
	public Integer maxBits() {
		return 64;
	}

	/**
	 * The minimum value that may be held. For Unsigned it is zero.
	 *
	 * @return minimum number of bits
	 */
	@Override
	public Double minValue() {
		return (double) Double.MIN_VALUE;
	}

	/**
	 * The maximum value that may be held. For Unsigned it is zero.
	 *
	 * @return maximum value
	 */
	@Override
	public Double maxValue() {
		return (double) Double.MAX_VALUE;
	}

	/**
	 * Convert the F64 value to an array of ByteUnsigned.
	 * <br>
	 * <b>See:</b>
	 * <br>
	 * <a href="https://www.h-schmidt.net/FloatConverter/IEEE754.html" target="_top">
	 * IEEE-754 Floating Point Converter.  Works for 32 bit only.
	 * </a>
	 * <br>
	 * <a href="http://weitz.de/ieee/" target="_top">
	 * IEEE-754 Floating Point Calculator.  Works for 32 and 64 bits.
	 * </a>
	 * <br>
	 * The complement is F64(ByteUnsigned []) constructor.
	 * <br>
	 * <b>Java implementation</b>
	 * This uses <code>Float.floatToIntBits(float)</code> to covert to an <code>Integer</code>.
	 *
	 * @return an array of ByteUnsigned
	 */
	public ByteUnsigned[] getBytes() {


		// consider using floatToIntBits(value);
		Long bits = Double.doubleToRawLongBits(value);

		// Integer to ByteUnsigned array
		ByteUnsigned[] byteAll = getByteUnsigned(bits);

		return byteAll;
	}

	private ByteUnsigned[] getByteUnsigned(Long input) {
		ByteUnsigned[] byteAll = new ByteUnsigned[8];
		// Big Endian
		byteAll[0] = new ByteUnsigned((input >>> 56) & 0x0000_00FF);
		byteAll[1] = new ByteUnsigned((input >>> 48) & 0x0000_00FF);
		byteAll[2] = new ByteUnsigned((input >>> 40) & 0x0000_00FF);
		byteAll[3] = new ByteUnsigned((input >>> 32) & 0x0000_00FF);
		byteAll[4] = new ByteUnsigned((input >>> 24) & 0x0000_00FF);
		byteAll[5] = new ByteUnsigned((input >>> 16) & 0x0000_00FF);
		byteAll[6] = new ByteUnsigned((input >>> 8) & 0x0000_00FF);
		byteAll[7] = new ByteUnsigned((input >>> 0) & 0x0000_00FF);
		return byteAll;
	}

	/**
	 * Create an F64 with an array of eight UnsignedBytes.  Bytes are interpreted as unsigned.
	 * <br>
	 * <b>See:</b>
	 * <br>
	 * <a href="https://www.h-schmidt.net/FloatConverter/IEEE754.html" target="_top">
	 * IEEE-754 Floating Point Converter
	 * </a>
	 * <br>
	 *
	 * @param byteAll an array of 8 UnsignedBytes
	 */
	public F64(ByteUnsigned[] byteAll) {
		Long valueLong = 0L;

		// TODO  check for at least 8 bytes to avoid null point exception.  Do the same for F32,
		//  ....

		// As a coding standard the index '[0]' must be in descending order.

		// Big Endian
		valueLong += byteAll[0].longValue() << 56;
		valueLong += byteAll[1].longValue() << 48;
		valueLong += byteAll[2].longValue() << 40;
		valueLong += byteAll[3].longValue() << 32;
		valueLong += byteAll[4].longValue() << 24;
		valueLong += byteAll[5].longValue() << 16;
		valueLong += byteAll[6].longValue() << 8;
		valueLong += byteAll[7].longValue() << 0;

		value = Double.longBitsToDouble(valueLong);
	}

	/**
	 * Create an F64 from the wasm file.
	 *
	 * @param bytesFile The wasm file.
	 * @return an F64
	 * <br>
	 * Floating points are stored in Little Endian.
	 * See:
	 * <a href="https://webassembly.github.io/spec/core/binary/values.html#floating-point"
	 * target="_top">
	 * https://webassembly.github.io/spec/core/binary/values.html#floating-point
	 * </a>
	 * <br>
	 * <a href="https://chortle.ccsu.edu/AssemblyTutorial/Chapter-15/ass15_3.html" target="_top">
	 * Little Endian vs Big Endian
	 * </a>
	 */
	public static F64 convert(BytesFile bytesFile) {
		Long valueLong;
		// Little Endian
		valueLong = (((long) bytesFile.readByte() & 0xFFL) << 0); // Least Significant Byte
		valueLong += (((long) bytesFile.readByte() & 0xFFL) << 8);
		valueLong += (((long) bytesFile.readByte() & 0xFFL) << 16);
		valueLong += (((long) bytesFile.readByte() & 0xFFL) << 24);

		valueLong += (((long) bytesFile.readByte() & 0xFFL) << 32);
		valueLong += (((long) bytesFile.readByte() & 0xFFL) << 40);
		valueLong += (((long) bytesFile.readByte() & 0xFFL) << 48);
		valueLong += (((long) bytesFile.readByte() & 0xFFL) << 56); // Most Significant Byte

		F64 result = new F64(Double.longBitsToDouble(valueLong));
		return result;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("F64{");
		sb.append("value=").append(value);
		if (value != null) {
			Long bits = Double.doubleToRawLongBits(value);
			ByteUnsigned[] bytesAll = getByteUnsigned(bits);
			sb.append(" hex =  0x");
			sb.append(bytesAll[0]).append(' ');
			sb.append(bytesAll[1]).append(' ');
			sb.append(bytesAll[2]).append(' ');
			sb.append(bytesAll[3]).append(' ');
			sb.append(bytesAll[4]).append(' ');
			sb.append(bytesAll[5]).append(' ');
			sb.append(bytesAll[6]).append(' ');
			sb.append(bytesAll[7]).append(' ');
		}
		sb.append('}');
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		F64 f64 = (F64) o;

		return value.equals(f64.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}
}
