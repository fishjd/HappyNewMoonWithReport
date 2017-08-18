package happynewmoonwithreport.type;

/**
 * A unsigned Integer of N bits.
 * <p>
 * Stored in the wasm file as a LEB128 variable-length integer, limited to N bits (i.e., the values [0,
 * 2^N-1]), represented by at most ceil(N/7) bytes that may contain padding 0x80
 * bytes.
 * <p>
 * <p>
 * Used to read and write to the wasm file. This project tends to use the 'main' integer types Int32, Int64, UInt32,
 * UInt64.  The recommend use is to convert to a 'main' type as soon as possible.
 * <p>
 * Usage:
 * <pre>
 *      {@code
 *          Int32 number = new VarUInt7(bytesFile);
 *      }
 *  </pre>
 * <p>
 * Source:  <a href="http://webassembly.org/docs/binary-encoding/#varuintn" target="_top">
 * http://webassembly.org/docs/binary-encoding/#varuintn
 * </a>
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

    @Override
    public byte byteValue() {
        return value.byteValue();
    }

    @Override
    public Integer integerValue() {
        return value.intValue();
    }

    @Override
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
