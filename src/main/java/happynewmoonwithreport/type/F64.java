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
 * This uses a <code>Double</code> type.  Doubles and F64 are the same IEEE 754.
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
public class F64 implements DataTypeNumberFloat {
	protected final Double value;

	public static final F64 ZERO_POSITIVE = new F64(0.0D);
	// Java stores a negative zero correctly,  Groovy/Spock has issues.
	public static final F64 ZERO_NEGATIVE = new F64(-0.0D);
	public static final F64 POSITIVE_INFINITY = new F64(Double.POSITIVE_INFINITY);
	public static final F64 NEGATIVE_INFINITY = new F64(Double.NEGATIVE_INFINITY);
	public static final F64 NAN = new F64(Double.NaN);  // Not a number

	// Java does not allow a Negative NAN.  It converts it to NAN.
	// public static final F64 NAN_NEGATIVE = F64.valueOf(Double.intBitsToDouble(0xffc00000));  //
	// negative Not a number
	public F64() {
		this.value = 0D;
	}

	public F64(Double value) {
		this.value = value;
	}

	/**
	 * Returns a {@code F64} object holding the
	 * {@code F64} value represented by the argument string
	 * {@code s}.
	 *
	 * <p>If {@code s} is {@code null}, then a
	 * {@code NullPointerException} is thrown.
	 *
	 * @param s the string to be parsed.
	 * @return a {@code F64} object holding the value
	 * represented by the {@code String} argument.
	 * @throws NumberFormatException if the string does not contain a parse-able number.
	 * @see Double#valueOf(String)
	 */
	public static F64 valueOf(String s) throws NumberFormatException {
		Double val;

		switch (s) {
			case ("-inf"):
				val = Double.NEGATIVE_INFINITY;
				break;
			case ("inf"):
				val = Double.POSITIVE_INFINITY;
				break;
			case ("nan"):
			case ("nan:0x4000000000000"):
			case ("-nan"):
			case ("-nan:0x4000000000000"):
			case ("nan:canonical"):
			case ("nan:arithmetic"):
				val = Double.NaN;
				break;
			default:
				val = Double.valueOf(s);
		}

		F64 result = new F64(val);
		return result;
	}

