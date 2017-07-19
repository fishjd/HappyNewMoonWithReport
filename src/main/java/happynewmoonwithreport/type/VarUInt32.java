package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;

import java.util.Arrays;

public final class VarUInt32 extends VarUInt<Long> {

    @SuppressWarnings("unused")
    private VarUInt32() {
        super();
    }

    public VarUInt32(BytesFile bytesFile) {
        value = convert(bytesFile);
    }

    /**
     * Create using a Long. Size is hard coded to 5. Used mainly in testing.
     *
     * @param value
     */
    public VarUInt32(Long value) {
        this.value = value;
        // set to default value.
    }

    /**
     * Create using a Integer. Size is hard coded to 1. Used mainly in testing.
     *
     * @param value
     */
    public VarUInt32(Integer value) {
        this.value = value.longValue();
        // set to default value.
    }

    /**
     * Create using a Byte. Size is hard coded to 1. Used mainly in testing.
     *
     * @param value
     */
    public VarUInt32(Byte value) {
        this.value = value.longValue();
    }

    // public VarUInt32(Byte b1, Byte b2, Byte b3, Byte b4, Byte b5) {
    // this();
    // in = new ByteArrayByteInput(b1, b2, b3, b4, b5);
    // }

    public Long convert(BytesFile bytesFile) {
        Integer cur;
        Integer count = 0;
        Long result = 0L;

        do {
            cur = bytesFile.readByte() & 0xff;
            result |= (cur & 0x7f) << (count * 7);
            count++;
        } while (((cur & 0x80) != 0) && count < maxBytes());

        return result;
    }

    /**
     * Does the <code>value</code> lay between Integer.minValue and Integer.maxValue.  i.e Integer.minValue <= value <=
     * Integer.maxValue;
     **/
    public Boolean isBoundByInteger() {
        return (Integer.MIN_VALUE <= value && value <= Integer.MAX_VALUE);
    }

    public void checkIfTooLarge() {
        if (isBoundByInteger() == false) {
            throw new RuntimeException("Value is too large!");
        }
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
        checkIfTooLarge();
        return value.intValue();
    }

}
