package happynewmoonwithreport.type;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Uint16ShiftValueTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testChangeInput() {
        Integer expected = 23;
        byte b1 = (byte) (expected & 0xFF);
        byte b2 = (byte) ((expected >> 8) & 0xFF);
        byte[] input = new byte[]{b1, b2};
        UInt16 uint16 = new UInt16(input);
        // change value of inputs.
        b1 = (byte) 0xFF;
        b2 = (byte) 0xFF;
        Integer result = uint16.value();
        assertEquals("i = " + expected.toString(), expected, result);

        assertEquals(new UInt16(expected), uint16);

    }

}
