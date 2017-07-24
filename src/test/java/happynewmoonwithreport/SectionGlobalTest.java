package happynewmoonwithreport;

import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt1;
import happynewmoonwithreport.type.VarUInt32;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class SectionGlobalTest {
    SectionGlobal sectionGlobal;

    @Before
    public void setUp() throws Exception {
        sectionGlobal = new SectionGlobal();
        assertNotNull(sectionGlobal);
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
        byte[] byteAll = {
                (byte) 0x01,//
                (byte) 0x7F, // -0x01 i32
                (byte) 0x00 // mutability = 0
        };
        BytesFile payload = new BytesFile(byteAll);

        // run
        sectionGlobal.instantiate(payload);

        // verify
        // the count is 1
        assertEquals(new UInt32(1L), sectionGlobal.getCount());

        ArrayList<GlobalVariableType> globals = sectionGlobal.getGlobals();
        assertEquals(1, globals.size());

        GlobalVariableType globalVariable = globals.get(0);
        GlobalType type = globalVariable.getType();
        assertEquals(new ValueType(-0x01), type.getContentType());
        assertEquals(new VarUInt1(0), type.getMutability());


    }

}