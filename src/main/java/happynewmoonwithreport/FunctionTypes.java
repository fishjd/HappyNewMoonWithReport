package happynewmoonwithreport;

import happynewmoonwithreport.type.VarUInt1;
import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.VarUInt7;

import java.util.ArrayList;

public class FunctionTypes implements Module {

    ArrayList<VarUInt32> indices;

    /**
     * Function section
     * <p>
     * The function section declares the signatures of all functions in the module (their definitions appear in the code
     * section).<p>
     * Field 	Type 	Description<p>
     * count 	varuint32 	count of signature indices to follow<p>
     * types    varuint32* 	sequence of indices into the type section<p>
     *
     * @param payload
     */
    @Override
    public void instantiate(byte[] payload) {
    }


}
