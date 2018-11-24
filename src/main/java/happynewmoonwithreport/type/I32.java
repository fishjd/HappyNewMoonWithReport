/*
 *  Copyright 2017 Whole Bean Software, LTD.
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

import happynewmoonwithreport.WasmRuntimeException;

import java.util.UUID;

/**
 * Signed Integer
 *
 * @param
 */
public class I32 extends Int {

	protected Integer value;

	public I32() {
		value = 0;
	}

	public I32(Integer value) {
		this.value = value;
	}

	public I32(Long value) {
		this();
		if (isBoundByInteger(value) == false) {
			throw new WasmRuntimeException(UUID.fromString("62298944-804a-430e-b645-7bda0ecab265"),
					"Value not bound by integer. Value = " + value + " (" + toHex(value) + ")");
		}
		this.value = value.intValue();
	}

	public I32(Byte[] byteAll) {
		this();
		value = 0;
		value += byteAll[0] << 24;
		value += byteAll[1] << 16;
		value += byteAll[2] << 8;
		value += byteAll[3] << 0;
	}


	/**
	 * Create an I32 using a Byte array, length, and sign extension.
	 * Big Endian.
	 *
	 * @param byteAll
	 * @param length
	 * @param signExtension
	 */
	public I32(Byte[] byteAll, Integer length, Boolean signExtension) {

		this();
		value = 0;
		switch (length) {
			case 8: {
				value += Byte.toUnsignedInt(byteAll[0]);
				if (signExtension) {
					value = signExtend(byteAll[0]);
				}
				break;
			}
			case 16: {
				value += (Byte.toUnsignedInt(byteAll[0]) << 0);
				value += (Byte.toUnsignedInt(byteAll[1]) << 8);
				if (signExtension && isSignBitSet(byteAll[1])) {
					value = twoComplement(value);
				}
				break;
			}
			// I'm not sure 24 and 32 are necessary or required by the specification.
			case 24: {
				value += (Byte.toUnsignedInt(byteAll[0]) << 0);
				value += (Byte.toUnsignedInt(byteAll[1]) << 8);
				value += (Byte.toUnsignedInt(byteAll[2]) << 16);
				if (signExtension && isSignBitSet(byteAll[2])) {
					value = twoComplement(value);
				}
				break;
			}
			case 32: {
				value += (Byte.toUnsignedInt(byteAll[0]) << 0);
				value += (Byte.toUnsignedInt(byteAll[1]) << 8);
				value += (Byte.toUnsignedInt(byteAll[2]) << 16);
				value += (Byte.toUnsignedInt(byteAll[3]) << 24);
				if (signExtension && isSignBitSet(byteAll[3])) {
					value = twoComplement(value);
				}
				break;
			}
			default: {
				throw new WasmRuntimeException(UUID.fromString("f8d78ad2-67ed-441f-a327-6df48f2afca7"),
						"I32 Constructor Illegal value in length.  Valid values are 8, 16, 24, 32.    Length =  " + length);
			}
		}
	}

	private String toHex(Long value) {
		return "0x" + Long.toHexString(value).toUpperCase();
	}

	private String toHex(Integer value) {
		return "0x" + Integer.toHexString(value).toUpperCase();
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
	 * use IntegerValue();
	 *
	 * @return
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
		if (this == o) return true;
		if (!(o instanceof I32)) return false;

		I32 i32 = (I32) o;

		return value.equals(i32.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public String toString() {
		String result = "I32{ value = " + value + " (hex = ~" + toHex(value) + ") }";
		return result;
	}
}
