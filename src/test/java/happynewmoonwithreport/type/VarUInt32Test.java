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

public class VarUInt32Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMaxBytes() {
        byte[] bytesAll = new byte[]{(byte) 0x87, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80};
        BytesFile payload = new BytesFile(bytesAll);
        assertEquals(new Integer(5), new VarUInt32(payload).maxBytes());
    }

    @Test
    public void testReadUnsigned() {
        // Byte byte1 = Integer.parseInt("0x01", 16).;
        byte[] bytesAll = new byte[]{(byte) 0x87, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x00};
        BytesFile payload = new BytesFile(bytesAll);
        assertEquals(new UInt32(7L), new VarUInt32(payload));
    }



}
