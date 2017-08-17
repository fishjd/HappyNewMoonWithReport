/**
 *
 */
package happynewmoonwithreport.section;

import happynewmoonwithreport.BytesFile;

/**
 * Interface for Sections.
 * All Sections are required to instantiate.
 */
public interface Section {

    void instantiate(BytesFile payload);

    /* TODO */
//  /**
//  * Write to a file.  The complement to instantiate.
//  */
//  write (bytesFile)
}
