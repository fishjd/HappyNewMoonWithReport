package happynewmoonwithreport;

import happynewmoonwithreport.type.UInt32;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class LocalEntryTest {
    LocalEntry localEntry;

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
                (byte) 0x02,    // Count
                (byte) 0x7F     // int32
        };
        BytesFile payload = new BytesFile(byteAll);

        // run
        localEntry = new LocalEntry(payload);

        // verify
        // the count is 2
        assertEquals(new UInt32(2L), localEntry.getCount());

        ValueType valueType0 = localEntry.getValueType();
        assertEquals(new ValueType("int32"), valueType0);

    }

}