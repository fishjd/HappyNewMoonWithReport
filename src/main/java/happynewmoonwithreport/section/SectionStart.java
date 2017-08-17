package happynewmoonwithreport.section;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;

/**
 * The start section declares the start function.
 * <p>
 * If the module has a start node defined, the function it refers should be called by the loader after the instance is
 * initialized, including its Memory and Table though Data and Element sections, and before the exported functions are
 * callable.
 * <p>
 * Source:  <a href="http://webassembly.org/docs/binary-encoding/#start-section" target="_top">
 * http://webassembly.org/docs/binary-encoding/#start-section
 * </a>
 * <p>
 * Source:  <a href="http://webassembly.org/docs/modules/#module-start-function" target="_top">
 * http://webassembly.org/docs/modules/#module-start-function
 * </a>
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/binary/modules.html#start-section" target="_top"> 
 * https://webassembly.github.io/spec/binary/modules.html#start-section 
 * </a> 
 */
public class SectionStart implements Section {

    protected UInt32 index;

    /**
     * @param payload the input BytesFile.
     */
    @Override
    public void instantiate(BytesFile payload) {

        //* Index
        index = new VarUInt32(payload);

    }

    public UInt32 getIndex() {
        return index;
    }
}
