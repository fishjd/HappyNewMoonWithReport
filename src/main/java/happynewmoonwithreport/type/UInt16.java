package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;

import java.util.Arrays;

/**
 * An unsigned integer of N bits, represented in N/8 bytes in little endian
 * order. N is either 8, 16, or 32.
 */
public class UInt16 extends UInt<Integer> {

    public UInt16(BytesFile bytesFile) {
        assert (bytesFile.longEnough( minBytes()));
        value = convert(bytesFile);
    }

    public UInt16(Integer value) {
        this.value = value;
    }

    public Integer convert(BytesFile bytesFile) {
        Integer result = 0;
        // little Endian!
        for (Integer i = 0; i < maxBits(); i = i + 8) {
            result += Byte.toUnsignedInt(bytesFile.readByte()) << i;
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
