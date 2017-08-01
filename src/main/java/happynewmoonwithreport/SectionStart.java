package happynewmoonwithreport;

import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.UInt32;

import java.util.ArrayList;

/**
 * Source <a href="http://webassembly.org/docs/binary-encoding/#memory-section"> http://webassembly.org/docs/binary-encoding/#memory-section</a>
 */
public class SectionStart implements Module {

    private UInt32 index;

    /**
     * @param payload
     */
    @Override
    public void instantiate(BytesFile payload) {

        //* Index
        index  = new VarUInt32(payload);

    }

    public UInt32 getIndex() {
        return index;
    }
}
