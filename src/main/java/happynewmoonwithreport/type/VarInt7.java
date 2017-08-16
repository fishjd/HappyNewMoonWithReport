package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;

public final class VarInt7 extends Int32 {

    @SuppressWarnings("unused")
    private VarInt7() {
        super();
    }

    public VarInt7(BytesFile bytesFile) {
        assert (bytesFile.longEnough(minBytes()));
        value = convert(bytesFile);
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
        // set to default value.
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

    public Integer maxBits() {
        return 7;
    }

    public Integer minValue() {
        return -1 * (1 << (maxBits() - 1));
    }

    public Integer maxValue() {
        return (1 << (maxBits() - 1)) - 1;
    }


    @Override
    public String toString() {
        return "VarInt7{" +
                "value=" + value +
                "} ";
    }
}
