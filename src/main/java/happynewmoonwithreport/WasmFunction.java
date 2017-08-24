package happynewmoonwithreport;

import happynewmoonwithreport.type.DataTypeNumber;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.WasmVector;

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

    public WasmVector<DataTypeNumber> getLocals() {
        return locals;
    }

    public void setLocals(WasmVector<DataTypeNumber> locals) {
        this.locals = locals;
    }

    public UInt32 getTypeIndex() {
        return typeIndex;
    }

    public byte[] getCode() {
        return body.getCode();
    }

    public UInt32 getBodySize() {
        return body.getBodySize();
    }

    public WasmVector<ValueType> getLocalEntryAll() {
        return body.getLocalEntryAll();
    }
}
