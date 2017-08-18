package happynewmoonwithreport.type;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class WasmVectorTest {
    WasmVector<String> vector;

    @Before
    public void setUp() throws Exception {
        vector = new WasmVector<>();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addTest() {
        vector.add("111");
        assertEquals("111", vector.get(0));
    }

    @Test
    public void addAtTest() {
        vector.add(0, "111");
        assertEquals("111", vector.get(0));
    }
}