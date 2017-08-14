package happynewmoonwithreport.section;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.ExportEntry;
import happynewmoonwithreport.ExternalKind;
import happynewmoonwithreport.section.SectionExport;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.WasmString;
import org.junit.After;
import org.junit.Assert;
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
        // This is in add32   starting at by 0x3D
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
        assertEquals(new UInt32(2L), sectionExport.getCount());

        ArrayList<ExportEntry> exports = sectionExport.getExports();
        assertEquals(2, exports.size());

        ExportEntry exportEntry0 = exports.get(0);
        assertEquals(new UInt32(6L), exportEntry0.getFieldLength());
        assertEquals( new WasmString("memory"), exportEntry0.getFieldName());
        assertEquals(new ExternalKind(ExternalKind.memory), exportEntry0.getExternalKind());
        assertEquals(new UInt32(0L), exportEntry0.getIndex());

        ExportEntry exportEntry1 = exports.get(1);
        assertEquals(new UInt32(5L), exportEntry1.getFieldLength());
        assertEquals( new WasmString("add32"), exportEntry1.getFieldName());
        assertEquals(new ExternalKind (ExternalKind.function), exportEntry1.getExternalKind());
        assertEquals(new UInt32(0L), exportEntry1.getIndex());



    }

}