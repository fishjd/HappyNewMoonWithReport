package happynewmoonwithreport.validation;

import happynewmoonwithreport.Wasm;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class WasmAdd32ValidationTest {
    private Wasm wasm;

    @Before
    public void setUp() throws Exception {

        String path = "./src/test/resources/add32/add32.wasm";
        File wasmFile = new File(path);
        assertTrue(wasmFile.exists());

        // C:\Users\James\Documents\Programming 2017\HappyNewMoonWithReport\src\test\resources\add32
        wasm = new Wasm(path);
        wasm.instantiate();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void isValidTest() {

        assertTrue(wasm.getValid());

    }

}
