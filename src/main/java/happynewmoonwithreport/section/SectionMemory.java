package happynewmoonwithreport.section;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.type.MemoryType;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.WasmVector;


/**
 * The encoding of the memory section.
 * <p>
 * Source <a href="http://webassembly.org/docs/binary-encoding/#memory-section" target="_top">
 * http://webassembly.org/docs/binary-encoding/#memory-section
 * </a>
 * <p>
 * Source:  <a href="http://webassembly.org/docs/modules/#linear-memory-section" target="_top">
 * http://webassembly.org/docs/modules/#linear-memory-section
 * </a>
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/binary/modules.html#memory-section" target="_top"> 
 * https://webassembly.github.io/spec/binary/modules.html#memory-section 
 * </a> 
 */
public class SectionMemory implements Section {

    private UInt32 count;
    private WasmVector<MemoryType> memoryTypeAll;

    /**
     * @param payload the input BytesFile.
     */
    @Override
    public void instantiate(BytesFile payload) {

        //* Count
        count = new VarUInt32(payload);

        //* Entries of Resizeable Limits
        memoryTypeAll = new WasmVector<>(count.integerValue());
        for (Integer index = 0; index < count.integerValue(); index++) {
            MemoryType memoryType = new MemoryType(payload);
            memoryTypeAll.add(index, memoryType);
        }
    }

    public UInt32 getCount() {
        return count;
    }

    public WasmVector<MemoryType> getMemoryTypeAll() {
        return memoryTypeAll;
    }
}
