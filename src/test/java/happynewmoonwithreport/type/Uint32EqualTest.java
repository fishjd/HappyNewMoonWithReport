package happynewmoonwithreport.type;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Uint32EqualTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testEqual() throws Exception {
        UInt32 expected = new UInt32(1L);
        UInt32 result = new UInt32(1L);
        assertEquals(expected, result);
    }

}
