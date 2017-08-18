package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;

/**
 * A unsigned Integer of 7 bits.
 * <p>
 * Stored in the wasm file as a LEB128 variable-length integer, limited to N bits (i.e., the values [0,
 * 2^N-1]), represented by at most ceil(N/7) bytes that may contain padding 0x80
 * bytes.
 * <p>
 * <p>
 * Used to read and write to the wasm file. This project tends to use the 'main' integer types Int32, Int64, UInt32,
 * UInt64.  The recommend use is to convert to a 'main' type as soon as possible.
 * <p>
 * Usage:
 * <pre>
 *      {@code
 *          UInt32 number = new VarUInt7(bytesFile);
 *      }
 * </pre>
 * <p>
 * Source:  <a href="http://webassembly.org/docs/binary-encoding/#varuintn" target="_top">
 * http://webassembly.org/docs/binary-encoding/#varuintn
 * </a>
 */
public final class VarUInt7 extends VarUInt<Integer> {
    // TODO change to extend UInt32

    @SuppressWarnings("unused")
    private VarUInt7() {
        super();
    }

    public VarUInt7(BytesFile bytesFile) {
        value = convert(bytesFile);
    }

    /**
     * Create using a Integer.  Used mainly in testing.
     *
     * @param value value
     */
    public VarUInt7(Integer value) {
        this.value = value;
    }

    /**
     * Create using a Byte. Used mainly in testing.
     *
     * @param value value
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
        return (1 << maxBits()) - 1;
    }


    @Override
    public String toString() {
        return "VarUInt7{" +
                "value=" + value() +
                "} ";
    }
}
