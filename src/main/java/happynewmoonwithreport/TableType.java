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

import happynewmoonwithreport.type.LimitType;
import happynewmoonwithreport.type.U32;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.UInt8;

/**
 * Table Type
 * <p>
 * Source : <a href= "http://webassembly.org/docs/binary-encoding/#table_type" target="_top">
 * http://webassembly.org/docs/binary-encoding/#table_type
 * </a>
 * </p>
 * <p>
 * Source:  <a href = "https://webassembly.github.io/spec/syntax/types.html#syntax-tabletype" target="_top">
 * Table Type
 * </a>
 */
public class TableType implements Validation {
/*
 * TODO  move to 'type' package
 */

    /**
     * may only be "anyFunc" in MVP.
     */
    private ElementType elementType;
    private LimitType limit;

    public TableType(BytesFile payload) {
        elementType = new ElementType(payload);
        limit = new LimitType(payload);
    }

    public TableType(ElementType elementType, LimitType limit) {
        this.elementType = elementType;
        this.limit = limit;
    }
    // end constructors

    /**
     * The limits must be valid.
     * <p>
     * source:  <a href="https://webassembly.github.io/spec/valid/types.html#table-types" target="_top">
     * https://webassembly.github.io/spec/valid/types.html#table-types
     * </a>
     *
     * @return true if valid.
     */
    @Override
    public Boolean valid() {
        return limit.valid();
    }

    // boring getters and setters
    public ElementType getElementType() {
        return elementType;
    }

    /**
     * minimum  of the memory in Page Size
     *
     * @return min
     */
    public U32 minimum() {
        return limit.minimum();
    }

    /**
     * maximum of the memory in Page Size
     * <p>
     * Usage :
     * <code>
     * if (hasMaximum()) {
     * max = maximum();
     * }
     * </code>
     * <p>
     * Throws RuntimeException is maximum is not set.
     *
     * @return maximum
     */
    public U32 maximum() {
        return limit.maximum();
    }

    public UInt8 hasMaximum() {
        return limit.hasMaximum();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TableType{");
        sb.append("hasMaximum=").append(hasMaximum());
        sb.append(",minimum=").append(minimum());
        if (limit.hasMaximum().booleanValue()) {
            sb.append(", maximum=").append(maximum());
        }
        sb.append('}');
        return sb.toString();
    }

}
