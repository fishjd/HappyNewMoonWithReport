/*
 *  Copyright 2018 Whole Bean Software, LTD.
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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class U64Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    private U64 u64;

    @Test
    public void maxBits() throws Exception {
        u64 = new U64(0L);
        assertEquals(new Integer(63), u64.maxBits());
    }

    @Test
    public void minValue() throws Exception {
        u64 = new U64(0L);
        assertEquals(new Long(0), u64.minValue());
    }

    @Test
    public void maxValue() throws Exception {
        u64 = new U64(0L);
        // If we had the full 64 bits.
        // assertEquals(new Long( 18_446_744_073_709_551_616L), u64.maxValue());
        assertEquals(new Long(9_223_372_036_854_775_807L), u64.maxValue());
    }


    private void assertEqualHex(Long expected, Long result) {
        assertEquals("i = " + expected.toString() + " hex = " + Long.toHexString(expected), new Long(expected), result);
    }


}
