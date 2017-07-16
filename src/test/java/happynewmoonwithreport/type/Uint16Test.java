package happynewmoonwithreport.type;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Uint16Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testReadUnsigned() throws Exception {
        for (Integer i = 0; i < Math.pow(2, 16); i++) {
            byte b1 = (byte) (i & 0xFF);
            byte b2 = (byte) ((i >> 8) & 0xFF);
            UInt16 uint16 = new UInt16(b1, b2);
            Integer result = uint16.value();
            assertEquals("i = " + i.toString(), i, result);
        }
    }

}
