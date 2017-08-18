package happynewmoonwithreport;

import happynewmoonwithreport.type.DataTypeNumber;
import happynewmoonwithreport.type.Int32;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.WasmVector;

import java.util.ArrayList;

/**
 * Web Assembly Function
 * <p>
 * Source:<a href="https://webassembly.github.io/spec/syntax/modules.html#syntax-func" target="_top">
 * https://webassembly.github.io/spec/syntax/modules.html#syntax-func
 * </a>
 */
public class WasmFunction {

    /**
     * an index into the vector%ltFunctionType%gt
     **/
    private UInt32 typeIndex;
    private WasmVector<DataTypeNumber> locals;
    private FunctionBody body;

    public WasmFunction() {
        super();
        locals = new WasmVector<>();
    }

    WasmFunction(UInt32 typeIndex, FunctionBody body) {
        this();
        this.typeIndex = typeIndex;
        this.body = body;
    }

    public WasmFunction(UInt32 typeIndex, WasmVector<DataTypeNumber> locals, FunctionBody body) {
        this.typeIndex = typeIndex;
        this.locals = locals;
        this.body = body;
    }

    void call(ArrayList<DataTypeNumber> returnAll, ArrayList<DataTypeNumber> resultAll) {
        returnAll.add(new Int32(7));
    }

}
