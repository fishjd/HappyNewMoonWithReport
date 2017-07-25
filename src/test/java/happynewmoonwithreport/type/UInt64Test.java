package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class UInt64Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    UInt64 uInt64;
    @Test
    public void maxBits() throws Exception {
        uInt64 = new UInt64( 0);
        assertEquals(new Integer(63), uInt64.maxBits());
    }

    @Test
    public void minValue() throws Exception {
        uInt64 = new UInt64( 0);
        assertEquals(new Long ( 0), uInt64.minValue());
    }

    @Test
    public void maxValue() throws Exception {
        uInt64 = new UInt64( 0);
        // If we had the full 64 bits.
        // assertEquals(new Long( 18_446_744_073_709_551_616L), uInt64.maxValue());
        assertEquals(new Long( 9_223_372_036_854_775_807L), uInt64.maxValue());
    }



    private void assertEqualHex(Long expected, Long result) {
        assertEquals("i = " + expected.toString() + " hex = " + Long.toHexString(expected), new Long(expected), result);
    }



}
