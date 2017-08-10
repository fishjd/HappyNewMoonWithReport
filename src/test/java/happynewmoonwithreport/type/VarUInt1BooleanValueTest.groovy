package happynewmoonwithreport.type

import spock.lang.Specification

class VarUInt1BooleanValueTest extends Specification {

    VarUInt1 varTrue, varFalse;

    void setup() {
        varTrue = new VarUInt1(1)
        varFalse = new VarUInt1(0)
    }

    void cleanup() {
    }


    def "BooleanValue"() {

        expect:
        varTrue.booleanValue() == true
        varFalse.booleanValue() == false

    }
}
