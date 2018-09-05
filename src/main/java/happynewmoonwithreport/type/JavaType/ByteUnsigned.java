/*
 *  Copyright 2018 Whole Bean Software, LTD.
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

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * The {@code ByteUnsigned} class wraps a value of primitive type {@code byte}
 * in an object.
 * <p>
 * This is a copy of the Java class {@code Byte},  the differene is the 8 bits are
 * interperted to be unsigned 0 - 255 instead of -128 to 127.
 *
 * @author James Haring
 * @see java.lang.Number
 * @see java.lang.Byte
 * @since September 4, 2018
 */

public final class ByteUnsigned extends Number
		//  TODO: implements Comparable<ByteUnsigned>
{

	// use int values because byte is signed and can not store 255 (0xFF).
	public static final int MIN_VALUE = 0;
	public static final int MAX_VALUE = 255;


	/**
	 * The value of the {@code Byte}.
	 *
	 * @serial
	 */
	private final short value;

	/**
	 * Constructs a newly allocated {@code Byte} object that
	 * represents the specified {@code byte} value.
	 *
	 * @param value the value to be represented by the
	 * {@code Byte}.
	 */
	public ByteUnsigned(byte value) {
		this.value = value;
	}


	/**
	 * Constructs a newly allocated {@code ByteUnsigned} object that
	 * represents the {@code byte} value indicated by the
	 * {@code String} parameter. The string is converted to a
	 * {@code int} value in exactly the manner used by the
	 * {@code parseByte} method for radix 10.
	 *
	 * @param s the {@code String} to be converted to a
	 * {@code ByteUnsigned}
	 *
	 * @throws NumberFormatException If the {@code String}
	 * does not contain a parsable {@code int} in the range ByteUnsigned.
	 * @see java.lang.Byte#parseByte(java.lang.String, int)
	 */
	public ByteUnsigned(String s) throws NumberFormatException {
		this.value = Byte.parseByte(s, 10);
	}

	/**
	 * Returns the value of this {@code ByteUnsigned} as a {@code double}
	 * after a widening primitive conversion.
	 *
	 * @jls 5.1.2 Widening Primitive Conversions
	 */
	@Override
	public double doubleValue() {
		throw new NotImplementedException();
		//return (double)value;
	}

	/**
	 * Returns the value of this {@code Byte} as an {@code int} after
	 * a widening primitive conversion.
	 *
	 * @jls 5.1.2 Widening Primitive Conversions
	 */
	@Override
	public int intValue() {
		return (int) value;
	}

	/**
	 * Returns the value of this {@code Byte} as a {@code long} after
	 * a widening primitive conversion.
	 *
	 * @jls 5.1.2 Widening Primitive Conversions
	 */
	@Override
	public long longValue() {
		throw new NotImplementedException();
		//		return (long)value;
	}

	/**
	 * Returns the value of this {@code Byte} as a {@code float} after
	 * a widening primitive conversion.
	 *
	 * @jls 5.1.2 Widening Primitive Conversions
	 */
	@Override
	public float floatValue() {
		throw new NotImplementedException();
		//return (float) value;
	}


}
