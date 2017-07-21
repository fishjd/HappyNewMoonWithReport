package happynewmoonwithreport;

import happynewmoonwithreport.type.VarUInt32;

import java.util.ArrayList;

/**
 * Source <a href="http://webassembly.org/docs/binary-encoding/#memory-section"> http://webassembly.org/docs/binary-encoding/#memory-section</a>
 */
public class SectionExport implements Module {

    private VarUInt32 count;
    private ArrayList<ExportEntry> exports;

    /**
     * @param payload
     */
    @Override
    public void instantiate(BytesFile payload) {

        //* Count
        count = new VarUInt32(payload);

        //* Entries of Global Variables.
        exports = new ArrayList<>(count.integerValue());
        for (Integer index = 0; index < count.integerValue(); index++) {
            ExportEntry export = new ExportEntry(payload);
            exports.add(index, export);
        }
    }

    public VarUInt32 getCount() {
        return count;
    }

    public ArrayList<ExportEntry> getExports() {
        return exports;
    }
}
