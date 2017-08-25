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

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class isBoundByIntegerTest {
    Integer maxCount = 1_000_000;

    Random random;

    @Before
    public void setUp() throws Exception {
        random = new Random(System.currentTimeMillis());

    }


    @Test
    public void inIntegerRange() throws Exception {
        for (Integer j = 0; j < maxCount; j++) {
            Integer i = random.nextInt();
            UInt64 uInt64 = new UInt64(i);

            // run
            Boolean boundByInteger = uInt64.isBoundByInteger();

            // test
            assertTrue("i = " + i.toString(), boundByInteger);
        }
    }

    @Test
    public void inIntegerRangeFails() throws Exception {

        UInt64 uInt64 = new UInt64( 1L + Integer.MAX_VALUE );

        // run
        Boolean boundByInteger = uInt64.isBoundByInteger();

        // test
        assertFalse( boundByInteger);
    }

}