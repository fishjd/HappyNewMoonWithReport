/*
 *  Copyright 2017 Whole Bean Software, LTD.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package happynewmoonwithreport;

import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;

/**
 * <h1>Local Entry</h1>
 * <p>
 * Each local entry declares a number of local variables of a given type. It is legal to have several entries with the
 * same type.
 * </p>
 * <p>
 * <table  summary="Local Entry Table ">
 * <tr> <th>Field</th> 	<th>Type</th> 	<th>Description</th> </tr>
 * <tr><td>count</td><td>	varuint32 </td><td>	number of local variables of the following type </td></tr>
 * <tr><td>type</td><td>value_type </td><td>	type of the variables</td></tr>
 * </table>
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
