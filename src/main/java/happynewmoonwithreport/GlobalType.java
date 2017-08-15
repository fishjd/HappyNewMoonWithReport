package happynewmoonwithreport;

import happynewmoonwithreport.type.VarUInt1;

/**
 * * <p> Source : <a href= "http://webassembly.org/docs/binary-encoding/#table_type" target="_top">http://webassembly.org/docs/binary-encoding/#table_type</a>
 * <p>
 */

public class GlobalType {

    /**
     * may only be "anyFunc" in MVP.
     */
    private ValueType contentType;
    private VarUInt1 mutability;

    public GlobalType(BytesFile payload) {
        contentType = new ValueType(payload);
        mutability = new VarUInt1(payload);
    }

    public ValueType getContentType() {
        return contentType;
    }

    public void setContentType(ValueType contentType) {
        this.contentType = contentType;
    }

    public VarUInt1 getMutability() {
        return mutability;
    }

    public void setMutability(VarUInt1 mutability) {
        this.mutability = mutability;
    }
}
