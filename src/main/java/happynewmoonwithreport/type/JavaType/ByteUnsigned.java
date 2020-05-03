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
package happynewmoonwithreport.type.JavaType;

import static java.lang.Integer.compare;

/**
 * The {@code ByteUnsigned} class wraps a value of primitive type {@code byte} in an object.
 * <p>
 * This is a copy of the Java class {@code Byte},  the difference is the 8 bits are interpreted to
 * be unsigned 0 - 255 instead of -128 to 127.
 *
 * @author James Haring
 * @see java.lang.Number
 * @see java.lang.Byte
 * @since September 4, 2018
 */

public final class ByteUnsigned extends Number implements Comparable<ByteUnsigned> {

	// use int values because byte is signed and can not store 255 (0xFF).
	public static final Short MIN_VALUE = 0;
	public static final Short MAX_VALUE = 255;


	/**
	 * The value of the {@code Byte}.
	 *
	 * @serial
	 */
	private final short value;

	/**
	 * Constructs a newly allocated {@code ByteUnsigned} object that represents the specified
	 * {@code
	 * byte} value.
	 * <p>
	 * The input is interperted as an unsigned value (0-255).
	 * </p>
	 *
	 * @param value the value to be represented by the {@code ByteUnsigned}.
	 */
	public ByteUnsigned(Byte value) {
		if (0 <= value) {
			this.value = value;
		} else {
			this.value = (short) (0x80 | (value & 0x7F));
		}

	}

	/**
	 * Construct a {@code ByteUnsigend} from a {@code Byte}.  The input is interperted as a signed
	 * value.
	 *
	 * @param b a java Byte -128 to 127
	 * @return a Unsigned Byte.  0 to 127.  Negitive values are converted to zero.
	 */
	public static ByteUnsigned fromSignedByte(Byte b) {
		if (b < 0) {
			b = 0;
		}

		return new ByteUnsigned((short) b & 0x7F);
	}

	/**
	 * Construct a {@code ByteUnsigend} from a {@code Byte}.  The input is interperted as an
	 * unsigned value (0-255).
	 * <p>
	 * Consider using the constuctor {@code ByteUnsigned(Byte)} instead.
	 *
	 * @param b a Java Byte
	 * @return a Unsigned byte.  0-255.
	 */
	public static ByteUnsigned from_Un_SignedByte(Byte b) {
		return new ByteUnsigned(b);
	}

	/**
	 * Constructs a newly allocated {@code ByteUnsigned} object that represents the specified
	 * {@code
	 * byteUnsigned} value.
	 *
	 * @param value the value to be represented by the {@code Byte}.
	 */
	public ByteUnsigned(Integer value) {
		if (inRange(value)) {
			this.value = value.shortValue();
		} else {
			throw new NumberFormatException("Value out of range. Value = " + value);
		}
	}

	/**
	 * Constructs a newly allocated {@code ByteUnsigned} object that represents the specified
	 * {@code
	 * byteUnsigned} value.
	 *
	 * @param value the value to be represented by the {@code Byte}.
	 */
	public ByteUnsigned(Long value) {
		if (inRange(value)) {
			this.value = value.shortValue();
		} else {
			throw new NumberFormatException("Value out of range. Value = " + value);
		}
	}

	public static final ByteUnsigned byteUnsignedZero = new ByteUnsigned(0);

	/**
	 * Constructs a newly allocated {@code ByteUnsigned} object that represents the {@code byte}
	 * value indicated by the {@code String} parameter. The string is converted to a {@code int}
	 * value in exactly the manner used by the {@code parseByte} method for radix 10.
	 *
	 * @param s the {@code String} to be converted to a {@code ByteUnsigned}
	 * @throws NumberFormatException If the {@code String} does not contain a parsable {@code int}
	 *                               in the range ByteUnsigned.
	 * @see java.lang.Byte#parseByte(java.lang.String, int)
	 */
	public ByteUnsigned(String s) throws NumberFormatException {
		this(s, 10);
	}

	public ByteUnsigned(String s, Integer radix) throws NumberFormatException {
		Short prospect = Short.parseShort(s, radix);
		if (inRange(prospect)) {
			this.value = prospect;
		} else {
			throw new NumberFormatException("Value out of range. Value = " + s);
		}
	}

	/**
	 * Returns true if the input value is within the {@code MIN_VALUE} and {@code MAX_VALUE}
	 *
	 * @param valueToTest The value to test.
	 * @return true if  {@code @MIN_VALUE <= valueToTest <= @MAX_VALUE}
	 */
	public static Boolean inRange(Short valueToTest) {
		Boolean result = true;
		result &= (MIN_VALUE <= valueToTest);
		result &= (valueToTest <= MAX_VALUE);
		return result;
	}

