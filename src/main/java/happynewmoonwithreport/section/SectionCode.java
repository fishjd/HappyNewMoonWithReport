package happynewmoonwithreport.section;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.FunctionBody;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;

import java.util.ArrayList;

/**
 * <h1>Code section</h1>
 * <p>
 * The code section contains a body for every function in the Module. The count of function declared in the function
 * section and function bodies defined in this section must be the same and the ith declaration corresponds to the ith
 * function body.
 * </p>
 * <p>
 * Source:  <a href="http://webassembly.org/docs/binary-encoding/#code-section" target="_top">
 * http://webassembly.org/docs/binary-encoding/#code-section
 * </a>
 * <p>
 * Source:  <a href="http://webassembly.org/docs/modules/#function-and-code-sections" target="_top">
 * http://webassembly.org/docs/modules/#function-and-code-sections
 * </a>
 */
public class SectionCode implements Section {

    private UInt32 count;
    private ArrayList<FunctionBody> functionAll;


    /**
     * @param payload the input BytesFile.
     */
    @Override
    public void instantiate(BytesFile payload) {

        //* FunctionBody Count
        count = new VarUInt32(payload);

        //* Functions
        functionAll = new ArrayList<>(count.integerValue());
        for (Integer index = 0; index < count.integerValue(); index++) {
            FunctionBody function = new FunctionBody(payload);
            functionAll.add(index, function);
        }
    }

    public UInt32 getCount() {
        return count;
    }

    public ArrayList<FunctionBody> getFunctionAll() {
        return functionAll;
    }
}
