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

import java.util.UUID;

import happynewmoonwithreport.WasmRuntimeException;

/**
 * An unsigned integer of N bits, represented in the *.wasm file as N/8 bytes in little endian
 * order. N is either 8, 16, or 32.  In the Java code represented by one of the integer types
 * Byte, Integer, Long.
 * <p>
 * Source:  <a href="http://webassembly.org/docs/binary-encoding/#uintn" target="_top">
 * http://webassembly.org/docs/binary-encoding/#uintn
 * </a>
 */
public class U32 extends I32 {

	// TODO Remove !!!   This hides 'value' from I32.
	protected Long value;

	public U32() {

	}


	public U32(Long value) {
		this();
		if (value < 0) {
			throw new WasmRuntimeException(UUID.fromString("6e4ec5d0-9778-462a-a705-1cb21809f687"),
				"Value may not be less than zero. value = " + value + " hex = 0x"
				+ Long.toHexString(value));
		}
		this.value = value;
	}

	public U32(Integer value) {
		this((long) value);
	}

	public U32(U32 input) {
		value = input.longValue();
	}

	@Override
	public Integer maxBits() {
		return 32;
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
	public Boolean isBoundByInteger() {
		return (Integer.MIN_VALUE <= value.longValue() && value.longValue() <= Integer.MAX_VALUE);
	}

	public I32 lessThan(U32 other) {
		I32 result;
		Integer iResult;
		if (value < other.value) {
			iResult = 1;
		} else {
			iResult = 0;
		}
		result = new I32(iResult);
		return result;
	}

	public I32 lessThanEqual(U32 other) {
		I32 result;
		Integer iResult;
		if (value <= other.value) {
			iResult = 1;
		} else {
			iResult = 0;
		}
		result = new I32(iResult);
		return result;
	}

	public I32 greaterThan(U32 other) {
		I32 result;
		Integer iResult;
		if (value > other.value) {
			iResult = 1;
		} else {
			iResult = 0;
		}
		result = new I32(iResult);
		return result;
	}

	public I32 greaterThanEqual(U32 other) {
		I32 result;
		Integer iResult;
		if (value >= other.value) {
			iResult = 1;
		} else {
			iResult = 0;
		}
		result = new I32(iResult);
		return result;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("U32{");
		sb.append("value=").append(value);
		sb.append('}');
		return sb.toString();
	}
	//
	//    /**
	//     * The value of the number
	//     */
	//
	//    @Override
	//    public Integer maxBytes() {
	//        Integer maxBytes = maxBits() / 8;
	//        return maxBytes;
	//    }
	//
	//    @Override
	//    public Integer minBytes() {
	//        Integer maxBytes = maxBits() / 8;
	//        return maxBytes;
	//    }
	//
	//    @Override
	//    public ValueType value() {
	//        return value;
	//    }

	// I don't think it is worth adding minValue() and maxValue() if the price
	// is you are required to add the Class<ValueType> as a parameter. It is
	// easier to add in the sub classes.
	//
	// public <ValueType extends Number> ValueType minValue(Class<ValueType>
	// valueType) {
	// return valueType.cast(0);
	// }
	//

	//    public Boolean isTrue() {
	//        return value.intValue() != 0;
	//    }
	//
	//    public Boolean booleanValue() {
	//        return value.intValue() != 0;
	//    }
	//
	//    @Override
	//    public byte byteValue() {
	//        return value.byteValue();
	//    }
	//
	//
	//

	//
	//    @Override
	//    public int hashCode() {
	//        final int prime = 31;
	//        int result = 1;
	//        result = prime * result + ((value == null) ? 0 : value.hashCode());
	//        return result;
	//    }
	//
	//    @Override
	//    public boolean equals(Object o) {
	//        if (this == o) return true;
	//        if (!(o instanceof U32)) return false;
	//
	//        U32<?> uInt = (U32<?>) o;
	//
	//        return value.equals(uInt.value);
	//    }
}
