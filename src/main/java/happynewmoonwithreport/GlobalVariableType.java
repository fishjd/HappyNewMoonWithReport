package happynewmoonwithreport;

/**
 * * <p> Source : <a href= "http://webassembly.org/docs/binary-encoding/#table_type" target="_top">http://webassembly.org/docs/binary-encoding/#table_type</a>
 * <p>
 */

public class GlobalVariableType {

    /**
     * may only be "anyFunc" in MVP.
     */
    private GlobalType type;

    // TODO
    // private ? InitialExpression;

    public GlobalVariableType(BytesFile payload) {
        type = new GlobalType(payload);
    }

    public GlobalType getType() {
        return type;
    }
}
