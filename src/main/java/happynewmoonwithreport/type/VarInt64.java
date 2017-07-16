package happynewmoonwithreport.type;

import java.util.Arrays;

public final class VarInt64 extends VarInt<Long> {

    @SuppressWarnings("unused")
    private VarInt64() {
        super();
    }

    public VarInt64(ByteInput in) {
        value = convert(in);
        size = setSize(in);
    }

    public VarInt64(byte[] byteAll, Integer offset) {
        // copyOfRange will add zero to end if not long enough. This is
        // convenient at this works and is exactly what we want. It does mean
        // size() will be incorrect.
        // assert (offset + maxBytes() <= byteAll.length);

        byte[] temp = Arrays.copyOfRange(byteAll, offset, offset + maxBytes());
        ByteInput in = new ByteArrayByteInput(temp);
        value = convert(in);
        size = setSize(in);
    }

    public VarInt64(byte[] byteAll) {
        this(new ByteArrayByteInput(byteAll));
    }

    /**
     * Create using a Long. Size is hard coded to 5. Used mainly in testing.
     *
     * @param value
     */
    public VarInt64(Long value) {
        this.value = value;
        // set to default value.
        this.size = maxBytes();
    }

    /**
     * Create using a Integer. Size is hard coded to 1. Used mainly in testing.
     *
     * @param value
     */
    public VarInt64(Integer value) {
        this.value = value.longValue();
        // set to default value.
        this.size = 1;
    }

    /**
     * Create using a Byte. Size is hard coded to 1. Used mainly in testing.
     *
     * @param value
     */
    public VarInt64(Byte value) {
        this.value = value.longValue();
        this.size = 1;
    }

    public Long convert(ByteInput in) {
        Integer cur;
        Integer count = 0;
        Long result = 0L;
        Long signBits = -1L;

        in.reset();
        do {
            cur = in.readByte() & 0xff;
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
     * Writes {@code value} as a signed integer to {@code out}, starting at
     * {@code offset}. Returns the number of bytes written.
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

    public Long LongValue() {
        return value;
    }

    public Integer IntegerValue() {
        return value.intValue();
    }

}
