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
	 * Sign extend an two bytes number to an integer.
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
	 * <b>Source:</b>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-extend-s" target="_top">
	 * https://webassembly.github.io/spec/core/exec/numerics.html#op-extend-s
	 * </a>
	 * <p>
	 * Note: perhaps,  this will be the code for opCode 0xC3 i64.extend16_s
	 * <br>
	 * <br>
	 * <b> References: </b>
	 * <br>
	 * <a href="http://www.aggregate.org/MAGIC/#Sign%20Extension">"http://www.aggregate.org/MAGIC/#Sign%20Extension"</a>
	 * <br>
	 * <br>
	 * <a href="https://stackoverflow.com/questions/6215256/sign-extension-from-16-to-32-bits-in-c#answer-51958446">
	 * href="https://stackoverflow.com/questions/6215256/sign-extension-from-16-to-32-bits-in-c
	 * #answer-51958446"</a>
	 *
	 * @param bInput an Unsigned byte of length 8
	 * @return an int of length 32.
	 */
	public static int signExtend8To32(ByteUnsigned bInput) {
		int result;
		int input = bInput.intValue();

		// clear all bytes except bytes 0 & 1
		input = 0x0000_00FF & input;

		int signBit = 1 << (8 - 1);
		result = (input ^ signBit);
		result = result - signBit;

		return result;
	}

	/**
	 * Sign extend an two bytes number to an integer.
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
	 * <b>Source:</b>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-extend-s" target="_top">
	 * https://webassembly.github.io/spec/core/exec/numerics.html#op-extend-s
	 * </a>
	 * <p>
	 * Note: perhaps,  this will be the code for opCode 0xC3 i64.extend16_s
	 * <br>
	 * <br>
	 * <b> References: </b>
	 * <br>
	 * <a href="http://www.aggregate.org/MAGIC/#Sign%20Extension">"http://www.aggregate.org/MAGIC/#Sign%20Extension"</a>
	 * <br>
	 * <br>
	 * <a href="https://stackoverflow.com/questions/6215256/sign-extension-from-16-to-32-bits-in-c#answer-51958446">
	 * href="https://stackoverflow.com/questions/6215256/sign-extension-from-16-to-32-bits-in-c
	 * #answer-51958446"</a>
	 *
	 * @param input an int of length 16
	 * @return an int of length 32.
	 */
	public static int signExtend16To32(int input) {
		int result;

		// clear all bytes except bytes 0 & 1
		input = 0x0000_FFFF & input;

		int signBit = 1 << (16 - 1);
		result = (input ^ signBit);
		result = result - signBit;

		return result;

	}

	/**
	 * Sign extend an two bytes number to an integer.
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
	 * <b>Source:</b>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-extend-s" target="_top">
	 * https://webassembly.github.io/spec/core/exec/numerics.html#op-extend-s
	 * </a>
	 * <br>
	 * <b> References: </b>
	 * <br>
	 * <a href="http://www.aggregate.org/MAGIC/#Sign%20Extension">"http://www.aggregate.org/MAGIC/#Sign%20Extension"</a>
	 * <br>
	 * <br>
	 * <a href="https://stackoverflow.com/questions/6215256/sign-extension-from-16-to-32-bits-in-c#answer-51958446">
	 * href="https://stackoverflow.com/questions/6215256/sign-extension-from-16-to-32-bits-in-c
	 * #answer-51958446"</a>
	 *
	 * @param bInput an Unsigned byte of length 8
	 * @return an int of length 64.
	 */
	public static long signExtend8To64(ByteUnsigned bInput) {
		long result;

		long input = bInput.longValue();

		// clear all bytes except byte 0
		input = 0x0000_0000_0000_00FFL & input;

		long signBit = 1 << (8 - 1);
		result = (input ^ signBit);
		result = result - signBit;

		return result;
	}

	/**
	 * Sign extend an two bytes number to an integer.
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
	 * <b>Source:</b>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-extend-s" target="_top">
	 * https://webassembly.github.io/spec/core/exec/numerics.html#op-extend-s
	 * </a>
	 * <p>
	 * Note: perhaps,  this will be the code for opCode 0xC3 i64.extend16_s
	 * <br>
	 * <br>
	 * <b> References: </b>
	 * <br>
	 * <a href="http://www.aggregate.org/MAGIC/#Sign%20Extension">"http://www.aggregate.org/MAGIC/#Sign%20Extension"</a>
	 * <br>
	 * <br>
	 * <a href="https://stackoverflow.com/questions/6215256/sign-extension-from-16-to-32-bits-in-c#answer-51958446">
	 * href="https://stackoverflow.com/questions/6215256/sign-extension-from-16-to-32-bits-in-c
	 * #answer-51958446"</a>
	 *
	 * @param input an int of length 16
	 * @return an int of length 64.
	 */
	public static long signExtend16To64(Long input) {
		long result;

		// clear all bytes except bytes 0 & 1
		input = 0x0000_0000_0000_FFFFL & input;

		long signBit = 1 << (16 - 1);
		result = (input ^ signBit);
		result = result - signBit;

		return result;
	}

	/**
	 * Sign extend a 32 bits to an 64 bit long.
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
	 * <b>Source:</b>
	 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-extend-s" target="_top">
	 * https://webassembly.github.io/spec/core/exec/numerics.html#op-extend-s
	 * </a>
	 * <p>
	 * Note: perhaps,  this will be the code for opCode 0xC3 i64.extend16_s
	 * <br>
	 * <br>
	 * <b> References: </b>
	 * <br>
	 * <a href="http://www.aggregate.org/MAGIC/#Sign%20Extension">"http://www.aggregate.org/MAGIC/#Sign%20Extension"</a>
	 * <br>
	 * <br>
	 * <a href="https://stackoverflow.com/questions/6215256/sign-extension-from-16-to-32-bits-in-c#answer-51958446">
	 * href="https://stackoverflow.com/questions/6215256/sign-extension-from-16-to-32-bits-in-c
	 * #answer-51958446"</a>
	 *
	 * @param input an int of length 16
	 * @return an int of length 64.
	 */
	public static long signExtend32To64(Long input) {
		long result;

		// clear all bytes except bytes 0 - 8
		input = 0x0000_0000_FFFF_FFFFL & input;

		long signBit = 1 << (32 - 1);
		result = (input ^ signBit);
		result = result - signBit;

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
	 * @return the twos complement of input
	 */
	public static int twoComplement(int input) {
		// ones complement,  i.e flip all the bits, i.e binary negation.
		int result = ~input;
		// Add one.
		result = result + 1;
		return result;
	}

	public static long twoComplement(long input) {
		// ones complement,  i.e flip all the bits, i.e binary negation.
		long result = ~input;
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
