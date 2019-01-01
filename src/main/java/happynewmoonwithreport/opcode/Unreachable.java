/*
 *  Copyright 2017 - 2019 Whole Bean Software, LTD.
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
import happynewmoonwithreport.WasmTrapException;

import java.util.UUID;

/**
 * Unreachable
 * <p>
 * <ol>
 * <li>
 * Trap i.e. Throw WasmTrapException.
 * </li>
 * </ol>
 * <p>
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/exec/instructions.html#exec-unreachable" target="_top">
 * https://webassembly.github.io/spec/exec/instructions.html#exec-unreachable
 * </a>
 */
public class Unreachable {
	private WasmInstanceInterface instance;

	private Unreachable() {
		super();
	}

	public Unreachable(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}

	/**
	 * Execute the opcode.
	 */
	public void execute() {
		throw new WasmTrapException(UUID.fromString("e496383f-377d-4090-9cff-64bdfc50a32e"),
				"Unreachable code tried to execute!");
	}
}
