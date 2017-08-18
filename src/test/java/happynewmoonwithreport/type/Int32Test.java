package happynewmoonwithreport.type;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Int32Test {
    private Int32 int32;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void maxBits() throws Exception {
        int32 = new Int32( 0);
        assertEquals(new Integer(32), int32.maxBits());
    }

    @Test
    public void minValue() throws Exception {
        int32 = new Int32( 0);
        assertEquals(new Integer(-2_147_483_648), int32.minValue());
    }

    @Test
    public void maxValue() throws Exception {
        int32 = new Int32( 0);
        assertEquals(new Integer (2_147_483_647), int32.maxValue());

        assertEquals((1 << 31) -1 , new Double( Math.pow(2, 31 )).intValue());
    }

}