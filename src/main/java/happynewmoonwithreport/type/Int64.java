package happynewmoonwithreport.type;

/**
 * An signed integer of N bits,
 */
public class Int64 extends Int<Long> {
    public Int64() {
        super();
    }

    public Int64(Integer value) {
        this();
        this.value = value.longValue();
    }

    public Int64(DataTypeNumber number) {
        this.value = number.longValue();
    }

    public Int64 (Long value){
        this.value = value;
    }

	/* private functions **/

	/* Override DataTypeNumber */

    @Override
    public Integer maxBits() {
        return 32;
    }

    public Long minValue() {
        Long minValue = -1L * (1L << (maxBits() - 1L));
        return minValue;

    }

    public Long maxValue() {
        Long maxValue = (1L << (maxBits() - 1L)) - 1L;
        return maxValue;
    }


    /* override of Object **/
    @Override
    public String toString() {
        return "Int64{" +
                "value=" + value +
                "} ";
    }
}
