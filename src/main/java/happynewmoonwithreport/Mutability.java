package happynewmoonwithreport;


import happynewmoonwithreport.type.VarUInt1;

import java.util.HashMap;

/**
 * Represent the Mutability flag.
 * <p>
 * <p>
 * Source:  <a href="http://webassembly.org/docs/binary-encoding/#global_type" target="_top">
 * http://webassembly.org/docs/binary-encoding/#global_type
 * </a>
 */
public class Mutability extends ValueBase {

    public static final String immutable = "immutable";
    public static final String mutable = "mutable";

    public void setup() {
        mapAll = new HashMap<>();
        mapAll.put(0, immutable);
        mapAll.put(1, mutable);
    }

    private Mutability() {
        className = Mutability.class.getName();
        setup();
    }

    public Mutability(Integer type) {
        this();
        this.type = type;
        this.value = calcValue(type);
    }

    public Mutability(VarUInt1 type) {
        this();
        this.type = type.integerValue();
        this.value = calcValue(this.type);
    }

    public Mutability(BytesFile payload) {
        this();
        VarUInt1 vt = new VarUInt1(payload);
        this.type = vt.integerValue();
        this.value = calcValue(type);
    }

    public Mutability(String value) {
        this();
        this.value = value;
        this.type = calcType(value);
    }


}