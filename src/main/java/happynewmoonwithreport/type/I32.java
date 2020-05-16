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

package happynewmoonwithreport.type;

import java.util.UUID;

import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.type.JavaType.ByteUnsigned;


/**
 * Signed Integer 32 Bits.
 *
 * <table>
 * <tr>
 * <td>High Byte</td><td>Byte</td><td>Byte</td><td>Low Byte</td>
 * </tr>
 * <tr>
 * <td>0</td><td>1</td><td>2</td><td>3</td>
 * </tr>
 * <tr>	<td>Msb000000</td><td>00000000</td><td>00000000</td><td>0000000Lsb</td>
 * </tr>
 * </table>
 * <p>
 * Range:  -2<sup>N-1</sup>| ... | -1 | 0 | 1 | ... 2<sup>N-1 </sup> -1
 * <p>
 * When N = 32
 * <p>
 * - ‭2,147,483,648‬ to 2,147,483,647
 */
public class I32 extends IntWasm {

	protected Integer value;

	public I32() {
		value = 0;
	}

	public I32(Integer value) {
		this.value = value;
	}

	/**
	 * Create a I32 given a long value.
	 *
	 * @param value Value to store in I32
	 * @throws RuntimeException if value is outside the range of an Integer.   @TODO validate if
	 *                          this is a good ideal.
	 */
	public I32(Long value) {
		this();
		if (isBoundByInteger(value) == false) {
			throw new WasmRuntimeException(UUID.fromString("62298944-804a-430e-b645-7bda0ecab265"),
				"Input value to I32(Long) is out of bounds.  Value not bound by integer. Value = "
				+ value + " (" + toHex(value) + ")",
				"Possible Solutions: Use I32(Integer) instead.  The input for this function is "
				+ "'Long'. Maybe you only need and input of 'Int'? Ex: for I32(0xFFFF_FFFFL); use "
				+ "instead I32(0xFFFF_FFFF); .");
		}
		this.value = value.intValue();
	}


	/**
	 * Create an I32 with an array of four UnsignedBytes.  Bytes are interpreted as unsigned.
	 *
	 * @param byteAll <b>Little Endian</b> an array of 4 UnsignedBytes
	 *                <p>
	 *                <p>
	 *                UnsignedByte[0] is the Most Significant Byte
	 *                <p>
	 *                UnsignedByte[3] is the Least Significant Byte
	 */
	public I32(ByteUnsigned[] byteAll) {
		this();
		Boolean signExtension = false;
		create(byteAll, 32, signExtension);
	}


	/**
	 * Create an I32 using a Byte array, length, and sign extension.
	 *
	 * @param byteAll       an array of unsigned bytes.  Little Endian.
	 * @param length        length
	 * @param signExtension is this a signed value?  True = signed.
	 */
	public I32(ByteUnsigned[] byteAll, Integer length, Boolean signExtension) {
		this();
		create(byteAll, length, signExtension);
	}

	private void create(ByteUnsigned[] byteAll, Integer length, Boolean signExtension) {
		value = 0;
		switch (length) {
			case 8: {
				value += byteAll[0].intValue();
				if (signExtension) {
					value = signExtend8To32(byteAll[0]);
				}
				break;
			}
			case 16: {
				value += ((byteAll[1].intValue()) << 0);    // Least Significant Byte
				value += ((byteAll[0].intValue()) << 8);    // Most  Significant Byte
				if (signExtension) {
					value = signExtend16To32(value);
				}
				break;
			}
			case 32: {
				value += ((byteAll[3].intValue()) << 0);    // Least  Significant Byte
				value += ((byteAll[2].intValue()) << 8);
				value += ((byteAll[1].intValue()) << 16);
				value += ((byteAll[0].intValue()) << 24);    // Most  Significant Byte
				if (signExtension) {
					value = twoComplement(value);
				}
				break;
			}
			default: {
				throw new WasmRuntimeException(
					UUID.fromString("f8d78ad2-67ed-441f-a327-6df48f2afca7"),
					"I32 Constructor Illegal value in length.  Valid values are 8, 16, 32.    "
					+ "Length =  " + length);
			}
		}

	}

