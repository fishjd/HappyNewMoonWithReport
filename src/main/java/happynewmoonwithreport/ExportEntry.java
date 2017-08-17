package happynewmoonwithreport;

import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.WasmString;

/**
 * Export Entry - The details of an export.
 * <p>
 * source:  <a href="http://webassembly.org/docs/binary-encoding/#export-entry" target="_top">
 * http://webassembly.org/docs/binary-encoding/#export-entry
 * </a>
 */
public class ExportEntry {

    private UInt32 fieldLength;
    private WasmString fieldName;
    /**
     * The 'kind' of export.  Function, Memory,
     */
    private ExternalKind externalKind;
    /**
     * The index to the table. For example if externalKind is 'function' then this is the index to the function table
     */
    private UInt32 index;

    public ExportEntry(BytesFile payload) {
        fieldLength = new VarUInt32(payload);
        fieldName = new WasmString(payload, fieldLength);
        externalKind = new ExternalKind(payload);
        index = new VarUInt32(payload);
    }

    public UInt32 getFieldLength() {
        return fieldLength;
    }

    public WasmString getFieldName() {
        return fieldName;
    }

    public ExternalKind getExternalKind() {
        return externalKind;
    }

    public UInt32 getIndex() {
        return index;
    }

}
