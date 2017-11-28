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

import static happynewmoonwithreport.type.utility.MathWBS.pow2;

/**
 * An signed integer of 8 bits
 * <p>
 * <p>
 * Source:  <a href="http://webassembly.org/docs/binary-encoding/#uintn" target="_top">
 * http://webassembly.org/docs/binary-encoding/#uintn
 * </a>
 */
public class SInt32 extends S32 {

    protected SInt32() {
    }

    public SInt32(Integer value) {
        this.value = value;
    }

    public SInt32 (S32 s32) {

    }

	/* private functions **/

	/* Override DataTypeNumber */

    @Override
    public Integer maxBits() {
        return 32;
    }

    @Override
    public Long minValue() {
        return -1L * pow2(maxBits() - 1);
    }

    @Override
    public Long maxValue() {
        return pow2(maxBits() - 1) - 1;
    }

    /* override of Object **/
    @Override
    public String toString() {
        return "Int8{" +
                "value=" + value +
                "} ";
    }
}