	/**
	 * Get an array of the bytes.  Big Endian.
	 *
	 * @return array of bytes.
	 */
	@Override
	public ByteUnsigned[] getBytes() {
		ByteUnsigned[] byteAll = new ByteUnsigned[4];
		byteAll[3] = new ByteUnsigned((value >>> 0) & 0x0000_00FF);
		byteAll[2] = new ByteUnsigned((value >>> 8) & 0x0000_00FF);
		byteAll[1] = new ByteUnsigned((value >>> 16) & 0x0000_00FF);
		byteAll[0] = new ByteUnsigned((value >>> 24) & 0x0000_00FF);
		return byteAll;
	}

	/**
	 * Convert to I64.  Interpreting the 32 bit value as signed.
	 *
	 * @return An I64 value
	 */
	public I64 toI64Signed() {
		long resultLong = signExtend32To64(value.longValue());
		I64 result = new I64(resultLong);
		return result;
	}

	/**
	 * Convert to I64.  Interpreting the 32 bit value as un-signed.
	 *
	 * @return An I64 value
	 */
	public I64 toI64Unsigned() {
		long resultLong = value.longValue();
		resultLong = resultLong & 0x0000_0000_FFFF_FFFFL;
		I64 result = new I64(resultLong);
		return result;
	}

	/**
	 * Extend 8 to 32 signed.  Interpreting the 8 least significant bits as signed and convert to
	 * 32 bits I32.
	 *
	 * @return An I32 value
	 */
	public I32 extend8To32Signed() {
		int resultInt = signExtend8to32(value);
		I32 result = new I32(resultInt);
		return result;
	}

	/**
	 * Extend 16 to 32 signed.  Interpreting the 16 least significant bits as signed and convert to
	 * 32 bits I32.
	 *
	 * @return An I32 value
	 */
	public I32 extend16To32Signed() {
		int resultInt = signExtend16To32(value);
		I32 result = new I32(resultInt);
		return result;
	}

	@Override
	public Integer maxBits() {
		return 32;
	}

	@Override
	public Integer maxBytes() {
		Integer maxBytes = maxBits() / 8;
		return maxBytes;
	}

	@Override
	public Integer minBytes() {
		Integer maxBytes = maxBits() / 8;
		return maxBytes;
	}

	@Override
	public Long minValue() {
		Long minValue = -1L * (1L << (maxBits() - 1L));
		return minValue;

	}

	@Override
	public Long maxValue() {
		Long maxValue = (1L << (maxBits() - 1L)) - 1L;
		return maxValue;
	}

	/**
	 * use integerValue().
	 *
	 * @return Integer Value
	 */
	@Deprecated
	public Integer getValue() {
		return value;
	}

	@Override
	public Byte byteValue() {
		return value.byteValue();
	}

	public S32 signedValue() {
		return new S32(value);
	}

	public U32 unsignedValue() {
		// java 8 and above.
		return new U32(Integer.toUnsignedLong(value));
	}

	@Override
	public Boolean booleanValue() {
		return value != 0;
	}

	@Override
	public Integer integerValue() {
		return value;
	}

	@Override
	public Long longValue() {
		return value.longValue();
	}

	@Override
	public Boolean isBoundByInteger() {
		return isBoundByInteger(value.longValue());
	}

	protected Boolean isBoundByInteger(Long input) {
		return (Integer.MIN_VALUE <= input.longValue() && input.longValue() <= Integer.MAX_VALUE);
	}

	public Boolean equals(I32 other) {
		return value.equals(other.getValue());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof I32)) {
			return false;
		}

		I32 i32 = (I32) o;

		return value.equals(i32.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public String toString() {
		String result = "I32{ value = " + value + " (hex = " + toHex(value) + ") }";
		return result;
	}
}
