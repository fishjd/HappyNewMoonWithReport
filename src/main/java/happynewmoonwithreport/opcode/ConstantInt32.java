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
import happynewmoonwithreport.type.Int32;
/**
 * Constant Int32
 * <p>
 * <b>Note this is the same for all Constant Operations</b>
 * <p>
 * t.constant c
 * <ol>
 * <li>
 * Push the value t.const c to the stack.   t = Int32
 * </li>
 * </ol>
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/exec/instructions.html#exec-const" target="_top">
 * https://webassembly.github.io/spec/exec/instructions.html#exec-const
 * </a>
 */
public class ConstantInt32 {
    private WasmInstanceInterface instance;

    private ConstantInt32() {
        super();
    }

    public ConstantInt32(WasmInstanceInterface instance) {
        this();
        this.instance = instance;
    }

    /**
     * Execute the opcode.
     */
    public void execute(Int32 value) {
        instance.stack().push(value);

    }
}
