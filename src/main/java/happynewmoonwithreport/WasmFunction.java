package happynewmoonwithreport;

import happynewmoonwithreport.type.DataTypeNumber;
import happynewmoonwithreport.type.Int32;

import java.util.ArrayList;

public class WasmFunction {

    FunctionType type;
    FunctionBody body;

    WasmFunction(FunctionType type, FunctionBody body) {
        this.type = type;
        this.body = body;
    }

    void call (ArrayList<DataTypeNumber> returnAll,  ArrayList<DataTypeNumber> resultAll){
        returnAll.add(new Int32(7));
    }

}
