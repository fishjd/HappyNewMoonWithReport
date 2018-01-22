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


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VarUInt1Test {

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    private VarUInt1 varUInt1;

    @Test
    public void maxBits() throws Exception {
        varUInt1 = new VarUInt1(0);
        assertEquals(new Integer(1), varUInt1.maxBits());
    }

    @Test
    public void minValue() throws Exception {
        varUInt1 = new VarUInt1(0);
        assertEquals(new Long(0), varUInt1.minValue());
    }

    @Test
    public void maxValue() throws Exception {
        varUInt1 = new VarUInt1(0);
        assertEquals(new Long(1), varUInt1.maxValue());
    }

    @Test
    public void testReadUnsigned() throws Exception {
        for (Long i = 0L; i < 1; i++) {
            VarUInt1 uin1 = new VarUInt1(new Long(i));
            Long result = uin1.longValue();
            assertEquals(i, result, "i = " + i.toString());
        }
    }

}
