package happynewmoonwithreport;

import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.WasmString;

/* @formatter:off */
/**
 *
 * Export entry
 * Field 	    Type 	        Description
 * field_len 	varuint32 	    length of field_str in bytes
 * field_str 	bytes 	        field name: valid UTF-8 byte sequence
 * kind 	    external_kind 	the kind of definition being exported
 * index 	    varuint32 	    the index into the corresponding index space
 *
 *
 * Source: http://webassembly.org/docs/binary-encoding/#func_type
 *//* @formatter:on */
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

    public Export export() {
        Export result = new Export(fieldName.getValue(), externalKind);
        return result;
    }
}
