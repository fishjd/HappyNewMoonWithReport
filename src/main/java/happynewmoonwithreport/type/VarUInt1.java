package happynewmoonwithreport.type;

import java.util.Arrays;

public final class VarUInt1 extends VarUInt<Integer> {

    @SuppressWarnings("unused")
    private VarUInt1() {
        super();
    }

    public VarUInt1(ByteInput in) {
        value = convert(in);
        size = setSize(in);
    }

    public VarUInt1(byte[] byteAll, Integer offset) {
        // copyOfRange will add zero to end if not long enough. This is
        // convenient at this works and is exactly what we want. It does mean
        // size() will be incorrect.
        assert (offset + maxBytes() <= byteAll.length);

        byte[] temp = Arrays.copyOfRange(byteAll, offset, offset + maxBytes());
        ByteInput in = new ByteArrayByteInput(temp);
        value = convert(in);
        size = setSize(in);
    }

    public VarUInt1(byte[] byteAll) {
        this(new ByteArrayByteInput(byteAll));
    }

    /**
     * Create using a Integer. Size is hard coded to 1. Used mainly in testing.
     *
     * @param value
     */
    public VarUInt1(Integer value) {
        this.value = value;
        // set to default value.
        this.size = 1;
    }

    /**
     * Create using a Byte. Size is hard coded to 1. Used mainly in testing.
     *
     * @param value
     */
    public VarUInt1(Byte value) {
        this.value = value.intValue();
        this.size = 1;
    }

    public Integer convert(ByteInput in) {
        Integer cur;
        Integer count = 0;
        Integer result = 0;

        in.reset();
        do {
            cur = in.readByte() & 0xff;
            result |= (cur & 0x7f) << (count * 7);
            count++;
        } while (((cur & 0x80) != 0) && count < maxBytes());

        return result;
    }

    @Override
    public Integer maxBits() {
        return 1;
    }

    @Override
    public Integer minValue() {
        return 0;
    }

    @Override
    public Integer maxValue() {
        return 2 ^ maxBits();
    }

    public Long LongValue() {
        return new Long(value);
    }

    public Integer IntegerValue() {
        return value;
    }

    public Boolean BooleanValue() {
        return value != 0;
    }

}
