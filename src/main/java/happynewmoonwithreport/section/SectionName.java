package happynewmoonwithreport.section;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.type.VarUInt7;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum for the defined section names.
 * <p>
 * source:  <a href="http://webassembly.org/docs/binary-encoding/#high-level-structure" target="_top">
 * http://webassembly.org/docs/binary-encoding/#high-level-structure
 * </a>
 *
 * <pre>
 * Type             1     Function signature declarations
 * Import           2     Import declarations
 * Function         3     Function declarations
 * Table            4     Indirect function table and other tables
 * Memory           5     Memory attributes
 * Global           6     Global declarations
 * Export           7     Exports
 * Start            8     Start function declaration
 * Element          9     Elements section
 * Code             10    Function bodies (code)
 * Data             11    Data
 * </pre>
 */
public class SectionName {
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

    private SectionName() {
    }

    private SectionName(Integer type) {
        this();
        this.type = type;
        calcValue(type);
    }

    public SectionName(VarUInt7 input) {
        this();
        this.type = input.integerValue();
        calcValue(type);
    }

    public SectionName(BytesFile payload) {
        this();
        VarUInt7 vt = new VarUInt7(payload);
        this.type = vt.integerValue();
        calcValue(type);
    }

    public SectionName(String value) {
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
        mapAll.put(2, IMPORT);
        mapAll.put(3, FUNCTION);
        mapAll.put(4, TABLE);
        mapAll.put(5, MEMORY);
        mapAll.put(6, GLOBAL);
        mapAll.put(7, EXPORT);
        mapAll.put(8, START);
        mapAll.put(9, ELEMENT);
        mapAll.put(10, CODE);
        mapAll.put(11, DATA);
    }


    private void calcValue(Integer input) {
        value = mapAll.get(input);
        if (value == null) {
            throw new RuntimeException("type in Section Code is not valid type = " + type);
        }

    }

    @Override
    public String toString() {
        return "SectionName{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
