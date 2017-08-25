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

/**
 * An signed integer of 32 bits,
 */
public class Int32 extends Int<Integer> {
    public Int32() {
        super();
    }

    public Int32(Integer value) {
        this();
        this.value = value;
    }

    public Int32(DataTypeNumber number) {
        this.value = number.integerValue();
    }

	/* private functions **/

	/* Override DataTypeNumber */

    @Override
    public Integer maxBits() {
        return 32;
    }

    @Override
    public Integer minValue() {
        Integer minValue = -1 * (1 << (maxBits() - 1));
        return minValue;

    }

    @Override
    public Integer maxValue() {
        Integer maxValue = (1 << (maxBits() - 1)) - 1;
        return maxValue;
    }


    /* override of Object **/
    @Override
    public String toString() {
        return "Int32{" +
                "value=" + value +
                "} ";
    }
}
