package happynewmoonwithreport;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.ValueType;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;

import java.util.ArrayList;

/**
 * <h1>Local Entry</h1>
 * <p>
 * Each local entry declares a number of local variables of a given type. It is legal to have several entries with the
 * same type.
 * <p>
 * <p>
 * <table>
 * <p>
 * <tr> <th>Field</th> 	<th>Type</th> 	<th>Description</th> </tr>
 * <p>
 * <tr><td>count</td><td>	varuint32 </td><td>	number of local variables of the following type </td></tr>
 * <p>
 * <tr><td>type</td><td>value_type </td><td>	type of the variables</td></tr>
 * <p>
 * </table>
 */
public class LocalEntry {

    private UInt32 count;
    private ValueType valueType;


    /**
     * @param payload
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
