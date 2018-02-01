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

import happynewmoonwithreport.type.UInt32;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LocalEntryTest {
    private LocalEntry localEntry;

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }


    /**
     * Run instantiate on the add32.wasm bytes.
     */
    @Test
    public void instantiateFull() {
        // I made this one up.  This is not in Add32.wasm
        byte[] byteAll = {
                (byte) 0x02,    // Count
                (byte) 0x7F     // int32
        };
        BytesFile payload = new BytesFile(byteAll);

        // run
        localEntry = new LocalEntry(payload);

        // verify
        // the count is 2
        assertEquals(new UInt32(2L), localEntry.getCount());

        ValueType valueType0 = localEntry.getValueType();
        assertEquals(new ValueType("int32"), valueType0);

    }

}