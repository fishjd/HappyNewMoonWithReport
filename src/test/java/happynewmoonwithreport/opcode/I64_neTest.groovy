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
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.I64
import spock.lang.Specification

/**
 * Created on 2018-01-23.
 */
class I64_neTest extends Specification {
    void setup() {
    }

    void cleanup() {
    }

    def "Execute I64 Not equal to "() {
        setup: " push two values on stack."
        WasmInstanceInterface instance = new WasmInstanceStub();
        instance.stack().push(new I64(val1));
        instance.stack().push(new I64(val2));

        I64_ne function = new I64_ne(instance);

        when: "run the opcode"
        function.execute();

        then: " a value of expected"
        instance.stack().pop() == new I32(expected)  ;

        where: ""
        val1                  | val2                  || expected
        4                     | 3                     || 1
        3                     | 4                     || 1
        4                     | 4                     || 0
        0                     | 0                     || 0
        0xFFFF_FFFF_FFFF_FFFF | 0xFFFF_FFFF_FFFF_FFFF || 0
        0xFFFF_FFFF_FFFF_FFFE | 0xFFFF_FFFF_FFFF_FFFF || 1
    }

    def "Execute I64_ne throw exception on incorrect Type on second param "() {
        setup: " a value of I64 value 1 and a value of I32 of value 2"
        WasmInstanceInterface instance = new WasmInstanceStub();
        instance.stack().push(new I64(3));  // value 1
        instance.stack().push(new I32(4));  // value 2 is an incorrect type

        I64_ne function = new I64_ne(instance);

        when: "run the opcode"
        function.execute();

        then: " Thrown Exception"
        WasmRuntimeException exception = thrown();
        exception.message.contains("Value2");
        exception.getUuid().toString().contains("35101325-10e5-41c3-86e1-b79dd7eac7c6");
    }

    def "Execute I64_ne throw exception on incorrect Type on first param "() {
        setup: " a value of I32 value 1 and a value of I64 of value 2"
        WasmInstanceInterface instance = new WasmInstanceStub();
        instance.stack().push(new I32(3));  // value 1 is an incorrect type
        instance.stack().push(new I64(4));  // value 2

        I64_ne function = new I64_ne(instance);

        when: "run the opcode"
        function.execute();

        then: " Thrown Exception"
        WasmRuntimeException exception = thrown();
        exception.message.contains("Value1");
        exception.getUuid().toString().contains("1f3ea0f6-6b0d-4aa0-9065-8f35e3218af2");
    }
}
