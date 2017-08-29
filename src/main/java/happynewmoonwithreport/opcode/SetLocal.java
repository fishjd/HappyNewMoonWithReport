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

import happynewmoonwithreport.WasmFrame;
import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.type.DataTypeNumber;
import happynewmoonwithreport.type.UInt32;

import java.util.UUID;

/**
 * Set Local - write a local variable or parameter
 * <ol>
 * <li>
 * Let F be the current frame.
 * </li> <li>
 * Assert: due to validation, F.locals[x] exists.
 * </li> <li>
 * Assert: due to validation, a value is on the top of the stack.
 * </li> <li>
 * Pop the value val from the stack.
 * </li> <li>
 * Replace F.locals[x] with the value val.
 * </li>
 * </ol>
 * <p>
 * Source:  <a href="http://webassembly.org/docs/binary-encoding/#variable-access-described-here" target="_top">
 * http://webassembly.org/docs/binary-encoding/#variable-access-described-here
 * </a>
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/exec/instructions.html#exec-set-local" target="_top">
 * https://webassembly.github.io/spec/exec/instructions.html#exec-set-local
 * </a>
 */
public class SetLocal {

    private WasmFrame frame;

    private SetLocal() {
        super();
    }

    public SetLocal(WasmFrame frame) {
        this();
        this.frame = frame;
    }

    /**
     * Execute the opcode
     *
     * @param index index in to the vector that contains the local variable.
     */
    public void execute(UInt32 index) {
        WasmInstanceInterface instance = frame.instance();
        // 1 Frame set in constructor.

        // 2 validate.
        if (frame.localAll().exists(index) == false) {
            throw new WasmRuntimeException(UUID.fromString("87eaa036-eaba-4740-93b6-590230b4ba49"),
                    "SetLocal: Local variable " + index.integerValue() + " does not exist");
        }

        // 3 validate
        if (instance.stack().empty()) {
            throw new WasmRuntimeException(UUID.fromString("5f1559de-055f-495f-b793-c210fd049e52"),
                    "SetLocal: No value on the stack");
        }
        // 4. value
        DataTypeNumber value = (DataTypeNumber) instance.stack().pop();

        // 5. replace
        frame.localAll().set(index, value);
    }


}
