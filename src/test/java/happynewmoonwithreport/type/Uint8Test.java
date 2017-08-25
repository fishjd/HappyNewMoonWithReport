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
package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Uint8Test {
    UInt8 uInt8;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void maxBits() throws Exception {
        uInt8 = new UInt8(0);
        assertEquals(new Integer(8), uInt8.maxBits());
    }

    @Test
    public void minValue() throws Exception {
        uInt8 = new UInt8(0);
        assertEquals(new Integer(0), uInt8.minValue());
    }

    @Test
    public void maxValue() throws Exception {
        uInt8 = new UInt8(0);
        assertEquals(new Integer(256), uInt8.maxValue());
    }


    @Test
    public void testReadUnsigned2() throws Exception {
        for (Integer i = 0; i < 256; i++) {
            byte[] bytesAll = new byte[]{new Integer(i).byteValue()};
            BytesFile bytesFile = new BytesFile(bytesAll);
            UInt8 uInt8 = new UInt8(bytesFile);
            Integer result = uInt8.value();
            assertEquals("i = " + i.toString(), i, result);
        }
    }


}
