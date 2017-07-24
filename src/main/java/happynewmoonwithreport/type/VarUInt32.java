package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;

public final class VarUInt32 extends UInt32 {

    @SuppressWarnings("unused")
    private VarUInt32() {
        super();
    }

    public VarUInt32(BytesFile bytesFile) {
        value = convert(bytesFile);
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

    @Override
    public Integer maxBytes() {
        Integer maxBytes = new Double(Math.ceil((double) maxBits() / 7.0D)).intValue();
        return maxBytes;
    }

    @Override
    public String toString() {
        return "VarUInt32{" +
                "value=" + value +
                "} ";
    }
}
