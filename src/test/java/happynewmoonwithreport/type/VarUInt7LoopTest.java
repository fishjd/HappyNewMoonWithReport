package happynewmoonwithreport.type;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VarUInt7LoopTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testReadUnsigned() throws Exception {
        for (Integer i = 0; i < 128; i++) {
            VarUInt7 leb7 = new VarUInt7(new Integer(i).byteValue());
            Integer result = leb7.value();
            assertEquals("i = " + i.toString(), i, result);
        }
    }

}
