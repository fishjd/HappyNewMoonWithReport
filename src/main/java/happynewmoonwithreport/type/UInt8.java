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

import static happynewmoonwithreport.type.utility.MathWBS.pow2;

/**
 * An unsigned integer of 8 bits, represented in N/8 bytes in little endian
 * order. N is either 8, 16, or 32.
 */
public class UInt8 extends U32 {  // TODO change to Short

    // protected Byte value;

    public UInt8() {

    }

    public UInt8(BytesFile bytesFile) {
        assert (bytesFile.longEnough(minBytes()));
        value = convert(bytesFile).longValue();
    }

    public UInt8(Integer value) {
        this.value = value.longValue();
    }

    public Integer convert(BytesFile bytesFile) {
        Integer result = 0;
        // little Endian!
        for (Integer i = 0; i < maxBits(); i = i + 8) {
            result += Byte.toUnsignedInt(bytesFile.readByte()) << i;
        }
        return result;
    }


	/* private functions **/

	/* Override DataTypeNumber */

    @Override
    public Integer maxBits() {
        return 8;
    }

    @Override
    public Long minValue() {
        return 0L;
    }

    @Override
    public Long maxValue() {
        return pow2(maxBits());
    }

    /* override of Object **/
    @Override
    public String toString() {
        return "UInt8{" +
                "value=" + value +
                "} ";
    }
}