	/**
	 * Create a F64 instance given a {@code Double}.
	 * <p>
	 * Does not handle -0F. To be more precise the object Float does not handle -0F.
	 * Use {@code F32.NEGATIVE_ZERO}  or {@code F32.valueOf(String)} instead.
	 *
	 * @param input
	 * @return
	 */
	public static F64 valueOf(Double input) {
		F64 result = new F64(input);
		return result;
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
	 * <p>
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
	 * <p>
	 * <b>See:</b>
	 * <p>
	 * <a href="https://www.h-schmidt.net/FloatConverter/IEEE754.html" target="_top">
	 * IEEE-754 Floating Point Converter.  Works for 32 bit only.
	 * </a>
	 * <br>
	 * <a href="http://weitz.de/ieee/" target="_top">
	 * IEEE-754 Doubleing Point Calculator.  Works for 32 & 64 bits.
	 * </a>
	 * <p>
	 * The complement is F64(ByteUnsigned []) constructor.
	 * <p>
	 * <b>Java implementation</b>
	 * This uses <code>Double.doubleToIntBits(double)</code> to covert to an <code>Integer</code>.
	 *
	 * @return an array of ByteUnsigned
	 */
	public ByteUnsigned[] getBytes() {
		Long bits = Double.doubleToRawLongBits(value);

		// Integer to ByteUnsigned array
		ByteUnsigned[] byteAll = getByteUnsigned(bits);

		return byteAll;
	}

	private ByteUnsigned[] getByteUnsigned(Long input) {
		ByteUnsigned[] byteAll = new ByteUnsigned[8];
		// Big Endian
		byteAll[0] = new ByteUnsigned((input >>> 56) & 0x0000_00FF);    // Most Significant Byte
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
	 * <p>
	 * <b>See:</b>
	 * <p>
	 * <a href="https://www.h-schmidt.net/FloatConverter/IEEE754.html" target="_top">
	 * IEEE-754 Floating Point Converter
	 * </a>
	 * <p>
	 *
	 * @param byteAll an array of 8 UnsignedBytes
	 */
	public F64(ByteUnsigned[] byteAll) {
		Long valueLong = 0L;

		// TODO  check for at least 8 bytes to avoid null point exception.  Do the same for F32,
		//  ....

		// As a coding standard the index '[0]' must be in descending order.

		// Big Endian
		valueLong += byteAll[0].longValue() << 56;    // Most Significant Byte
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

	/**
	 * Calculate the Absolute value according to the Wasm Specification.
	 * <pre>F64 -> F64</pre>
	 *
	 * <h2>Source:</h2>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#xref-exec-numerics-op-feq-mathrm-feq-n-z-1-z-2" target="_top">
	 * Float Abs
	 * </a>
	 * <p>
	 * <ul>
	 * <li>If z is a NaN, then return z with positive sign.
	 * </li><li>
	 * Else if z is an infinity, then return positive infinity.
	 * </li><li>
	 * Else if z is a zero, then return positive zero.
	 * </li><li>
	 * Else if z is a positive value, then z.
	 * </li><li>
	 * Else return z negated.
	 * </ul>
	 *
	 * @return the absolute value
	 */
	public F64 absWasm() {
		Double z = value;
		// If z is a NaN, then return z with positive sign.
		// Java Implementation Note:  Java does not implement negative non a number -NAN.
		if (z.isNaN()) {
			return F64.NAN;
		}
		// if z is an infinity, then return positive infinity.
		if (z.isInfinite()) {
			return F64.POSITIVE_INFINITY;
		}
		// if z is a zero, then return positive zero.
		if (z == 0F || z == -0F) {
			return F64.ZERO_POSITIVE;
		}
		// Else if z is a positive value, then z.
		if (0F < z) {
			return this;
		} else {
			return new F64(-value);
		}
	}

	public static F64 negWasm(F64 z1) {
		return z1.negWasm();
	}

	/**
	 * Calculate the Negative value according to the Wasm Specification.
	 * <pre>F64 -> F64</pre>
	 *
	 * <h2>Source:</h2>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-fneg" target="_top">
	 * Float Neg
	 * </a>
	 * <p>
	 * <ul>
	 * <li>If z is a NaN, then return z with negated sign.
	 * </li><li>
	 * Else if z is an infinity, then return that infinity negated.
	 * </li><li>
	 * Else if z is a zero, then return that zero negated.
	 * </li><li>
	 * Else return z negated.
	 * </li>
	 * </ul>
	 *
	 * @return the negative value
	 */
	public F64 negWasm() {
		Double z = value;
		// If z is a NaN, then return z with negated sign.
		// Java Implementation Note:  Java does not implement negative non a number -NAN.
		if (z.isNaN()) {
			return F64.NAN;
		}
		// if z is an infinity, then return that infinity negated.
		if (z.equals(Float.POSITIVE_INFINITY)) {
			return F64.NEGATIVE_INFINITY;
		}
		if (z.equals(Float.NEGATIVE_INFINITY)) {
			return F64.POSITIVE_INFINITY;
		}
		// if z is a zero, then return that zero negated.
		if (Double.doubleToLongBits(z) == Double.doubleToLongBits(F64.ZERO_NEGATIVE.value)) {
			return F64.ZERO_POSITIVE;
		}
		if (Double.doubleToLongBits(z) == Double.doubleToLongBits(F64.ZERO_POSITIVE.value)) {
			return F64.ZERO_NEGATIVE;
		}

		// Else return z negate
		return new F64(-value);
	}


	/**
	 * Equals according to the Wasm specification.
	 * <pre>F64 F64 -> I32</pre>
	 * <p>
	 * Source: <br>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#xref-exec-numerics-op-feq-mathrm-feq-n-z-1-z-2" target="_top">
	 * Numerics equals
	 * </a><br>
	 * If either z1 or z2 is a NaN, then return 0<br>
	 * Else if both z1 and z2 are zeroes, then return 1<br>
	 * Else if both z1 and z2 are the same value, then return 1<br>
	 * Else return 0.<br>
	 *
	 * @param other
	 * @return 1 if equal otherwise 0 <code>z1 == z2 </code>
	 */
	public I32 equalsWasm(F64 other) {
		return equalsWasm(this, other);
	}

	/**
	 * Equals according to the Wasm specification.
	 * <pre>F64 F64 -> I32</pre>
	 * <p>
	 * Source: <br>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#xref-exec-numerics-op-feq-mathrm-feq-n-z-1-z-2" target="_top">
	 * Numerics equals
	 * </a><br>
	 * If either z1 or z2 is a NaN, then return 0<br>
	 * Else if both z1 and z2 are zeroes, then return 1<br>
	 * Else if both z1 and z2 are the same value, then return 1<br>
	 * Else return 0.<br>
	 *
	 * @param z1 The left side of the equals
	 * @param z2 The right side of the equals
	 * @return 1 if equal otherwise 0.  <code>z1 == z2 </code>
	 */
	public static I32 equalsWasm(F64 z1, F64 z2) {
		Integer result = 0;

		// If either z1 or z2 is a NaN, then return 0<br>
		if (z1.value.isNaN() || z1.value.isNaN()) {
			result = 0;
		} else
			// Else if both z1 and z2 are zeroes, then return 1
			// Java Implementation: Check for both ZERO_POSITIVE plus ZERO_NEGATIVE.
			// I think the specification was trying to say check Positive and Negative, but it is
			// not explicit.
			if ((z1.equals(ZERO_POSITIVE) || z1.equals(ZERO_NEGATIVE))        //
				&&                                                            //
				(z2.equals(ZERO_POSITIVE) || z2.equals(ZERO_NEGATIVE))    //
			) {
				result = 1;
			} else {
				// Else if both z1 and z2 are the same value, then return 1<br>
				if (z1.value.equals(z2.value)) {
					result = 1;
				}
			}
		return new I32(result);
	}

	/**
	 * greater Than or equal to  according to the Wasm specification.
	 * <pre>F64 F64 -> I32</pre>
	 * <p>
	 * Source:
	 * <a  href="https://webassembly.github.io/spec/core/exec/numerics.html#xref-exec-numerics-op-fge-mathrm-fge-n-z-1-z-2" target="_top">
	 * Numerics greater Than or equal  Wasm Specification.
	 * </a>
	 * <p>
	 *
	 * @param other the value to compare to
	 * @return 1 if greater than or equal to otherwise 0
	 * @see F64#greaterThanEqualWasm(F64, F64)
	 */
	public I32 greaterThaEqualnWasm(F64 other) {
		return greaterThanEqualWasm(this, other);
	}

	/**
	 * Greater Than or Equal to according to the Wasm specification.
	 * <pre>F64 F64 -> I32</pre>
	 * <p>
	 * Source:
	 * <a  href="https://webassembly.github.io/spec/core/exec/numerics.html#xref-exec-numerics-op-fge-mathrm-fge-n-z-1-z-2" target="_top">
	 * Numerics greater Than or equal Wasm Specification.
	 * </a>
	 * <br>
	 * <pre>
	 *  1 If either z1 or z2 is a NaN, then return 0
	 *  2 Else if z1 and z2 are the same value, then return 1
	 *  3 Else if z1 is positive infinity, then return 1
	 *  4 Else if z1 is negative infinity, then return 0
	 *  5 Else if z2 is positive infinity, then return 0
	 *  6 Else if z2 is negative infinity, then return 1
	 *  7 Else if both z1 and z2 are zeroes, then return 1
	 *  8 Else if z1 is larger than z2, then return 1
	 *  9 Else return 0
	 * </pre>
	 *
	 * @param z1 the left number.
	 * @param z2 the right number.
	 * @return 1 if z1 greater than z2 otherwise 0.   z1 > z2
	 */
	public static I32 greaterThanEqualWasm(F64 z1, F64 z2) {
		Integer result = 0;

		// 1 If either z1 or z2 is a NaN, then return 0<br>
		if (z1.value.isNaN() || z2.value.isNaN()) {
			return I32.zero;
		}
		// 2 Else if z1 and z2 are the same value, then return 0
		if (z1.equals(z2)) {
			return I32.one;
		}
		// 3 Else if z1 is positive infinity, then return 1
		if (z1.equals(F64.POSITIVE_INFINITY)) {
			return I32.one;
		}
		// 4 Else if z1 is negative infinity, then return 0
		if (z1.equals(F64.NEGATIVE_INFINITY)) {
			return I32.zero;
		}
		// 5 Else if z2 is positive infinity, then return 0
		if (z2.equals(POSITIVE_INFINITY)) {
			return I32.zero;
		}

		// 6 Else if z2 is negative infinity, then return 1
		if (z2.equals(NEGATIVE_INFINITY)) {
			return I32.one;
		}
		// 7 Else if both z1 and z2 are zeroes, then return 0
		// Java Implementation: Check for both ZERO_POSITIVE plus ZERO_NEGATIVE.
		// I think the specification was trying to say check Positive and Negative, but it is
		// not explicit.
		if ((z1.equals(ZERO_POSITIVE) || z1.equals(ZERO_NEGATIVE))    //
			&&                                                        //
			(z2.equals(ZERO_POSITIVE) || z2.equals(ZERO_NEGATIVE))    //
		) {
			return I32.one;
		}
		// 8 Else if z1 is larger than z2, then return 1
		if (z1.value > z2.value) {
			return I32.one;
		}
		// 9 Else return 0
		return I32.zero;
	}

	/**
	 * greaterThan according to the Wasm specification.
	 * <pre>F64 F64 -> I32</pre>
	 * <pre>
	 * Source: <br>
	 * </pre>
	 * <p>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#xref-exec-numerics-op-feq-mathrm-feq-n-z-1-z-2" target="_top">
	 * Numerics Equal
	 * </a>
	 * <p>
	 *
	 * @param other
	 * @return 1 if greater than otherwise 0
	 */
	public I32 greaterThanWasm(F64 other) {
		return greaterThanWasm(this, other);
	}

	/**
	 * Greater Than  according to the Wasm specification.
	 * <pre>F64 F64 -> I32</pre>
	 * <p>
	 * Source:
	 * <a  href="https://webassembly.github.io/spec/core/exec/numerics
	 * .html#xref-exec-numerics-op-fgt-mathrm-fgt-n-z-1-z-2" target="_top">
	 * Numerics greater Than Wasm Specification.
	 * </a>
	 * <br>
	 * <pre>
	 *  1 If either z1 or z2 is a NaN, then return 0
	 *  2 Else if z1 and z2 are the same value, then return 0
	 *  3 Else if z1 is positive infinity, then return 1
	 *  4 Else if z1 is negative infinity, then return 0
	 *  5 Else if z2 is positive infinity, then return 0
	 *  6 Else if z2 is negative infinity, then return 1
	 *  7 Else if both z1 and z2 are zeroes, then return 0
	 *  8 Else if z1 is larger than z2, then return 1
	 *  9 Else return 0
	 * </pre>
	 *
	 * @param z1 the left number.
	 * @param z2 the right number.
	 * @return 1 if z1 greater than z2 otherwise 0.   z1 > z2
	 */
	public static I32 greaterThanWasm(F64 z1, F64 z2) {
		Integer result = 0;

		// 1 If either z1 or z2 is a NaN, then return 0<br>
		if (z1.value.isNaN() || z2.value.isNaN()) {
			return I32.zero;
		}
		// 2 Else if z1 and z2 are the same value, then return 0
		if (z1.equals(z2)) {
			return I32.zero;
		}
		// 3 Else if z1 is positive infinity, then return 1
		if (z1.equals(F64.POSITIVE_INFINITY)) {
			return I32.one;
		}
		// 4 Else if z1 is negative infinity, then return 0
		if (z1.equals(F64.NEGATIVE_INFINITY)) {
			return I32.zero;
		}
		// 5 Else if z2 is positive infinity, then return 0
		if (z2.equals(POSITIVE_INFINITY)) {
			return I32.zero;
		}

		// 6 Else if z2 is negative infinity, then return 1
		if (z2.equals(NEGATIVE_INFINITY)) {
			return I32.one;
		}
		// 7 Else if both z1 and z2 are zeroes, then return 0
		// Java Implementation: Check for both ZERO_POSITIVE plus ZERO_NEGATIVE.
		// I think the specification was trying to say check Positive and Negative, but it is
		// not explicit.
		if ((z1.equals(ZERO_POSITIVE) || z1.equals(ZERO_NEGATIVE))    //
			&&                                                        //
			(z2.equals(ZERO_POSITIVE) || z2.equals(ZERO_NEGATIVE))    //
		) {
			return I32.zero;
		}
		// 8 Else if z1 is larger than z2, then return 1
		if (z1.value > z2.value) {
			return I32.one;
		}
		// 9 Else return 0
		return I32.zero;
	}

	/**
	 * lessThan or Equal according to the Wasm specification.
	 * <pre>F64 F64 -> I32</pre>
	 * <p>
	 * Source:
	 * <a  href="https://webassembly.github.io/spec/core/exec/numerics.html#xref-exec-numerics-op-fle-mathrm-fle-n-z-1-z-2" target="_top">
	 * Numerics Less Than or Equal Wasm Specification.
	 * </a>
	 * <p>
	 *
	 * @param other the value to compare to.
	 * @return 1 if less or equal than otherwise 0
	 * @see F64#lessThanEqualWasm(F64, F64)
	 */
	public I32 lessThanEqualWasm(F64 other) {
		return lessThanEqualWasm(this, other);
	}

	/**
	 * lessThan or Equal according to the Wasm specification.
	 * <pre>F64 F64 -> I32</pre>
	 * <p>
	 * Source:
	 * <a  href="https://webassembly.github.io/spec/core/exec/numerics.html#xref-exec-numerics-op-fle-mathrm-fle-n-z-1-z-2" target="_top">
	 * Numerics Less Than or Equal Wasm Specification.
	 * </a>
	 * <br>
	 * <pre>
	 * 1 If either z1 or z2 is a NaN, then return 0
	 * 2 Else if z1 and z2 are the same value, then return 1
	 * 3 Else if z1 is positive infinity, then return 0
	 * 4 Else if z1 is negative infinity, then return 1
	 * 5 Else if z2 is positive infinity, then return 1
	 * 6 Else if z2 is negative infinity, then return 0
	 * 7 Else if both z1 and z2 are zeroes, then return 1
	 * 8 Else if z1 is smaller than or equal  z2, then return 1
	 * 9 Else return 0
	 * </pre>
	 *
	 * @param z1 the left number.
	 * @param z2 the right number.
	 * @return 1 if z1 less than or equal z2 otherwise 0.   z1 <= z2
	 */
	public static I32 lessThanEqualWasm(F64 z1, F64 z2) {
		Integer result = 0;

		// 1 If either z1 or z2 is a NaN, then return 0<br>
		if (z1.value.isNaN() || z2.value.isNaN()) {
			return I32.zero;
		}
		// 2 Else if z1 and z2 are the same value, then return 1
		if (z1.equals(z2)) {
			return I32.one;
		}
		// 3 Else if z1 is positive infinity, then return 0
		if (z1.equals(F64.POSITIVE_INFINITY)) {
			return I32.zero;
		}
		// 4 Else if z1 is negative infinity, then return 1
		if (z1.equals(F64.NEGATIVE_INFINITY)) {
			return I32.one;
		}
		// 5 Else if z2 is positive infinity, then return 1
		if (z2.equals(POSITIVE_INFINITY)) {
			return I32.one;
		}

		// 6 Else if z2 is negative infinity, then return 0
		if (z2.equals(NEGATIVE_INFINITY)) {
			return I32.zero;
		}
		// 7 Else if both z1 and z2 are zeroes, then return 0
		// Java Implementation: Check for both ZERO_POSITIVE plus ZERO_NEGATIVE.
		// I think the specification was trying to say check Positive and Negative, but it is
		// not explicit.
		if ((z1.equals(ZERO_POSITIVE) || z1.equals(ZERO_NEGATIVE))    //
			&&                                                        //
			(z2.equals(ZERO_POSITIVE) || z2.equals(ZERO_NEGATIVE))    //
		) {
			return I32.one;
		}
		// 8 Else if z1 is smaller than z2, then return 1
		if (z1.value <= z2.value) {
			return I32.one;
		}
		// 9 Else return 0
		return I32.zero;
	}


	/**
	 * lessThan according to the Wasm specification.
	 * <pre>F32 F32 -> I32</pre>
	 * <pre>
	 * Source: <br>
	 * </pre>
	 * <p>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#xref-exec-numerics-op-feq-mathrm-feq-n-z-1-z-2" target="_top">
	 * Numerics Equal
	 * </a>
	 * <p>
	 *
	 * @param other
	 * @return 1 if less than otherwise 0
	 */
	public I32 lessThanWasm(F64 other) {
		return lessThanWasm(this, other);
	}

	/**
	 * lessThan according to the Wasm specification.
	 * <pre>F64 F64 -> I32</pre>
	 * <p>
	 * Source:
	 * <a  href="https://webassembly.github.io/spec/core/exec/numerics.html#xref-exec-numerics-op-flt-mathrm-flt-n-z-1-z-2" target="_top">
	 * Numerics Less Than Wasm Specification.
	 * </a>
	 * <br>
	 * <pre>
	 * 1 If either z1 or z2 is a NaN, then return 0
	 * 2 Else if z1 and z2 are the same value, then return 0
	 * 3 Else if z1 is positive infinity, then return 0
	 * 4 Else if z1 is negative infinity, then return 1
	 * 5 Else if z2 is positive infinity, then return 1
	 * 6 Else if z2 is negative infinity, then return 0
	 * 7 Else if both z1 and z2 are zeroes, then return 0
	 * 8 Else if z1 is smaller than z2, then return 1
	 * 9 Else return 0
	 * </pre>
	 *
	 * @param z1 the left number.
	 * @param z2 the right number.
	 * @return 1 if z1 less than z2 otherwise 0.   z1 < z2
	 */
	public static I32 lessThanWasm(F64 z1, F64 z2) {
		Integer result = 0;

		// 1 If either z1 or z2 is a NaN, then return 0<br>
		if (z1.value.isNaN() || z2.value.isNaN()) {
			return I32.zero;
		}
		// 2 Else if z1 and z2 are the same value, then return 0
		if (z1.equals(z2)) {
			return I32.zero;
		}
		// 3 Else if z1 is positive infinity, then return 0
		if (z1.equals(F64.POSITIVE_INFINITY)) {
			return I32.zero;
		}
		// 4 Else if z1 is negative infinity, then return 1
		if (z1.equals(F64.NEGATIVE_INFINITY)) {
			return I32.one;
		}
		// 5 Else if z2 is positive infinity, then return 1
		if (z2.equals(POSITIVE_INFINITY)) {
			return I32.one;
		}

		// 6 Else if z2 is negative infinity, then return 0
		if (z2.equals(NEGATIVE_INFINITY)) {
			return I32.zero;
		}
		// 7 Else if both z1 and z2 are zeroes, then return 0
		// Java Implementation: Check for both ZERO_POSITIVE plus ZERO_NEGATIVE.
		// I think the specification was trying to say check Positive and Negative, but it is
		// not explicit.
		if ((z1.equals(ZERO_POSITIVE) || z1.equals(ZERO_NEGATIVE))    //
			&&                                                        //
			(z2.equals(ZERO_POSITIVE) || z2.equals(ZERO_NEGATIVE))    //
		) {
			return I32.zero;
		}
		// 8 Else if z1 is smaller than z2, then return 1
		if (z1.value < z2.value) {
			return I32.one;
		}
		// 9 Else return 0
		return I32.zero;
	}

	public F64 copysign(F64 z2) {
		return copysign(this, z2);
	}

	/**
	 * Returns the sign of the value.
	 *
	 * @return True if the sign is positive
	 * False if the sign is negative.
	 * <p>
	 * Zero can be both positive or negative
	 */
	public Boolean isPositive() {
		Long bits = Double.doubleToLongBits(value);
		Long mask = bits & 0x8000_0000_0000_0000L;
		Boolean result = (0 == mask);
		return result;
	}

	/**
	 * Source:
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-fcopysign" target="_top">
	 * Copysign
	 * </a>
	 *
	 * @param z1 Value1
	 * @param z2 Value2
	 * @return The copysign result.
	 */
	public static F64 copysign(F64 z1, F64 z2) {
		F64 result;

		Boolean z1Pos = z1.isPositive();
		Boolean z2Pos = z2.isPositive();

		// 1. If z1 and z2 have the same sign, then return z1.
		if (z1Pos == z2Pos) {
			result = z1;
		} else {
			// 2. Else return z1 with negated sign.
			result = negWasm(z1);
		}
		return result;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("F64{");
		sb.append("value=").append(value);
		if (value != null) {
			if (value.isNaN()) {
				sb.append(" NaN");
			}
			if (value.isInfinite()) {
				sb.append(" Infinite");
			}

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
