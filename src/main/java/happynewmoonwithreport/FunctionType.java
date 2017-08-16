package happynewmoonwithreport;

import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt1;
import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.WasmVector;

import java.util.ArrayList;

;

/**
 * Source: <a href = "http://webassembly.org/docs/binary-encoding/#func_type" target="_top">
 * http://webassembly.org/docs/binary-encoding/#func_type
 * </a>
 * <p>
 * <a href = "https://webassembly.github.io/spec/binary/types.html#function-types" target="_top">
 * https://webassembly.github.io/spec/binary/types.html#function-types</a>
 */
public class FunctionType implements Validation {

    /**
     * Always 0x60  ValueType("func")
     */
    private ValueType form;

    /**
     * The number of parameters
     */
    private UInt32 paramCount;

    /**
     * Array of the types of parameters
     */
    private WasmVector<ValueType> paramTypeAll;

    /**
     * The number of return values Currently only zero or one value
     */
    private UInt32 returnCount;

    /**
     * Array of the types of of the return values.
     */
    private WasmVector<ValueType> returnTypeAll;

    /**
     * Create a FunctionType from a BytesFile.
     *
     * @param payload the input BytesFile.
     */
    public FunctionType(BytesFile payload) {
        //* form
        form = new ValueType(payload);
        assert (form.getValue().equals("func"));

        //* Parameter Count
        paramCount = new VarUInt32(payload);

        //* Parameters Types
        paramTypeAll = new WasmVector<>(paramCount.integerValue());
        for (Integer count = 0; count < paramCount.integerValue(); count++) {
            ValueType paramType = new ValueType(payload);
            paramTypeAll.add(count, paramType);
        }

        //* Return Count
        returnCount = new VarUInt1(payload);
        // current version only allows 0 or 1
        assert (returnCount.value() <= 1);

        //* Return Types.
        returnTypeAll = new WasmVector<>(returnCount.integerValue());
        for (Integer count = 0; count < returnCount.integerValue(); count++) {
            ValueType returnType = new ValueType(payload);
            returnTypeAll.add(count, returnType);
        }

    }


    public FunctionType(UInt32 paramCount, WasmVector<ValueType> paramTypeAll,
                        UInt32 returnCount, WasmVector<ValueType> returnTypeAll) {
        this(new ValueType("func"), paramCount, paramTypeAll, returnCount, returnTypeAll);
    }



    public FunctionType(ValueType form, UInt32 paramCount, WasmVector<ValueType> paramTypeAll,
                        UInt32 returnCount, WasmVector<ValueType> returnTypeAll) {
        super();
        this.form = form;
        this.paramCount = paramCount;
        this.paramTypeAll = paramTypeAll;
        this.returnCount = returnCount;
        this.returnTypeAll = returnTypeAll;
    }

    /**
     * Function types may not specify more than one result.
     * <p>
     * source:  <a href="https://webassembly.github.io/spec/valid/types.html#function-types" target="_top">
     * https://webassembly.github.io/spec/valid/types.html#function-types</a>
     * <p>
     * Note:
     * This restriction may be removed in future versions of WebAssembly.
     *
     * @return true if valid.
     */
    @Override
    public Boolean valid() {
        Boolean isValid;

        isValid = returnCount.integerValue() <= 1;
        isValid &= 0 <= returnCount.integerValue();

        return isValid;
    }


    public ValueType getForm() {
        return form;
    }


    public UInt32 getParamCount() {
        return paramCount;
    }


    public ArrayList<ValueType> getParamTypeAll() {
        return paramTypeAll;
    }


    public UInt32 getReturnCount() {
        return returnCount;
    }

    public ArrayList<ValueType> getReturnTypeAll() {
        return returnTypeAll;
    }

    @Override
    public String toString() {
        return "FunctionType{" +
                "form=" + form +
                ", paramCount=" + paramCount +
                ", paramTypeAll=" + paramTypeAll +
                ", returnCount=" + returnCount +
                ", returnTypeAll=" + returnTypeAll +
                '}';
    }
}
