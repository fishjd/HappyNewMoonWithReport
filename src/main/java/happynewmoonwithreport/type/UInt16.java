package happynewmoonwithreport.type;

import java.util.Arrays;

/**
 * An unsigned integer of N bits, represented in N/8 bytes in little endian
 * order. N is either 8, 16, or 32.
 */
public class UInt16 extends UInt<Integer> {

    public UInt16(byte in1, byte in2) {
        this(new byte[]{in1, in2});
    }

    public UInt16(byte[] in) {
        if (minBytes() < in.length) {
            throw new IllegalArgumentException("Must be length of 2");
        }
        value = convert(new ByteArrayByteInput(in));
    }

    public UInt16(byte[] byteAll, Integer offset) {
        // copyOfRange will add zero to end if not long enough. This is
        // convenient at this works and is exactly what we want. It does mean
        // size() will be incorrect.
        assert (offset + minBytes() <= byteAll.length);

        byte[] temp = Arrays.copyOfRange(byteAll, offset, offset + maxBytes());
        value = convert(new ByteArrayByteInput(temp));

    }

    public UInt16(Integer value) {
        this.value = value;
    }

    public Integer convert(ByteInput in) {
        Integer result = 0;
        in.reset();
        // little Endian!
        for (Integer i = 0; i < maxBits(); i = i + 8) {
            result += Byte.toUnsignedInt(in.readByte()) << i;
        }
        return result;
    }

	/* private functions **/

	/* Override DataTypeNumber */

    @Override
    public Integer maxBits() {
        return 16;
    }

    @Override
    public Integer minValue() {
        return 0;
    }

    @Override
    public Integer maxValue() {
        return 2 ^ maxBits();
    }

	/* override of Object **/

    @Override
    public String toString() {
        return "Uint16 [value=" + value + "]";
    }

}
