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

import happynewmoonwithreport.BytesFile;

/**
 * A variable integer of 64 bits.
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
 *          Int64 number = new VarInt64(bytesFile);
 *      }
 * </pre>
 * <p>
 * Source:  <a href="http://webassembly.org/docs/binary-encoding/#varintn" target="_top">
 * http://webassembly.org/docs/binary-encoding/#varintn
 * </a>
 */
public final class VarInt64 extends S64 {

    @SuppressWarnings("unused")
    private VarInt64() {
        super();
    }

    public VarInt64(BytesFile bytesFile) {
        assert (bytesFile.longEnough(minBytes()));
        value = convert(bytesFile);
    }

    /**
     * Create using a Long. Size is hard coded to 5. Used mainly in testing.
     *
     * @param value value
     */
    public VarInt64(Long value) {
        this.value = value;
        // set to default value.
    }

    /**
     * Create using a Integer. Size is hard coded to 1. Used mainly in testing.
     *
     * @param value value
     */
    public VarInt64(Integer value) {
        this.value = value.longValue();
        // set to default value.
    }

    /**
     * Create using a Byte. Size is hard coded to 1. Used mainly in testing.
     *
     * @param value value
     */
    public VarInt64(Byte value) {
        this.value = value.longValue();
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

    public Long convert(BytesFile bytesFile) {
        Integer cur;
        Integer count = 0;
        Long result = 0L;
        Long signBits = -1L;

        do {
            cur = bytesFile.readByte() & 0xff;
            result |= ((long) (cur & 0x7f)) << (count * 7);
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
        Long remaining = value >> 7;
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
    public Integer maxBits() {
        return 64;
    }

    @Override
    public Long minValue() {
        return -1L * (1L << (maxBits() - 1));
    }

    @Override
    public Long maxValue() {
        return (1L << (maxBits() - 1)) - 1;
    }


    @Override
    public String toString() {
        return "VarInt64{" +
                "value=" + value +
                "} ";
    }
}
