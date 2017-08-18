package happynewmoonwithreport.type;

/**
 * An signed integer of 8 bits
 */
public class Int8 extends Int<Byte> {


    public Int8(Byte value) {
        this.value = value;
    }

	/* private functions **/

	/* Override DataTypeNumber */

    @Override
    public Integer maxBits() {
        return 8;
    }

    @Override
    public Byte minValue() {
        Integer minValue = (int) Math.pow(-2 , maxBits() - 1);
        return minValue.byteValue();

    }

    @Override
    public Byte maxValue() {
        Integer maxValue = (int) Math.pow(2 , maxBits() - 1) -1;
        return maxValue.byteValue();
    }

    /* override of Object **/
    @Override
    public String toString() {
        return "Int8{" +
                "value=" + value +
                "} ";
    }
}
