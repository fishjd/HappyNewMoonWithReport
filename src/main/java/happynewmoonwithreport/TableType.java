package happynewmoonwithreport;

/**
 * * <p> Source : <a href= "http://webassembly.org/docs/binary-encoding/#table_type">http://webassembly.org/docs/binary-encoding/#table_type</a>
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
}
