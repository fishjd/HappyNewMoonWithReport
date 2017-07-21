package happynewmoonwithreport;

import happynewmoonwithreport.type.VarInt7;

import java.util.HashMap;
import java.util.Map;

/**
 * The Element Type  in Web Assembly. <p> A varint7 indicating the types of elements in a table. In the MVP, only one
 * type is available: <p> anyfunc <p> Note: In the future :unicorn:, other element types may be allowed. </p>
 * <p>
 * Source : <a href= "http://webassembly.org/docs/binary-encoding/#elem_type">http://webassembly.org/docs/binary-encoding/#elem_type</a>
 * <p>
 */
public class ElementType {

    private Integer type;
    private String value;

    private ElementType() {

    }

    /**
     * note use the integer value <code>new ElementType(-0x10)</code> <b>not</code> the byte in the *.wasm file
     * <code>new ElementType(0x70)</code>.
     *
     * @param type
     */
    public ElementType(Integer type) {
        this();
        this.type = type;
        calcValue(type);
    }

    public ElementType(VarInt7 input) {
        this();
        this.type = input.integerValue();
        calcValue(type);
    }

    public ElementType(BytesFile payload) {
        this();
        VarInt7 vt = new VarInt7(payload);
        this.type = vt.integerValue();
        calcValue(type);
    }

    public ElementType(String value) {
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

    public VarInt7 getTypeVarInt7() {
        return new VarInt7(type);
    }

    private static Map<Integer, String> mapAll;

    static {
        mapAll = new HashMap<>();

        // anyFunc(-0x10)    byte value  0x70
        mapAll.put(-0x10, "anyFunc");

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

        ElementType that = (ElementType) o;

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
