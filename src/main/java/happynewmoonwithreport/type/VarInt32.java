/*
 *  Copyright 2017 - 2021 Whole Bean Software, LTD.
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
 * A variable integer of 32 bits.
 * <br>
 * Used to read and write to the wasm file.
 * <br>
 * A Signed LEB128 variable-length integer, limited to N bits (i.e., the values [-2^(N-1), +2^
 * (N-1)-1]), represented by
 * at most ceil(N/7) bytes that may contain padding for positive number 0x80 bytes or for
 * negative numbers 0xFF bytes.
 * <br>
 * This project tends to use the 'main' integer types Int32, Int64.
 * The recommend use is to convert to a 'main' type as soon as possible.
 * <br>
 * * Usage:
 * <pre>
 *      {@code
 *          Int32 number = new VarInt32(bytesFile);
 *      }
 * </pre>
 * <br>
 * Source:
 * <a href="https://webassembly.github.io/spec/core/binary/values.html#integers" target="_top">
 * https://webassembly.github.io/spec/core/binary/values.html#integers
 * </a>
 */
public final class VarInt32 extends SInt32 {

	@SuppressWarnings("unused")
	private VarInt32() {
	}

	public VarInt32(BytesFile bytesFile) {
		assert (bytesFile.longEnough(minBytes()));
		value = convert(bytesFile);
	}

	public VarInt32(DataTypeNumber value) {
		this.value = value.integerValue();
	}

	/**
	 * Create using a Long. Used mainly in testing.
	 *
	 * @param value value
	 */
	public VarInt32(Long value) {
		this.value = value.intValue();
	}

	/**
	 * Create using a Integer. Size is hard coded to 1. Used mainly in testing.
	 *
	 * @param value value
	 */
	public VarInt32(Integer value) {
		this.value = value.intValue();
	}

	/**
	 * Create using a Byte. Size is hard coded to 1. Used mainly in testing.
	 *
	 * @param value value
	 */
	public VarInt32(Byte value) {
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
			result |= (cur & 0x7f) << (count * 7);
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
		Integer remaining = value >> 7;
		boolean hasMore = true;
		int end = ((value & Long.MIN_VALUE) == 0) ? 0 : -1;

		while (hasMore) {
			hasMore = (remaining != end) || ((remaining & 1) != ((value >> 6) & 1));

			out.writeByte((byte) ((value & 0x7f) | (hasMore ? 0x80 : 0)));
			value = remaining;
			remaining >>= 7;
		}
		return out;
	}

	@Override
	public String toString() {
		return "VarInt32{" + "value=" + value + "} ";
	}
}
