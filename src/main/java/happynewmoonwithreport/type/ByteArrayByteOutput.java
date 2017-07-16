package happynewmoonwithreport.type;

import java.util.Arrays;

public final class ByteArrayByteOutput implements ByteOutput {

    private byte[] bytes;
    private int position;

    public ByteArrayByteOutput(Integer size) {
        this.bytes = new byte[size];
        this.position = 0;
    }

    @Override
    public void writeByte(byte i) {
        bytes[position] = i;
        position++;
    }

    @Override
    public byte[] bytes() {
        return bytes;
    }

    public void reset() {
        position = 0;
    }

    @Override
    public String toString() {
        return "ByteArrayByteOutput [bytes=" + Arrays.toString(bytes) + "]";
    }

}
