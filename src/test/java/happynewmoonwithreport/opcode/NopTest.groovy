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
import happynewmoonwithreport.type.S32
import spock.lang.Specification
/**
 * Created on 2017-08-25.
 */
class NopTest extends Specification {
    void setup() {
    }

    void cleanup() {
    }

    def "Execute"() {
        setup: " an instance with one local variable "
        WasmInstanceInterface instance = new WasmInstanceStub();
        instance.localAll().add(new S32(3));

        Nop function = new Nop(instance);

        when: "run the opcode"
        function.execute();

        then: " the stack should be empty."
        0 == instance.stack().size()
    }
}
