/*
 *  Copyright 2017 Whole Bean Software, LTD.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package happynewmoonwithreport;

import happynewmoonwithreport.type.DataTypeNumber;
import happynewmoonwithreport.type.Int32;
import happynewmoonwithreport.type.WasmVector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WasmAdd32Test {
    private Wasm wasm;
    private WasmModule module;

    @Before
    public void setUp() throws Exception {

        String path = "./src/test/resources/add32/add32.wasm";
        File wasmFile = new File(path);
        assertTrue(wasmFile.exists());

        wasm = new Wasm(path);
        module = wasm.instantiate();
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * This an end to end test
     */
    @Test
    public void wasmTest() {

        assertTrue(wasm.validate());

        assertEquals(new Integer(1), wasm.getFunctionSignatures().getSize());

        assertEquals(2, wasm.exports().size());
        assertEquals(new ExternalKind(ExternalKind.memory), wasm.exports().get(0).getExternalKind());
        assertEquals("memory", wasm.exports().get(0).getFieldName().getValue());

        assertEquals(new ExternalKind(ExternalKind.function), wasm.exports().get(1).getExternalKind());
        assertEquals("add32", wasm.exports().get(1).getFieldName().getValue());

        WasmInstance instance = new WasmInstance(module);
        assertNotNull (instance.stack());
        WasmFunction functionAdd32 = instance.exportFunction("add32");

        WasmVector<DataTypeNumber> returnAll = new WasmVector<>(1);
        WasmVector<DataTypeNumber> paramAll = new WasmVector<>(2);
        paramAll.add(new Int32(3));
        paramAll.add(new Int32(4));

        instance.call(functionAdd32, returnAll, paramAll);

        assertNotNull(returnAll);
        assertEquals(1, returnAll.size());
        assertEquals(new Int32(7), returnAll.get(0));


    }

}
