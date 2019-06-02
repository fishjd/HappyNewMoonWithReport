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

import happynewmoonwithreport.type.JavaType.ByteUnsigned;

/**
 * I64 is the WebAssembly runtime 64 bit Integer.  It can be interpreted as either signed or
 * unsigned.
 * <p>
 * source https://webassembly.github.io/spec/core/text/values.html#integers
 */
public class I64 extends IntWasm {
	protected Long value;

	public I64() {
		super();
	}

	public I64(Integer value) {
		this.value = value.longValue();
	}

	public I64(Long value) {
		this();
		this.value = value.longValue();
	}

	public I64(ByteUnsigned[] byteAll) {
		this();
		value = 0L;
		value += byteAll[0].longValue() << 56;
		value += byteAll[1].longValue() << 48;
		value += byteAll[2].longValue() << 40;
		value += byteAll[3].longValue() << 32;
		value += byteAll[4].longValue() << 24;
		value += byteAll[5].longValue() << 16;
		value += byteAll[6].longValue() << 8;
		value += byteAll[7].longValue() << 0;
	}

	/**
	 * Get an array of the bytes.  Big Endian.
	 *
	 * @return array of bytes.
	 */
	public ByteUnsigned[] getBytes() {
		ByteUnsigned[] byteAll = new ByteUnsigned[8];
		byteAll[7] = new ByteUnsigned((value >>> 0) & 0x0000_00FF);
		byteAll[6] = new ByteUnsigned((value >>> 8) & 0x0000_00FF);
		byteAll[5] = new ByteUnsigned((value >>> 16) & 0x0000_00FF);
		byteAll[4] = new ByteUnsigned((value >>> 24) & 0x0000_00FF);
		byteAll[3] = new ByteUnsigned((value >>> 32) & 0x0000_00FF);
		byteAll[2] = new ByteUnsigned((value >>> 40) & 0x0000_00FF);
		byteAll[1] = new ByteUnsigned((value >>> 48) & 0x0000_00FF);
		byteAll[0] = new ByteUnsigned((value >>> 56) & 0x0000_00FF);
		return byteAll;
	}


	@Override
	public Integer maxBits() {
		return 64;
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

	@Override
	public Boolean isBoundByInteger() {
		return (Integer.MIN_VALUE <= value.longValue() && value.longValue() <= Integer.MAX_VALUE);
	}

	public S64 signedValue() {
		return new S64(value);
	}

	public U64 unsignedValue() {
		return new U64(value);
	}

	@Override
	public Boolean booleanValue() {
		return value != 0;
	}

	@Override
	public Byte byteValue() {
		return value.byteValue();
	}

	@Override
	public Integer integerValue() {
		return value.intValue();
	}

	@Override
	public Long longValue() {
		return value.longValue();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other == null || getClass() != other.getClass()) {
			return false;
		}

		I64 i64Other = (I64) other;

		return value != null ? value.equals(i64Other.value) : i64Other.value == null;
	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}

	@Override
	public String toString() {
		String result = "I64{ value = " + value + " (hex = " + toHex(value) + ") }";
		return result;
	}
}
