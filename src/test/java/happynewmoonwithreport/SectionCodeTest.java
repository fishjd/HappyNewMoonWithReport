package happynewmoonwithreport;

import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt1;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class SectionCodeTest {
    SectionCode sectionCode;

    @Before
    public void setUp() throws Exception {
        sectionCode = new SectionCode();
        assertNotNull(sectionCode);
    }

    @After
    public void tearDown() throws Exception {
    }


    /**
     * Run instantiate on the add32.wasm bytes.
     */
    @Test
    public void instantiateAdd32() {
        byte[] byteAll =
                {
                        //* Payload
                        (byte) 0x01,                                                        // count of functions
                        //** function Body
                        (byte) 0x87, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x00,    // Body Size 7
                        (byte) 0x00,                                                        // Local Count 0
                        // *** Code
                        (byte) 0x20, (byte) 0x01, (byte) 0x20, (byte) 0x00, (byte) 0x6A,
                        (byte) 0x0B                                                         // End Byte 0x0B
                };

        BytesFile payload = new BytesFile(byteAll);

        // run
        sectionCode.instantiate(payload);

        // verify
        // the count is 1
        assertEquals(new UInt32(1L), sectionCode.getCount());

        FunctionBody functionBody = sectionCode.getFunctionAll().get(0);

        //* verify
        //** Body Size
        assertEquals(new UInt32(7L), functionBody.getBodySize());

        //** Local Count
        assertEquals(new UInt32(0L), functionBody.getLocalCount());

        //** Local Variables
//        assertEquals(2, functionBody.getLocalAll().size());
//        assertEquals(new ValueType("int32"), functionBody.getLocalAll().get(0));
//        assertEquals(new ValueType("int32"), functionBody.getLocalAll().get(1));

        //** Code
        byte[] expectedCode = {(byte) 0x20, (byte) 0x01, (byte) 0x20, (byte) 0x00, (byte) 0x6A};
        assertArrayEquals(expectedCode , functionBody.getCode());

        //** End Byte
        assertEquals(0x0B, functionBody.getEnd());
    }

}