package happynewmoonwithreport.type;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VarUInt1Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    VarUInt1 varUInt1;
    @Test
    public void maxBits() throws Exception {
        varUInt1 = new VarUInt1( 0);
        assertEquals(new Integer(1), varUInt1.maxBits());
    }

    @Test
    public void minValue() throws Exception {
        varUInt1 = new VarUInt1( 0);
        assertEquals(new Long ( 0), varUInt1.minValue());
    }

    @Test
    public void maxValue() throws Exception {
        varUInt1 = new VarUInt1( 0);
        assertEquals(new Long( 1), varUInt1.maxValue());
    }

    @Test
    public void testReadUnsigned() throws Exception {
        for (Long i = 0L; i < 1; i++) {
            VarUInt1 uin1 = new VarUInt1(new Long(i));
            Long result = uin1.value();
            assertEquals("i = " + i.toString(), i, result);
        }
    }

}
