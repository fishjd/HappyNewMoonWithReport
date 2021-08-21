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
 * <br><a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html">
 * Java Documentation on types.
 * </a>
 *
 * <br><a href="https://webassembly.github.io/spec/core/syntax/types.html#value-types">
 * WASM documentation on types.
 * </a>
 */
public class F64 implements DataTypeNumberFloat {
	protected final Double value;

	// @formatter:off
	// 					 		Name						  Bits						   S    EXP         Fraction															// Long representation.
	private static final Long	NanCanonicalPos_Bits 			= 0x7FF8_0000_0000_0000L;	// 0  111_1111_1111 1000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000	9221120237041090560
	private static final Long	NanCanonicalNeg_Bits 			= 0xFFF8_0000_0000_0000L;	// 1  111_1111_1111 1000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000	-2251799813685248
	private static final Long	NanPos_Bits  	       			= NanCanonicalPos_Bits;
	private static final Long	NanNeg_Bits      	   			= NanCanonicalNeg_Bits;
	private static final Long	Nan0x4_0000_0000_0000Pos_Bits 	= 0x7FFC_0000_0000_0000L;   // 0  111_1111_1111 1100_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000	9222246136947933184
	private static final Long	Nan0x4_0000_0000_0000Neg_Bits 	= 0xFFFC_0000_0000_0000L;   // 0  111_1111_1111 1100_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000	-1125899906842624
	private static final Long	NanArithmeticPos_Bits			= 0x7FFF_FFFF_FFFF_FFFFL;   // 0  111_1111_1111 1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111	9223372036854775807
	private static final Long	NanArithmeticNeg_Bits			= 0xFFFF_FFFF_FFFF_FFFFL;   // 1  111_1111_1111 1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111	-1
	private static final Long	InfinityPos_Bits	    		= 0x7FF0_0000_0000_0000L;   // 0  111_1111_1111 0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000	9218868437227405312
	private static final Long	InfinityNeg_Bits	    		= 0xFFF0_0000_0000_0000L;   // 1  111_1111_1111 0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000	-4503599627370496
	// @formatter:on
	// Source:  https://en.wikipedia.org/wiki/Double-precision_floating-point_format
	// Bits -   The binary bits of the constant.  Use in Double.intBitsToDouble();
	// S -  Sign  The sign of the constant.
	// Exponent -  The exponent of the constant.
	// Fraction -  The fraction of the constant.
	// Integer Representation - the bit when stored as a integer.
	//

	public static final F64 ZeroPositive = new F64(0.0D);
	// Java stores a negative zero correctly,  Groovy/Spock has issues.
	public static final F64 ZeroNegative = new F64(-0.0D);

	/**
	 * Infinity Positive
	 */
	public static final F64 InfinityPositive = new F64(Double.POSITIVE_INFINITY);

	/**
	 * Infinity Negative
	 */
	public static final F64 InfinityNegative = new F64(Double.NEGATIVE_INFINITY);

	/**
	 * Not a Number
	 * <p>
	 * NaN is equivalent to the value returned by Double.longBitsToDouble(0x7FF8_0000_0000_0000L).
	 * <p>
	 * <p>
	 * <b>Source:</b><p>
	 * <a href="https://docs.oracle.com/javase/7/docs/api/java/lang/Double.html#NaN" target="_top">
	 * https://docs.oracle.com/javase/7/docs/api/java/lang/Double.html#NaN
	 * </a>
	 */
	public static final F64 Nan = new F64(Double.longBitsToDouble(NanCanonicalPos_Bits));

	/**
	 * Not a Number in Canonical form.
	 * <p>
	 * NaN is equivalent to the value returned by Double.longBitsToDouble(0x7FF8_0000_0000_0000L).
	 * <p>
	 * <p>
	 * <b>Source:</b><p>
	 * <a href="https://docs.oracle.com/javase/7/docs/api/java/lang/Double.html#NaN" target="_top">
	 * https://docs.oracle.com/javase/7/docs/api/java/lang/Double.html#NaN
	 * </a>
	 */
	public static final F64 NanCanonicalPos = new F64(Double.longBitsToDouble(NanCanonicalPos_Bits));
	public static final F64 NanCanonicalNeg = new F64(Double.longBitsToDouble(NanCanonicalNeg_Bits));
	public static final F64 NanNeg = new F64(Double.longBitsToDouble(NanNeg_Bits));
	public static final F64 Nan0x4_0000_0000_0000Pos = new F64(Double.longBitsToDouble(Nan0x4_0000_0000_0000Pos_Bits));
	public static final F64 Nan0x4_0000_0000_0000Neg = new F64(Double.longBitsToDouble(Nan0x4_0000_0000_0000Neg_Bits));
	public static final F64 NanArithmeticPos = new F64(Double.longBitsToDouble(NanArithmeticPos_Bits));
	public static final F64 NanArithmeticNeg = new F64(Double.longBitsToDouble(NanArithmeticNeg_Bits));


	public F64() {
		this.value = 0D;
	}

	public F64(Double value) {
		this.value = value;
	}

	// Groovy/Spock need the copy constructor.

	/**
	 * Copy Constructor.
	 *
	 * @param input value to copy
	 */
	public F64(F64 input) {
		this.value = input.value;
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
		F64 val;

		switch (s) {
			case ("-inf"):
				val = F64.InfinityNegative;
				break;
			case ("inf"):
				val = F64.InfinityPositive;
				break;
			case ("nan"):
			case ("nan:canonical"):
				val = F64.Nan;
				break;
			case ("-nan"):
			case ("-nan:canonical"):
				val = F64.NanNeg;
				break;
			case ("nan:0x4000000000000"):
				val = F64.Nan0x4_0000_0000_0000Pos;
				break;
			case ("-nan:0x4000000000000"):
				val = F64.Nan0x4_0000_0000_0000Neg;
				break;
			case ("nan:arithmetic"):
				val = F64.NanArithmeticPos;
				break;
			case ("-nan:arithmetic"):
				val = F64.NanArithmeticNeg;
				break;
			default:
				val = new F64(Double.valueOf(s));
		}
		return val;
	}

	/**
	 * Create a F64 instance given a {@code Float}.
	 * <p>
	 * Does not handle -0F. To be more precise the object Float does not handle
	 * -0F.
	 * Use {@code F64.NEGATIVE_ZERO}  or {@code F64.valueOf(String)} instead.
	 *
	 * @param input value to store in the new object.
	 * @return an F64 set to the input value.
	 */
	public static F64 valueOf(Float input) {
		return new F64(input.doubleValue());
	}

