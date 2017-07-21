package happynewmoonwithreport;

import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.WasmString;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class SectionStartTest {
    SectionStart sectionStart;

    @Before
    public void setUp() throws Exception {
        sectionStart = new SectionStart();
        assertNotNull(sectionStart);
    }

    @After
    public void tearDown() throws Exception {
    }


    /**
     * Run instantiate on the add32.wasm bytes.
     */
    @Test
    public void instantiateFull() {
        // I made this one up.  This is not in Add32
        byte[] byteAll = {(byte) 0x02};
        BytesFile payload = new BytesFile(byteAll);

        // run
        sectionStart.instantiate(payload);

        // verify
        // the count is 2
        assertEquals(new VarUInt32(2L), sectionStart.getIndex());


    }

}