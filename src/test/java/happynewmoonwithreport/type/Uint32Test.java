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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Uint32Test {

    private Map<Long, byte[]> problemChildren;
    private Random random;

    @Before
    public void setUp() throws Exception {
        problemChildren = new HashMap<>();
        setupProblemChildren();
        random = new Random(System.currentTimeMillis());

    }

    private void setupProblemChildren() {
        problemChildren.put(0L, new byte[]{0x00, 0x00, 0x00, 0x00});
        problemChildren.put(1L, new byte[]{0x01, 0x00, 0x00, 0x00});
        problemChildren.put(134217728L, new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x08});
        problemChildren.put(4294967295L, new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
    }

    @After
    public void tearDown() throws Exception {
    }

    private UInt32 uInt32;

    @Test
    public void maxBits() throws Exception {
        uInt32 = new UInt32(0);
        assertEquals(new Integer(32), uInt32.maxBits());
    }

    @Test
    public void minValue() throws Exception {
        uInt32 = new UInt32(0);
        assertEquals(new Long(0), uInt32.minValue());
    }

    @Test
    public void maxValue() throws Exception {
        uInt32 = new UInt32(0);
        assertEquals(new Long(4_294_967_295L), uInt32.maxValue());
    }

    @Test
    public void testProblemChildren() throws Exception {
        for (Entry<Long, byte[]> child : problemChildren.entrySet()) {
            BytesFile bytesFile = new BytesFile(child.getValue());
            UInt32 uint32 = new UInt32(bytesFile);
            Long result = uint32.value();

            assertEqualHex(child.getKey(), result);
        }
    }

    private void assertEqualHex(Long expected, Long result) {
        assertEquals("i = " + expected.toString() + " hex = " + Long.toHexString(expected), expected, result);
    }

    @Test
    public void testReadUnsignedConstructor1() throws Exception {
        // for (Integer i = 0; i < Math.pow(2, 32); i++) {
        // for (Integer i = 0; i < Math.pow(2, 8); i++) {
        for (Integer j = 0; j < maxCount; j++) {
            Integer i = Math.abs(random.nextInt());
            byte b1 = (byte) (i & 0xFF);
            byte b2 = (byte) ((i >> 8) & 0xFF);
            byte b3 = (byte) ((i >> 16) & 0xFF);
            byte b4 = (byte) ((i >> 24) & 0xFF);

            UInt32 uint32 = new UInt32(b1, b2, b3, b4);
            Long result = uint32.value();
            assertEquals("i = " + i.toString() + " hex = " + Long.toHexString(i), new Long(i), result);

        }
    }

    private Integer maxCount = 1_000_000;

    @Test
    public void testReadUnsignedConstrutor2() throws Exception {
        // for (Integer i = 0; i < Math.pow(2, 32); i++) {
        // for (Integer i = 0; i < Math.pow(2, 8); i++) {
        for (Integer j = 0; j < maxCount; j++) {
            Integer i = Math.abs(random.nextInt());
            byte b1 = (byte) (i & 0xFF);
            byte b2 = (byte) ((i >> 8) & 0xFF);
            byte b3 = (byte) ((i >> 16) & 0xFF);
            byte b4 = (byte) ((i >> 24) & 0xFF);

            byte[] bArray = new byte[]{b1, b2, b3, b4};
            BytesFile bytesFile = new BytesFile(bArray);
            UInt32 uint32_b = new UInt32(bytesFile);
            Long result_b = uint32_b.value();

            assertEquals("i = " + i.toString() + " hex = " + Long.toHexString(i), new Long(i), result_b);
        }
    }

    @Test
    public void testMaxValue() {
        UInt32 uInt32 = new UInt32(0x01);
        assertEquals(new Long(0L), uInt32.minValue());
        assertEquals(new Long(4_294_967_295L), uInt32.maxValue());
    }

    @Test
    public void testLongConstructorThrowsExceptionWithNegativeNumber() {
        try {
            UInt32 neg_two = new UInt32(-2L);
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("87765c72-a4b0-437f-ae27-9b57e702dc50"));

        }
    }

    @Test
    public void testLongConstructorThrowsExceptionWithTooLargeNumber() {
        try {
            UInt32 uInt32 = new UInt32(Long.MAX_VALUE);
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("f4ac4150-12c7-40c1-bd25-47f5dc4a28ba"));
        }
    }

    @Test
    public void testSigned() {
        UInt32 zero = new UInt32(0x00);
        assertEquals(new S32(0x00), zero.signed());

        UInt32 one = new UInt32(0x01);
        assertEquals(new S32(0x01), one.signed());

        UInt32 neg_one = new UInt32(0xFFFF_FFFFL);
        assertEquals(new Long(4_294_967_295L), neg_one.longValue());
        assertEquals(new S32(-1), neg_one.signed());

        UInt32 neg_two = new UInt32(0xFFFF_FFFEL);
        assertEquals(new Long(4_294_967_294L), neg_two.longValue());
        assertEquals(new S32(-2), neg_two.signed());

        UInt32 neg_three = new UInt32(0xFFFF_FFFDL);
        assertEquals(new Long(4_294_967_293L), neg_three.longValue());
        assertEquals(new S32(-3), neg_three.signed());
    }

}
