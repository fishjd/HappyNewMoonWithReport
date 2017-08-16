package happynewmoonwithreport;

import happynewmoonwithreport.type.LimitType;
import happynewmoonwithreport.type.UInt32;

/**
 * Table Type
 * <p>
 * Source : <a href= "http://webassembly.org/docs/binary-encoding/#table_type" target="_top">
 * http://webassembly.org/docs/binary-encoding/#table_type
 * </a>
 * </p>
 *
 * Source:  <a href = "https://webassembly.github.io/spec/syntax/types.html#syntax-tabletype" target="_top">
 * Table Type
 * </a>
 *
 */
public class TableType implements Validation {

    /**
     * may only be "anyFunc" in MVP.
     */
    private ElementType elementType;
    private LimitType limit;

    public TableType(BytesFile payload) {
        elementType = new ElementType(payload);
        limit = new LimitType(payload);
    }

    public TableType(ElementType elementType, LimitType limit) {
        this.elementType = elementType;
        this.limit = limit;
    }
    // end constructors

    /**
     * The limits must be valid.
     * <p>
     * source:  <a href="https://webassembly.github.io/spec/valid/types.html#table-types" target="_top">
     * https://webassembly.github.io/spec/valid/types.html#table-types
     * </a>
     *
     * @return true if valid.
     */
    @Override
    public Boolean valid() {
        return limit.valid();
    }

    // boring getters and setters
    public ElementType getElementType() {
        return elementType;
    }

    /**
     * minimum  of the memory in Page Size
     *
     * @return min
     */
    public UInt32 minimum() {
        return limit.minimum();
    }

    /**
     * maximum of the memory in Page Size
     * <p>
     * Usage :
     * <code>
     * if (hasMaximum()) {
     * max = maximum();
     * }
     * </code>
     * <p>
     * Throws RuntimeException is maximum is not set.
     *
     * @return maximum
     */
    public UInt32 maximum() {
        return limit.maximum();
    }

    public UInt32 hasMaximum() {
        return limit.hasMaximum();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TableType{");
        sb.append("hasMaximum=").append(hasMaximum());
        sb.append(",minimum=").append(minimum());
        if (limit.hasMaximum().booleanValue()) {
            sb.append(", maximum=").append(maximum());
        }
        sb.append('}');
        return sb.toString();
    }

}
