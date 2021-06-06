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
 * <br><a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html">
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

	// @formatter:off
	// 					   Name						  Bits						Integer32 value
	private static final Integer NanCanonicalPos_Bits 	= 0x7fc0_0000;			// 2143289344
	private static final Integer NanCanonicalNeg_Bits 	= 0xffc0_0000;			// -4194304
	private static final Integer NanPos_Bits  	       	= NanCanonicalPos_Bits;
	private static final Integer NanNeg_Bits      	   	= NanCanonicalNeg_Bits;
	private static final Integer Nan0x20_0000Pos_Bits 	= 0x7fa0_0000;			// 2141192192
	private static final Integer Nan0x20_0000Neg_Bits 	= 0xffa0_0000;			// -6291456
	private static final Integer NanArithmeticPos_Bits	= 0x7fff_ffff;			// 2147483647
	private static final Integer NanArithmeticNeg_Bits	= 0xffff_ffff;			// -1
	private static final Integer InfinityPos_Bits	    = 0x7f80_0000;			// 2139095040
	private static final Integer InfinityNeg_Bits	    = 0x8f80_0000;			// -1887436800
	// @formatter:on


	public static final F32 ZeroPositive = new F32(0.0F);
	// Java stores a negative zero correctly,  Groovy/Spock has issues.
	public static final F32 ZeroNegative = new F32(-0.0F);

	/**
	 * Infinity Positive
	 */
	public static final F32 InfinityPositive = new F32(Float.POSITIVE_INFINITY);

	/**
	 * Infinity Negative
	 */
	public static final F32 InfinityNegative = new F32(Float.NEGATIVE_INFINITY);

	/**
	 * Not a Number
	 * <p>
	 * NaN is equivalent to the value returned by Float.intBitsToFloat(0x7fc00000).
	 * <p>
	 * <b>Source:</b><p>
	 * <a href="https://docs.oracle.com/javase/7/docs/api/java/lang/Float.html#NaN" target="_top">
	 * https://docs.oracle.com/javase/7/docs/api/java/lang/Float.html#NaN
	 * </a>
	 */
	public static final F32 Nan = new F32(Float.intBitsToFloat(NanCanonicalPos_Bits));

	/**
	 * Not a Number in Canonical form.
	 * <p>
	 * NaN is equivalent to the value returned by Float.intBitsToFloat(0x7fc00000).
	 * <p>
	 * <b>Source:</b><p>
	 * <a href="https://docs.oracle.com/javase/7/docs/api/java/lang/Float.html#NaN" target="_top">
	 * https://docs.oracle.com/javase/7/docs/api/java/lang/Float.html#NaN
	 * </a>
	 */
	public static final F32 NanCanonicalPos = F32.Nan;


	public static final F32 NanCanonicalNeg = new F32(Float.intBitsToFloat(NanCanonicalNeg_Bits));
	public static final F32 NanNeg = new F32(Float.intBitsToFloat(NanNeg_Bits));
	public static final F32 Nan0x20_0000Pos = new F32(Float.intBitsToFloat(Nan0x20_0000Pos_Bits));
	public static final F32 Nan0x20_0000Neg = new F32(Float.intBitsToFloat(Nan0x20_0000Neg_Bits));
	public static final F32 NanArithmeticPos = new F32(Float.intBitsToFloat(NanArithmeticPos_Bits));
	public static final F32 NanArithmeticNeg = new F32(Float.intBitsToFloat(NanArithmeticNeg_Bits));


	// @formatter:off
	//  #Canociacal /  Arithmetic
	//  https://webassembly.github.io/spec/core/syntax/values.html#canonical-nan
	//					Sign	Exponent 	fraction  / payload				binary										String
	//  nan:canonical	x		1111_1111   100_0000_0000_0000_0000_0000	0b_x111_1111_1100_0000_0000_0000_0000_0000	+/- nan:0x400000
	//  nan:arithmetic  x		1111_1111   1xx_xxxx_xxxx_xxxx_xxxx_xxxx	0b_x111_1111_11xx_xxxx_xxxx_xxxx_xxxx_xxxx  nan:arithmetic
	//  x = don't care, 0 or 1.

	// Quite Nan
	// # nan:0x200000 and "-nan:0x200000"
	//  "-nan:0x200000"
	//  0x_0200_0000  is 0b_0010_0000_0000_0000_0000_0000
	//					Sign	Exponent 	fraction  / payload				binary										String       	Hex (f32)
	//  nan:0x200000    x		1111_1111   010_0000_0000_0000_0000_0000	0b_1111_1111_1010_0000_0000_0000_0000_0000	+nan:0x200000	0x7fa00000


	// # Quite Bit
	// The second most significant bit of the significand field is the is_quiet bit.
	// 0b_0010_0000_0000_0000_0000_0000    0x20_0000
	//
	//
	// IEEE 754 - 2008 standard See: https://en.wikipedia.org/wiki/NaN
	// For binary formats, the second most significant bit of the significand field should be an
	// is_quiet flag. That is, this bit is
	// non-zero if the NaN is quiet,
	// and
	// zero if the NaN is signaling.
	//
	// WASM states it uses IEEE 754 - 2019.  So the 2008 should also hold 2019.
	// @formatter:on


	public F32() {
		this.value = 0F;
	}

	public F32(Float value) {
		this.value = value;
	}


	// Groovy/Spock need the copy constructor.

	/**
	 * Copy Constructor.
	 *
	 * @param input value to copy
	 */
	public F32(F32 input) {
		this.value = input.value;
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
		F32 val;

		switch (s) {
			case ("-inf"):
				val = F32.InfinityNegative;
				break;
			case ("inf"):
				val = F32.InfinityPositive;
				break;
			case ("nan"):
			case ("nan:canonical"):
				val = F32.Nan;
				break;
			case ("-nan"):
			case ("-nan:canonical"):
				val = F32.NanNeg;
				break;
			case ("nan:0x200000"):
				val = F32.Nan0x20_0000Pos;
				break;
			case ("-nan:0x200000"):
				val = F32.Nan0x20_0000Neg;
				break;
			case ("nan:arithmetic"):
				val = F32.NanArithmeticPos;
				break;
			case ("-nan:arithmetic"):
				val = F32.NanArithmeticNeg;
				break;
			// I think 0x400000 is for F64 only.
			//	case ("nan:0x400000"):
			//		val = F32.Nan;
			//		break;
			//	case ("-nan:0x400000"):
			//		val = F32.NanNeg;
			//		break;
			default:
				val = new F32(Float.valueOf(s));
		}
		return val;
	}

	/**
	 * Create a F32 instance given a {@code Float}.
	 * <p>
	 * Does not handle -0F. To be more precise the object Float does not handle
	 * -0F.
	 * Use {@code F32.NEGATIVE_ZERO}  or {@code F32.valueOf(String)} instead.
	 *
	 * @param input value to store in the new object.
	 * @return an F32 set to the input value.
	 */
	public static F32 valueOf(Float input) {
		return new F32(input);
	}

	/**
	 * Create a F32 instance given a {@code Double}.
	 * <p>
	 * Does not handle -0F. To be more precise the object Float does not handle -0F.
	 * Use {@code F32.NEGATIVE_ZERO}  or {@code F32.valueOf(String)} instead.
	 * <p>
	 * Warning this may lose digits.
	 *
	 * @param input value to store in the new object.
	 * @return an F32 set to the input value.
	 */
	public static F32 valueOf(Double input) {
		return new F32(input.floatValue());
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
	 * <br>
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
	 * <br>
	 * <b>See:</b>
	 * <br>
	 * <a href="https://www.h-schmidt.net/FloatConverter/IEEE754.html" target="_top">
	 * IEEE-754 Floating Point Converter
	 * </a>
	 * <br>
	 * <a href="http://weitz.de/ieee/" target="_top">
	 * IEEE-754 Floating Point Calculator.  Works for 32 and 64 bits.
	 * </a>
	 * <br>
	 * The complement is F32(ByteUnsigned []) constructor.
	 * <br>
	 * <b>Java implementation</b>
	 * This uses <code>Float.floatToRawIntBits(float)</code> to covert to an <code>Integer</code>.
	 *
	 * @return an array of ByteUnsigned
	 */
	@Override
	public ByteUnsigned[] getBytes() {
		Integer bits = Float.floatToRawIntBits(value);

		// Integer to ByteUnsigned array
		return getByteUnsigned(bits);
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
	 * <br>
	 * <b>See:</b>
	 * <br>
	 * <a href="https://www.h-schmidt.net/FloatConverter/IEEE754.html" target="_top">
	 * IEEE-754 Floating Point Converter
	 * </a>
	 * <br>
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
	public static F32 convert(BytesFile bytesFile) {
		Integer valueInteger;
		// Little Endian
		valueInteger = (((int) bytesFile.readByte() & 0xFF) << 0); // Least Significant Byte
		valueInteger += (((int) bytesFile.readByte() & 0xFF) << 8);
		valueInteger += (((int) bytesFile.readByte() & 0xFF) << 16);
		valueInteger += (((int) bytesFile.readByte() & 0xFF) << 24); // Most Significant Byte

		return new F32(Float.intBitsToFloat(valueInteger));
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
	 * It is equivalent to the value returned by Float.intBitsToFloat(0x7fc00000).
	 * <p>
	 * <p>
	 * A canonical NaN is a floating-point value ±nan(canonN) where canonN is a payload whose most significant bit is
	 * 1 while all others are 0:
	 * <p>
	 * <b>Source:</b>
	 * <a href="https://webassembly.github.io/spec/core/syntax/values.html#canonical-nan" target="_top">
	 * https://webassembly.github.io/spec/core/syntax/values.html#canonical-nan
	 * </a>
	 *
	 * @return True if value in bits is 0x7fc0_0000 or 0xffc0_0000.
	 */
	public Boolean isNanCanonical() {
		Boolean result = false;
		result |= Float.floatToRawIntBits(value) == NanCanonicalPos_Bits;
		result |= Float.floatToRawIntBits(value) == NanCanonicalNeg_Bits;
		return result;
	}


	/**
	 * Calculate the Absolute value according to the Wasm Specification.
	 * <pre>F32 -> F32</pre>
	 *
	 * <h2>Source:</h2>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-fabs" target="_top">
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
		// Wasm manual states No Nan Propagation on Absolute Value.

		Float z = value;
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
			return F32.InfinityPositive;
		}
		// if z is a zero, then return positive zero.
		if (z == 0F || z == -0F) {
			return F32.ZeroPositive;
		}
		// Else if z is a positive value, then z.
		if (0F < z) {
			return this;
		} else {
			// Else return z negated.
			return negate();
		}
	}

	public static F32 negWasm(F32 z1) {
		return z1.negWasm();
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
		if (z.isNaN()) {
			return negate();
		}
		// if z is an infinity, then return that infinity negated.
		if (z.equals(Float.POSITIVE_INFINITY)) {
			return F32.InfinityNegative;
		}
		if (z.equals(Float.NEGATIVE_INFINITY)) {
			return F32.InfinityPositive;
		}
		// if z is a zero, then return that zero negated.
		if (Float.floatToRawIntBits(z) == F32.ZeroNegative.toBits()) {
			return F32.ZeroPositive;
		}
		if (Float.floatToRawIntBits(z) == F32.ZeroPositive.toBits()) {
			return F32.ZeroNegative;
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
	public F32 negate() {
		Integer rawBits = Float.floatToRawIntBits(value);

		// toggle the sign bit
		rawBits = 0x8000_0000 ^ rawBits;

		Float result = Float.intBitsToFloat(rawBits);

		return new F32(result);
	}

	/**
	 * Calculate the Ceiling value according to the Wasm Specification.
	 * <pre>F32 -> F32</pre>
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
	 * Else if z is smaller than 0 but greater than −1, then return negative zero.
	 * </li><li>
	 * Else return the smallest integral value that is not smaller than z.
	 * </ul>
	 *
	 * @return the ceiling of the input value
	 */
	public F32 ceilWasm() {
		Float z = value;

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
		// Else if z is smaller than 0 but greater than −1, then return negative zero.
		if (-1f < z && z < 0f) {
			return ZeroNegative;
		}
		// Else return the smallest integral value that is not smaller than z.
		double ceil = Math.ceil(z);
		return F32.valueOf(ceil);
	}

	/**
	 * Calculate the Floor value according to the Wasm Specification.
	 * <pre>F32 -> F32</pre>
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
	public F32 floorWasm() {
		Float z = value;

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
		return F32.valueOf(floor);
	}

	/**
	 * Calculate the Nearest value according to the Wasm Specification.
	 * <pre>F32 -> F32</pre>
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
	 * Else if z is smaller than 0 but greater than or equal to −0.5, then return negative zero.
	 * </li><li>
	 * Else return the integral value that is nearest to z; if two values are equally near, return
	 * the even one.
	 * </ul>
	 *
	 * @return the Nearest of the input value
	 */
	public F32 nearestWasm() {
		Float z = value;

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
		// Else if z is smaller than 0 but greater than or equal to −0.5, then return negative
		// zero.
		if (-0.5F <= z && z < 0F) {
			return ZeroNegative;
		}
		// Else return the integral value that is nearest to z; if two values are equally near,
		// return the even one.
		double nearest = Math.rint(z);
		return F32.valueOf(nearest);
	}

	/**
	 * Calculate the Truncated value according to the Wasm Specification.
	 * <pre>F32 -> F32</pre>
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
	 * Else if z is greater than 0 but smaller than 1, then return positive zero.
	 * </li><li>
	 * Else if z is smaller than 0 but greater than −1, then return negative zero.
	 * </li><li>
	 * Else return the integral value with the same sign as z and the largest magnitude that is
	 * not larger than the magnitude of z.
	 * </ul>
	 *
	 * @return the Truncated of the input value
	 */
	public F32 trunkWasm() {
		Float z = value;

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
		// Else if z is smaller than 0 but greater than −1, then return negative zero.
		if (-1F < z && z < 0F) {
			return ZeroNegative;
		}
		// Else return the smallest integral value that is not smaller than z.
		Float trunk = truncate(z);
		return F32.valueOf(trunk);
	}

	static Float truncate(float value) {
		// Source: https://www.dotnetperls.com/double-truncate-java
		// For negative numbers, use Math.ceil.
		// For positive numbers, use Math.floor.
		if (value < 0F) {
			return (float) Math.ceil(value);
		} else {
			return (float) Math.floor(value);
		}
	}

	/**
	 * Calculate the Square Root according to the Wasm Specification.
	 * <pre>F32 -> F32</pre>
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
	public F32 sqrtWasm() {
		Float z = value;

		//if z is a NaN, then return an element of nansN{z}
		if (z.isNaN()) {
			return nanPropagation(this);
		}
		// Else if z is negative infinity, then return an element of nansN{}.
		if (isNegative() && z.isInfinite()) {
			return Nan;
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
			return Nan;
		}
		// Else return the square root of z.
		double sqrt = Math.sqrt(z);
		return F32.valueOf(sqrt);
	}

	/**
	 * Calculate Addition according to the Wasm Specification.
	 * <pre>F32 F32 -> F32</pre>
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
	public static F32 addWasm(F32 z1, F32 z2) {

		//	If either z1 or z2 is a NaN, then return an element of nansN{z1, z2}.
		if (z1.isNan() || z2.isNan()) {
			return nanPropagation(z1, z2);
		}
		// Else if both z1 and z2 are infinities of opposite signs, then return an element of nansN{}.
		if (isInfinityOfOppositeSign(z1, z2)) {
			return nanPropagation();
		}

		// Else if both z1 and z2 are infinities of equal sign, then return that infinity.
		if (isInfinityOfOppositeSign(z1, z2)) {
			return InfinityPositive;
		}

		// Else if one of z1 or z2 is an infinity, then return that infinity.
		if (isAnyInfinity(z1)) {
			return z1;
		}
		if (isAnyInfinity(z2)) {
			return z2;
		}

		// Else if both z1 and z2 are zeroes of opposite sign, then return positive zero.
		if (isZeroOfOppositeSign(z1, z2)) {
			return ZeroPositive;
		}

		// Else if both z1 and z2 are zeroes of equal sign, then return that zero.
		if (isZeroOfEqualSign(z1, z2)) {
			return ZeroPositive;
		}

		// Else if one of z1 or z2 is a zero, then return the other operand.
		if (isAnyZero(z1)) {
			return z2;
		}
		if (isAnyZero(z2)) {
			return z1;
		}

		// Else if both z1 and z2 are values with the same magnitude but opposite signs, then return positive zero.

		// Else return the result of adding z1 and z2, rounded to the nearest representable value.
		float add = z1.value + z2.value;
		return F32.valueOf(add);
	}

	public F32 addWasm(F32 other) {
		return addWasm(this, other);
	}

	/**
	 * Calculate subtraction according to the Wasm Specification.
	 * <pre>F32 F32 -> F32</pre>
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
	public static F32 subWasm(F32 z1, F32 z2) {

		//	If either z1 or z2 is a NaN, then return an element of nansN{z1, z2}.
		if (z1.isNan() || z2.isNan()) {
			return nanPropagation(z1, z2);
		}
		// 2 Else if both z1 and z2 are infinities of equal signs, then return an element of nansN{}.
		if (isInfinityOfEqualSign(z1, z2)) {
			return nanPropagation();
		}

		// 3 Else if both z1 and z2 are infinities of opposite sign, then return z1.
		if (isInfinityOfOppositeSign(z1, z2)) {
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
		if (isZeroOfEqualSign(z1, z2)) {
			return ZeroPositive;
		}

		// 7 Else if both z1 and z2 are zeroes of opposite sign, then return z1.
		if (isZeroOfOppositeSign(z1, z2)) {
			return z1;
		}

		// 8. Else if z2 is a zero, then return z1.
		if (isAnyZero(z2)) {
			return z1;
		}

		// 9 Else if z1 is a zero, then return z2 negated.
		if (isAnyZero(z1)) {
			return z2.negate();
		}

		// 10 Else if both z1 and z2 are the same value, then return positive zero.
		if (equalsWasm(z1, z2) == I32.True) {
			return ZeroPositive;
		}

		// 11 Else return the result of subtracting z2 from z1, rounded to the nearest representable value.
		float subtract = z1.value - z2.value;
		return F32.valueOf(subtract);
	}

	private static Boolean isInfinityOfEqualSign(F32 z1, F32 z2) {
		Boolean result = false;
		result |= (z1 == InfinityPositive && z2 == InfinityPositive);
		result |= (z1 == InfinityNegative && z2 == InfinityNegative);
		return result;
	}

	private static Boolean isInfinityOfOppositeSign(F32 z1, F32 z2) {
		Boolean result = false;
		result |= (z1 == InfinityPositive && z2 == InfinityNegative);
		result |= (z1 == InfinityNegative && z2 == InfinityPositive);
		return result;
	}

	private static Boolean isAnyInfinity(F32... values) {
		Boolean result = false;
		for (F32 value : values) {
			result |= value == InfinityPositive;
			result |= value == InfinityNegative;
		}
		return result;
	}

	private static Boolean isZeroOfEqualSign(F32 z1, F32 z2) {
		Boolean result = false;
		result |= (z1 == ZeroPositive && z2 == ZeroPositive);
		result |= (z1 == ZeroNegative && z2 == ZeroNegative);
		return result;
	}

	private static Boolean isZeroOfOppositeSign(F32 z1, F32 z2) {
		Boolean result = false;
		result |= (z1 == ZeroPositive && z2 == ZeroNegative);
		result |= (z1 == ZeroNegative && z2 == ZeroPositive);
		return result;
	}

	private static Boolean isAnyZero(F32... values) {
		Boolean result = false;
		for (F32 value : values) {
			result |= value == ZeroPositive;
			result |= value == ZeroNegative;
		}
		return result;
	}


	public F32 subWasm(F32 other) {
		return subWasm(this, other);
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
	 * @param inputArray any number of F32, may or may not be NaN.
	 */
	public static F32 nanPropagation(F32... inputArray) {
		for (F32 val : inputArray) {
			if (val.isNan() && val.isNanCanonical() == false) {
				// Otherwise the payload is picked non-deterministically among all arithmetic NaNs; that is, its
				// most significant bit is 1 and all others are unspecified.

				// Note: the Square Root and other WASM unit test require to return NanArithmetic in this case.
				// This does not align with the documentation that states it should be 'Non-deterministically'.
				// Who knows?  This is pretty deep in to the specification.
				return F32.NanArithmeticPos;
			}
		}
		// If the payload of all NaN inputs to the operator is canonical (including the case that there	are no NaN
		// inputs), then the payload of the output is canonical as well.
		return F32.NanCanonicalPos;
	}


	/**
	 * Convert to the bits.  This is Raw conversion.   Nan Values are not converted to Canonical Nan.
	 *
	 * @return Integer representation of the bits of F32.
	 */
	public Integer toBits() {
		return Float.floatToRawIntBits(value);
	}


	/**
	 * Equals according to the Wasm specification.
	 * <pre>F32 F32 -> I32</pre>
	 * <p>
	 * Source: <br>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#xref-exec-numerics-op-feq-mathrm-feq-n-z-1
	 * -z-2" target="_top">
	 * Numerics equals
	 * </a><br>
	 * If either z<sub>1</sub> or z<sub>2</sub> is a NaN, then return 0<br>
	 * Else if both z<sub>1</sub> and z<sub>2</sub> are zeroes, then return 1<br>
	 * Else if both z<sub>1</sub> and z<sub>2</sub> are the same value, then return 1<br>
	 * Else return 0.<br>
	 *
	 * @param other z<sub>2</sub> the second or right side value of an equals statement.
	 * @return 1 if equal otherwise 0 <code>z<sub>1</sub> == z<sub>2</sub> </code>
	 */
	public I32 equalsWasm(F32 other) {
		return equalsWasm(this, other);
	}

	/**
	 * Equals according to the Wasm specification.
	 * <pre>F32 F32 -> I32</pre>
	 * <p>
	 * Source: <br>
	 * @formatter:off
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#xref-exec-numerics-op-feq-mathrm-feq-n-z-1-z-2" target="_top">
	 * @formatter:on
	 * Numerics equals
	 * </a><br>
	 * If either z<sub>1</sub> or z<sub>2</sub> is a NaN, then return 0<br>
	 * Else if both z<sub>1</sub> and z<sub>2</sub> are zeroes, then return 1<br>
	 * Else if both z<sub>1</sub> and z<sub>2</sub> are the same value, then return 1<br>
	 * Else return 0.<br>
	 *
	 * @param z1 The left side of the equals
	 * @param z2 The right side of the equals
	 * @return
	 * <code>z<sub>1</sub> == z<sub>2</sub> </code><br>
	 * 1: if equal <br>
	 * 0: otherwise  <br>
	 */
	public static I32 equalsWasm(F32 z1, F32 z2) {
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
	public I32 greaterThanEqualWasm(F32 other) {
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
	 *  1 If either z<sub>1</sub> or z<sub>2</sub> is a NaN, then return 0
	 *  2 Else if z<sub>1</sub> and z<sub>2</sub> are the same value, then return 1
	 *  3 Else if z<sub>1</sub> is positive infinity, then return 1
	 *  4 Else if z<sub>1</sub> is negative infinity, then return 0
	 *  5 Else if z<sub>2</sub> is positive infinity, then return 0
	 *  6 Else if z<sub>2</sub> is negative infinity, then return 1
	 *  7 Else if both z<sub>1</sub> and z<sub>2</sub> are zeroes, then return 1
	 *  8 Else if z<sub>1</sub> is larger than z<sub>2</sub> , then return 1
	 *  9 Else return 0
	 * </pre>
	 *
	 * @param z1 the left number.
	 * @param z2 the right number.
	 * @return 1 if z<sub>1</sub> greater than z<sub>2</sub> otherwise 0.   z<sub>1</sub> > z<sub>2</sub>
	 */
	public static I32 greaterThanEqualWasm(F32 z1, F32 z2) {

		// 1 If either z1 or z2 is a NaN, then return 0<br>
		if (z1.value.isNaN() || z2.value.isNaN()) {
			return I32.zero;
		}
		// 2 Else if z1 and z2 are the same value, then return 0
		if (z1.equals(z2)) {
			return I32.one;
		}
		// 3 Else if z1 is positive infinity, then return 1
		if (z1.equals(F32.InfinityPositive)) {
			return I32.one;
		}
		// 4 Else if z1 is negative infinity, then return 0
		if (z1.equals(F32.InfinityNegative)) {
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
	 * @param other z2 the right number.
	 * @return 1 if greater than otherwise 0
	 */
	public I32 greaterThanWasm(F32 other) {
		return greaterThanWasm(this, other);
	}

	/**
	 * Greater Than  according to the Wasm specification.
	 * <pre>F32 F32 -> I32</pre>
	 * <p>
	 * <b>Source:</b>
	 * <br>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-fgt" target="_top">
	 * Numerics greater Than Wasm Specification.
	 * </a>
	 * <br>
	 * <pre>
	 *  1 If either z<sub>1</sub> or z<sub>2</sub> is a NaN, then return 0
	 *  2 Else if z<sub>1</sub> and z<sub>2</sub> are the same value, then return 0
	 *  3 Else if z<sub>1</sub> is positive infinity, then return 1
	 *  4 Else if z<sub>1</sub> is negative infinity, then return 0
	 *  5 Else if z<sub>2</sub> is positive infinity, then return 0
	 *  6 Else if z<sub>2</sub> is negative infinity, then return 1
	 *  7 Else if both z<sub>1</sub> and z<sub>2</sub> are zeroes, then return 0
	 *  8 Else if z<sub>1</sub> is larger than z<sub>2</sub> , then return 1
	 *  9 Else return 0
	 * </pre>
	 *
	 * @param z1 the left number.
	 * @param z2 the right number.
	 * @return 1 if z<sub>1</sub> greater than z<sub>2</sub> otherwise 0.   z<sub>1</sub> > z<sub>2</sub>
	 */
	public static I32 greaterThanWasm(F32 z1, F32 z2) {

		// 1 If either z1 or z2 is a NaN, then return 0<br>
		if (z1.value.isNaN() || z2.value.isNaN()) {
			return I32.zero;
		}
		// 2 Else if z1 and z2 are the same value, then return 0
		if (z1.equals(z2)) {
			return I32.zero;
		}
		// 3 Else if z1 is positive infinity, then return 1
		if (z1.equals(F32.InfinityPositive)) {
			return I32.one;
		}
		// 4 Else if z1 is negative infinity, then return 0
		if (z1.equals(F32.InfinityNegative)) {
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
	 * <pre>F32 F32 -> I32</pre>
	 * <p>
	 * <b>Source:</b>  <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-flt" target="_top">
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
	 * <b>Source:</b>  <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-fle" target="_top">
	 * Numerics Less Than or Equal Wasm Specification.
	 * </a>
	 * <p>
	 * <br>
	 * <pre>
	 * 1 If either z<sub>1</sub> or z<sub>2</sub> is a NaN, then return 0
	 * 2 Else if z<sub>1</sub> and z<sub>2</sub> are the same value, then return 1
	 * 3 Else if z<sub>1</sub> is positive infinity, then return 0
	 * 4 Else if z<sub>1</sub> is negative infinity, then return 1
	 * 5 Else if z<sub>2</sub> is positive infinity, then return 1
	 * 6 Else if z<sub>2</sub> is negative infinity, then return 0
	 * 7 Else if both z<sub>1</sub> and z<sub>2</sub> are zeroes, then return 1
	 * 8 Else if z<sub>1</sub> is smaller than or equal  z<sub>2</sub> , then return 1
	 * 9 Else return 0
	 * </pre>
	 *
	 * @param z1 the left number.
	 * @param z2 the right number.
	 * @return 1 if z<sub>1</sub> less than or equal z<sub>2</sub> otherwise 0.   z<sub>1</sub> <= z<sub>2</sub>
	 */
	public static I32 lessThanEqualWasm(F32 z1, F32 z2) {

		// 1 If either z1 or z2 is a NaN, then return 0<br>
		if (z1.value.isNaN() || z2.value.isNaN()) {
			return I32.zero;
		}
		// 2 Else if z1 and z2 are the same value, then return 1
		if (z1.equals(z2)) {
			return I32.one;
		}
		// 3 Else if z1 is positive infinity, then return 0
		if (z1.equals(F32.InfinityPositive)) {
			return I32.zero;
		}
		// 4 Else if z1 is negative infinity, then return 1
		if (z1.equals(F32.InfinityNegative)) {
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
	 * @param other Z<sub>2</sub>  The right side
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
	 * 1 If either z<sub>1</sub> or z<sub>2</sub> is a NaN, then return 0
	 * 2 Else if z<sub>1</sub> and z<sub>2</sub> are the same value, then return 0
	 * 3 Else if z<sub>1</sub> is positive infinity, then return 0
	 * 4 Else if z<sub>1</sub> is negative infinity, then return 1
	 * 5 Else if z<sub>2</sub> is positive infinity, then return 1
	 * 6 Else if z<sub>2</sub> is negative infinity, then return 0
	 * 7 Else if both z<sub>1</sub> and z<sub>2</sub> are zeroes, then return 0
	 * 8 Else if z<sub>1</sub> is smaller than z<sub>2</sub> , then return 1
	 * 9 Else return 0
	 * </pre>
	 *
	 * @param z1 the left number.
	 * @param z2 the right number.
	 * @return 1 if z<sub>1</sub> less than z<sub>2</sub> otherwise 0.   z<sub>1</sub> < z<sub>2</sub>
	 */
	public static I32 lessThanWasm(F32 z1, F32 z2) {

		// 1 If either z1 or z2 is a NaN, then return 0<br>
		if (z1.value.isNaN() || z2.value.isNaN()) {
			return I32.zero;
		}
		// 2 Else if z1 and z2 are the same value, then return 0
		if (z1.equals(z2)) {
			return I32.zero;
		}
		// 3 Else if z1 is positive infinity, then return 0
		if (z1.equals(F32.InfinityPositive)) {
			return I32.zero;
		}
		// 4 Else if z1 is negative infinity, then return 1
		if (z1.equals(F32.InfinityNegative)) {
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

	public F32 copysign(F32 z2) {
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
		Integer bits = Float.floatToRawIntBits(value);
		Integer mask = bits & 0x8000_0000;
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
		result |= Float.floatToRawIntBits(this.value) == Float.floatToRawIntBits(F32.ZeroNegative.value);
		result |= Float.floatToRawIntBits(this.value) == Float.floatToRawIntBits(F32.ZeroPositive.value);
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
	public static F32 copysign(F32 z1, F32 z2) {
		F32 result;

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
		final StringBuilder sb = new StringBuilder("F32{");
		sb.append("value=").append(value);

		if (value != null) {
			if (value.isNaN()) {
				sb.append(" ");
				sb.append(nanPrint());
			}
			if (value.isInfinite()) {
				sb.append(" Infinite");
			}

			Integer bits = Float.floatToRawIntBits(value);
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

	public String nanPrint() {
		Integer rawBits = Float.floatToRawIntBits(value);
		if (rawBits.equals(NanCanonicalPos_Bits)) {
			return "Nan Positive Canonical";
		}
		if (rawBits.equals(NanCanonicalNeg_Bits)) {
			return "Nan Negative Canonical";
		}
		if (rawBits.equals(Nan0x20_0000Pos_Bits)) {
			return "Nan Positive 0x20_0000";
		}
		if (rawBits.equals(Nan0x20_0000Neg_Bits)) {
			return "Nan Negative 0x20_0000";
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

		F32 f32 = (F32) o;

		Integer valueRaw = Float.floatToRawIntBits(value);
		Integer otherRaw = Float.floatToRawIntBits(f32.value);
		return valueRaw.equals(otherRaw);
		//		return value.equals(f32.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}
}
