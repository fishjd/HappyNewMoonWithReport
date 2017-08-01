package happynewmoonwithreport;

import happynewmoonwithreport.type.UInt32;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class FunctionBodyTest {
    FunctionBody functionBody;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }


    /**
     * Run instantiate on the add32.wasm bytes.
     */
    @Test
    public void instantiateFull() {
        // I made this one up.  This is not in Add32.wasm
        byte[] byteAll = {
                //Body Size
                (byte) 0x09,
                // Local Count
                (byte) 0x02,
                // Local Entry
                (byte) 0x02,    // count
                (byte) 0x7F,    // int 32
                // Code
                (byte) 0x20,
                (byte) 0x01,
                (byte) 0x20,
                (byte) 0x00,
                (byte) 0x6A,
                // End Byte
                (byte) 0x0B     // always 0x0B
        };
        BytesFile payload = new BytesFile(byteAll);

        // run
        functionBody = new FunctionBody(payload);

        //* verify
        //** Body Size
        assertEquals(new UInt32(9L), functionBody.getBodySize());

        //** Local Count
        assertEquals(new UInt32(2L), functionBody.getLocalCount());

        //** Local Variables
        assertEquals(2, functionBody.getLocalAll().size());
        assertEquals(new ValueType("int32"), functionBody.getLocalAll().get(0));
        assertEquals(new ValueType("int32"), functionBody.getLocalAll().get(1));

        //** Code
        byte[] expectedCode = {(byte) 0x20, (byte) 0x01, (byte) 0x20, (byte) 0x00, (byte) 0x6A};
        assertArrayEquals(expectedCode , functionBody.getCode());

        //** End Byte
        assertEquals(0x0B, functionBody.getEnd());
    }

}