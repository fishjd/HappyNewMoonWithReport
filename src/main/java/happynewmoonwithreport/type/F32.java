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
 * <h2>Java Implementation</h2>
 * This uses a {@code Float} type.  Floats and F32 are the same IEEE 754.
 * <p>
 * See:
 * <p><a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html">
 * Java Documentation on types.
 * </a>
 * <p>
 * This class is immutable.
 * <h2>Wasm documentation</h2>
 * <p><a href="https://webassembly.github.io/spec/core/syntax/types.html#value-types">
 * WASM documentation on types.
 * </a>
 */
public class F32 implements DataTypeNumberFloat {
	protected final Float value;

	public static final F32 ZERO_POSITIVE = new F32(0.0F);
	// Java stores a negative zero correctly,  Groovy/Spock has issues.
	public static final F32 ZERO_NEGATIVE = new F32(-0.0F);
	public static final F32 POSITIVE_INFINITY = new F32(Float.POSITIVE_INFINITY);
	public static final F32 NEGATIVE_INFINITY = new F32(Float.NEGATIVE_INFINITY);
	public static final F32 NAN = new F32(Float.NaN);  // Not a number
	// Java does not allow a Negative NAN.  It converts it to NAN.
	// public static final F32 NAN_NEGATIVE = F32.valueOf(Float.intBitsToFloat(0xffc00000));  //
	// negative Not a number


	public F32() {
		this.value = 0F;
	}

	public F32(Float value) {
		this.value = value;
	}

	/**
	 * Use F32.valueOf(String)
	 * <pre>
	 * {@code
	 *    try {
	 *         F32 zero = F32.valueOf("0.0");
	 *    } catch (NumberFormatException nfe) {
	 *        // do something.
	 *    }
	 *
	 * }
	 * </pre>
	 */
	@Deprecated
	public F32(String input) {
		throw new java.lang.UnsupportedOperationException(
			"Constructor F32(String) not supported. Use F32 ValueOf(String)");
	}

	/**
	 * Returns a {@code F32} object holding the
	 * {@code F32} value represented by the argument string
	 * {@code s}.
	 *
	 * <p>If {@code s} is {@code null}, then a
	 * {@code NullPointerException} is thrown.
	 *
	 * @param s the string to be parsed.
	 * @return a {@code F32} object holding the value
	 * represented by the {@code String} argument.
	 * @throws NumberFormatException if the string does not contain a parse-able number.
	 * @see Float#valueOf(String)
	 */
	public static F32 valueOf(String s) throws NumberFormatException {
		Float val;

		switch (s) {
			case ("-inf"):
				val = Float.NEGATIVE_INFINITY;
				break;
			case ("inf"):
				val = Float.POSITIVE_INFINITY;
				break;
			case ("nan"):
			case ("nan:0x200000"):    // TODO figure out what "nan:0x200000" is trying to express.
				val = Float.NaN;
				break;
			case ("-nan"):
			case ("-nan:0x200000"):  // TODO figure out what "-nan:0x200000" is trying to express.
				val = Float.NaN;
				break;
			default:
				val = Float.valueOf(s);
		}

		F32 result = new F32(val);
		return result;
	}

