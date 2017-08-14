package happynewmoonwithreport;

import happynewmoonwithreport.type.VarUInt32;

import java.util.ArrayList;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.WasmVector;

/**
 * Source <a href="http://webassembly.org/docs/binary-encoding/#table-section"> http://webassembly.org/docs/binary-encoding/#table-section</a>
 */
public class SectionTable implements Section {

    private UInt32 count;
    private WasmVector<TableType> tables;



    /**
     *
     * @param payload
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
