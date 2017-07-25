package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;

/**
 * An unsigned integer of N bits, represented in N/8 bytes in little endian
 * order. N is either 8, 16, or 32, 64
 */
public class UInt64 extends UInt<Long> {

    public UInt64() {
        super();
    }

    public UInt64(Long value) {
        this.value = value;
    }

    public UInt64(Integer value) {
        this.value = value.longValue();
    }

    @Override
    public Integer integerValue() {
        checkIfTooLarge();
        return value.intValue();
    }

    /**
     * Does the <code>value</code> lay between Integer.minValue and Integer.maxValue.  i.e Integer.minValue <= value <=
     * Integer.maxValue;
     **/
    public Boolean isBoundByInteger() {
        return (Integer.MIN_VALUE <= value && value <= Integer.MAX_VALUE);
    }

    public void checkIfTooLarge() {
        if (isBoundByInteger() == false) {
            throw new RuntimeException("Value is too large!");
        }
    }


	/* Override DataTypeNumber */

    @Override
    public Integer maxBits() {
        return 63;  // should be 64 but that is not possible using type Long.
    }

    @Override
    public Long minValue() {
        return 0L;
    }

    @Override
    public Long maxValue() {
        return (1L << (maxBits())) -1;
    }

	/* override of Object **/

    @Override
    public String toString() {
        return "UInt64{" +
                "value=" + value +
                "} ";
    }
}