	/**
	 * Create a F32 instance given a {@code Float}.
	 * <p>
	 * Does not handle -0F. To be more precise the object Float does not handle -0F.
	 * Use {@code F32.NEGATIVE_ZERO}  or {@code F32.valueOf(String)} instead.
	 *
	 * @param input
	 * @return
	 */
	public static F32 valueOf(Float input) {
		F32 result = new F32(input);
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

		// TODO  check for at least 8 bytes to avoid null point exception.  Do the same for F32,
		//  ....

		// As a coding standard the index '[0]' must be in descending order.
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

	/**
	 * Calculate the Absolute value according to the Wasm Specification.
	 * <pre>F32 -> F32</pre>
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
	public F32 absWasm() {
		Float z = value;
		// If z is a NaN, then return z with positive sign.
		// Java Implementation Note:  Java does not implement negative non a number -NAN.
		if (z.isNaN()) {
			return F32.NAN;
		}
		// if z is an infinity, then return positive infinity.
		if (z.isInfinite()) {
			return F32.POSITIVE_INFINITY;
		}
		// if z is a zero, then return positive zero.
		if (z == 0F || z == -0F) {
			return F32.ZERO_POSITIVE;
		}
		// Else if z is a positive value, then z.
		if (0F < z) {
			return this;
		} else {
			return new F32(-value);
		}
	}


	/**
	 * Calculate the Negative value according to the Wasm Specification.
	 * <pre>F32 -> F32</pre>
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
	public F32 negWasm() {
		Float z = value;
		// If z is a NaN, then return z with negated sign.
		// Java Implementation Note:  Java does not implement negative non a number -NAN.
		if (z.isNaN()) {
			return F32.NAN;
		}
		// if z is an infinity, then return that infinity negated.
		if (z.equals(Float.POSITIVE_INFINITY)) {
			return F32.NEGATIVE_INFINITY;
		}
		if (z.equals(Float.NEGATIVE_INFINITY)) {
			return F32.POSITIVE_INFINITY;
		}
		// if z is a zero, then return that zero negated.
		if (Float.floatToIntBits(z) == Float.floatToIntBits(F32.ZERO_NEGATIVE.value)) {
			return F32.ZERO_POSITIVE;
		}
		if (Float.floatToIntBits(z) == Float.floatToIntBits(F32.ZERO_POSITIVE.value)) {
			return F32.ZERO_NEGATIVE;
		}

		// Else return z negate
		return new F32(-value);
	}

	/**
	 * Equals according to the Wasm specification.
	 * <pre>F32 F32 -> I32</pre>
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
	public I32 equalsWasm(F32 other) {
		return equalsWasm(this, other);
	}

	/**
	 * Equals according to the Wasm specification.
	 * <pre>F32 F32 -> I32</pre>
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
	public static I32 equalsWasm(F32 z1, F32 z2) {
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
	 * <pre>F32 F32 -> I32</pre>
	 * <p>
	 * Source:
	 * <a  href="https://webassembly.github.io/spec/core/exec/numerics.html#xref-exec-numerics-op-fge-mathrm-fge-n-z-1-z-2" target="_top">
	 * Numerics greater Than or equal  Wasm Specification.
	 * </a>
	 * <p>
	 *
	 * @param other the value to compare to
	 * @return 1 if greater than or equal to otherwise 0
	 * @see F32#greaterThanEqualWasm(F32, F32)
	 */
	public I32 greaterThaEqualnWasm(F32 other) {
		return greaterThanEqualWasm(this, other);
	}

	/**
	 * Greater Than or Equal to according to the Wasm specification.
	 * <pre>F32 F32 -> I32</pre>
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
	public static I32 greaterThanEqualWasm(F32 z1, F32 z2) {
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
		if (z1.equals(F32.POSITIVE_INFINITY)) {
			return I32.one;
		}
		// 4 Else if z1 is negative infinity, then return 0
		if (z1.equals(F32.NEGATIVE_INFINITY)) {
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
	 * @return 1 if greater than otherwise 0
	 */
	public I32 greaterThanWasm(F32 other) {
		return greaterThanWasm(this, other);
	}

	/**
	 * Greater Than  according to the Wasm specification.
	 * <pre>F32 F32 -> I32</pre>
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
	public static I32 greaterThanWasm(F32 z1, F32 z2) {
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
		if (z1.equals(F32.POSITIVE_INFINITY)) {
			return I32.one;
		}
		// 4 Else if z1 is negative infinity, then return 0
		if (z1.equals(F32.NEGATIVE_INFINITY)) {
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
	 * <pre>F32 F32 -> I32</pre>
	 * <p>
	 * Source:
	 * <a  href="https://webassembly.github.io/spec/core/exec/numerics.html#xref-exec-numerics-op-fle-mathrm-fle-n-z-1-z-2" target="_top">
	 * Numerics Less Than or Equal Wasm Specification.
	 * </a>
	 * <p>
	 *
	 * @param other the value to compare to.
	 * @return 1 if less or equal than otherwise 0
	 * @see F32#lessThanEqualWasm(F32, F32)
	 */
	public I32 lessThanEqualWasm(F32 other) {
		return lessThanEqualWasm(this, other);
	}

	/**
	 * lessThan or Equal according to the Wasm specification.
	 * <pre>F32 F32 -> I32</pre>
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
	public static I32 lessThanEqualWasm(F32 z1, F32 z2) {
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
		if (z1.equals(F32.POSITIVE_INFINITY)) {
			return I32.zero;
		}
		// 4 Else if z1 is negative infinity, then return 1
		if (z1.equals(F32.NEGATIVE_INFINITY)) {
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
	public I32 lessThanWasm(F32 other) {
		return lessThanWasm(this, other);
	}

	/**
	 * lessThan according to the Wasm specification.
	 * <pre>F32 F32 -> I32</pre>
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
	public static I32 lessThanWasm(F32 z1, F32 z2) {
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
		if (z1.equals(F32.POSITIVE_INFINITY)) {
			return I32.zero;
		}
		// 4 Else if z1 is negative infinity, then return 1
		if (z1.equals(F32.NEGATIVE_INFINITY)) {
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
