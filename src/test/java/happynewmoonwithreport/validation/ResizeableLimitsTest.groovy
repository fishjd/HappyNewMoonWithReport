package happynewmoonwithreport.validation

import happynewmoonwithreport.LimitType
import happynewmoonwithreport.type.UInt32
import happynewmoonwithreport.type.VarUInt1
import spock.lang.Specification

class ResizeableLimitsTest extends Specification {

    LimitType limits;

    void setup() {

    }

    void cleanup() {
    }


    def "Valid "() {

        given: "Valid limit with min of 1 and max of 20"
        def limit = new LimitType(new VarUInt1(1), new UInt32(1), new UInt32(20));


        when: " assert limit is valid. "
        Boolean var = limit.valid();

        then:
        true == var;
    }


    def "Valid no Max "() {

        given: "Valid limit with min of 1, no max set"
        def limit = new LimitType(new VarUInt1(0), new UInt32(1));


        when: " assert limit is valid. "
        Boolean var = limit.valid();

        then:
        true == var;
    }


    def "InValid  with min larger than max"() {

        given: "invalid limit with min larger than max"
        def limit = new LimitType(new VarUInt1(1), new UInt32(400), new UInt32(20));


        when: " assert limit is valid. "
        Boolean var = limit.valid();

        then:
        false == var;
    }


}
