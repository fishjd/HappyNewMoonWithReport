package happynewmoonwithreport.type;

/**
 * varuintN
 * <p>
 * A LEB128 variable-length integer, limited to N bits (i.e., the values [0,
 * 2^N-1]), represented by at most ceil(N/7) bytes that may contain padding 0x80
 * bytes.
 *
 * @author James
 */

public abstract class VarUInt<ValueType extends Number> implements DataTypeNumber<ValueType> {
    protected ValueType value;

    public VarUInt() {
        super();
    }

    @Override
    public Integer maxBytes() {
        Integer maxBytes = new Double(Math.ceil((double) maxBits() / 7.0D)).intValue();
        return maxBytes;
    }

    @Override
    public Integer minBytes() {
        return 1;
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
        VarUInt<ValueType> other = (VarUInt<ValueType>) obj;
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
