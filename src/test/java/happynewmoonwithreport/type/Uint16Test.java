package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Uint16Test {
    UInt16 uInt16;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void maxBits() throws Exception {
        uInt16 = new UInt16( 0);
        assertEquals(new Integer(16), uInt16.maxBits());
    }

    @Test
    public void minValue() throws Exception {
        uInt16 = new UInt16( 0);
        assertEquals(new Integer ( 0), uInt16.minValue());
    }

    @Test
    public void maxValue() throws Exception {
        uInt16 = new UInt16( 0);
        assertEquals(new Integer( 65_536), uInt16.maxValue());
    }

    @Test
    public void testReadUnsigned() throws Exception {
        for (Integer i = 0; i < Math.pow(2, 16); i++) {
            byte b1 = (byte) (i & 0xFF);
            byte b2 = (byte) ((i >> 8) & 0xFF);
            BytesFile bytesFile = new BytesFile(new byte[] {b1,b2});
            UInt16 uint16 = new UInt16(bytesFile);
            Integer result = uint16.value();
            assertEquals("i = " + i.toString(), i, result);
        }
    }

}
