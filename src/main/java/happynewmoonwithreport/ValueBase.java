package happynewmoonwithreport;


import java.util.Map;

/**
 * An extension of java.Enum all entries contain a Key and Value.   Constructors provided for both key and value.
 */
public class ValueBase {

    protected String className;
    protected Integer type;
    protected String value;

    protected static Map<Integer, String> mapAll;

    /**
     * get the value
     *
     * @return
     */
    public String getValue() {
        return value;
    }

    public Integer getType() {
        return type;
    }

    /**
     * Find the value and set type and value.  Used in constructors.
     *
     * @param value value
     */
    protected Integer calcType(String value) {
        Integer result = null;
        Boolean found = false;
        for (Map.Entry<Integer, String> entry : mapAll.entrySet()) {
            if (value.equals(entry.getValue())) {
                result = entry.getKey();
                found = true;
            }
        }
        if (found == false) {
            throw new RuntimeException(className + " " + value + " not valid/found");
        }
        return result;
    }

    /**
     * Find the value and set the value.
     *
     * @param input input
     */
    protected String  calcValue(Integer input) {
        String result = mapAll.get(input);
        if (result  == null) {
            throw new RuntimeException("Type in " + className + " is not valid type = " + type + " hex = 0x" + Integer.toHexString(input));
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValueBase that = (ValueBase) o;

        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public String toString() {
        return className + "{" +
                "type = " + type +
                ", value = " + value +
                '}';
    }

}
