package happynewmoonwithreport.type;

import java.util.Arrays;

public final class VarUInt32 extends VarUInt<Long> {

    @SuppressWarnings("unused")
    private VarUInt32() {
        super();
    }

    public VarUInt32(ByteInput in) {
        value = convert(in);
        size = setSize(in);
    }

    public VarUInt32(byte[] byteAll, Integer offset) {
        // copyOfRange will add zero to end if not long enough. This is
        // convenient at this works and is exactly what we want. It does mean
        // size() will be incorrect.
        //assert (offset + maxBytes() <= byteAll.length);

        @SuppressWarnings("Since15") byte[] temp = Arrays.copyOfRange(byteAll,
                offset,
                offset + maxBytes());
        ByteInput in = new ByteArrayByteInput(temp);
        value = convert(in);
        size = setSize(in);
    }

    public VarUInt32(byte[] byteAll) {
        this(new ByteArrayByteInput(byteAll));
    }

    /**
     * Create using a Long. Size is hard coded to 5. Used mainly in testing.
     *
     * @param value
     */
    public VarUInt32(Long value) {
        this.value = value;
        // set to default value.
        this.size = 5;
    }

    /**
     * Create using a Integer. Size is hard coded to 1. Used mainly in testing.
     *
     * @param value
     */
    public VarUInt32(Integer value) {
        this.value = value.longValue();
        // set to default value.
        this.size = 1;
    }

    /**
     * Create using a Byte. Size is hard coded to 1. Used mainly in testing.
     *
     * @param value
     */
    public VarUInt32(Byte value) {
        this.value = value.longValue();
        this.size = 1;
    }

    // public VarUInt32(Byte b1, Byte b2, Byte b3, Byte b4, Byte b5) {
    // this();
    // in = new ByteArrayByteInput(b1, b2, b3, b4, b5);
    // }

    public Long convert(ByteInput in) {
        Integer cur;
        Integer count = 0;
        Long result = 0L;

        in.reset();
        do {
            cur = in.readByte() & 0xff;
            result |= (cur & 0x7f) << (count * 7);
            count++;
        } while (((cur & 0x80) != 0) && count < maxBytes());

        return result;
    }

    public Integer getMaxBits() {
        return 32;
    }

    public Integer maxBits() {
        return 32;
    }

    public Long minValue() {
        return 0L;
    }

    public Long maxValue() {
        return 2L ^ maxBits();
    }

    public Long LongValue() {
        return value;
    }

    public Integer IntegerValue() {
        return value.intValue();
    }

}
