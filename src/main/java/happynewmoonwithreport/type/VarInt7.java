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

import happynewmoonwithreport.BytesFile;

/**
 * A variable integer of 7 bits.
 * <p>
 * Used to read and write to the wasm file. This project tends to use the 'main' integer types Int32, Int64, UInt32,
 * UInt64.  The recommend use is to convert to a 'main' type as soon as possible.
 * <p>
 * A Signed LEB128 variable-length integer, limited to N bits (i.e., the values [-2^(N-1), +2^(N-1)-1]), represented by
 * at most ceil(N/7) bytes that may contain padding for positive number 0x80 bytes or for negative numbers 0xFF bytes.
 * <p>
 * Usage:
 * <pre>
 *      {@code
 *          Int32 number = new VarInt7(bytesFile);
 *      }
 *  </pre>
 * <p>
 * Source:  <a href="http://webassembly.org/docs/binary-encoding/#varintn" target="_top">
 * http://webassembly.org/docs/binary-encoding/#varintn
 * </a>
 */
public final class VarInt7 extends SInt8 {

	@SuppressWarnings("unused")
	private VarInt7() {
		super();
	}

	public VarInt7(BytesFile bytesFile) {
		assert (bytesFile.longEnough(minBytes()));
		value = convert(bytesFile).intValue();
	}

	/**
	 * Create using a Long. Used mainly in testing.
	 *
	 * @param value value
	 */
	public VarInt7(Long value) {
		this.value = value.intValue();
		// set to default value.
	}

	/**
	 * Create using a Integer. Size is hard coded to 1. Used mainly in testing.
	 *
	 * @param value value
	 */
	public VarInt7(Integer value) {
		this.value = value.intValue();
	}

	/**
	 * Create using a Byte. Size is hard coded to 1. Used mainly in testing.
	 *
	 * @param value value
	 */
	public VarInt7(Byte value) {
		this.value = value.intValue();
	}

	@Override
	public Integer maxBytes() {
		Integer maxBytes = new Double(Math.ceil((double) maxBits() / 7.0D)).intValue();
		return maxBytes;
	}

	@Override
	public Integer minBytes() {
		return 1;
	}

	public Integer convert(BytesFile bytesFile) {
		Integer cur;
		Integer count = 0;
		Integer result = 0;
		Integer signBits = -1;

		do {
			cur = bytesFile.readByte() & 0xff;
			result |= ((cur & 0x7f)) << (count * 7);
			signBits <<= 7;
			count++;
		} while (((cur & 0x80) != 0) && count < maxBytes());

		// Sign extend if appropriate
		if (((signBits >> 1) & result) != 0) {
			result |= signBits;
		}

		return result;
	}

	/**
	 * Writes the value as a byte stream.
	 *
	 * @return byte stream.
	 */
	public ByteOutput convert() {
		ByteOutput out = new ByteArrayByteOutput(maxBytes());
		byte remaining = (byte) (value >> 7);
		boolean hasMore = true;
		int end = ((value & Long.MIN_VALUE) == 0) ? 0 : -1;

		while (hasMore) {
			hasMore = (remaining != end) || ((remaining & 1) != ((value >> 6) & 1));

			out.writeByte((byte) ((value & 0x7f) | (hasMore ? 0x80 : 0)));
			value = ((int) remaining);
			remaining = (byte) (remaining >> 7);
		}
		return out;
	}

	@Override
	public Integer maxBits() {
		return 7;
	}

//    public Integer minValue() {
//        return -1 * (1 << (maxBits() - 1));
//    }
//
//    public Integer maxValue() {
//        return (1 << (maxBits() - 1)) - 1;
//    }


	@Override
	public String toString() {
		return "VarInt7{" +
				"value=" + value +
				"} ";
	}
}
