package happynewmoonwithreport.type;

/**
 * A byte sink.
 */
public interface ByteOutput {

    /**
     * Write bytes to this one at a time to create an byte array.
     *
     * @param i  byte to write.
     * @throws IndexOutOfBoundsException if all bytes have been written.
     */
    void writeByte(byte i);

    byte[] bytes();
}
