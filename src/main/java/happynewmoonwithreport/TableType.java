package happynewmoonwithreport;

import happynewmoonwithreport.type.Int32;

/**
 * * <p> Source : <a href= "http://webassembly.org/docs/binary-encoding/#table_type">http://webassembly.org/docs/binary-encoding/#table_type</a>
 * <p>
 * Source:  <a href = "https://webassembly.github.io/spec/syntax/types.html#syntax-tabletype> Table Type</a>
 * <p>
 * <p>
 */

public class TableType {

    /**
     * may only be "anyFunc" in MVP.
     */
    private ElementType elementType;
    private ResizeableLimits limits;

    public TableType(BytesFile payload) {
        elementType = new ElementType(payload);
        limits = new ResizeableLimits(payload);
    }

    public TableType(ElementType elementType, ResizeableLimits limits) {
        this.elementType = elementType;
        this.limits = limits;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public ResizeableLimits getLimits() {
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
