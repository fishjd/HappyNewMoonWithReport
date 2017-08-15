package happynewmoonwithreport.section;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.FunctionType;
import happynewmoonwithreport.ValueType;
import happynewmoonwithreport.type.VarUInt1;
import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.WasmVector;

public class SectionType implements Section {

    // all the Function Types.
    private WasmVector<FunctionType> functionSignatures;

    /**
     * The type section declares all function signatures that will be used in the module.
     * <p>
     * <table> <tr> <td>Field</td> <td>Type</td> <td>Description</td> </tr> <tr> <td>count</td> <td>varuint32</td>
     * <td>count of type entries to follow</td> <tr> <tr> <td>entries</td> <td>func_type</td> <td>repeated type entries
     * as described above</td> <tr> </table> Source: <a href = "http://webassembly.org/docs/binary-encoding/#type-section">http://webassembly.org/docs/binary-encoding/#type-section</a>
     *
     * @param payload
     */
    @Override
    public void instantiate(BytesFile payload) {

        ValueType form;
        UInt32 paramCount;
        VarUInt1 varReturnCount;

        // Type Count
        UInt32 typeCount = new VarUInt32(payload);

        functionSignatures = new WasmVector<>(typeCount.integerValue());

        FunctionType functionType;
        for (Integer countFT = 0; countFT < typeCount.integerValue(); countFT++) {
            functionType = new FunctionType(payload);
            functionSignatures.add(countFT, functionType);
        }
    }

    public WasmVector<FunctionType> getFunctionSignatures() {
        return functionSignatures;
    }

    public Integer getSize() {
        return functionSignatures.size();
    }

}
