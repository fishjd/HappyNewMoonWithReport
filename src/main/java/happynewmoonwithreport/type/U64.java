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

/**
 * An Unsigned integer of 64 bits,
 * <p>
 * Implementation Note:  Range is from 0 to 9_223_372_036_854_775_807.  The wasm spec is 0 to
 * 18_446_744_073_709_551_616
 */
public class U64 extends I64 {
	public U64() {
		super();
	}

	@Override
	public Integer maxBits() {
		return 63;  // should be 64 but that is not possible using Java type Long.
	}

	public U64(Long value) {
		if (isBoundByLong(value) == false) {
			throw new WasmRuntimeException(UUID.fromString("1c282299-6b1b-4d02-9e81-a29c279840d3"),
				"Value too large.  Value = " + value + "Only values from 0 to " + Long.MAX_VALUE +
				" are currently supported.");
		}
		this.value = value;
	}

	/**
	 * True if value can be stored in U64.
	 *
	 * @param value number to test.
	 * @return True if value can be stored in U64.  0 to 9_223_372_036_854_775_807  ie 0 to Long
	 * .MAX_VALUE; <br>
	 * False if value can not be stored in U64.  9_223_372_036_854_775_808 to
	 * 18_446_744_073_709_551_616
	 */
	public static boolean isBoundByLong(Long value) {
		return (0x8000_0000_0000_0000L & value.longValue()) == 0;
	}


	public I32 lessThan(U64 other) {
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

	public I32 lessThanEqual(U64 other) {
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

	public I32 greaterThan(U64 other) {
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

	public I32 greaterThanEqual(U64 other) {
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
	public Long minValue() {
		// @TODO change return type to U64.
		return 0L;
	}

	@Override
	public Long maxValue() {
		return (1L << (maxBits())) - 1;
	}

}
