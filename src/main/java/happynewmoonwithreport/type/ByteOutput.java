package happynewmoonwithreport.type;

/**
 * A byte sink.
 */
public interface ByteOutput {

    /**
     * Writes a byte.
     *
     * @param i  byte to write.
     * @throws IndexOutOfBoundsException if all bytes have been written.
     */
    void writeByte(byte i);

    byte[] bytes();
}
