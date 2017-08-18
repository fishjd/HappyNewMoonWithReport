package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WasmStringTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testReadUnsigned() {
        byte[] fox = new byte[]{0x54, (byte) 0x68, (byte) 0x65, (byte) 0x20, (byte) 0x71, (byte) 0x75, (byte) 0x69,
                (byte) 0x63, (byte) 0x6B, (byte) 0x20, (byte) 0x62, (byte) 0x72, (byte) 0x6F, (byte) 0x77, (byte) 0x6E};
        BytesFile payload = new BytesFile(fox);
        WasmString wasmFox = new WasmString(payload, new UInt8(fox.length));
        assertEquals(new WasmString("The quick brown"), wasmFox);

    }
}