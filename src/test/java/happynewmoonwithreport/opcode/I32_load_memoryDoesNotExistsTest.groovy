/*
 *  Copyright 2018 Whole Bean Software, LTD.
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

import happynewmoonwithreport.WasmFrame
import happynewmoonwithreport.WasmModule
import happynewmoonwithreport.WasmRuntimeException
import spock.lang.Specification

/**
 * Created on 2018-02-12.
 */
class I32_load_memoryDoesNotExistsTest extends Specification {
	WasmModule module;
	WasmFrame frame;
	I32_load i32Load;

	void setup() {
		module = new WasmModule();
		frame = new WasmFrame(module)
		i32Load = new I32_load(frame);
	}

	void cleanup() {
	}

	def "Execute Golden Path"() {
		// setup: ""

		when: ""
		i32Load.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.getUuid().toString().contains("35030ef5-2f4a-496c-8e67-06245e05d56d");


	}


}
