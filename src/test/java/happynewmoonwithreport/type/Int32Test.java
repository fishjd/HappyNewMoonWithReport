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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Int32Test {
    private Int32 int32;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void maxBits() throws Exception {
        int32 = new Int32( 0);
        assertEquals(new Integer(32), int32.maxBits());
    }

    @Test
    public void minValue() throws Exception {
        int32 = new Int32( 0);
        assertEquals(new Integer(-2_147_483_648), int32.minValue());
    }

    @Test
    public void maxValue() throws Exception {
        int32 = new Int32( 0);
        assertEquals(new Integer (2_147_483_647), int32.maxValue());

        assertEquals((1 << 31) -1 , new Double( Math.pow(2, 31 )).intValue());
    }

}