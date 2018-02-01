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
package happynewmoonwithreport.section;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.FunctionBody;
import happynewmoonwithreport.type.UInt32;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SectionCodeTest {
    private SectionCode sectionCode;

    @BeforeEach
    public void setUp() throws Exception {
        sectionCode = new SectionCode();
        assertNotNull(sectionCode);
    }

    @AfterEach
    public void tearDown() throws Exception {
    }


    /**
     * Run instantiate on the add32.wasm bytes.
     */
    @Test
    public void instantiateAdd32() {
        byte[] byteAll =
                {
                        //* Payload
                        (byte) 0x01,                                                        // count of functions
                        //** function Body
                        (byte) 0x87, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x00,    // Body Size 7
                        (byte) 0x00,                                                        // Local Count 0
                        // *** Code
                        (byte) 0x20, (byte) 0x01, (byte) 0x20, (byte) 0x00, (byte) 0x6A,
                        (byte) 0x0B                                                         // End Byte 0x0B
                };

        BytesFile payload = new BytesFile(byteAll);

        // run
        sectionCode.instantiate(payload);

        // verify
        // the count is 1
        assertEquals(new UInt32(1L), sectionCode.getCount());

        FunctionBody functionBody = sectionCode.getFunctionAll().get(0);

        //* verify
        //** Body Size
        assertEquals(new UInt32(7L), functionBody.getBodySize());

        //** Local Count
        assertEquals(new UInt32(0L), functionBody.getLocalCount());

        //** Local Variables
//        assertEquals(2, functionBody.getLocalAll().size());
//        assertEquals(new ValueType("int32"), functionBody.getLocalAll().get(0));
//        assertEquals(new ValueType("int32"), functionBody.getLocalAll().get(1));

        //** Code
        byte[] expectedCode = {(byte) 0x20, (byte) 0x01, (byte) 0x20, (byte) 0x00, (byte) 0x6A};
        assertArrayEquals(expectedCode, functionBody.getCode());

        //** End Byte
        assertEquals(0x0B, functionBody.getEnd());
    }

}