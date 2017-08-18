package happynewmoonwithreport.section;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.GlobalVariableType;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.WasmVector;


/**
 * The encoding of the Global section:
 * <p>
 * Source <a href="http://webassembly.org/docs/binary-encoding/#memory-section" target="_top">
 * http://webassembly.org/docs/binary-encoding/#memory-section
 * </a>
 * <p>
 * Source:  <a href="http://webassembly.org/docs/modules/#global-section" target="_top">
 * http://webassembly.org/docs/modules/#global-section
 * </a>
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/binary/modules.html#global-section" target="_top">
 * https://webassembly.github.io/spec/binary/modules.html#global-section
 * </a>
 */
public class SectionGlobal implements Section {

    private UInt32 count;
    private WasmVector<GlobalVariableType> globals;

    /**
     * @param payload the input BytesFile.
     */
    @Override
    public void instantiate(BytesFile payload) {

        //* Count
        count = new VarUInt32(payload);

        //* Entries of Global Variables.
        globals = new WasmVector<>(count.integerValue());
        for (Integer index = 0; index < count.integerValue(); index++) {
            GlobalVariableType globalVariable = new GlobalVariableType(payload);
            globals.add(index, globalVariable);
        }
    }

    public UInt32 getCount() {
        return count;
    }

    public WasmVector<GlobalVariableType> getGlobals() {
        return globals;
    }
}
