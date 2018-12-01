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

import happynewmoonwithreport.type.JavaType.ByteUnsigned;

/**
 * Web Assembly Integer
 */
public abstract class Int implements DataTypeNumber {


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
	 * Sign extend a byte to an integer.
	 * <p>
	 * <p>
	 * extend_sM,N(i)
	 * <ol>
	 * <li>
	 * Let j be the signed interpretation of i of size M.
	 * </li> <li>
	 * Return the two’s complement of j relative to size N.
	 * </li>
	 * </ol>
	 * <p>
	 * <p>
	 * <b>Source:</b>  <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-extend-s" target="_top">
	 * https://webassembly.github.io/spec/core/exec/numerics.html#op-extend-s
	 * </a>
	 *
	 * @param input a byte
	 *
	 * @return an integer that is the same value of the byte.
	 */
	public static Integer signExtend(ByteUnsigned input) {
		int result;

		result = input.intValue();
		if (input.isSignBitSet()) {
			// negative
			result = twoComplement(result);
		}
		return new Integer(result);
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
	 * @param input
	 *
	 * @return the twos complement.
	 */
	public static int twoComplement(int input) {
		// ones complement,  i.e flip all the bits, i.e binary negation.
		int result = ~input;
		// Add one.
		result = result + 1;
		return result;
	}


}
