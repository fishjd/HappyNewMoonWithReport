/*
 *  Copyright 2017 - 2021 Whole Bean Software, LTD.
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
package happynewmoonwithreport.opcode.control;


import happynewmoonwithreport.WasmInstanceInterface;

/**
 * No Operation - Do Nothing
 * <ol>
 * <li>
 * Do Nothing
 * </li>
 * </ol>
 * Source:
 * <a href="https://webassembly.github.io/spec/core/exec/instructions.html#exec-nop"target="_top">
 * https://webassembly.github.io/spec/core/exec/instructions.html#exec-nop
 * </a>
 */
public class Nop {
	private WasmInstanceInterface instance;

	private Nop() {
		super();
	}

	public Nop(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}

	/**
	 * Execute the opcode.
	 */
	public void execute() {
		// Do Nothing!
	}
}
