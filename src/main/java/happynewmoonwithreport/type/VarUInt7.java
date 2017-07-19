package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;

import java.util.Arrays;

public final class VarUInt7 extends VarUInt<Integer> {

    @SuppressWarnings("unused")
    private VarUInt7() {
        super();
    }

    public VarUInt7(BytesFile bytesFile) {
        value = convert(bytesFile);
    }

    /**
     * Create using a Integer. Size is hard coded to 1. Used mainly in testing.
     *
     * @param value
     */
    public VarUInt7(Integer value) {
        this.value = value;
        // set to default value.
    }

    /**
     * Create using a Byte. Size is hard coded to 1. Used mainly in testing.
     *
     * @param value
     */
    public VarUInt7(Byte value) {
        this.value = value.intValue();
    }

    public Integer convert(BytesFile bytesFile) {
        Integer cur;
        Integer count = 0;
        Integer result = 0;

        do {
            cur = bytesFile.readByte() & 0xff;
            result |= (cur & 0x7f) << (count * 7);
            count++;
        } while (((cur & 0x80) != 0) && count < maxBytes());

        return result;
    }

    @Override
    public Integer maxBits() {
        return 7;
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

}
