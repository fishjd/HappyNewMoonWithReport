package happynewmoonwithreport.section;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.ElementType;
import happynewmoonwithreport.ResizeableLimits;
import happynewmoonwithreport.TableType;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt1;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class SectionTableTest {
    SectionTable sectionTable;

    @Before
    public void setUp() throws Exception {
        sectionTable = new SectionTable();
        assertNotNull(sectionTable);
    }

    @After
    public void tearDown() throws Exception {
    }


    /**
     *  Run instantiate on the add32.wasm bytes.
     */
    @Test
    public void instantiateAdd32()  {
        byte[] byteAll = {(byte) 0x01, (byte) 0x70, (byte) 0x00, (byte) 0x00};
        BytesFile payload = new BytesFile(byteAll);

        // run
        sectionTable.instantiate(payload);

        // verify
        // the count is 1
        assertEquals(new UInt32(1L), sectionTable.getCount());

        ArrayList<TableType> typeAll = sectionTable.getTables();
        assertEquals(1, typeAll.size());

        TableType table = typeAll.get(0);
        assertNotNull(table);

        assertEquals(new ElementType("anyFunc"), table.getElementType());

        ResizeableLimits limits = table.getLimits();
        assertEquals (new VarUInt1(0), limits.getHasMax()) ;
        assertEquals (new UInt32(0L), limits.getInitialLength()) ;
        assertNull(limits.getMaximumLength());

    }

}