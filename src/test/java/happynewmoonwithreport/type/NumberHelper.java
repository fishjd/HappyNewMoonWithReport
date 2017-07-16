package happynewmoonwithreport.type;

import static org.junit.Assert.assertEquals;

public class NumberHelper {

    public static void assertEqualHex(Long expected, Long result) {
        assertEquals("i = " + expected.toString() + " hex = " + Long.toHexString(expected), new Long(expected), result);
    }

}
