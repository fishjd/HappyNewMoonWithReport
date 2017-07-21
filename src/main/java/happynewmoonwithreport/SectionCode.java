package happynewmoonwithreport;

import happynewmoonwithreport.type.VarUInt7;

import java.util.HashMap;
import java.util.Map;

/* @formatter:off */
/**
 * Enum for the defined section names.
 * <p>
 *
 * Source http://webassembly.org/docs/binary-encoding/#high-level-structure<p>
 * Type 		1 	Function signature declarations<
 * Import 		2 	Import declarations
 * Function 	3 	Function declarations
 * Table 		4 	Indirect function table and other tables
 * Memory 		5 	Memory attributes
 * Global 		6 	Global declarations
 * Export 		7 	Exports
 * Start 		8 	Start function declaration
 * Element 	    9 	Elements section
 * Code 		10 	Function bodies (code)
 * Data 		11
 *//* @formatter:on */
public class SectionCode {
    private Integer type;
    private String value;

    public static final String TYPE = "type";
    public static final String IMPORT = "import";
    public static final String FUNCTION = "function";
    public static final String TABLE = "table";
    public static final String MEMORY = "memory";
    public static final String GLOBAL = "global";
    public static final String EXPORT = "export";
    public static final String START = "start";
    public static final String ELEMENT = "element";
    public static final String CODE = "code";
    public static final String DATA = "data";

    private SectionCode() {
    }

    private SectionCode(Integer type) {
        this();
        this.type = type;
        calcValue(type);
    }

    public SectionCode(VarUInt7 input) {
        this();
        this.type = input.integerValue();
        calcValue(type);
    }

    public SectionCode(BytesFile payload) {
        this();
        VarUInt7 vt = new VarUInt7(payload);
        this.type = vt.integerValue();
        calcValue(type);
    }

    public SectionCode(String value) {
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

    public VarUInt7 getTypeVarUInt7() {
        return new VarUInt7(type);
    }

    private static Map<Integer, String> mapAll;

    static {
        mapAll = new HashMap<>();
        mapAll.put(1, TYPE);
        mapAll.put(2, "import");
        mapAll.put(3, "function");
        mapAll.put(4, "table");
        mapAll.put(5, "memory");
        mapAll.put(6, "global");
        mapAll.put(7, "export");
        mapAll.put(8, "start");
        mapAll.put(9, "element");
        mapAll.put(10, "code");
        mapAll.put(11, "data");
    }


    private void calcValue(Integer input) {
        value = mapAll.get(input);
        if (value == null) {
            throw new RuntimeException("type in Section Code is not valid type = " + type);
        }

    }

    @Override
    public String toString() {
        return "SectionCode{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
