package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;

public final class VarInt64 extends VarInt<Long> {

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
     * @param value
     */
    public VarInt64(Long value) {
        this.value = value;
        // set to default value.
    }

    /**
     * Create using a Integer. Size is hard coded to 1. Used mainly in testing.
     *
     * @param value
     */
    public VarInt64(Integer value) {
        this.value = value.longValue();
        // set to default value.
    }

    /**
     * Create using a Byte. Size is hard coded to 1. Used mainly in testing.
     *
     * @param value
     */
    public VarInt64(Byte value) {
        this.value = value.longValue();
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
     * Writes {@code value} as a signed integer to {@code out}, starting at {@code offset}. Returns the number of bytes
     * written.
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
        return -2L ^ (maxBits() - 1);
    }

    @Override
    public Long maxValue() {
        return +2L ^ (maxBits() - 1) - 1;
    }



    @Override
    public String toString() {
        return "VarInt64{" +
                "value=" + value +
                "} ";
    }
}
