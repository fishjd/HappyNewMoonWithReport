package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Leb32Test {

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
        assertEquals(new VarUInt32(7L), new VarUInt32(payload));
    }



}
