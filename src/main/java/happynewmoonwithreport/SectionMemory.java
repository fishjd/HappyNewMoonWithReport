package happynewmoonwithreport;

import happynewmoonwithreport.type.VarUInt32;

import java.util.ArrayList;import happynewmoonwithreport.type.UInt32;


/**
 * Source <a href="http://webassembly.org/docs/binary-encoding/#memory-section"> http://webassembly.org/docs/binary-encoding/#memory-section</a>
 */
public class SectionMemory implements Module {

    private UInt32 count;
    private ArrayList<ResizeableLimits> limits;

    /**
     *
     * @param payload
     */
    @Override
    public void instantiate(BytesFile payload) {

        //* Count
        count = new VarUInt32(payload);

        //* Entries of Resizeable Limits
        limits = new ArrayList<>(count.integerValue());
        for (Integer index = 0; index < count.integerValue(); index++) {
            ResizeableLimits limit = new ResizeableLimits(payload);
            limits.add(index, limit);
        }
    }

    public UInt32 getCount() {
        return count;
    }

    public ArrayList<ResizeableLimits> getLimits() {
        return limits;
    }
}
