package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;

/**
 * An unsigned integer of 16 bits. .
 */
public class UInt16 extends UInt<Integer> {

    public UInt16(BytesFile bytesFile) {
        assert (bytesFile.longEnough(minBytes()));
        value = convert(bytesFile);
    }

    public UInt16(Integer value) {
        this.value = value;
    }

    public UInt16(DataTypeNumber number) {
        this.value = number.integerValue();
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
        return 1 << maxBits();
    }

	/* override of Object **/

    @Override
    public String toString() {
        return "UInt16{" +
                "value=" + value +
                "} ";
    }

}
