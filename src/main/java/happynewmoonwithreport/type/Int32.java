package happynewmoonwithreport.type;

/**
 * An signed integer of 32 bits,
 */
public class Int32 extends Int<Integer> {
    public Int32() {
        super();
    }

    public Int32(Integer value) {
        this();
        this.value = value;
    }

    public Int32(DataTypeNumber number) {
        this.value = number.integerValue();
    }

	/* private functions **/

	/* Override DataTypeNumber */

    @Override
    public Integer maxBits() {
        return 32;
    }

    @Override
    public Integer minValue() {
        Integer minValue = -1 * (1 << (maxBits() - 1));
        return minValue;

    }

    @Override
    public Integer maxValue() {
        Integer maxValue = (1 << (maxBits() - 1)) - 1;
        return maxValue;
    }


    /* override of Object **/
    @Override
    public String toString() {
        return "Int32{" +
                "value=" + value +
                "} ";
    }
}
