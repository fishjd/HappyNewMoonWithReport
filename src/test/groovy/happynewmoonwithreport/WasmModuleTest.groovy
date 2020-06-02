/*
 *  Copyright 2017 - 2020 Whole Bean Software, LTD.
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
package happynewmoonwithreport

import happynewmoonwithreport.type.MemoryType
import happynewmoonwithreport.type.UInt32
import happynewmoonwithreport.type.UInt8
import spock.lang.Specification

/**
 * Created on 2017-12-22.
 */
class WasmModuleTest extends Specification {
	def "MemoryExists Golden "() {
		setup: "Set up with valid memory"
		WasmModule module = new WasmModule();

		// set up memory and add
		MemoryType memory = new MemoryType(new UInt8(0), new UInt32(0));
		module.memoryAll.add(0, memory);

		when: "do nothing "
		// all code in constructor

		then: " check exists"
		(module.memoryExists(new UInt32(index)) == expected);

		where: ""
		index | expected
		0     | true
		1     | false
		2     | false
	}

	def "MemoryExists fails when no memory exists  "() {
		setup: "Set up with valid memory"
		WasmModule module = new WasmModule();

		// set up memory and add
		// MemoryType memory = new MemoryType(new UInt8(0), new UInt32(0));
		// module.memoryAll.add(0, memory);

		when: "do nothing "
		// all code in constructor

		then: " check does not exists"
		(module.memoryExists(new UInt32(index)) == expected);

		where: ""
		index | expected
		0     | false
		1     | false
		2     | false

	}
}
