package happynewmoonwithreport;

import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class SectionFunctionTest {
    SectionFunction sectionFunction;

    @Before
    public void setUp() throws Exception {
        sectionFunction = new SectionFunction();
        assertNotNull(sectionFunction);
    }

    @After
    public void tearDown() throws Exception {
    }



    @Test
    public void instantiate() throws Exception {
        byte[] byteAll = {(byte) 0x01, (byte) 0x00};
        BytesFile payload = new BytesFile(byteAll);

        // run
        sectionFunction.instantiate(payload);

        // verify
        // the count is 1
        assertEquals(new UInt32(1L), sectionFunction.getCount());

        ArrayList<VarUInt32> typeAll = sectionFunction.getTypes();
        assertEquals(1, typeAll.size());

        VarUInt32 type = typeAll.get(0);
        assertEquals(new UInt32(0L), type);



    }

}