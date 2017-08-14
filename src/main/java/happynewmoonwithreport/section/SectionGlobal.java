package happynewmoonwithreport.section;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.GlobalVariableType;
import happynewmoonwithreport.type.VarUInt32;

import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.WasmVector;


/**
 * Source <a href="http://webassembly.org/docs/binary-encoding/#memory-section"> http://webassembly.org/docs/binary-encoding/#memory-section</a>
 */
public class SectionGlobal implements Section {

    private UInt32 count;
    private WasmVector<GlobalVariableType> globals;

    /**
     * @param payload
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
