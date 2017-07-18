package happynewmoonwithreport;

import happynewmoonwithreport.type.VarInt;
import happynewmoonwithreport.type.VarInt7;

import java.util.HashMap;
import java.util.Map;

/**
 * The data types in Web Assembly. int32, int64, f32, f64 <br>
 * Source : <a href=
 * "http://webassembly.org/docs/semantics/#types">http://webassembly.org/docs/semantics/#types</a>
 */
public enum ValueType {
    /**
     * 32 bit integer. In java stored as "Integer" @see Integer<br>
     * <p>
     * Source :
     * <a href="http://webassembly.org/docs/binary-encoding/#language-types">
     * http://webassembly.org/docs/binary-encoding/#language-types</a>
     */
    int32(-0x01), // byte value  0x7F

    /**
     * 64 bit integer. In java stored as "Long" @see Long <br>
     * <p>
     * Source :
     * <a href="http://webassembly.org/docs/binary-encoding/#language-types">
     * http://webassembly.org/docs/binary-encoding/#language-types</a>
     */
    int64(-0x02), // byte value  0x7e

    /**
     * 32 bit float. In java stored as "Float" @see Float<br>
     * <p>
     * Source :
     * <a href="http://webassembly.org/docs/binary-encoding/#language-types">
     * http://webassembly.org/docs/binary-encoding/#language-types</a>
     */
    f32(-0x03), // byte value  0x7d

    /**
     * 64 bit float. In java stored as "Double" @see Double <br>
     * <p>
     * Source :
     * <a href="http://webassembly.org/docs/binary-encoding/#language-types">
     * http://webassembly.org/docs/binary-encoding/#language-types</a>
     */
    f64(-0x04), // byte value  0x7C

    /**
     * Any Function<br>
     * <p>
     * Source :
     * <a href="http://webassembly.org/docs/binary-encoding/#language-types">
     * http://webassembly.org/docs/binary-encoding/#language-types</a>
     */
    anyFunc(-0x10), //  byte value  0x70

    /**
     * Function<br>
     * <p>
     * Source :
     * <a href="http://webassembly.org/docs/binary-encoding/#language-types">
     * http://webassembly.org/docs/binary-encoding/#language-types</a>
     */
    func(-0x20),  //  byte value  0x60

    /**
     * Pseudo type for representing an empty block_type<br>
     * <p>
     * Source :
     * <a href="http://webassembly.org/docs/binary-encoding/#language-types">
     * http://webassembly.org/docs/binary-encoding/#language-types</a>
     */
    emptyBlock(-0x40) // byte value  +0x40
    ; // <-- the terminating semicolon.

    private Integer type;

    private ValueType(Integer type) {
        this.type = type;
    }

    ValueType(byte[] byteAll, Integer index) {
        VarInt7 vt = new VarInt7(byteAll, index);
        this.type = vt.IntegerValue();

    }

    public Integer getType() {
        return type;
    }

    public VarInt7 getTypeVarInt7() {
        return new VarInt7(type);
    }

    private static Map<Integer, ValueType> map = new HashMap<Integer, ValueType>();

    static {
        for (ValueType legEnum : ValueType.values()) {
            map.put(legEnum.type, legEnum);
        }
    }

    public static ValueType valueOf(int input) {
        return map.get(input);
    }

    public static ValueType valueOf(VarInt7 input) {
        return map.get(input.IntegerValue());
    }

}
