/**
 *
 */
package happynewmoonwithreport.type;

/**
 * A number read from the *.wasm file. It is usually create with a collection of
 * bytes.
 *
 * @param <ValueType> The backing type. The Type returned by the value() function.
 */
public interface DataTypeNumber<ValueType extends Number> {

    /**
     * The value of the class
     *
     * @return
     */
    ValueType value();

    /**
     * The minimum number of bytes this number may be represented by. For UIint
     * this is fixed and is the same as maxBytes(). For VarUInt it is fixed.
     *
     * @return
     */
    Integer minBytes();

    /**
     * The maximum number of bytes this number may be represented by. For UIint
     * this is fixed. For VarUInt if may vary.
     *
     * @return
     */
    Integer maxBytes();

    /**
     * The minimum number of bytes this number is represented by. It is
     * constant. Usually in the class name VarUInt32 sets maxBits to 32.
     *
     * @return
     */
    Integer maxBits();

    /**
     * The minimum value that may be held. For Unsigned it is zero.
     *
     * @return
     */
    ValueType minValue();

    /**
     * The maximum value that may be held. For Unsigned it is zero.
     *
     * @return
     */
    ValueType maxValue();

}
