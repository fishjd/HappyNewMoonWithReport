package happynewmoonwithreport;

import happynewmoonwithreport.type.VarUInt;
import happynewmoonwithreport.type.VarUInt32;

import java.util.ArrayList;

public class SectionFunction implements Module {

    private VarUInt32 count;
    private ArrayList<VarUInt32> types;

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
    public void instantiate(BytesFile payload) {

        //* Parameter Count
        count = new VarUInt32(payload);

        //* Parameters Types
        types = new ArrayList<>(count.integerValue());
        for (Integer index = 0; index < count.integerValue(); index++) {
            VarUInt32 type = new VarUInt32(payload);
            types.add(index, type);
        }
    }

    public VarUInt32 getCount() {
        return count;
    }

    public ArrayList<VarUInt32> getTypes() {
        return types;
    }
}
