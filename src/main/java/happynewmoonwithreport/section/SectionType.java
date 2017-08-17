package happynewmoonwithreport.section;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.FunctionType;
import happynewmoonwithreport.ValueType;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt1;
import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.WasmVector;


/**
 * The type section declares all function signatures that will be used in the module.
 * <p>
 * Source: <a href = "http://webassembly.org/docs/binary-encoding/#type-section" target="_top">
 * http://webassembly.org/docs/binary-encoding/#type-section
 * </a>
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/binary/modules.html#type-section" target="_top">
 * https://webassembly.github.io/spec/binary/modules.html#type-section
 * </a>
 */
public class SectionType implements Section {

    // all the Function Types.
    private WasmVector<FunctionType> functionSignatures;


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
