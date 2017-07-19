package happynewmoonwithreport.type;

/**
 * varintN
 * <p>
 * A Signed LEB128 variable-length integer, limited to N bits (i.e., the values
 * [-2^(N-1), +2^(N-1)-1]), represented by at most ceil(N/7) bytes that may
 * contain padding 0x80 or 0xFF bytes.
 *
 * @author James
 */

public abstract class VarInt<ValueType extends Number> implements DataTypeNumber<ValueType> {
    protected ValueType value;

    public VarInt() {
        super();
    }

    public VarInt(ByteInput in) {
        setSize(in);
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

    public Integer setSize(ByteInput in) {
        int cur;
        int count = 0;
        in.reset();
        do {
            cur = in.readByte() & 0xff;
            count++;
        } while (((cur & 0x80) != 0) && count < maxBytes());

        return count;
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
        VarInt<ValueType> other = (VarInt<ValueType>) obj;
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
