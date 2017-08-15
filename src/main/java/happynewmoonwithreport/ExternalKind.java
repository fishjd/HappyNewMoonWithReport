package happynewmoonwithreport;

import happynewmoonwithreport.type.UInt8;

import java.util.HashMap;
import java.util.Map;

/* @formatter:off */
/**
 * external_kind
 *
 *  A single-byte unsigned integer indicating the kind of definition being imported or defined:
 *
 *  0 indicating a Function import or definition
 *  1 indicating a Table import or definition
 *  2 indicating a Memory import or definition
 *  3 indicating a Global import or definition
 *
 * <p>
 * Source : <a href= "http://webassembly.org/docs/binary-encoding/#external_kind" target="_top">http://webassembly.org/docs/binary-encoding/#external_kind</a>
 * <p>
 *//* @formatter:on */
public class ExternalKind {

    private Integer type;
    private String value;

    public static final String function = "function";
    public static final String table = "table";
    public static final String memory = "memory";
    public static final String global = "global ";

    private static Map<Integer, String> mapAll;

    static {
        mapAll = new HashMap<>();
        mapAll.put(0, function);
        mapAll.put(1, table);
        mapAll.put(2, memory);
        mapAll.put(3, global);
    }

    private ExternalKind() {

    }

    /**
     * note use the integer value <code>new ElementType(-0x10)</code> <b>not</code> the byte in the *.wasm file
     * <code>new ElementType(0x70)</code>.
     *
     * @param type
     */
    public ExternalKind(Integer type) {
        this();
        this.type = type;
        calcValue(type);
    }

    public ExternalKind(UInt8 input) {
        this();
        this.type = input.integerValue();
        calcValue(type);
    }

    public ExternalKind(BytesFile payload) {
        this();
        UInt8 vt = new UInt8(payload);
        this.type = vt.integerValue();
        calcValue(type);
    }

    public ExternalKind(String value) {
        this();
        Boolean found = false;
        for (Map.Entry<Integer, String> entry : mapAll.entrySet()) {
            if (value.equals(entry.getValue())) {
                this.type = entry.getKey();
                this.value = value;
                found = true;
            }
        }
        if (found == false) {
            throw new RuntimeException("Element Type " + value + " not valid/found");
        }
    }

    public String getValue() {
        return value;
    }

    public Integer getType() {
        return type;
    }

    public UInt8 getTypeUInt8() {
        return new UInt8(type);
    }

    private void calcValue(Integer input) {
        value = mapAll.get(input);
        if (value == null) {
            throw new RuntimeException("type in ElementType is not valid type = " + type + " hex = 0x" + Integer.toHexString(input));
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExternalKind that = (ExternalKind) o;

        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public String toString() {
        return "ElementType{" +
                "type = " + type +
                ", value = " + value +
                '}';
    }
}
