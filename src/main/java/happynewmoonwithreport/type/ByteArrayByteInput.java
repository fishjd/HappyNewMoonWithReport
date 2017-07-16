package happynewmoonwithreport.type;

public final class ByteArrayByteInput implements ByteInput {

    private final byte[] bytes;
    private int position;

    public ByteArrayByteInput(byte... bytes) {
        this.bytes = bytes;
        this.position = 0;
    }

    @Override
    public byte readByte() {
        return bytes[position++];
    }

    @Override
    public void reset() {
        position = 0;
    }

}
