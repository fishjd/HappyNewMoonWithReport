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
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Int)) return false;

        Int<?> anInt = (Int<?>) other;

        return value.equals(anInt.value);
    }
}
