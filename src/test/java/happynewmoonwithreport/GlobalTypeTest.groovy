package happynewmoonwithreport

import spock.lang.Specification

class GlobalTypeTest extends Specification {
    def "Global Type Valid"() {
        setup: "A valid GlobalType "
        ValueType contentType = new ValueType(ValueType.int32);
        Mutability mutability = new Mutability(Mutability.immutable);
        GlobalType globalType = new GlobalType(contentType, mutability);

        when: ""

        then: "Should be Valid"
        globalType.valid();

    }
}
