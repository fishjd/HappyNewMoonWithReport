package happynewmoonwithreport;

/**
 * <p>
 * Also know as Global Entry
 * </p>
 * Source : <a href= "http://webassembly.org/docs/binary-encoding/#global-entry" target="_top">
 * http://webassembly.org/docs/binary-encoding/#global-entry
 * </a>
 */
public class GlobalVariableType implements Validation {

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

    @Override
    public Boolean valid() {
        Boolean result = true;
        result &= type.valid();
        return result;
    }
}
