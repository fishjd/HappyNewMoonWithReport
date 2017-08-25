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

import happynewmoonwithreport.type.VarUInt1;

/**
 * The description of a global variable.
 * <p>
 * Source : <a href= "http://webassembly.org/docs/binary-encoding/#global_type" target="_top">
 * http://webassembly.org/docs/binary-encoding/#global_type
 * </a>
 */

public class GlobalType implements Validation {

    /**
     * may only be "anyFunc" in MVP.
     */
    private ValueType contentType;
    private Mutability mutability;

    public GlobalType(BytesFile payload) {
        contentType = new ValueType(payload);
        mutability = new Mutability(new VarUInt1(payload));
    }

    public GlobalType(ValueType contentType, Mutability mutability) {
        this.contentType = contentType;
        this.mutability = mutability;
    }

    @Override
    public Boolean valid() {
        Boolean result = true;
        result &= mutability.valid();
        result &= contentType.valid();

        return result;
    }

    public ValueType getContentType() {
        return contentType;
    }

    public void setContentType(ValueType contentType) {
        this.contentType = contentType;
    }

    public Mutability getMutability() {
        return mutability;
    }

    public void setMutability(Mutability mutability) {
        this.mutability = mutability;
    }


}
