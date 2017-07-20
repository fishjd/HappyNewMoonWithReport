package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;

import java.util.Arrays;

/**
 * An unsigned integer of N bits, represented in N/8 bytes in little endian
 * order. N is either 8, 16, or 32.
 */
public class UInt32 extends UInt<Long> {

    @SuppressWarnings("unused")
    private UInt32() {
        super();
    }

    public UInt32(BytesFile bytesFile) {
        assert (bytesFile.longEnough( minBytes()));
        value = convert(bytesFile);
    }

    public UInt32(byte in1, byte in2, byte in3, byte in4) {
        byte[] byteAll =new byte[]{in1, in2, in3, in4};
        BytesFile bytesFile = new BytesFile(byteAll);
        value = convert(bytesFile);
    }

    public UInt32(byte[] in) {
        if (minBytes() < in.length) {
            throw new IllegalArgumentException("Must be length of 4");
        }
        BytesFile bytesFile = new BytesFile(in);
        value = convert(bytesFile);
    }

    public UInt32(Long value) {
        this.value = value;
    }

    public Long convert(BytesFile bytesFile) {
        Long result = 0L;
        // little Endian!
        for (Integer i = 0; i < maxBits(); i = i + 8) {
            result += Byte.toUnsignedLong(bytesFile.readByte()) << i;
        }
        return result;
    }

	/* Override DataTypeNumber */

    @Override
    public Integer maxBits() {
        return 32;
    }

    @Override
    public Long minValue() {
        return 0L;
    }

    @Override
    public Long maxValue() {
        return 2L ^ maxBits();
    }

	/* override of Object **/

    @Override
    public String toString() {
        return "UInt32{" +
                "value=" + value +
                "} ";
    }
}
