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

public class Uint32Test {

    Map<Long, byte[]> problemChildren;
    Random random;

    @Before
    public void setUp() throws Exception {
        problemChildren = new HashMap<>();
        setupProblemChildren();
        random = new Random(System.currentTimeMillis());

    }

    public void setupProblemChildren() {
        problemChildren.put(new Long(0), new byte[]{0x00, 0x00, 0x00, 0x00});
        problemChildren.put(new Long(1), new byte[]{0x01, 0x00, 0x00, 0x00});
        problemChildren.put(new Long(134217728L), new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x08});
        problemChildren.put(new Long(4294967295L), new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
    }

    @After
    public void tearDown() throws Exception {
    }

    UInt32 uInt32;
    @Test
    public void maxBits() throws Exception {
        uInt32 = new UInt32( 0);
        assertEquals(new Integer(32), uInt32.maxBits());
    }

    @Test
    public void minValue() throws Exception {
        uInt32 = new UInt32( 0);
        assertEquals(new Long ( 0), uInt32.minValue());
    }

    @Test
    public void maxValue() throws Exception {
        uInt32 = new UInt32( 0);
        assertEquals(new Long( 4_294_967_296L), uInt32.maxValue());
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
        assertEquals("i = " + expected.toString() + " hex = " + Long.toHexString(expected), new Long(expected), result);
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

    Integer maxCount = 1_000_000;

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
            BytesFile bytesFile = new BytesFile( bArray);
            UInt32 uint32_b = new UInt32(bytesFile);
            Long result_b = uint32_b.value();

            assertEquals("i = " + i.toString() + " hex = " + Long.toHexString(i), new Long(i), result_b);
        }
    }

}
