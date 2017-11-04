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
package happynewmoonwithreport.type

class Int32GTest extends spock.lang.Specification {
    S32 int32

    void setup() {
        int32 = new S32(0)
    }

    void cleanup() {
    }


    void "MaxBits"() {

        expect:
        new Integer(32) == int32.maxBits()
    }

    void "MinValue"() {
        expect:
        new Integer(-2_147_483_648) == int32.minValue()
    }

    void "MaxValue"() {

        expect:
        new Integer(2_147_483_647) == int32.maxValue()
        (1 << 31) - 1 == new Double(Math.pow(2, 31)).intValue()

    }
}
