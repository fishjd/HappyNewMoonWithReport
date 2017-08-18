package happynewmoonwithreport;

import happynewmoonwithreport.type.VarUInt1;

/**
 * The description of a global variable.
 * <p>
 * Source : <a href= "http://webassembly.org/docs/binary-encoding/#global_type" target="_top">
 * http://webassembly.org/docs/binary-encoding/#global_type
 * </a>
 */

public class GlobalType {

    /**
     * may only be "anyFunc" in MVP.
     */
    private ValueType contentType;
    private Mutability mutability;

    public GlobalType(BytesFile payload) {
        contentType = new ValueType(payload);
        mutability = new Mutability(new VarUInt1(payload));
    }

    public ValueType getContentType() {
        return contentType;
    }

    public void setContentType(ValueType contentType) {
        this.contentType = contentType;
    }

    public Mutability getMutability() {
        return mutability;
    }

    public void setMutability(Mutability mutability) {
        this.mutability = mutability;
    }


}
