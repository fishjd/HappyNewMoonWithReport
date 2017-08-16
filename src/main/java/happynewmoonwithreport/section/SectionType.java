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
     *
     * Source: <a href = "http://webassembly.org/docs/binary-encoding/#type-section" target="_top">
     *     http://webassembly.org/docs/binary-encoding/#type-section
     * </a>
     *
     * @param payload the input BytesFile.
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
