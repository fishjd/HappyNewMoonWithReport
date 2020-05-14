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

package happynewmoonwithreport.opcode.convert


import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.I64
import spock.lang.Specification

class I64_extend_I32_u_Test extends Specification {
	// CUT  Component/Class/Code under test
	I64_extend_I32_u i64_extend_i32_u;

	WasmInstanceInterface instance;

	void setup() {
		instance = new WasmInstanceStub();

		// create class to test.
		i64_extend_i32_u = new I64_extend_I32_u(instance);
	}

	void cleanup() {
	}

	def "test execute"() {
		given: // An I32 value
		I32 value = new I32(input0);

		// push on stack
		instance.stack().push(value);


		when:  // convert
		// execute
		i64_extend_i32_u.execute();

		then:
		// verify
		I64 result = instance.stack().pop();
		new I64(expected) == result;

		where:
		input0            || expected
		0                 || 0
		100               || 100
		-100              || 0x0000_0000_FFFF_FF9CL
		10000             || 10000
		-10000            || 0x0000_0000_FFFF_D8F0L
		0x7FFF            || 32767
		-1                || 0x0000_0000_FFFF_FFFFL
		(int) 0x8000_0000 || 0x0000_0000_8000_0000L
		0x7FFF_FFFF       || 0x0000_0000_7FFF_FFFFL
		Integer.MIN_VALUE || 0x0000_0000_8000_0000L
		Integer.MAX_VALUE || 2147483647
	}
}
