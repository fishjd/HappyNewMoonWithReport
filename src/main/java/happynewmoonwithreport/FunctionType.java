package happynewmoonwithreport;

import happynewmoonwithreport.type.VarInt7;
import happynewmoonwithreport.type.VarUInt1;
import happynewmoonwithreport.type.VarUInt32;

import java.util.ArrayList;

/**
 * Source: http://webassembly.org/docs/binary-encoding/#func_type
 */
public class FunctionType {

    /**
     * No Clue?
     */
    private ValueType form;

    /**
     * The number of parameters
     */
    private VarUInt32 paramCount;

    /**
     * Array of the types of parameters
     */
    private ArrayList<ValueType> paramTypeAll;

    /**
     * The number of return values Currently only zero or one value
     */
    private VarUInt1 returnCount;

    /**
     * Array of the types of of the return values.
     */
    private ArrayList<ValueType> returnTypeAll;

    public FunctionType(ValueType form, VarUInt32 paramCount, ArrayList<ValueType> paramTypeAll,
                        VarUInt1 returnCount, ArrayList<ValueType> returnTypeAll) {
        super();
        this.form = form;
        this.paramCount = paramCount;
        this.paramTypeAll = paramTypeAll;
        this.returnCount = returnCount;
        this.returnTypeAll = returnTypeAll;
    }

    public ValueType getForm() {
        return form;
    }


    public VarUInt32 getParamCount() {
        return paramCount;
    }


    public ArrayList<ValueType> getParamTypeAll() {
        return paramTypeAll;
    }


    public VarUInt1 getReturnCount() {
        return returnCount;
    }

    public ArrayList<ValueType> getReturnTypeAll() {
        return returnTypeAll;
    }

}
