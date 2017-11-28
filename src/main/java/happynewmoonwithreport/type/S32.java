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

import happynewmoonwithreport.WasmRuntimeException;

import java.util.UUID;

/**
 * An signed integer of 32 bits,
 */
public class S32 extends I32 {

    public S32() {
    }

    public S32(Integer value) {
        this();
        this.value = value;
    }

    public S32(Long value) {
        this();
        if (isBoundByInteger(value) == false) {
            throw new WasmRuntimeException(UUID.fromString("2c992eb4-00c8-495f-ac1e-afb85fd47aff"), "Value not bound by integer");
        }
        this.value = value.intValue();
    }

    public S32(S32 input) {
        this();
        this.value = input.integerValue();
    }

    public S32(U32 input) {
        this();
        this.value = input.integerValue();
    }

    /* private functions **/

	/* Override DataTypeNumber */

    @Override
    public Integer maxBits() {
        return 32;
    }

    @Override
    public Boolean booleanValue() {
        return value != 0;
    }

    public I32 lessThan(S32 other) {
        I32 result;
        Integer iResult;
        if (value < other.value) {
            iResult = 1;
        } else {
            iResult = 0;
        }
        result = new I32(iResult);
        return result;
    }

    public I32 lessThanEqual(S32 other) {
        I32 result;
        Integer iResult;
        if (value <= other.value) {
            iResult = 1;
        } else {
            iResult = 0;
        }
        result = new I32(iResult);
        return result;
    }

    public I32 greaterThan(S32 other) {
        I32 result;
        Integer iResult;
        if (value > other.value) {
            iResult = 1;
        } else {
            iResult = 0;
        }
        result = new I32(iResult);
        return result;
    }

    public I32 greaterThanEqual(S32 other) {
        I32 result;
        Integer iResult;
        if (value >= other.value) {
            iResult = 1;
        } else {
            iResult = 0;
        }
        result = new I32(iResult);
        return result;
    }


//
//    @Override
//    public Long minValue() {
//        Long minValue = -1L * (1L << (maxBits() - 1L));
//        return minValue;
//
//    }
//
//    @Override
//    public Long maxValue() {
//        Long maxValue = (1L << (maxBits() - 1L)) - 1L;
//        return maxValue;
//    }
//
//
    /* override of Object **/
    @Override
    public String toString() {
        return "S32{" +
                "value=" + value +
                "} ";
    }
}
