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
import happynewmoonwithreport.type.UInt32;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SectionFunctionTest {
    private SectionFunction sectionFunction;

    @BeforeEach
    public void setUp() throws Exception {
        sectionFunction = new SectionFunction();
        assertNotNull(sectionFunction);
    }

    @AfterEach
    public void tearDown() throws Exception {
    }


    @Test
    public void instantiate() throws Exception {
        byte[] byteAll = {(byte) 0x01, (byte) 0x00};
        BytesFile payload = new BytesFile(byteAll);

        // run
        sectionFunction.instantiate(payload);

        // verify
        // the count is 1
        assertEquals(new UInt32(1L), sectionFunction.getCount());

        ArrayList<UInt32> typeAll = sectionFunction.getTypes();
        assertEquals(1, typeAll.size());

        UInt32 type = typeAll.get(0);
        assertEquals(new UInt32(0L), type);


    }

}