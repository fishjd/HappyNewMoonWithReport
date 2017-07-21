package happynewmoonwithreport;

import happynewmoonwithreport.type.VarUInt1;
import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.WasmString;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class SectionExportTest {
    SectionExport sectionExport;

    @Before
    public void setUp() throws Exception {
        sectionExport = new SectionExport();
        assertNotNull(sectionExport);
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
                (byte) 0x02, (byte) 0x06, (byte) 0x6D, (byte) 0x65, (byte) 0x6D, (byte) 0x6F, (byte) 0x72,
                (byte) 0x79, (byte) 0x02, (byte) 0x00, (byte) 0x05, (byte) 0x61, (byte) 0x64, (byte) 0x64,
                (byte) 0x33, (byte) 0x32, (byte) 0x00, (byte) 0x00
        };
        BytesFile payload = new BytesFile(byteAll);

        // run
        sectionExport.instantiate(payload);

        // verify
        // the count is 2
        assertEquals(new VarUInt32(2L), sectionExport.getCount());

        ArrayList<ExportEntry> exports = sectionExport.getExports();
        assertEquals(2, exports.size());

        ExportEntry exportEntry0 = exports.get(0);
        assertEquals(new VarUInt32(6L), exportEntry0.getFieldLength());
        assertEquals( new WasmString("memory"), exportEntry0.getFieldName());
        assertEquals(new ExternalKind(2), exportEntry0.getExternalKind());
        assertEquals(new VarUInt32(0L), exportEntry0.getIndex());

        ExportEntry exportEntry1 = exports.get(1);
        assertEquals(new VarUInt32(5L), exportEntry1.getFieldLength());
        assertEquals( new WasmString("add32"), exportEntry1.getFieldName());
        assertEquals(new ExternalKind (0), exportEntry1.getExternalKind());
        assertEquals(new VarUInt32(0L), exportEntry1.getIndex());



    }

}