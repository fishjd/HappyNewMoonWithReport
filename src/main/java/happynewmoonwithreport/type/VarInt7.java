package happynewmoonwithreport.type;

import java.util.Arrays;

public final class VarInt7 extends VarInt<Integer> {

    @SuppressWarnings("unused")
    private VarInt7() {
        super();
    }

    public VarInt7(ByteInput in) {
        value = convert(in);
        size = setSize(in);
    }

    public VarInt7(byte[] byteAll, Integer offset) {
        // copyOfRange will add zero to end if not long enough. This is
        // convenient at this works and is exactly what we want. It does mean
        // size() will be incorrect.
        // assert (offset + maxBytes() <= byteAll.length);

        byte[] temp = Arrays.copyOfRange(byteAll, offset, offset + maxBytes());
        ByteInput in = new ByteArrayByteInput(temp);
        value = convert(in);
        size = setSize(in);
    }

    public VarInt7(byte[] byteAll) {
        this(new ByteArrayByteInput(byteAll));
    }

    /**
     * Create using a Long. Used mainly in testing.
     *
     * @param value
     */
    public VarInt7(Long value) {
        this.value = value.intValue();
        // set to default value.
        this.size = maxBytes();
    }

    /**
     * Create using a Integer. Size is hard coded to 1. Used mainly in testing.
     *
     * @param value
     */
    public VarInt7(Integer value) {
        this.value = value.intValue();
        // set to default value.
        this.size = 1;
    }

    /**
     * Create using a Byte. Size is hard coded to 1. Used mainly in testing.
     *
     * @param value
     */
    public VarInt7(Byte value) {
        this.value = value.intValue();
        this.size = 1;
    }

    public Integer convert(ByteInput in) {
        Integer cur;
        Integer count = 0;
        Integer result = 0;
        Integer signBits = -1;

        in.reset();
        do {
            cur = in.readByte() & 0xff;
            result |= ((int) (cur & 0x7f)) << (count * 7);
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

    public Integer maxBits() {
        return 7;
    }

    public Integer minValue() {
        return -2 ^ (maxBits() - 1);
    }

    public Integer maxValue() {
        return +2 ^ (maxBits() - 1) - 1;
    }

    public Long LongValue() {
        return value.longValue();
    }

    public Integer IntegerValue() {
        return value.intValue();
    }

}
