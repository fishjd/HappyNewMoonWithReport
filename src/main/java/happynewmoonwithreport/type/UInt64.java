package happynewmoonwithreport.type;

/**
 * An unsigned integer of 64 bits.
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

    public void checkIfTooLarge() {
        if (isBoundByInteger() == false) {
            throw new RuntimeException("Value is too large!");
        }
    }


	/* Override DataTypeNumber */

    @Override
    public Integer maxBits() {
        return 63;  // should be 64 but that is not possible using Java type Long.
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
