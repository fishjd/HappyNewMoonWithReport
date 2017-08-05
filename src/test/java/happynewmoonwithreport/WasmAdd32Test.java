package happynewmoonwithreport;

import happynewmoonwithreport.type.DataTypeNumber;
import happynewmoonwithreport.type.Int32;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WasmAdd32Test {
    Wasm wasm;

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
    public void wasmTest() {

        assertEquals(new Integer(1), wasm.getFunctionSignatures().getSize());


        assertEquals(2, wasm.exports().size());
        assertEquals(new ExternalKind(ExternalKind.memory), wasm.exports().get(0).getExternalKind());
        assertEquals("memory", wasm.exports().get(0).getFieldName().getValue());

        assertEquals(new ExternalKind(ExternalKind.function), wasm.exports().get(1).getExternalKind());
        assertEquals("add32", wasm.exports().get(1).getFieldName().getValue());

//        WasmFunction wf =wasm.exports().function("add32");
//        Int32 result = wf.call(new Int32(3), new Int32(4));
//        assertEquals(new Int32(7), result);

        WasmFunction wf = wasm.exportFunction("add32");
        ArrayList<DataTypeNumber> returnAll = new ArrayList<>(1);
        ArrayList<DataTypeNumber> paramAll = new ArrayList<>(2);
        paramAll.add(new Int32(3));
        paramAll.add(new Int32(4));
        wf.call(returnAll, paramAll);

        assertEquals(new Int32(7), returnAll.get(0));


    }

}
