package happynewmoonwithreport.type;

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
        assertEquals(new Integer(5), new VarUInt32(bytesAll).maxBytes());
    }

    @Test
    public void testReadUnsigned() {
        // Byte byte1 = Integer.parseInt("0x01", 16).;
        byte[] bytesAll = new byte[]{(byte) 0x87, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x00};
        assertEquals(new VarUInt32(7L), new VarUInt32(bytesAll));
    }

    @Test
    public void testConstByteArr_Int() {
        // Byte byte1 = Integer.parseInt("0x01", 16).;
        byte[] bytesAll = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x87, (byte) 0x80, (byte) 0x80, (byte) 0x80,
                (byte) 0x00};
        assertEquals(7, new VarUInt32(bytesAll, 2).value().intValue());
    }

    @Test
    public void testConstByteArr_Int_short() {
        // Byte byte1 = Integer.parseInt("0x01", 16).;
        byte[] bytesAll = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x87};
        assertEquals(7, new VarUInt32(bytesAll, 2).value().intValue());
    }

    @Test
    public void testSize() {
        // Byte byte1 = Integer.parseInt("0x01", 16).;
        byte[] bytesAll = new byte[]{(byte) 0x87, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x00};
        VarUInt32 leb32 = new VarUInt32(bytesAll);
        assertEquals(7, leb32.value().intValue());
        assertEquals(new Integer(5), leb32.size());

        bytesAll = new byte[]{(byte) 0x07, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x00};
        assertEquals(1, new VarUInt32(bytesAll).size().intValue());
    }

}
