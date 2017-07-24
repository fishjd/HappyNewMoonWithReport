package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;

/**
 * An signed integer of N bits, represented in N/8 bytes in little endian
 * order. N is either 8, 16, or 32.
 */
public class Int8 extends UInt<Integer> {

    public Int8(BytesFile bytesFile) {
        assert (bytesFile.longEnough( minBytes()));
        value = convert(bytesFile);
    }

    public Int8(Integer value) {
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
        return 8;
    }

    public Integer minValue() {
        return -2 ^ (maxBits() - 1);
    }

    public Integer maxValue() {
        return +2 ^ (maxBits() - 1) - 1;
    }
	/* override of Object **/
    @Override
    public String toString() {
        return "Int8{" +
                "value=" + value +
                "} ";
    }
}
