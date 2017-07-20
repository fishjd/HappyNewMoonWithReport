package happynewmoonwithreport;

import happynewmoonwithreport.type.VarUInt32;

import java.util.ArrayList;

/**
 * Source <a href="http://webassembly.org/docs/binary-encoding/#memory-section"> http://webassembly.org/docs/binary-encoding/#memory-section</a>
 */
public class SectionGlobal implements Module {

    private VarUInt32 count;
    private ArrayList<GlobalVariableType> globals;

    /**
     * @param payload
     */
    @Override
    public void instantiate(BytesFile payload) {

        //* Count
        count = new VarUInt32(payload);

        //* Entries of Global Variables.
        globals = new ArrayList<>(count.integerValue());
        for (Integer index = 0; index < count.integerValue(); index++) {
            GlobalVariableType globalVariable = new GlobalVariableType(payload);
            globals.add(index, globalVariable);
        }
    }

    public VarUInt32 getCount() {
        return count;
    }

    public ArrayList<GlobalVariableType> getGlobals() {
        return globals;
    }
}
