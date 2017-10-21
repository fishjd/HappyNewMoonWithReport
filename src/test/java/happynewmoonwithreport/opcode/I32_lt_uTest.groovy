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
 * Created on 2017-08-25.
 */
class I32_lt_uTest extends Specification {
    void setup() {
    }

    void cleanup() {
    }

    def "Execute I32 Less than unsigned "() {
        setup: " push two values on stack."
        WasmInstanceInterface instance = new WasmInstanceStub();
        instance.stack().push(new I32(val1));
        instance.stack().push(new I32(val2));

        I32_lt_u function = new I32_lt_u(instance);

        when: "run the opcode"
        function.execute();

        then: " a value of expected"
        new I32(expected) == instance.stack().pop();
        System.out.println("Result = " + expected + " Value 1 = " + val1 + " Value 2 = " + val2);


        where: ""
        val1        | val2        || expected
        3           | 4           || 1
        4           | 3           || 0
        4           | 4           || 0
        0           | 0           || 0
        0xFFFF_FFFF | 0xFFFF_FFFF || 0
        0xFFFF_FFFE | 0xFFFF_FFFF || 1
        0x0FFF_FFFF | 0xFFFF_FFFF || 1
        0xFFFF_FFFF | 0x0FFF_FFFF || 0
    }

    def "Execute i32_lt_u throw exception on incorrect Type on second param "() {
        setup: " a value of I32  value 1  and a value of I64 of value 2"
        WasmInstanceInterface instance = new WasmInstanceStub();
        instance.stack().push(new I32(3));  // value 1
        instance.stack().push(new I64(4));  // value 2

        I32_lt_u function = new I32_lt_u(instance);

        when: "run the opcode"
        function.execute();

        then: " Thrown Exception"
        WasmRuntimeException exception = thrown();
        exception.message.contains("Value2");
        exception.getUuid().toString().contains("cb177362-ff56-4f17-800d-023c699a510e");
    }

    def "Execute i32_lt_u throw exception on incorrect Type on first param "() {
        setup: " a value of I64  value 1  and a value of I32 of value 2"
        WasmInstanceInterface instance = new WasmInstanceStub();
        instance.stack().push(new I64(3));  // value 1
        instance.stack().push(new I32(4));  // value 2

        I32_lt_u function = new I32_lt_u(instance);

        when: "run the opcode"
        function.execute();

        then: " Thrown Exception"
        WasmRuntimeException exception = thrown();
        exception.message.contains("Value1");
        exception.getUuid().toString().contains("d05dde28-6aaa-4de5-b140-2343c02204db");
    }

}
