package happynewmoonwithreport.validation

import happynewmoonwithreport.FunctionType
import happynewmoonwithreport.LimitType
import happynewmoonwithreport.ValueType
import happynewmoonwithreport.type.UInt32
import happynewmoonwithreport.type.WasmVector
import spock.lang.Specification

class FunctionTypeTest extends Specification {

    LimitType limits;

    void setup() {

    }

    void cleanup() {
    }


    def "Valid "() {

        given: "Valid function type with one returnType"
        WasmVector<ValueType> paramTypeAll = new WasmVector<>(1);
        paramTypeAll.add(new ValueType(ValueType.int32));

        WasmVector<ValueType> returnTypeAll = new WasmVector<>(1);
        returnTypeAll.add(new ValueType(ValueType.int64));

        FunctionType functionType = new FunctionType(new UInt32(1), paramTypeAll,new UInt32(1), returnTypeAll );


        when: " assert limit is valid. "
        Boolean var = functionType.valid();

        then:
        true == var;
    }


    def "Valid with zero return type "() {

        given: "Valid function type with one returnType"
        WasmVector<ValueType> paramTypeAll = new WasmVector<>(1);
        paramTypeAll.add(new ValueType(ValueType.int32));

        WasmVector<ValueType> returnTypeAll = new WasmVector<>(0);

        FunctionType functionType = new FunctionType(new UInt32(1), paramTypeAll,new UInt32(0), returnTypeAll );


        when: " assert is valid. "
        Boolean var = functionType.valid();

        then:
        true == var;
    }

    def "InValid with two return type "() {

        given: "Valid function type with two returnType"
        WasmVector<ValueType> paramTypeAll = new WasmVector<>(1);
        paramTypeAll.add(new ValueType(ValueType.int32));

        WasmVector<ValueType> returnTypeAll = new WasmVector<>(2);
        returnTypeAll.add(new ValueType(ValueType.int64));
        returnTypeAll.add(new ValueType(ValueType.int64));
        
        FunctionType functionType = new FunctionType(new UInt32(1), paramTypeAll,new UInt32(2), returnTypeAll );


        when: " assert is Invalid. "
        Boolean isValid = functionType.valid();

        then:
        false == isValid;
    }



}
