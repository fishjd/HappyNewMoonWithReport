package happynewmoonwithreport;

import happynewmoonwithreport.type.Int32;

/**
 * * <p> Source : <a href= "http://webassembly.org/docs/binary-encoding/#table_type">http://webassembly.org/docs/binary-encoding/#table_type</a>
 * <p>
 * Source:  <a href = "https://webassembly.github.io/spec/syntax/types.html#syntax-tabletype> Table Type</a>
 * <p>
 * <p>
 */

public class TableType implements Validation {

    /**
     * may only be "anyFunc" in MVP.
     */
    private ElementType elementType;
    private LimitType limits;

    public TableType(BytesFile payload) {
        elementType = new ElementType(payload);
        limits = new LimitType(payload);
    }

    public TableType(ElementType elementType, LimitType limits) {
        this.elementType = elementType;
        this.limits = limits;
    }
    // end constructors

    /**
     * The limits must be valid.
     * <p>
     * source:  <a href="https://webassembly.github.io/spec/valid/types.html#table-types">
     * https://webassembly.github.io/spec/valid/types.html#table-types
     * </a>
     *
     * @return true if valid.
     */
    @Override
    public Boolean valid() {
        return limits.valid();
    }

    // boring getters and setters
    public ElementType getElementType() {
        return elementType;
    }

    public LimitType getLimits() {
        return limits;
    }

    /**
     * min and max are to satisfy the minimum requirements of Table Type.
     *
     * @return min
     */
    Int32 min() {
        return new Int32(0);
    }

    /**
     * min and max are to satisfy the minimum requirements of Table Type.
     *
     * @return max
     */
    Int32 max() {
        return new Int32(Integer.MAX_VALUE);
    }

}
