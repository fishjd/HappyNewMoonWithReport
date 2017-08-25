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
import happynewmoonwithreport.type.DataTypeNumber;
import happynewmoonwithreport.type.Int32;

import java.util.UUID;

/**
 * Sign-agnostic addition
 * <p>
 * <b>Note this is the same for all Binary Operations</b>
 * d<p>
 * t.binop
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
 * If binopt(c1,c2) is defined, then: Let c be a possible result of computing binopt(c1,c2). Push the value t.const
 * c to the stack.
 * </li>
 * <li>
 * Else:
 * Trap.
 * <p>
 * </li>
 * </ol>
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/exec/instructions.html#numeric-instructions" target="_top">
 * https://webassembly.github.io/spec/exec/instructions.html#numeric-instructions  t.binop
 * </a>
 */
public class AddI32 {
    private WasmInstanceInterface instance;

    private AddI32() {
        super();
    }

    public AddI32(WasmInstanceInterface instance) {
        this();
        this.instance = instance;
    }

    /**
     * Execute the opcode.
     */
    public void execute() {
        WasmStack<DataTypeNumber> stack = instance.stack();
        if ((instance.stack().peek() instanceof Int32) == false) {
            throw new WasmRuntimeException(UUID.fromString("22500212-e077-4507-a27a-3a08039da2b7"),
                    "addI32: Value1 type is incorrect");
        }
        Int32 value1 = (Int32) stack.pop();
        if ((stack.peek() instanceof Int32) == false) {
            throw new WasmRuntimeException(UUID.fromString("59c20edb-690b-4260-b5cf-704cd509ac07"),
                    "addI32: Value2 type is incorrect");
        }
        Int32 value2 = (Int32) stack.pop();

        Int32 result = new Int32(value1.integerValue() + value2.integerValue());

        stack.push(result);
    }
}
