package happynewmoonwithreport;

import happynewmoonwithreport.type.VarUInt32;

import java.util.ArrayList;

/**
 * Source <a href="http://webassembly.org/docs/binary-encoding/#table-section"> http://webassembly.org/docs/binary-encoding/#table-section</a>
 */
public class SectionTable implements Module {

    private VarUInt32 count;
    private ArrayList<TableType> tables;



    /**
     *
     * @param payload
     */
    @Override
    public void instantiate(BytesFile payload) {

        //* Count
        count = new VarUInt32(payload);

        //* Entries of TableType
        tables = new ArrayList<>(count.integerValue());
        for (Integer index = 0; index < count.integerValue(); index++) {
            TableType table = new TableType(payload);
            tables.add(index, table);
        }
    }

    public VarUInt32 getCount() {
        return count;
    }

    public ArrayList<TableType> getTables() {
        return tables;
    }
}
