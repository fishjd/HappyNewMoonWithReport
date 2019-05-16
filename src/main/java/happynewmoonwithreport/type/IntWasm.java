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
 * Web Assembly Integer
 */
public abstract class IntWasm implements DataTypeNumber {


	@Override
	public Integer maxBytes() {
		return maxBits() / 8;
	}

	@Override
	public Integer minBytes() {
		return maxBits() / 8;
	}


	public abstract Boolean booleanValue();

	//    public abstract Byte byteValue();
//    public abstract Short shortValue();


///**
//     * Return a signed
//     *
//     * @return a signed
//     */
//    public abstract Int signedValue();
//
//    /**
//     * Return a unsigned
//     *
//     * @return a s
//     */
//    public abstract Int unsignedValue();


	// public abstract Boolean equals(Int other) ;

	/**
	 * Sign extend an byte to an integer.
	 * <br>
	 * <br>
	 * extend_sM,N(i)
	 * <ol>
	 * <li>
	 * Let j be the signed interpretation of i of size M.
	 * </li> <li>
	 * Return the two’s complement of j relative to size N.
	 * </li>
	 * </ol>
	 * <p>
	 * <b>Source:</b>  <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-extend-s" target="_top">
	 * https://webassembly.github.io/spec/core/exec/numerics.html#op-extend-s
	 * </a>
	 *
	 * @param input an Unsigned byte of length 8
	 *
	 * @return an int of length 32.
	 */
	public static int signExtend8To32(ByteUnsigned input) {
		int result;

		if (input.isSignBitSet()) {
			result = 0xFFFF_FF00 | input.byteValue();  // it works?
			// negative
			//result = twoComplement(result);
		} else {
			result = input.intValue();
		}
		return result;
	}



	/**
	 * Is the sign bit set?   Is input negative?
	 *
	 * @param input
	 *
	 * @return true if sign bit is set.
	 */
//	public static Boolean isSignBitSet(ByteUnsigned input) {
//		int mask = 0x80;
//		Boolean result = ((byte) mask & (byte) input) != 0;
//		return result;
//	}

	/**
	 * calculate the two complement of a number
	 * <p>
	 * <b>Source:</b>  <a href="https://introcs.cs.princeton.edu/java/61data/" target="_top">
	 * https://introcs.cs.princeton.edu/java/61data/
	 * </a>
	 *
	 * @param input value to modify
	 *
	 * @return the twos complement of input
	 */
	public static int twoComplement(int input) {
		// ones complement,  i.e flip all the bits, i.e binary negation.
		int result = ~input;
		// Add one.
		result = result + 1;
		return result;
	}


	public static String toHex(Long input) {
		return "0x" + Long.toHexString(input).toUpperCase();
	}

	public static String toHex(Integer input) {
		return "0x" + Integer.toHexString(input).toUpperCase();
	}

}
