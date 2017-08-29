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
package happynewmoonwithreport.opcode

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.type.Int32
import spock.lang.Specification

/**
 * Created on 2017-08-25.
 */
class ConstantInt32Test extends Specification {
    void setup() {
    }

    void cleanup() {
    }

    def "Execute Constant Int32"() {
        setup: " a value of ex : 3 "
        WasmInstanceInterface instance = new WasmInstanceStub();
        Int32 value = new Int32(val1);

        ConstantInt32 function = new ConstantInt32(instance);

        when: "run the opcode"
        function.execute(value);

        then: " value is placed on the stack "

        new Int32(expected) == instance.stack().pop();

        where: ""
        val1                    || expected
        3                       || 3
        4                       || 4
        0x7FFF_FFFE             || 0x7FFF_FFFE
        0x7FFF_FFFF             || new Int32(0).maxValue()
        new Int32(0).minValue() || new Int32(0).minValue()
    }

}
