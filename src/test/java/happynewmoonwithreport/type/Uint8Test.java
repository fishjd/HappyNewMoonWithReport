package happynewmoonwithreport.type;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Uint8Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testReadUnsigned() throws Exception {
        for (Integer i = 0; i < 256; i++) {
            UInt8 leb7 = new UInt8(new Integer(i).byteValue());
            Integer result = leb7.value();
            assertEquals("i = " + i.toString(), i, result);
        }
    }

}
