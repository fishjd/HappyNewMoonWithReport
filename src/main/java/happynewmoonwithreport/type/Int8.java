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
 * An signed integer of 8 bits
 */
public class Int8 extends Int<Byte> {


    public Int8(Byte value) {
        this.value = value;
    }

	/* private functions **/

	/* Override DataTypeNumber */

    @Override
    public Integer maxBits() {
        return 8;
    }

    @Override
    public Byte minValue() {
        Integer minValue = (int) Math.pow(-2, maxBits() - 1);
        return minValue.byteValue();

    }

    @Override
    public Byte maxValue() {
        Integer maxValue = (int) Math.pow(2, maxBits() - 1) - 1;
        return maxValue.byteValue();
    }

    /* override of Object **/
    @Override
    public String toString() {
        return "Int8{" +
                "value=" + value +
                "} ";
    }
}
