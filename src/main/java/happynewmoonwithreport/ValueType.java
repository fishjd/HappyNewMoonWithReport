package happynewmoonwithreport;

import happynewmoonwithreport.type.VarInt7;

import java.util.HashMap;
import java.util.Map;

/**
 * The data types in Web Assembly. int32, int64, f32, f64 <p>
 * <p>
 * Source : <a href= "http://webassembly.org/docs/semantics/#types">http://webassembly.org/docs/semantics/#types</a>
 * <p>
 * Source : <a href="http://webassembly.org/docs/binary-encoding/#language-types">
 * http://webassembly.org/docs/binary-encoding/#language-types</a>
 */
public class ValueType {

    private Integer type;
    private String value;

    private ValueType() {
    }

    public ValueType(Integer type) {
        this();
        this.type = type;
        calcValue(type);
    }

    public ValueType(VarInt7 input) {
        this();
        this.type = input.IntegerValue();
        calcValue(type);
    }

    public ValueType(BytesFile payload) {
        this();
        VarInt7 vt = new VarInt7(payload);
        this.type = vt.IntegerValue();
        calcValue(type);
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

    private static Map<Integer, String> mapNew;

    static {
        mapNew = new HashMap<>();

        // int32(-0x01)      byte value  0x7F
        mapNew.put(-0x01, "int32");
        // int64(-0x02)      byte value  0x7e
        mapNew.put(-0x02, "int64");
        // f32(-0x03)        byte value  0x7d
        mapNew.put(-0x03, "f32");
        // f64(-0x04)        byte value  0x7C
        mapNew.put(-0x04, "f64");
        // anyFunc(-0x10)    byte value  0x70
        mapNew.put(-0x10, "anyFunc");
        // func(-0x20)       byte value  0x60
        mapNew.put(-0x20, "func");
        // emptyBlock(-0x40) byte value  0x40
        mapNew.put(-0x40, "emptyBlock");

    }

    private void calcValue(Integer input) {
        value = mapNew.get(input);
        if (value == null) {
            throw new RuntimeException("type in ValueType is not valid type = " + type);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValueType valueType = (ValueType) o;

        return type.equals(valueType.type);
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public String toString() {
        return "ValueType{" +
                "type = " + type +
                ", value = " + mapNew.get(type) +
                '}';
    }
}