	public static Boolean inRange(Integer valueToTest) {
		Boolean result = true;
		result &= (MIN_VALUE <= valueToTest);
		result &= (valueToTest <= MAX_VALUE);
		return result;
	}

	public static Boolean inRange(Long valueToTest) {
		Boolean result = true;
		result &= (MIN_VALUE <= valueToTest);
		result &= (valueToTest <= MAX_VALUE);
		return result;
	}

	public static Boolean inRange(Byte valueToTest) {
		Boolean result = true;
		result &= (MIN_VALUE <= valueToTest);
		result &= (valueToTest <= MAX_VALUE);
		return result;
	}


	/**
	 * Returns the value of this {@code Byte} as an {@code int} after a widening primitive
	 * conversion.
	 * <p>
	 * jls 5.1.2 Widening Primitive Conversions
	 */
	@Override
	public int intValue() {
		return (int) value;
	}

	/**
	 * Returns the value of this {@code Byte} as a {@code long} after a widening primitive
	 * conversion.
	 * <p>
	 * jls 5.1.2 Widening Primitive Conversions
	 */
	@Override
	public long longValue() {
		return (long) value;
	}

	/**
	 * Returns the value of this {@code Byte} as a {@code float} after a widening primitive
	 * conversion.
	 * <p>
	 * jls 5.1.2 Widening Primitive Conversions
	 */
	@Override
	public float floatValue() {
		return (float) value;
	}


	/**
	 * Returns the value of this {@code ByteUnsigned} as a {@code double} after a widening
	 * primitive
	 * conversion.
	 * <p>
	 * jls 5.1.2 Widening Primitive Conversions
	 */
	@Override
	public double doubleValue() {
		return (double) value;
	}

	public byte byteValue() {
		return (byte) (value & 0xFF);
	}

	/**
	 * shift bits to the right
	 *
	 * @return value shifted one bit to the right.
	 */
	public ByteUnsigned shiftRight() {
		byte myByte = byteValue();
		return new ByteUnsigned((myByte & 0xFF) >>> 1);

	}

	/**
	 * shift bits to the Left
	 *
	 * @return value shifted one bit to the left.
	 */
	public ByteUnsigned shiftLeft() {
		byte myByte = byteValue();
		int temp = (myByte & 0xFF) << 1;
		temp = (temp & 0xFF);
		return new ByteUnsigned(temp);
	}

	/**
	 * Is the high bit set on the byte?
	 *
	 * @return true if the high bit is set. false otherwise.
	 */
	public Boolean isSignBitSet() {
		Boolean result;
		result = 0 < (value & 0x80);
		return result;
	}


	/**
	 * Compares two {@code ByteUnsigned} objects numerically.
	 *
	 * @param anotherByte the {@code ByteUnsigned} to be compared.
	 * @return the value {@code 0} if this {@code ByteUnsigned} is equal to the argument {@code
	 * ByteUnsigned}; a value less than {@code 0} if this {@code ByteUnsigned} is numerically less
	 * than the argument {@code ByteUnsigned}; and a value greater than {@code 0} if this {@code
	 * ByteUnsigned} is numerically greater than the argument {@code ByteUnsigned} (signed
	 * comparison).
	 * @since 1.2
	 */
	public int compareTo(ByteUnsigned anotherByte) {
		return compare(this.value, anotherByte.value);
	}

	/**
	 * Returns a {@code String} object representing this {@code ByteUnsigend}'s value.  The
	 * value is
	 * converted to signed decimal representation and returned as a string, exactly as if the
	 * {@code
	 * byte} value were given as an argument to the {@link java.lang.Byte#toString(byte)} method.
	 *
	 * @return a string representation of the value of this object in base 10.
	 */
	@Override
	public String toString() {
		return Integer.toString((int) value) + "(0x" + Integer.toHexString((int) value)
															  .toUpperCase() + ")";
	}

	/**
	 * Returns a {@code String} object representing this {@code ByteUnsigend}'s value.  The
	 * value is
	 * converted to signed decimal representation and returned as a string, exactly as if the
	 * {@code
	 * byte} value were given as an argument to the {@link java.lang.Byte#toString(byte)} method.
	 *
	 * @param radix the base,  usually 10(decimal) or 16(hexidecimal)
	 * @return a string representation of the value of this object in base {@code radix}.
	 */
	public String toString(int radix) {
		return Integer.toString((int) value, radix);
	}

	/**
	 * Returns a hash code for this {@code ByteUnsigned}.
	 *
	 * @return a hash code value for this {@code ByteUnsigned}
	 */
	@Override
	public int hashCode() {
		return Short.hashCode(value);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ByteUnsigned that = (ByteUnsigned) o;

		return (value & 0xFF) == (that.value & 0xFF);
	}
}
