package happynewmoonwithreport;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ProcessTypeSectionTest {

    FunctionSigniture functionSigniture;

    @Before
    public void setUp() throws Exception {
        functionSigniture = new FunctionSigniture();
        assertNotNull(functionSigniture);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCount() {
        byte[] payload = {(byte) 0x00, (byte) 0x01, (byte) 0x60, (byte) 0x02, (byte) 0x7F, (byte) 0x7F, (byte) 0x01};
        functionSigniture.instantiate(payload);
    }

}
