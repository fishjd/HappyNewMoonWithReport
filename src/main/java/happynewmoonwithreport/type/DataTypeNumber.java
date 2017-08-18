/**
 *
 */
package happynewmoonwithreport.type;

/**
 * A number read from the *.wasm file. It is usually create with a collection of bytes.
 *
 * @param <ValueType> The backing type. The Type returned by the value() function.
 */
public interface DataTypeNumber<ValueType extends Number> {

    /**
     * The value of the class
     *
     * @return value.
     */
    ValueType value();


    /**
     * The value converted to a byte type.
     *
     * @return value as a byte
     */
    byte byteValue();  // TODO convert byte to Byte.


    /**
     * The value converted to a Integer type.
     *
     * @return value as a Integer.
     */
    Integer integerValue();

    /**
     * The value converted to a Long type.
     *
     * @return value as a Long.
     */
    Long longValue();

    /**
     * Does the <code>value</code> lay between Integer.minValue and Integer.maxValue.  i.e
     * <code>Integer.minValue &lt;= value &lt;= Integer.maxValue;</code>
     * <p>
     * This will <b>always</b> be true for class where the ValueType is Integer, Byte. But may be false when ValueType
     * is Long.
     *
     * @return true if between Integer.minValue and Integer.maxValue.
     **/
    Boolean isBoundByInteger();

    /**
     * The minimum number of bytes this number may be represented by. For UInt this is fixed and is the same as
     * maxBytes(). For VarUInt it may be less tha maxBytes().
     *
     * @return minimum number of bytes.
     */
    Integer minBytes();

    /**
     * The maximum number of bytes this number may be represented by. For UInt this is fixed. For VarUInt if may vary.
     * <p>
     * <code> maxBytes = ceiling(maxBits/8); </code>
     *
     * @return maximum number of bytes.
     */
    Integer maxBytes();

    /**
     * The minimum number of bites this number is represented by. It is constant. Usually in the class name VarUInt32
     * sets maxBits to 32.
     *
     * @return maximum number of bits
     */
    Integer maxBits();

    /**
     * The minimum value that may be held. For Unsigned it is zero.
     *
     * @return minimum number of bits
     */
    ValueType minValue();

    /**
     * The maximum value that may be held. For Unsigned it is zero.
     *
     * @return maximum value
     */
    ValueType maxValue();

}
