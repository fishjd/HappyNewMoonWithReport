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


import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmLabel;

/**
 * Block
 * <p>
 * <ol>
 * <li>
 * Let n be the arity |t?| of the result type t?.
 * </li><li>
 * Let L be the label whose arity is n and whose continuation is the end of the block.
 * </li><li>
 * Enter the block instr∗ with label L.
 * </li>
 * </ol>
 * <p>
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/exec/instructions.html#exec-block" target="_top">
 * https://webassembly.github.io/spec/exec/instructions.html#exec-block
 * </a>
 */
public class Block {
    private WasmInstanceInterface instance;

    private Block() {
        super();
    }

    public Block(WasmInstanceInterface instance) {
        this();
        this.instance = instance;
    }

    /**
     * Execute the opcode.
     */
    public void execute() {
        BytesFile code = instance.getCode();

        WasmLabel label = new WasmLabel(code);
        instance.stack().push(label);
    }
}
