package happynewmoonwithreport;

import happynewmoonwithreport.type.MemoryType;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.WasmVector;


/**
 * Source <a href="http://webassembly.org/docs/binary-encoding/#memory-section"> http://webassembly.org/docs/binary-encoding/#memory-section</a>
 */
public class SectionMemory implements Module {

    private UInt32 count;
    private WasmVector<MemoryType> memoryTypeAll;

    /**
     * @param payload
     */
    @Override
    public void instantiate(BytesFile payload) {

        //* Count
        count = new VarUInt32(payload);

        //* Entries of Resizeable Limits
        memoryTypeAll = new WasmVector<>(count.integerValue());
        for (Integer index = 0; index < count.integerValue(); index++) {
            MemoryType limit = new MemoryType(payload);
            memoryTypeAll.add(index, limit);
        }
    }

    public UInt32 getCount() {
        return count;
    }

    public WasmVector<MemoryType> getMemoryTypeAll() {
        return memoryTypeAll;
    }
}
