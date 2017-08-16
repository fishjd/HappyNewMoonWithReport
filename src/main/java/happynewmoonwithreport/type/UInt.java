package happynewmoonwithreport.type;

public abstract class UInt<ValueType extends Number> implements DataTypeNumber<ValueType> {

    protected ValueType value;

    @Override
    public Integer maxBytes() {
        Integer maxBytes = maxBits() / 8;
        return maxBytes;
    }

    @Override
    public Integer minBytes() {
        Integer maxBytes = maxBits() / 8;
        return maxBytes;
    }

    @Override
    public ValueType value() {
        return value;
    }

    // I don't think it is worth adding minValue() and maxValue() if the price
    // is you are required to add the Class<ValueType> as a parameter. It is
    // easier to add in the sub classes.
    //
    // public <ValueType extends Number> ValueType minValue(Class<ValueType>
    // valueType) {
    // return valueType.cast(0);
    // }
    //

    public Boolean isTrue() {
        return value.intValue()!= 0;
    }

    public Boolean booleanValue() {
        return value.intValue() != 0;
    }

    public byte byteValue() {
        return value.byteValue();
    }

    public Integer integerValue() {
        return value.intValue();
    }

    public Long longValue() {
        return value.longValue();
    }

    /**
     * Does the <code>value</code> lay between Integer.minValue and Integer.maxValue.  i.e
     * <code>Integer.minValue &lt;= value &lt;= Integer.maxValue;</code>
     **/
    @Override
    public Boolean isBoundByInteger() {
        return (Integer.MIN_VALUE <= value.longValue() && value.longValue() <= Integer.MAX_VALUE);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UInt)) return false;

        UInt<?> uInt = (UInt<?>) o;

        return value.equals(uInt.value);
    }
}
