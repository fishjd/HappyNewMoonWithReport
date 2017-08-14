package happynewmoonwithreport.section;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.ExportEntry;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.WasmVector;

/**
 * Source <a href="http://webassembly.org/docs/binary-encoding/#memory-section"> http://webassembly.org/docs/binary-encoding/#memory-section</a>
 */
public class SectionExport implements Section {

    private UInt32 count;
    private WasmVector<ExportEntry> exports;

    /**
     * @param payload
     */
    @Override
    public void instantiate(BytesFile payload) {

        //* Count
        count = new VarUInt32(payload);

        //* Entries of Global Variables.
        exports = new WasmVector<>(count.integerValue());
        for (Integer index = 0; index < count.integerValue(); index++) {
            ExportEntry export = new ExportEntry(payload);
            exports.add(index, export);
        }
    }

    public UInt32 getCount() {
        return count;
    }

    public WasmVector<ExportEntry> getExports() {
        return exports;
    }
}