	/**
	 * Create a F64 instance given a {@code Double}.
	 * <p>
	 * Does not handle -0F. To be more precise the object Float does not handle -0F.
	 * Use {@code F64.NEGATIVE_ZERO}  or {@code F64.valueOf(String)} instead.
	 *
	 * @param input value to store in the new object.
	 * @return an F64 set to the input value.
	 */
	public static F64 valueOf(Double input) {
		return new F64(input);
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
	 * IEEE-754 Doubleing Point Calculator.  Works for 32 & 64 bits.
	 * </a>
	 * <br>
	 * The complement is F64(ByteUnsigned []) constructor.
	 * <br>
	 * <b>Java implementation</b>
	 * This uses <code>Double.doubleToIntBits(double)</code> to covert to an <code>Integer</code>.
	 *
	 * @return an array of ByteUnsigned
	 */
	public ByteUnsigned[] getBytes() {
		Long bits = Double.doubleToRawLongBits(value);

		// Integer to ByteUnsigned array
		return getByteUnsigned(bits);
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

		return new F64(Double.longBitsToDouble(valueLong));
	}

	/**
	 * Is Not a Number (NaN).   This is true for any form of NaN.
	 * <p>
	 * Nan all bits of the exponent or set and at least 1 bit of the mantissa.
	 * May be positive or negative.
	 *
	 * @return True if value is a Nan.
	 */
	public Boolean isNan() {
		return value.isNaN();
	}

	/**
	 * Is Infinite
	 * <p>
	 * Infinite =  all bits of the exponent or set.  All bits of the mantissa are zero.
	 * May be positive or negative.
	 * <p>
	 * If value is infinityNegative or InfinityPositive then true.
	 *
	 * @return True: if value is infinite
	 * False: all other cases
	 */
	public Boolean isInfinite() {
		return value.isInfinite();
	}

	/**
	 * Is this Not a Number (NaN) is Canonical form.
	 * <p>
	 * It is equivalent to the value returned by Double.longBitsToDouble(0x7ff8000000000000L).
	 * <p>
	 * <p>
	 * A canonical NaN is a floating-point value �nan(canonN) where canonN is a payload whose most significant bit is
	 * 1 while all others are 0:
	 * <p>
	 * <b>Source:</b>
	 * <a href="https://webassembly.github.io/spec/core/syntax/values.html#canonical-nan" target="_top">
	 * https://webassembly.github.io/spec/core/syntax/values.html#canonical-nan
	 * </a>
	 *
	 * @return True if value in bits is 0x7ff8000000000000L or 0xfff8000000000000L.
	 */
	public Boolean isNanCanonical() {
		Boolean result = false;
		result |= Double.doubleToRawLongBits(value) == NanCanonicalPos_Bits;
		result |= Double.doubleToRawLongBits(value) == NanCanonicalNeg_Bits;
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
	public F64 abs() {
		// Wasm manual states No Nan Propagation on Absolute Value.

		Double z = value;
		// If z is a NaN, then return z with positive sign.
		if (isNan()) {
			if (isPositive()) {
				return this;
			} else {
				return negate();
			}
		}
		// if z is an infinity, then return positive infinity.
		if (z.isInfinite()) {
			return F64.InfinityPositive;
		}
		// if z is a zero, then return positive zero.
		if (z == 0F || z == -0F) {
			return F64.ZeroPositive;
		}
		// Else if z is a positive value, then z.
		if (0F < z) {
			return this;
		} else {
			// Else return z negated.
			return negate();
		}
	}

	public static F64 neg(F64 z1) {
		return z1.neg();
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
	public F64 neg() {
		Double z = value;
		// If z is a NaN, then return z with negated sign.
		// Java Implementation Note:  Java does not implement negative non a number -NAN.
		if (z.isNaN()) {
			return negate();
		}
		// if z is an infinity, then return that infinity negated.
		if (z.equals(Float.POSITIVE_INFINITY)) {
			return F64.InfinityNegative;
		}
		if (z.equals(Float.NEGATIVE_INFINITY)) {
			return F64.InfinityPositive;
		}
		// if z is a zero, then return that zero negated.
		if (Double.doubleToRawLongBits(z) == F64.ZeroNegative.toBits()) {
			return F64.ZeroPositive;
		}
		if (Double.doubleToRawLongBits(z) == F64.ZeroPositive.toBits()) {
			return F64.ZeroNegative;
		}

		// Else return z negated
		return negate();
	}

	/**
	 * Change the sign bit.
	 * Works with Nan, Nan 20_0000, etc
	 *
	 * @return The same value with the sign bit toggled.
	 */
	public F64 negate() {
		Long rawBits = Double.doubleToRawLongBits(value);

		// toggle the sign bit
		rawBits = 0x8000_0000_0000_0000L ^ rawBits;

		Double result = Double.longBitsToDouble(rawBits);

		return new F64(result);
	}

	/**
	 * Calculate the Ceiling value according to the Wasm Specification.
	 * <pre>F64 -> F64</pre>
	 *
	 * <h2>Source:</h2>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-fceil" target="_top">
	 * Float Ceil
	 * </a>
	 * <p>
	 * <ul>
	 * <li>if z is a NaN, then return an element of nansN{z}.
	 * </li><li>
	 * Else if z is an infinity, then return z.
	 * </li><li>
	 * Else if z is a zero, then return z.
	 * </li><li>
	 * Else if z is smaller than 0 but greater than -1, then return negative zero.
	 * </li><li>
	 * Else return the smallest integral value that is not smaller than z.
	 * </ul>
	 *
	 * @return the ceiling of the input value
	 */
	public F64 ceil() {
		Double z = value;

		//if z is a NaN, then return an element of nansN{z}
		if (z.isNaN()) {
			return nanPropagation(this);
		}
		// Else if z is an infinity, then return z.
		if (z.isInfinite()) {
			return this;
		}
		//	 Else if z is a zero, then return z.
		if (isZero()) {
			return this;
		}
		// Else if z is smaller than 0 but greater than -1, then return negative zero.
		if (-1f < z && z < 0f) {
			return ZeroNegative;
		}
		// Else return the smallest integral value that is not smaller than z.
		double ceil = Math.ceil(z);
		return F64.valueOf(ceil);
	}

	/**
	 * Calculate the Floor value according to the Wasm Specification.
	 * <pre>F64 -> F64</pre>
	 *
	 * <h2>Source:</h2>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-ffloor" target="_top">
	 * Float Floor
	 * </a>
	 * <p>
	 * <ul>
	 * <li>if z is a NaN, then return an element of nansN{z}.
	 * </li><li>
	 * Else if z is an infinity, then return z.
	 * </li><li>
	 * Else if z is a zero, then return z.
	 * </li><li>
	 * Else if z is greater than 0 but smaller than 1, then return positive zero.
	 * </li><li>
	 * Else return the largest integral value that is not larger than z.
	 * </ul>
	 *
	 * @return the Floor of the input value
	 */
	public F64 floor() {
		Double z = value;

		//if z is a NaN, then return an element of nansN{z}
		if (z.isNaN()) {
			return nanPropagation(this);
		}
		// Else if z is an infinity, then return z.
		if (z.isInfinite()) {
			return this;
		}
		//	 Else if z is a zero, then return z.
		if (isZero()) {
			return this;
		}
		// Else if z is greater than 0 but smaller than 1, then return positive zero.
		if (0F < z && z < 1F) {
			return ZeroPositive;
		}
		// Else return the smallest integral value that is not smaller than z.
		double floor = Math.floor(z);
		return F64.valueOf(floor);
	}

	/**
	 * Calculate the Nearest value according to the Wasm Specification.
	 * <pre>F64 -> F64</pre>
	 *
	 * <h2>Source:</h2>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-fnearest" target="_top">
	 * Float Nearest
	 * </a>
	 * <p>
	 * <ul>
	 * <li>if z is a NaN, then return an element of nansN{z}.
	 * </li><li>
	 * Else if z is an infinity, then return z.
	 * </li><li>
	 * Else if z is a zero, then return z.
	 * </li><li>
	 * Else if z is greater than 0 but smaller than or equal to 0.5, then return positive zero.
	 * </li><li>
	 * Else if z is smaller than 0 but greater than or equal to -0.5, then return negative zero.
	 * </li><li>
	 * Else return the integral value that is nearest to z; if two values are equally near, return
	 * the even one.
	 * </ul>
	 *
	 * @return the Nearest of the input value
	 */
	public F64 nearest() {
		Double z = value;

		//if z is a NaN, then return an element of nansN{z}
		if (z.isNaN()) {
			return nanPropagation(this);
		}
		// Else if z is an infinity, then return z.
		if (z.isInfinite()) {
			return this;
		}
		//	 Else if z is a zero, then return z.
		if (isZero()) {
			return this;
		}
		// Else if z is greater than 0 but smaller than or equal to 0.5, then return positive zero.
		if (0F < z && z <= 0.5F) {
			return ZeroPositive;
		}
		// Else if z is smaller than 0 but greater than or equal to -0.5, then return negative
		// zero.
		if (-0.5F <= z && z < 0F) {
			return ZeroNegative;
		}
		// Else return the integral value that is nearest to z; if two values are equally near,
		// return the even one.
		double nearest = Math.rint(z);
		return F64.valueOf(nearest);
	}

	/**
	 * Calculate the Truncated value according to the Wasm Specification.
	 * <pre>F64 -> F64</pre>
	 *
	 * <h2>Source:</h2>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-ftrunc" target="_top">
	 * Trunc
	 * </a>
	 * <p>
	 * <ul>
	 * <li>if z is a NaN, then return an element of nansN{z}.
	 * </li><li>
	 * Else if z is an infinity, then return z.
	 * </li><li>
	 * Else if z is a zero, then return z.
	 * </li><li>
	 * Else if z is greater than 0 but smaller than 1, then return positive zero.
	 * </li><li>
	 * Else if z is smaller than 0 but greater than -1, then return negative zero.
	 * </li><li>
	 * Else return the integral value with the same sign as z and the largest magnitude that is
	 * not larger than the magnitude of z.
	 * </ul>
	 *
	 * @return the Truncated of the input value
	 */
	public F64 trunc() {
		Double z = value;

		//if z is a NaN, then return an element of nansN{z}
		if (z.isNaN()) {
			return nanPropagation(this);
		}
		// Else if z is an infinity, then return z.
		if (z.isInfinite()) {
			return this;
		}
		//	 Else if z is a zero, then return z.
		if (isZero()) {
			return this;
		}
		// Else if z is greater than 0 but smaller than 1, then return positive zero.
		if (0F < z && z < 1F) {
			return ZeroPositive;
		}
		// Else if z is smaller than 0 but greater than -1, then return negative zero.
		if (-1F < z && z < 0F) {
			return ZeroNegative;
		}
		// Else return the smallest integral value that is not smaller than z.
		Double trunc = truncate(z);
		return F64.valueOf(trunc);
	}

	static Double truncate(double value) {
		// Source: https://www.dotnetperls.com/double-truncate-java
		// For negative numbers, use Math.ceil.
		// For positive numbers, use Math.floor.
		if (value < 0F) {
			return (double) Math.ceil(value);
		} else {
			return (double) Math.floor(value);
		}
	}

	/**
	 * Calculate the Square Root according to the Wasm Specification.
	 * <pre>F64 -> F64</pre>
	 *
	 * <h2>Source:</h2>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-fsqrt" target="_top">
	 * Square Root
	 * </a>
	 * <ul>
	 * 		<li>
	 * 		    if z is a NaN, then return an element of nansN{z}.
	 * 		</li><li>
	 * 			Else if z is negative infinity, then return an element of nansN{}.
	 * 		</li><li>
	 * 			Else if z is positive infinity, then return positive infinity.
	 * 		</li><li>
	 * 			Else if z is a zero, then return that zero.
	 * 		</li><li>
	 * 			Else if z has a negative sign, then return an element of nansN{}.
	 * 		</li>
	 * </ul>
	 *
	 * @return the Square Root of the input value
	 */
	public F64 sqrt() {
		Double z = value;

		//if z is a NaN, then return an element of nansN{z}
		if (z.isNaN()) {
			return nanPropagation(this);
		}
		// Else if z is negative infinity, then return an element of nansN{}.
		if (isNegative() && z.isInfinite()) {
			return nanPropagation();
		}
		// Else if z is positive infinity, then return positive infinity.
		if (isPositive() && z.isInfinite()) {
			return this;
		}
		// Else if z is a zero, then return that zero.
		if (isZero()) {
			return this;
		}
		// Else if z has a negative sign, then return an element of nansN{}.
		if (isNegative()) {
			return nanPropagation();
		}
		// Else return the square root of z.
		double sqrt = Math.sqrt(z);
		return F64.valueOf(sqrt);
	}

	/**
	 * Calculate Addition according to the Wasm Specification.
	 * <pre>F64 F64 -> F64</pre>
	 *
	 * <h2>Source:</h2>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-fadd" target="_top">
	 * Add z<sub>1</sub> z<sub>2</sub>
	 * </a>
	 * <ul>
	 * 		<li>
	 * 			 If either z<sub>1</sub> or z<sub>2</sub> is a NaN, then return an element of nansN{z<sub>1</sub> ,
	 * 			 z<sub>2</sub> }.
	 * 		</li><li>
	 * 			 Else if both z<sub>1</sub> and z<sub>2</sub> are infinities of opposite signs, then return an element
	 * 			 of nansN{}.
	 * 		</li><li>
	 * 			 Else if both z<sub>1</sub> and z<sub>2</sub> are infinities of equal sign, then return that infinity.
	 * 		</li><li>
	 * 			 Else if one of z<sub>1</sub> or z<sub>2</sub> is an infinity, then return that infinity.
	 * 		</li><li>
	 * 			 Else if both z<sub>1</sub> and z<sub>2</sub> are zeroes of opposite sign, then return positive zero.
	 * 		</li><li>
	 * 			 Else if both z<sub>1</sub> and z<sub>2</sub> are zeroes of equal sign, then return that zero.
	 * 		</li><li>
	 * 			 Else if one of z<sub>1</sub> or z<sub>2</sub> is a zero, then return the other operand.
	 * 		</li><li>
	 * 			Else if both z<sub>1</sub> and z<sub>2</sub> are values with the same magnitude but opposite signs,
	 * 			then return positive zero.
	 * 		</li><li>
	 * 			Else return the result of adding z<sub>1</sub> and z<sub>2</sub>, rounded to the nearest representable
	 * 			value.
	 * 		</li>
	 * </ul>
	 *
	 * @return the addition of the input values
	 */
	public static F64 add(F64 z1, F64 z2) {

		//	If either z1 or z2 is a NaN, then return an element of nansN{z1, z2}.
		if (z1.isNan() || z2.isNan()) {
			return nanPropagation(z1, z2);
		}
		// Else if both z1 and z2 are infinities of opposite signs, then return an element of nansN{}.
		if (isBothInfinitiesOfOppositeSign(z1, z2)) {
			return nanPropagation();
		}

		// Else if both z1 and z2 are infinities of equal sign, then return that infinity.
		if (isBothInfinitiesOfEqualSign(z1, z2)) {
			return z1;
		}

		// Else if one of z1 or z2 is an infinity, then return that infinity.
		if (isAnyInfinity(z1)) {
			return z1;
		}
		if (isAnyInfinity(z2)) {
			return z2;
		}

		// Else if both z1 and z2 are zeroes of opposite sign, then return positive zero.
		if (isBothZerosOfOppositeSign(z1, z2)) {
			return ZeroPositive;
		}

		// Else if both z1 and z2 are zeroes of equal sign, then return that zero.
		if (isBothZerosOfEqualSign(z1, z2)) {
			return z1;
		}

		// Else if one of z1 or z2 is a zero, then return the other operand.
		if (z1.isZero()) {
			return z2;
		}
		if (z2.isZero()) {
			return z1;
		}

		// Else if both z1 and z2 are values with the same magnitude but opposite signs, then return positive zero.

		// Else return the result of adding z1 and z2, rounded to the nearest representable value.
		double add = z1.value + z2.value;
		return F64.valueOf(add);
	}

	public F64 add(F64 other) {
		return add(this, other);
	}

	/**
	 * Calculate subtraction according to the Wasm Specification.
	 * <pre>F64 F64 -> F64</pre>
	 *
	 * <h2>Source:</h2>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-fsub" target="_top">
	 * Sub z<sub>1</sub> z<sub>2</sub>
	 * </a>
	 * <ol>
	 * 		<li>
	 * 			If either z1 or z2 is a NaN, then return an element of nansN{z1,z2}.
	 * 		</li><li>
	 * 			Else if both z1 and z2 are infinities of equal signs, then return an element of nansN{}.
	 * 		</li><li>
	 * 			Else if both z1 and z2 are infinities of opposite sign, then return z1.
	 * 		</li><li>
	 * 			Else if z1 is an infinity, then return that infinity.
	 * 		</li><li>
	 * 			Else if z2 is an infinity, then return that infinity negated.
	 * 		</li><li>
	 * 			Else if both z1 and z2 are zeroes of equal sign, then return positive zero.
	 * 		</li><li>
	 * 			Else if both z1 and z2 are zeroes of opposite sign, then return z1.
	 * 		</li><li>
	 * 			Else if z2 is a zero, then return z1.
	 * 		</li><li>
	 * 			Else if z1 is a zero, then return z2 negated.
	 * 		</li><li>
	 * 			Else if both z1 and z2 are the same value, then return positive zero.
	 * 		</li><li>
	 * 			Else return the result of subtracting z2 from z1, rounded to the nearest representable value.
	 * 		</li>
	 * </ol>
	 *
	 * @return the subtraction of the input values
	 */
	public static F64 sub(F64 z1, F64 z2) {

		//	If either z1 or z2 is a NaN, then return an element of nansN{z1, z2}.
		if (z1.isNan() || z2.isNan()) {
			return nanPropagation(z1, z2);
		}
		// 2 Else if both z1 and z2 are infinities of equal signs, then return an element of nansN{}.
		if (isBothInfinitiesOfEqualSign(z1, z2)) {
			return nanPropagation();
		}

		// 3 Else if both z1 and z2 are infinities of opposite sign, then return z1.
		if (isBothInfinitiesOfOppositeSign(z1, z2)) {
			return z1;
		}

		// 4 Else if z1 is an infinity, then return that infinity.
		if (z1 == InfinityPositive) {
			return InfinityPositive;
		}
		if (z1 == InfinityNegative) {
			return InfinityNegative;
		}

		// 5 Else if z2 is an infinity, then return that infinity negated.
		if (z2 == InfinityPositive) {
			return InfinityNegative;
		}
		if (z2 == InfinityNegative) {
			return InfinityPositive;
		}

		// 6 Else if both z1 and z2 are zeroes of equal sign, then return positive zero.
		if (isBothZerosOfEqualSign(z1, z2)) {
			return ZeroPositive;
		}

		// 7 Else if both z1 and z2 are zeroes of opposite sign, then return z1.
		if (isBothZerosOfOppositeSign(z1, z2)) {
			return z1;
		}

		// 8. Else if z2 is a zero, then return z1.
		if (z2.isZero()) {
			return z1;
		}

		// 9 Else if z1 is a zero, then return z2 negated.
		if (z1.isZero()) {
			return z2.negate();
		}

		// 10 Else if both z1 and z2 are the same value, then return positive zero.
		if (equalsWasm(z1, z2) == I32.True) {
			return ZeroPositive;
		}

		// 11 Else return the result of subtracting z2 from z1, rounded to the nearest representable value.
		double subtract = z1.value - z2.value;
		return F64.valueOf(subtract);
	}

	public F64 sub(F64 other) {
		return sub(this, other);
	}

	/**
	 * Calculate Multiplication according to the Wasm Specification.
	 * <pre>F64 F64 -> F64</pre>
	 *
	 * <h2>Source:</h2>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-fsub" target="_top">
	 * Mul z<sub>1</sub> z<sub>2</sub>
	 * </a>
	 * <ol>
	 * 		<li>
	 * 			If either z1 or z2 is a NaN, then return an element of nansN{z1, z2}.
	 * 		</li><li>
	 * 			Else if one of z1 and z2 is a zero and the other an infinity, then return an element of nansN{}.
	 * 		</li><li>
	 * 			Else if both z1 and z2 are infinities of equal sign, then return positive infinity.
	 * 		</li><li>
	 * 			Else if both z1 and z2 are infinities of opposite sign, then return negative infinity.
	 * 		</li><li>
	 * 			Else if one of z1 or z2 is an infinity and the other a value with equal sign, then return positive
	 * 			infinity.
	 * 		</li><li>
	 * 			Else if one of z1 or z2 is an infinity and the other a value with opposite sign, then return negative
	 * 			infinity.
	 * 		</li><li>
	 * 			Else if both z1 and z2 are zeroes of equal sign, then return positive zero.
	 * 		</li><li>
	 * 			Else if both z1 and z2 are zeroes of opposite sign, then return negative zero.
	 * 		</li><li>
	 * 			Else return the result of multiplying z1 and z2, rounded to the nearest representable value.
	 * 		</li>
	 * </ol>
	 *
	 * @return the multiplication of the input values
	 */
	public static F64 mul(F64 z1, F64 z2) {

		//1 If either z1 or z2 is a NaN, then return an element of nansN{z1, z2}.
		if (z1.isNan() || z2.isNan()) {
			return nanPropagation(z1, z2);
		}
		// 2 Else if one of z1 and z2 is a zero and the other an infinity, then return an element of nansN{}.
		if (z1.isInfinite() && z2.isZero()) {
			return nanPropagation();
		}
		if (z2.isInfinite() && z1.isZero()) {
			return nanPropagation();
		}

		// 3 Else if both z1 and z2 are infinities of equal sign, then return positive infinity.
		if (isBothInfinitiesOfEqualSign(z1, z2)) {
			return InfinityPositive;
		}

		// 4 Else if both z1 and z2 are infinities of opposite sign, then return negative infinity.
		if (isBothInfinitiesOfOppositeSign(z1, z2)) {
			return InfinityNegative;
		}

		// 5 Else if one of z1 or z2 is an infinity and the other a value with equal sign,
		// then return positive infinity.
		if (isInfinityOfEqualSign(z1, z2)) {
			return InfinityPositive;
		}
		if (isInfinityOfEqualSign(z2, z1)) {
			return InfinityPositive;
		}

		// 6 Else if one of z1 or z2 is an infinity and the other a value with opposite sign,
		// then return negative infinity.
		if (isInfinityOfOppositeSign(z1, z2)) {
			return InfinityNegative;
		}
		if (isInfinityOfOppositeSign(z2, z1)) {
			return InfinityNegative;
		}

		// 7 Else if both z1 and z2 are zeroes of equal sign, then return positive zero.
		if (isBothZerosOfEqualSign(z1, z2)) {
			return ZeroPositive;
		}

		// 8 Else if both z1 and z2 are zeroes of opposite sign, then return negative zero.
		if (isBothZerosOfOppositeSign(z1, z2)) {
			return ZeroNegative;
		}

		// 9 Else return the result of multiplying z1 and z2, rounded to the nearest representable value.
		double multiply = z1.value * z2.value;
		return F64.valueOf(multiply);
	}

	public F64 mul(F64 other) {
		return mul(this, other);
	}

	/**
	 * Calculate Division according to the Wasm Specification.
	 * <pre>F64 F64 -> F64</pre>
	 *
	 * <h2>Source:</h2>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-fdiv" target="_top">
	 * Div z<sub>1</sub> z<sub>2</sub>
	 * </a>
	 * <ol>
	 * 		<li>
	 * 			If either z1 or z2 is a NaN, then return an element of nansN{z1,z2}.
	 * 		</li><li>
	 * 			Else if both z1 and z2 are infinities, then return an element of nansN{}.
	 * 		</li><li>
	 * 			Else if both z1 and z2 are zeroes, then return an element of nansN{z1,z2}.
	 * 		</li><li>
	 * 			Else if z1 is an infinity and z2 a value with equal sign, then return positive infinity.
	 * 		</li><li>
	 * 			Else if z1 is an infinity and z2 a value with opposite sign, then return negative infinity.
	 * 		</li><li>
	 * 			Else if z2 is an infinity and z1 a value with equal sign, then return positive zero.
	 * 		</li><li>
	 * 			Else if z2 is an infinity and z1 a value with opposite sign, then return negative zero.
	 * 		</li><li>
	 * 			Else if z1 is a zero and z2 a value with equal sign, then return positive zero.
	 * 		</li><li>
	 * 			Else if z1 is a zero and z2 a value with opposite sign, then return negative zero.
	 * 		</li><li>
	 * 			Else if z2 is a zero and z1 a value with equal sign, then return positive infinity.
	 * 		</li><li>
	 * 			Else if z2 is a zero and z1 a value with opposite sign, then return negative infinity.
	 * 		</li><li>
	 * 			Else return the result of dividing z1 by z2, rounded to the nearest representable value.
	 * 		</li>
	 * </ol>
	 *
	 * @return the Division of the input values
	 */
	public static F64 div(F64 z1, F64 z2) {

		//1 If either z1 or z2 is a NaN, then return an element of nansN{z1,z2}.
		if (z1.isNan() || z2.isNan()) {
			return nanPropagation(z1, z2);
		}
		// 2 Else if both z1 and z2 are infinities, then return an element of nansN{}.
		if (z1.isInfinite() && z2.isInfinite()) {
			return nanPropagation();
		}

		// 3 Else if both z1 and z2 are zeroes, then return an element of nansN{z1,z2}.
		if (z1.isZero() && z2.isZero()) {
			return nanPropagation(z1, z2);
		}

		// 4 Else if z1 is an infinity and z2 a value with equal sign, then return positive infinity.
		if (isBothInfinitiesOfEqualSign(z1, z2)) {
			return InfinityPositive;
		}

		// 5 Else if z1 is an infinity and z2 a value with opposite sign, then return negative infinity.
		if (isBothInfinitiesOfOppositeSign(z1, z2)) {
			return InfinityNegative;
		}

		// 6 Else if z2 is an infinity and z1 a value with equal sign, then return positive zero.
		if (isBothInfinitiesOfEqualSign(z2, z1)) {
			return ZeroPositive;
		}

		// 7 Else if z2 is an infinity and z1 a value with opposite sign, then return negative zero.
		if (isBothInfinitiesOfOppositeSign(z2, z1)) {
			return ZeroNegative;
		}

		// 8 Else if z1 is a zero and z2 a value with equal sign, then return positive zero.
		if (isBothZerosOfEqualSign(z1, z2)) {
			return ZeroPositive;
		}

		// 9 Else if z1 is a zero and z2 a value with opposite sign, then return negative zero.
		if (isBothZerosOfOppositeSign(z1, z2)) {
			return ZeroNegative;
		}

		// 10 Else if z2 is a zero and z1 a value with equal sign, then return positive infinity.
		if (isZeroOfEqualSign(z2, z1)) {
			return InfinityPositive;
		}

		// 11 Else if z2 is a zero and z1 a value with opposite sign, then return negative infinity.
		if (isZeroOfOppositelSign(z2, z1)) {
			return InfinityNegative;
		}

		// 9 Else return the result of dividing z1 and z2, rounded to the nearest representable value.
		double division = z1.value / z2.value;
		return F64.valueOf(division);
	}

	public F64 div(F64 other) {
		return div(this, other);
	}

	/**
	 * Z1 and Z2 are infinity of the same sign.
	 *
	 * @param z1 value 1
	 * @param z2 value 2
	 * @return True:  Both Z1 and Z2 are an infinity and are the same sign.
	 * False: All other cases.
	 */
	private static Boolean isBothInfinitiesOfEqualSign(F64 z1, F64 z2) {
		Boolean result = false;
		result |= (z1 == InfinityPositive && z2 == InfinityPositive);
		result |= (z1 == InfinityNegative && z2 == InfinityNegative);
		return result;
	}

	/**
	 * Z1 and Z2 are infinity of the opposite sign.
	 *
	 * @param z1 value 1
	 * @param z2 value 2
	 * @return True:  Both Z1 and Z2 are an infinity and are the opposite sign.
	 * False: All other cases.
	 */
	private static Boolean isBothInfinitiesOfOppositeSign(F64 z1, F64 z2) {
		Boolean result = false;
		result |= (z1 == InfinityPositive && z2 == InfinityNegative);
		result |= (z1 == InfinityNegative && z2 == InfinityPositive);
		return result;
	}

	/**
	 * Is any of the input values positive or negative infinity?
	 *
	 * @param values A list of values to check.
	 * @return True:  Any value is an infinity.
	 * False: No value is an infinity.
	 */
	private static Boolean isAnyInfinity(F64... values) {
		Boolean result = false;
		for (F64 value : values) {
			result |= value == InfinityPositive;
			result |= value == InfinityNegative;
		}
		return result;
	}

	/**
	 * Is z1 is infinity and z2 is a value with the same sign.
	 *
	 * @param z1 value 1
	 * @param z2 value 2
	 * @return True:  z1 is Positive or Negative Infinity and z2 is the same sign.<br>
	 * False: In all other cases.
	 */
	private static Boolean isInfinityOfEqualSign(F64 z1, F64 z2) {
		Boolean result = false;
		result |= (z1.isInfinityPositive() && z2.isPositive());
		result |= (z1.isInfinityNegative() && z2.isNegative());
		return result;
	}

	/**
	 * Is z1 is infinity and z2 is a value with the opposite sign.
	 *
	 * @param z1 value 1
	 * @param z2 value 2
	 * @return True:  z1 is Positive or Negative Infinity and z2 is the opposite sign.<br>
	 * False: In all other cases.
	 */
	private static Boolean isInfinityOfOppositeSign(F64 z1, F64 z2) {
		Boolean result = false;
		result |= (z1.isInfinityPositive() && z2.isNegative());
		result |= (z1.isInfinityNegative() && z2.isPositive());
		return result;
	}

	/**
	 * Z1 and Z2 are zeros of the same sign.
	 *
	 * @param z1 value 1
	 * @param z2 value 2
	 * @return True:  Both Z1 and Z2 are a zeros and are the same sign.
	 * False: All other cases.
	 */
	private static Boolean isBothZerosOfEqualSign(F64 z1, F64 z2) {
		Boolean result = false;
		result |= (z1.isZeroPositive() && z2.isZeroPositive());
		result |= (z1.isZeroNegative() && z2.isZeroNegative());
		return result;
	}


	/**
	 * Z1 and Z2 are zeros of the opposite sign.
	 *
	 * @param z1 value 1
	 * @param z2 value 2
	 * @return True:  Both Z1 and Z2 are a zeros and are the opposite sign.
	 * False: All other cases.
	 */
	private static Boolean isBothZerosOfOppositeSign(F64 z1, F64 z2) {
		Boolean result = false;
		result |= (z1.isZeroPositive() && z2.isZeroNegative());
		result |= (z1.isZeroNegative() && z2.isZeroPositive());
		return result;
	}

	/**
	 * Is z1 is zero and z2 is a value with the same sign.
	 *
	 * @param z1 value 1
	 * @param z2 value 2
	 * @return True:  z1 is Positive or Negative zero and z2 is the same sign.<br>
	 * False: In all other cases.
	 */
	private static Boolean isZeroOfEqualSign(F64 z1, F64 z2) {
		Boolean result = false;
		result |= (z1.isZeroPositive() && z2.isPositive());
		result |= (z1.isZeroNegative() && z2.isNegative());
		return result;
	}

	/**
	 * Is z1 is zero and z2 is a value with the opposite sign.
	 *
	 * @param z1 value 1
	 * @param z2 value 2
	 * @return True:  z1 is Positive or Negative zero and z2 is the opposite sign.<br>
	 * False: In all other cases.
	 */
	private static Boolean isZeroOfOppositelSign(F64 z1, F64 z2) {
		Boolean result = false;
		result |= (z1.isZeroPositive() && z2.isNegative());
		result |= (z1.isZeroNegative() && z2.isPositive());
		return result;
	}


	/**
	 * Calculate Minimum  according to the Wasm Specification.
	 * <pre>F64 F64 -> F64</pre>
	 *
	 * <h2>Source:</h2>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-fmin" target="_top">
	 * Min z<sub>1</sub> z<sub>2</sub>
	 * </a>
	 * <ol>
	 * 		<li>
	 *  		If either z1 or z2 is a NaN, then return an element of nansN{z1,z2}.
	 * 		</li><li>
	 * 			Else if one of z1 or z2 is a negative infinity, then return negative infinity.
	 * 		</li><li>
	 * 			Else if one of z1 or z2 is a positive infinity, then return the other value.
	 * 		</li><li>
	 * 			Else if both z1 and z2 are zeroes of opposite signs, then return negative zero.
	 * 		</li><li>
	 * 			Else return the smaller value of z1 and z2.
	 * 		</li>
	 * </ol>
	 *
	 * @return The Minimum of the input values
	 */
	public static F64 min(F64 z1, F64 z2) {

		//1 If either z1 or z2 is a NaN, then return an element of nansN{z1,z2}.
		if (z1.isNan() || z2.isNan()) {
			return nanPropagation(z1, z2);
		}
		// 2 Else if one of z1 or z2 is a negative infinity, then return negative infinity.
		if (z1.isInfinityNegative() || z2.isInfinityNegative()) {
			return InfinityNegative;
		}

		// 3 Else if one of z1 or z2 is a positive infinity, then return the other value.
		if (z1.isInfinityPositive()) {
			return z2;
		}
		if (z2.isInfinityPositive()) {
			return z1;
		}

		// 4 Else if both z1 and z2 are zeroes of opposite signs, then return negative zero.
		if (isBothZerosOfOppositeSign(z1, z2)) {
			return ZeroNegative;
		}

		// 5 Else return the smaller value of z1 and z2.
		if (lessThan(z1, z2) == I32.one) {
			return z1;
		} else {
			return z2;
		}
	}

	public F64 min(F64 other) {
		return min(this, other);
	}

	/**
	 * Calculate Maximum according to the Wasm Specification.
	 * <pre>F64 F64 -> F64</pre>
	 *
	 * <h2>Source:</h2>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-fmax" target="_top">
	 * Max z<sub>1</sub> z<sub>2</sub>
	 * </a>
	 * <ol>
	 * 		<li>
	 * 			If either z1 or z2 is a NaN, then return an element of nansN{z1,z2}.
	 * 		</li><li>
	 * 			Else if one of z1 or z2 is a positive infinity, then return positive infinity.
	 * 		</li><li>
	 * 			Else if one of z1 or z2 is a negative infinity, then return the other value.
	 * 		</li><li>
	 * 			Else if both z1 and z2 are zeroes of opposite signs, then return positive zero.
	 * 		</li><li>
	 * 			Else return the larger value of z1 and z2.
	 * 		</li>
	 * </ol>
	 *
	 * @return the Maximum of the input values
	 */
	public static F64 max(F64 z1, F64 z2) {

		//1 If either z1 or z2 is a NaN, then return an element of nansN{z1,z2}.
		if (z1.isNan() || z2.isNan()) {
			return nanPropagation(z1, z2);
		}
		// 2 Else if one of z1 or z2 is a positive infinity, then return positive infinity.
		if (z1.isInfinityPositive() || z2.isInfinityPositive()) {
			return InfinityPositive;
		}

		// 3 Else if one of z1 or z2 is a negative infinity, then return the other value.
		if (z1.isInfinityNegative()) {
			return z2;
		}
		if (z2.isInfinityNegative()) {
			return z1;
		}

		// 4 Else if both z1 and z2 are zeroes of opposite signs, then return positive zero.
		if (isBothZerosOfOppositeSign(z1, z2)) {
			return ZeroPositive;
		}

		// 5 Else return the larger value of z1 and z2.
		if (greaterThan(z1, z2) == I32.one) {
			return z1;
		} else {
			return z2;
		}
	}

	public F64 max(F64 other) {
		return max(this, other);
	}

	/**
	 * NaN Propagation<p>
	 * Not a Number Propagation<p>
	 * <p>
	 * When the result of a floating-point operator other than fneg, fabs, or fcopysign
	 * is a NaN, then its sign is non-deterministic and the payload is computed as follows:
	 * <ul>
	 *     <li>
	 *         If the payload of all NaN inputs to the operator is canonical (including the case that there are no NaN
	 *         inputs), then the payload of the output is canonical as well.
	 * 		</li><li>
	 *         Otherwise the payload is picked non-deterministically among all arithmetic NaNs; that is, its most
	 *         significant bit is 1 and all others are unspecified.
	 * 		</li>
	 * </ul>
	 *
	 * <p>
	 * <b>Source:</b>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#aux-nans" target="_top">
	 * https://webassembly.github.io/spec/core/exec/numerics.html#aux-nans
	 * </a>
	 *
	 * @param inputArray any number of F64, may or may not be NaN.
	 */
	public static F64 nanPropagation(F64... inputArray) {
		for (F64 val : inputArray) {
			if (val.isNan() && val.isNanCanonical() == false) {
				// Otherwise the payload is picked non-deterministically among all arithmetic NaNs; that is, its
				// most significant bit is 1 and all others are unspecified.

				// Note: the Square Root and other WASM unit test require to return NanArithmetic in this case.
				// This does not align with the documentation that states it should be 'Non-deterministically'.
				// Who knows?  This is pretty deep in to the specification.
				return F64.NanArithmeticPos;
			}
		}
		// If the payload of all NaN inputs to the operator is canonical (including the case that there	are no NaN
		// inputs), then the payload of the output is canonical as well.
		return F64.NanCanonicalPos;
	}


	/**
	 * Convert to the bits.  This is Raw conversion.   Nan Values are not converted to Canonical Nan.
	 *
	 * @return Integer representation of the bits of F64.
	 */
	public Long toBits() {
		return Double.doubleToRawLongBits(value);
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
		if (z1.value.isNaN() || z2.value.isNaN()) {
			result = 0;
		} else
			// Else if both z1 and z2 are zeroes, then return 1
			// Java Implementation: Check for both ZERO_POSITIVE plus ZERO_NEGATIVE.
			// I think the specification was trying to say check Positive and Negative, but it is
			// not explicit.
			if ((z1.equals(ZeroPositive) || z1.equals(ZeroNegative))        //
				&&                                                            //
				(z2.equals(ZeroPositive) || z2.equals(ZeroNegative))    //
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
	 * @see F64#greaterThanEqual(F64, F64)
	 */
	public I32 greaterThanEqual(F64 other) {
		return greaterThanEqual(this, other);
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
	public static I32 greaterThanEqual(F64 z1, F64 z2) {

		// 1 If either z1 or z2 is a NaN, then return 0<br>
		if (z1.value.isNaN() || z2.value.isNaN()) {
			return I32.zero;
		}
		// 2 Else if z1 and z2 are the same value, then return 0
		if (z1.equals(z2)) {
			return I32.one;
		}
		// 3 Else if z1 is positive infinity, then return 1
		if (z1.equals(F64.InfinityPositive)) {
			return I32.one;
		}
		// 4 Else if z1 is negative infinity, then return 0
		if (z1.equals(F64.InfinityNegative)) {
			return I32.zero;
		}
		// 5 Else if z2 is positive infinity, then return 0
		if (z2.equals(InfinityPositive)) {
			return I32.zero;
		}

		// 6 Else if z2 is negative infinity, then return 1
		if (z2.equals(InfinityNegative)) {
			return I32.one;
		}
		// 7 Else if both z1 and z2 are zeroes, then return 0
		// Java Implementation: Check for both ZERO_POSITIVE plus ZERO_NEGATIVE.
		// I think the specification was trying to say check Positive and Negative, but it is
		// not explicit.
		if ((z1.equals(ZeroPositive) || z1.equals(ZeroNegative))    //
			&&                                                        //
			(z2.equals(ZeroPositive) || z2.equals(ZeroNegative))    //
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
	public I32 greaterThan(F64 other) {
		return greaterThan(this, other);
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
	public static I32 greaterThan(F64 z1, F64 z2) {

		// 1 If either z1 or z2 is a NaN, then return 0<br>
		if (z1.value.isNaN() || z2.value.isNaN()) {
			return I32.zero;
		}
		// 2 Else if z1 and z2 are the same value, then return 0
		if (z1.equals(z2)) {
			return I32.zero;
		}
		// 3 Else if z1 is positive infinity, then return 1
		if (z1.equals(F64.InfinityPositive)) {
			return I32.one;
		}
		// 4 Else if z1 is negative infinity, then return 0
		if (z1.equals(F64.InfinityNegative)) {
			return I32.zero;
		}
		// 5 Else if z2 is positive infinity, then return 0
		if (z2.equals(InfinityPositive)) {
			return I32.zero;
		}

		// 6 Else if z2 is negative infinity, then return 1
		if (z2.equals(InfinityNegative)) {
			return I32.one;
		}
		// 7 Else if both z1 and z2 are zeroes, then return 0
		// Java Implementation: Check for both ZERO_POSITIVE plus ZERO_NEGATIVE.
		// I think the specification was trying to say check Positive and Negative, but it is
		// not explicit.
		if ((z1.equals(ZeroPositive) || z1.equals(ZeroNegative))    //
			&&                                                        //
			(z2.equals(ZeroPositive) || z2.equals(ZeroNegative))    //
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
	 * @see F64#lessThanEqual(F64, F64)
	 */
	public I32 lessThanEqual(F64 other) {
		return lessThanEqual(this, other);
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
	public static I32 lessThanEqual(F64 z1, F64 z2) {

		// 1 If either z1 or z2 is a NaN, then return 0<br>
		if (z1.value.isNaN() || z2.value.isNaN()) {
			return I32.zero;
		}
		// 2 Else if z1 and z2 are the same value, then return 1
		if (z1.equals(z2)) {
			return I32.one;
		}
		// 3 Else if z1 is positive infinity, then return 0
		if (z1.equals(F64.InfinityPositive)) {
			return I32.zero;
		}
		// 4 Else if z1 is negative infinity, then return 1
		if (z1.equals(F64.InfinityNegative)) {
			return I32.one;
		}
		// 5 Else if z2 is positive infinity, then return 1
		if (z2.equals(InfinityPositive)) {
			return I32.one;
		}

		// 6 Else if z2 is negative infinity, then return 0
		if (z2.equals(InfinityNegative)) {
			return I32.zero;
		}
		// 7 Else if both z1 and z2 are zeroes, then return 0
		// Java Implementation: Check for both ZERO_POSITIVE plus ZERO_NEGATIVE.
		// I think the specification was trying to say check Positive and Negative, but it is
		// not explicit.
		if ((z1.equals(ZeroPositive) || z1.equals(ZeroNegative))    //
			&&                                                        //
			(z2.equals(ZeroPositive) || z2.equals(ZeroNegative))    //
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
	 * @return 1 if less than otherwise 0
	 */
	public I32 lessThan(F64 other) {
		return lessThan(this, other);
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
	public static I32 lessThan(F64 z1, F64 z2) {

		// 1 If either z1 or z2 is a NaN, then return 0<br>
		if (z1.value.isNaN() || z2.value.isNaN()) {
			return I32.zero;
		}
		// 2 Else if z1 and z2 are the same value, then return 0
		if (z1.equals(z2)) {
			return I32.zero;
		}
		// 3 Else if z1 is positive infinity, then return 0
		if (z1.equals(F64.InfinityPositive)) {
			return I32.zero;
		}
		// 4 Else if z1 is negative infinity, then return 1
		if (z1.equals(F64.InfinityNegative)) {
			return I32.one;
		}
		// 5 Else if z2 is positive infinity, then return 1
		if (z2.equals(InfinityPositive)) {
			return I32.one;
		}

		// 6 Else if z2 is negative infinity, then return 0
		if (z2.equals(InfinityNegative)) {
			return I32.zero;
		}
		// 7 Else if both z1 and z2 are zeroes, then return 0
		// Java Implementation: Check for both ZERO_POSITIVE plus ZERO_NEGATIVE.
		// I think the specification was trying to say check Positive and Negative, but it is
		// not explicit.
		if ((z1.equals(ZeroPositive) || z1.equals(ZeroNegative))    //
			&&                                                        //
			(z2.equals(ZeroPositive) || z2.equals(ZeroNegative))    //
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
		Long bits = Double.doubleToRawLongBits(value);
		Long mask = bits & 0x8000_0000_0000_0000L;
		Boolean result = (0 == mask);
		return result;
	}

	public Boolean isNegative() {
		return isPositive() == false;
	}
	/**
	 * Returns true if value is positive or negative zero.
	 *
	 * @return True:  if value is Zero_Positive or Zero_Negative.
	 */
	public Boolean isZero() {
		Boolean result = false;
		result |= Double.doubleToRawLongBits(this.value) == Double.doubleToRawLongBits(F64.ZeroNegative.value);
		result |= Double.doubleToRawLongBits(this.value) == Double.doubleToRawLongBits(F64.ZeroPositive.value);
		return result;
	}

	/**
	 * Returns true if value is positive infinity.
	 *
	 * @return True:  if value is Infinity_Positive.
	 */
	public Boolean isInfinityPositive() {
		Boolean result = this.equals(InfinityPositive);
		return result;
	}

	/**
	 * Returns true if value is negative infinity.
	 *
	 * @return True:  if value is Infinity_Negative
	 */
	public Boolean isInfinityNegative() {
		Boolean result = this.equals(InfinityNegative);
		return result;
	}

	/**
	 * Returns true if value is positive zero.
	 *
	 * @return True: if value is ZeroPositive.
	 */
	public Boolean isZeroPositive() {
		Boolean result = this.equals(ZeroPositive);
		return result;
	}

	/**
	 * Returns true if value is negative zero.
	 *
	 * @return True: if value is ZeroNegative.
	 */
	public Boolean isZeroNegative() {
		Boolean result = this.equals(ZeroNegative);
		return result;
	}

	/**
	 * <ul>
	 * 		<li>
	 * 			If z<sub>1</sub> and z<sub>2</sub> have the same sign, then return z<sub>1</sub> .
	 * 		</li> <li>
	 * 			Else return z<sub>1</sub> with negated sign.
	 * 		</li>
	 * </ul>
	 * <p>
	 * <p>
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
			result = neg(z1);
		}
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("F64{");
		sb.append("value=").append(value);

		if (value != null) {
			if (isNan()) {
				sb.append(" ");
				sb.append(nanPrint());
			}
			if (isInfinityPositive()) {
				sb.append(" Positive Infinite");
			}
			if (isInfinityNegative()) {
				sb.append(" Negative Infinite");
			}
			if (isZeroPositive()) {
				sb.append(" Positive Zero");
			}
			if (isZeroNegative()) {
				sb.append(" Negative Zero");
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

	public String nanPrint() {
		Long rawBits = Double.doubleToRawLongBits(value);
		if (rawBits.equals(NanCanonicalPos_Bits)) {
			return "Nan Positive Canonical";
		}
		if (rawBits.equals(NanCanonicalNeg_Bits)) {
			return "Nan Negative Canonical";
		}
		if (rawBits.equals(Nan0x4_0000_0000_0000Pos_Bits)) {
			return "Nan Positive 0x4_0000_0000_0000";
		}
		if (rawBits.equals(Nan0x4_0000_0000_0000Neg_Bits)) {
			return "Nan Negative 0x4_0000_0000_0000";
		}
		if (rawBits.equals(NanArithmeticPos_Bits)) {
			return "Nan Positive Arithmetic";
		}
		if (rawBits.equals(NanArithmeticNeg_Bits)) {
			return "Nan Negative Arithmetic";
		}
		return "Nan Unknown";
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		F64 f64 = (F64) o;

		Long valueRaw = Double.doubleToRawLongBits(value);
		Long otherRaw = Double.doubleToRawLongBits(f64.value);
		return valueRaw.equals(otherRaw);
		//		return value.equals(F64.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}
}
