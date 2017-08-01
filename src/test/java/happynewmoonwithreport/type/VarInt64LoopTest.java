package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.type.util.Hex;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class VarInt64LoopTest {

    Map<Long, byte[]> problemChildren;
    Random random;

    @Before
    public void setUp() throws Exception {
        problemChildren = new HashMap<>();
        setupProblemChildren();
        random = new Random(System.currentTimeMillis());

    }

    public void setupProblemChildren() {
        problemChildren.put(-624485L, new byte[]{(byte) 0x9B, (byte) 0xF1, (byte) 0x59});
        problemChildren.put(new Long(0), new byte[]{0x00});
        problemChildren.put(new Long(1), new byte[]{0x01});
        problemChildren.put(new Long(2), new byte[]{0x02});
        problemChildren.put(new Long(-1), new byte[]{0x7F});
        problemChildren.put(134217728L, new byte[]{(byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0xC0, (byte) 0x00 });
        problemChildren.put(-1066294073546240435L, new byte[]{(byte) 0xCD, (byte) 0x9C, (byte) 0xB0, (byte) 0xC1,
                (byte) 0x88, (byte) 0xE6, (byte) 0xF0, (byte) 0x99, (byte) 0x71});
         problemChildren.put(new Long(4294967295L), new byte[] { (byte) 0xFF,(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x0F  });
         //problemChildren.put(2368947463459826787L, new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF });

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testProblemChildren() throws Exception {
        for (Entry<Long, byte[]> child : problemChildren.entrySet()) {
            VarInt64 expected = new VarInt64( child.getKey());
            ByteOutput convert = expected.convert();
//            System.out.print("expected = " + child.getKey() + " " );
//            String byteString = Hex.bytesToHex(convert.bytes());
//            System.out.println( byteString);


            BytesFile bytesFile = new BytesFile(child.getValue());
            VarInt64 varInt64 = new VarInt64(bytesFile);
            Long result = varInt64.value();

            assertEqualHex(child.getKey(), result);
        }
    }

    @Test
    public void testProblemChildrenWrite() throws Exception {
        for (Entry<Long, byte[]> child : problemChildren.entrySet()) {

            VarInt64 varInt64 = new VarInt64(child.getKey());
            ByteOutput result = varInt64.convert();

            assertArrayEqualsJDH(child.getValue(), result.bytes());
        }
    }

    public void assertArrayEqualsJDH(byte[] expected, byte[] actual) {
        Integer length = Math.min(expected.length, actual.length);
        Boolean equal = true;
        for (int i = 0; i < length; i++) {
            if (expected[i] == 0 || actual[i] == 0) {
                break;
            }
            if (expected[i] != actual[i]) {
                equal = false;
                throw new AssertionError(
                        "Array not equals" + "expected " + expected.toString() + " actual = " + actual.toString());
            }
        }
    }

    private void assertEqualHex(Long expected, Long result) {
        assertEquals("i = " + expected.toString() + " hex = " + Long.toHexString(expected), new Long(expected), result);
    }

    Integer maxCount = 1_000_000;

    @Test
    public void testReadUnsigned() throws Exception {
        for (Integer j = 0; j < maxCount; j++) {
            Long i = random.nextLong();

            VarInt64 expected = new VarInt64(i);
            ByteArrayByteOutput out = (ByteArrayByteOutput) expected.convert();

            BytesFile bytesFile = new BytesFile(out.bytes());
            VarInt64 varInt64_b = new VarInt64(bytesFile);
            Long result_b = varInt64_b.value();

            assertEquals("i = " + i.toString() + " hex = " + Long.toHexString(i), new Long(i), result_b);
        }
    }

}
