package happynewmoonwithreport;

import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt1;
import happynewmoonwithreport.type.VarUInt32;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class SectionMemoryTest {
    SectionMemory sectionMemory;

    @Before
    public void setUp() throws Exception {
        sectionMemory = new SectionMemory();
        assertNotNull(sectionMemory);
    }

    @After
    public void tearDown() throws Exception {
    }


    /**
     *  Run instantiate on the add32.wasm bytes.
     */
    @Test
    public void instantiateAdd32()  {
        byte[] byteAll = {(byte) 0x01, (byte) 0x00, (byte) 0x01};
        BytesFile payload = new BytesFile(byteAll);

        // run
        sectionMemory.instantiate(payload);

        // verify
        // the count is 1
        assertEquals(new UInt32(1), sectionMemory.getCount());

        ArrayList<ResizeableLimits> limitAll = sectionMemory.getLimits();
        assertEquals(1, limitAll.size());

        ResizeableLimits limits = limitAll.get(0);
        assertEquals (new VarUInt1(0), limits.getFlags()) ;
        assertEquals (new UInt32(1), limits.getInitialLength()) ;
        assertNull(limits.getMaximumLength());

    }

}