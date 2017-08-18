package happynewmoonwithreport.section;

import happynewmoonwithreport.type.UInt32;

/**
 * The start section is optional, this is the class to use when it has not been specified.
 *
 * @see SectionStart
 */
public class SectionStartEmpty extends SectionStart {

    public SectionStartEmpty() {
        index = new UInt32(-1);
    }

}
