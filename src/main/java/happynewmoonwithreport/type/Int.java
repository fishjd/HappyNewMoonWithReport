package happynewmoonwithreport.type;

public abstract class Int<ValueType extends Number> implements DataTypeNumber<ValueType> {

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

    public byte byteValue() {
        return value.byteValue();
    }
    public Integer integerValue() {
        return value.intValue();
    }
    public Long longValue(){
        return value.longValue();
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Int<ValueType> other = (Int<ValueType>) obj;
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

}
