package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;

public final class VarUInt1 extends UInt32 {

    @SuppressWarnings("unused")
    private VarUInt1() {
        super();
    }

    public VarUInt1(BytesFile bytesFile) {
        value = convert(bytesFile);
    }

    /**
     * Create using a Integer.  Used mainly in testing.
     *
     * @param value
     */
    public VarUInt1(Integer value) {
        this.value = value.longValue();
    }

    /**
     * Create using a Long.  Used mainly in testing.
     *
     * @param value
     */
    public VarUInt1(Long value) {
        this.value = value;
    }

    /**
     * Create using a Byte.  Used mainly in testing.
     *
     * @param value
     */
    public VarUInt1(Byte value) {
        this.value = value.longValue();
    }

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

    public Boolean isTrue() {
        return value != 0;
    }

    public Boolean isFalse() {
        return value == 0;
    }

    @Override
    public Integer maxBits() {
        return 1;
    }

    @Override
    public Long minValue() {
        return 0L;
    }

    @Override
    public Long maxValue() {
        return (1 << maxBits()) - 1L;
    }

    public Boolean booleanValue() {
        return value != 0;
    }

    @Override
    public String toString() {
        return "VarUInt1{" +
                "value=" + value +
                "} ";
    }
}
