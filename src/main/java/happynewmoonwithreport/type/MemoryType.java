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
package happynewmoonwithreport.type;


import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.Validation;

/**
 * Memory Type,
 * <p>
 * <p>
 * Source:  <a href="http://webassembly.org/docs/binary-encoding/#memory_type" target="_top">
 * http://webassembly.org/docs/binary-encoding/#memory_type
 * </a>
 * <p>
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/syntax/types.html#memory-types" target="_top">
 * https://webassembly.github.io/spec/syntax/types.html#memory-types
 * </a>
 */
public class MemoryType implements Validation {

    private LimitType limit;

    public MemoryType(UInt32 hasMaximum, UInt32 minimum, UInt32 maximum) {
        limit = new LimitType(hasMaximum, minimum, maximum);
    }

    public MemoryType(BytesFile payload) {
        limit = new LimitType(payload);
    }

    /**
     * The limits must be valid.
     * <p>
     * source:  <a href="https://webassembly.github.io/spec/valid/types.html#memory-types" target="_top">
     * https://webassembly.github.io/spec/valid/types.html#memory-types
     * </a>
     *
     * @return true if valid.
     */
    @Override
    public Boolean valid() {
        return limit.valid();
    }


    /**
     * minimum  of the memory in Page Size
     *
     * @return min
     */
    public UInt32 minimum() {
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
    public UInt32 maximum() {
        return limit.maximum();
    }

    public UInt32 hasMaximum() {
        return limit.hasMaximum();
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MemoryType{");
        sb.append("678 hasMaximum=").append(hasMaximum());
        sb.append(", minimum=").append(minimum());
        if (limit.hasMaximum().booleanValue()) {
            sb.append(", maximum=").append(maximum());
        }
        sb.append('}');
        return sb.toString();
    }
}
