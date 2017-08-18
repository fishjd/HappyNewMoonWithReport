package happynewmoonwithreport.section;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.TableType;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.WasmVector;

/**
 * The encoding of a Table section.
 * <p>
 * Source <a href="http://webassembly.org/docs/binary-encoding/#table-section" target="_top">
 * http://webassembly.org/docs/binary-encoding/#table-section
 * </a>
 * <p>
 * Source:  <a href="http://webassembly.org/docs/modules/#table-section" target="_top">
 * http://webassembly.org/docs/modules/#table-section
 * </a>
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/binary/modules.html#table-section" target="_top">
 * https://webassembly.github.io/spec/binary/modules.html#table-section
 * </a>
 */
public class SectionTable implements Section {

    private UInt32 count;
    private WasmVector<TableType> tables;


    /**
     * @param payload the input BytesFile.
     */
    @Override
    public void instantiate(BytesFile payload) {

        //* Count
        count = new VarUInt32(payload);

        //* Entries of TableType
        tables = new WasmVector<>(count.integerValue());
        for (Integer index = 0; index < count.integerValue(); index++) {
            TableType table = new TableType(payload);
            tables.add(index, table);
        }
    }

    public UInt32 getCount() {
        return count;
    }

    public WasmVector<TableType> getTables() {
        return tables;
    }
}
