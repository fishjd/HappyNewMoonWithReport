package happynewmoonwithreport;

import happynewmoonwithreport.type.VarUInt1;
import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.VarUInt7;

import java.util.ArrayList;

public class FunctionSigniture implements Module {

    // all the Function Types.
    ArrayList<FunctionType> functionSignitures;

    /**
     * The type section declares all function signatures that will be used in
     * the module.
     * <p>
     * <table>
     * <tr>
     * <td>Field</td>
     * <td>Type</td>
     * <td>Description</td>
     * </tr>
     * <tr>
     * <td>count</td>
     * <td>varuint32</td>
     * <td>count of type entries to follow</td>
     * <tr>
     * <tr>
     * <td>entries</td>
     * <td>func_type</td>
     * <td>repeated type entries as described above</td>
     * <tr>
     * </table>
     * Source: <a href =
     * "http://webassembly.org/docs/binary-encoding/#type-section">http://webassembly.org/docs/binary-encoding/#type-section</a>
     *
     * @param payload
     */
    @Override
    public void instantiate(byte[] payload) {
        Integer payloadIndex = 0;

        // Type Count
        VarUInt32 leb32TypeCount = new VarUInt32(payload, payloadIndex);
        payloadIndex += leb32TypeCount.size();

        functionSignitures = new ArrayList<>(leb32TypeCount.IntegerValue());

        FunctionType functionType;
        for (Integer countFT = 0; countFT < leb32TypeCount.IntegerValue(); countFT++) {
            // form
            VarUInt7 varForm = new VarUInt7(payload, payloadIndex);
            Integer form = varForm.value();
            payloadIndex += varForm.size();

            assert (form.equals(ValueType.func.getType()));

            // Parameter Count
            VarUInt32 varParamCount = new VarUInt32(payload, payloadIndex);
            payloadIndex += varParamCount.size();

            // Parameters Types
            ArrayList<ValueType> paramAll = new ArrayList<>(varParamCount.IntegerValue());
            for (Integer count = 0; count < varParamCount.IntegerValue(); count++) {
                VarUInt7 varParmType = new VarUInt7(payload, payloadIndex);
                payloadIndex += varParmType.size();

                Integer paramType = varParmType.value();
                ValueType valueType = ValueType.valueOf(paramType);
                paramAll.add(count, valueType);
            }

            // Return Count
            VarUInt1 varReturnCount = new VarUInt1(payload, payloadIndex);
            Integer returnCount = varReturnCount.value();
            payloadIndex += varReturnCount.size();
            // current version only allows 0 or 1
            assert (returnCount <= 1);

            // Return Types.
            ArrayList<ValueType> returnAll = new ArrayList<>(returnCount);
            for (Integer count = 0; count < returnCount; count++) {
                VarUInt7 varParmType = new VarUInt7(payload, payloadIndex);
                payloadIndex += varParmType.size();

                Integer paramType = varParmType.value();
                ValueType valueType = ValueType.valueOf(paramType);
                returnAll.add(count, valueType);
            }

            functionType = new FunctionType(form, varParamCount.IntegerValue(), paramAll, returnCount, returnAll);
            functionSignitures.add(countFT, functionType);
        }
    }

    public ArrayList<FunctionType> getFunctionSignitures() {
        return functionSignitures;
    }

    public Integer getSize() {
        return functionSignitures.size();
    }

}
