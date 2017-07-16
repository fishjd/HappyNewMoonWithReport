package happynewmoonwithreport;

import java.util.ArrayList;

/**
 * Source: http://webassembly.org/docs/binary-encoding/#func_type
 */
public class FunctionType {

    /**
     * No Clue?
     */
    private Integer form;

    /**
     * The number of parameters
     */
    private Integer paramCount;

    /**
     * Array of the types of parameters
     */
    private ArrayList<ValueType> paramTypeAll;

    /**
     * The number of return values Currently only zero or one value
     */
    private Integer returnCount;

    /**
     * Array of the types of of the return values.
     */
    private ArrayList<ValueType> returnTypeAll;

    public FunctionType(Integer form, Integer paramCount, ArrayList<ValueType> paramTypeAll,
                        Integer returnCount, ArrayList<ValueType> returnTypeAll) {
        super();
        this.form = form;
        this.paramCount = paramCount;
        this.paramTypeAll = paramTypeAll;
        this.returnCount = returnCount;
        this.returnTypeAll = returnTypeAll;
    }

    public Integer getForm() {
        return form;
    }

    public void setForm(Integer form) {
        this.form = form;
    }

    public Integer getParamCount() {
        return paramCount;
    }

    public void setParamCount(Integer paramCount) {
        this.paramCount = paramCount;
    }

    public ArrayList<ValueType> getParamTypeAll() {
        return paramTypeAll;
    }

    public void setParamTypeAll(ArrayList<ValueType> paramTypeAll) {
        this.paramTypeAll = paramTypeAll;
    }

    public Integer getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }

    public ArrayList<ValueType> getReturnTypeAll() {
        return returnTypeAll;
    }

    public void setReturnTypeAll(ArrayList<ValueType> returnTypeAll) {
        this.returnTypeAll = returnTypeAll;
    }

}
