package happynewmoonwithreport.section;

import happynewmoonwithreport.type.UInt32;

/**
 * The start section is optional.  This is the class to use when it has not been specified.
 * <p>
 * Source <a href="http://webassembly.org/docs/binary-encoding/#memory-section"> http://webassembly.org/docs/binary-encoding/#memory-section</a>
 */
public class SectionStartEmpty   extends  SectionStart{


    public SectionStartEmpty() {
        index = new UInt32(-1);
    }

}
