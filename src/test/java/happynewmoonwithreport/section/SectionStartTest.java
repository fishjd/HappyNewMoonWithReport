package happynewmoonwithreport.section;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.section.SectionStart;
import happynewmoonwithreport.type.UInt32;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        assertEquals(new UInt32(2L), sectionStart.getIndex());


    }

}