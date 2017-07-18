package happynewmoonwithreport;

import happynewmoonwithreport.type.VarInt7;
import happynewmoonwithreport.type.VarUInt1;
import happynewmoonwithreport.type.VarUInt32;

import java.util.ArrayList;

public class FunctionSignature implements Module {

    // all the Function Types.
    ArrayList<FunctionType> functionSignitures;

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
    public void instantiate(byte[] payload) {
        Integer index = 0;

        ValueType form;
        VarUInt32 paramCount;
        VarUInt1 varReturnCount;

        // Type Count
        VarUInt32 typeCount = new VarUInt32(payload, index);
        index += typeCount.size();

        functionSignitures = new ArrayList<>(typeCount.IntegerValue());

        FunctionType functionType;
        for (Integer countFT = 0; countFT < typeCount.IntegerValue(); countFT++) {
            //* form
            form = new ValueType(payload, index);
            index += form.getSize();
            assert (form.getValue().equals("func"));

            //* Parameter Count
            paramCount = new VarUInt32(payload, index);
            index += paramCount.size();

            //* Parameters Types
            ArrayList<ValueType> paramAll = new ArrayList<>(paramCount.IntegerValue());
            for (Integer count = 0; count < paramCount.IntegerValue(); count++) {
                ValueType paramType = new ValueType(payload, index);
                index += paramType.getSize();
                paramAll.add(count, paramType);
            }

            //* Return Count
            VarUInt1 returnCount = new VarUInt1(payload, index);
            index += returnCount.size();
            // current version only allows 0 or 1
            assert (returnCount.value() <= 1);

            //* Return Types.
            ArrayList<ValueType> returnAll = new ArrayList<>(returnCount.value());
            for (Integer count = 0; count < returnCount.value(); count++) {
                ValueType returnType = new ValueType(payload, index);
                index += returnType.getSize();
                returnAll.add(count, returnType);
            }

            functionType = new FunctionType(form, paramCount, paramAll, returnCount, returnAll);
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
