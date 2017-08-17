package happynewmoonwithreport.type;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Int8Test {
    Int8 int8;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void maxBits() throws Exception {
        int8 = new Int8((byte) 0);
        assertEquals(new Integer(8), int8.maxBits());
    }

    @Test
    public void minValue() throws Exception {
        int8 = new Int8((byte) 0);
        assertEquals(new Byte((byte) -128), int8.minValue());
    }

    @Test
    public void maxValue() throws Exception {
        int8 = new Int8((byte) 0);
        assertEquals(new Byte((byte) 127), int8.maxValue());
    }

}