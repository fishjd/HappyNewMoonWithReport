package happynewmoonwithreport.type

class Int32GTest extends spock.lang.Specification {
    Int32 int32

    void setup() {
        int32 = new Int32(0)
    }

    void cleanup() {
    }


    void "MaxBits"() {

        expect:
        new Integer(32) == int32.maxBits()
    }

    void "MinValue"() {
        expect:
        new Integer(-2_147_483_648) == int32.minValue()
    }

    void "MaxValue"() {

        expect:
        new Integer(2_147_483_647) == int32.maxValue()
        (1 << 31) - 1 == new Double(Math.pow(2, 31)).intValue()

    }
}
