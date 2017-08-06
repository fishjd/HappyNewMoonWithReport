package happynewmoonwithreport.type;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by James Haring on 2017-08-06.
 * Copyright 2017 Whole Bean Software Limited
 */
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