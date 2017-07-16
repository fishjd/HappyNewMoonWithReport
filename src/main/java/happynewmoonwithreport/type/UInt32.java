package happynewmoonwithreport.type;

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

    public UInt32(byte in1, byte in2, byte in3, byte in4) {
        this(new byte[]{in1, in2, in3, in4});
    }

    public UInt32(byte[] in) {
        if (minBytes() < in.length) {
            throw new IllegalArgumentException("Must be length of 4");
        }
        value = convert(new ByteArrayByteInput(in));
    }

    public UInt32(byte[] byteAll, Integer offset) {
        // copyOfRange will add zero to end if not long enough. This is
        // convenient at this works and is exactly what we want. It does mean
        // size() will be incorrect.
        assert (offset + minBytes() <= byteAll.length);

        byte[] temp = Arrays.copyOfRange(byteAll, offset, offset + maxBytes());
        value = convert(new ByteArrayByteInput(temp));
    }

    public UInt32(Long value) {
        this.value = value;
    }

    public Long convert(ByteInput in) {
        Long result = 0L;
        in.reset();
        // little Endian!
        for (Integer i = 0; i < maxBits(); i = i + 8) {
            result += Byte.toUnsignedLong(in.readByte()) << i;
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
        return "Uint32 [value=" + value + "]";
    }

}
