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
package happynewmoonwithreport.opcode;


import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.I32;
import happynewmoonwithreport.type.UInt32;

import java.util.UUID;

/**
 * I32 Less than Unsigned  (i32_lt_u)
 * <p>
 * <b>Note this is the same for all Relative Operations</b>
 * <p>
 * t.relop
 * <ol>
 * <li>
 * Assert: due to validation, two values of value type t are on the top of the stack.
 * </li>
 * <li>
 * Pop the value t.const c2 from the stack.
 * </li>
 * <li>
 * Pop the value t.const c1 from the stack.
 * </li>
 * <li>
 * Let c be the result of computing relopt(c1,c2).
 * </li>
 * <li>
 * Push the value i32.const c to the stack.
 * <p>
 * </li>
 * </ol>
 * <p>
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/exec/instructions.html#exec-relop" target="_top">
 * https://webassembly.github.io/spec/exec/instructions.html#exec-relop
 * </a>
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/exec/numerics.html#op-ilt-u" target="_top">
 * https://webassembly.github.io/spec/exec/numerics.html#op-ilt-u
 * </a>
 * </p>
 */
public class I32_lt_u {
    private WasmInstanceInterface instance;

    private I32_lt_u() {
        super();
    }

    public I32_lt_u(WasmInstanceInterface instance) {
        this();
        this.instance = instance;
    }


    /**
     * Execute the opcode.
     */
    public void execute() {
        WasmStack<Object> stack = instance.stack();
        if ((stack.peek() instanceof I32) == false) {
            throw new WasmRuntimeException(UUID.fromString("cb177362-ff56-4f17-800d-023c699a510e"),
                    "I32_lt_u: Value2 type is incorrect");
        }
        I32 value2 = (I32) stack.pop();

        if ((stack.peek() instanceof I32) == false) {
            throw new WasmRuntimeException(UUID.fromString("d05dde28-6aaa-4de5-b140-2343c02204db"),
                    "I32_lt_u: Value1 type is incorrect");
        }
        I32 value1 = (I32) stack.pop();

        // these values are unsigned values and thus we use UInt32
        UInt32 value2Unsigned = value2.unsigned();
        UInt32 value1Unsigned = value1.unsigned();
        Integer iResult;
        if (value1Unsigned.value() < value2Unsigned.value()) {
            iResult = 1;
        } else {
            iResult = 0;
        }
        I32 result = new I32(iResult);

        stack.push(result);
    }


}
