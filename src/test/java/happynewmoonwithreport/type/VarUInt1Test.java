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

    @Test
    public void testReadUnsigned() throws Exception {
        for (Integer i = 0; i < 1; i++) {
            VarUInt1 uin1 = new VarUInt1(new Integer(i).byteValue());
            Integer result = uin1.value();
            assertEquals("i = " + i.toString(), i, result);
        }
    }

}
