package happynewmoonwithreport;

import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;

/**
 * <h1>Local Entry</h1>
 * <p>
 * Each local entry declares a number of local variables of a given type. It is legal to have several entries with the
 * same type.
 * </p>
 *
 * <table  summary="Local Entry Table ">
 * <tr> <th>Field</th> 	<th>Type</th> 	<th>Description</th> </tr>
 * <tr><td>count</td><td>	varuint32 </td><td>	number of local variables of the following type </td></tr>
 * <tr><td>type</td><td>value_type </td><td>	type of the variables</td></tr>
 * </table>
 *
 */
public class LocalEntry {

    private UInt32 count;
    private ValueType valueType;


    /**
     * @param payload the input BytesFile.
     */
    public LocalEntry(BytesFile payload) {

        //* Count
        count = new VarUInt32(payload);

        //* Type
        valueType = new ValueType(payload);
    }

    public UInt32 getCount() {
        return count;
    }

    public ValueType getValueType() {
        return valueType;
    }
}
