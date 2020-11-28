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
package happynewmoonwithreport.opcode.bitwise.F32

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.type.F32
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.I64
import spock.lang.Specification

/**
 * Test F32_abs opcode.
 * <p>
 * Created on 2020-11-28
 */
class F32_absTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

/**
 * F32_abs unit test.
 * @param count What line of parameters is executing. Only used for debugging.
 * @param val1 The test value.   The input for the opcode.
 * @param expected The expected value.  What the opcode should return.
 * @return None.
 */
	def "Execute F32_abs with #count | #val1 || #expected "(Integer count, Float val1, Float expected) {
		setup: " push two values on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F32(val1));

		F32_abs opcode = new F32_abs(instance);

		when: "run the opcode"
		opcode.execute();

		then: " verify results"
		F32 result = instance.stack().pop();

		then: " verify result equals value of expected"
		new F32(expected) == result

		where: "val1 equals val2 returns #expected"
		count | val1 || expected
		1     | 4.1  || 4.1
		2     | -4.1 || 4.1
		3     | 0F   || 0F
		4     | -0F  || 0
	}

/**
 * F32_abs unit test.
 * <p>
 * <a href="https://github.com/WebAssembly/spec/blob/7526564b56c30250b66504fe795e9c1e88a938af/test/core/f32_bitwise.wast">
 *     Official Web Assembly test code.
 * </a>
 * @param val1_s The test value.   The input for the opcode.
 * @param expected The expected value.  What the opcode should return.
 * @return None.
 */
	def "Execute F32 less than #count | #val1_s  || # expected "(Integer count, String val1_s, String expected) {
		setup: " push two values on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(F32.valueOf(val1_s));

		F32_abs opcode = new F32_abs(instance);

		when: "run the opcode"
		opcode.execute();
		F32 result = instance.stack().pop();

		then: " verify result equals value of expected"
		F32 expectedF32 = F32.valueOf(expected);
		expectedF32  == result

		where: "val1 returns #expected"
		count | val1_s             || expected
		1     | "-0x0p+0"          || "0x0p+0"
		2     | "0x0p+0"           || "0x0p+0"
		3     | "-0x1p-149"        || "0x1p-149"
		4     | "0x1p-149"         || "0x1p-149"
		5     | "-0x1p-126"        || "0x1p-126"
		6     | "0x1p-126"         || "0x1p-126"
		7     | "-0x1p-1"          || "0x1p-1"
		8     | "0x1p-1"           || "0x1p-1"
		9     | "-0x1p+0"          || "0x1p+0"
		10    | "0x1p+0"           || "0x1p+0"
		11    | "-0x1.921fb6p+2"   || "0x1.921fb6p+2"
		12    | "0x1.921fb6p+2"    || "0x1.921fb6p+2"
		13    | "-0x1.fffffep+127" || "0x1.fffffep+127"
		14    | "0x1.fffffep+127"  || "0x1.fffffep+127"
		15    | "-inf"             || "inf"
		16    | "inf"              || "inf"
		17    | "-nan"             || "nan"
		18    | "nan"              || "nan"

	}

	def "Execute F32_abs throws exception on incorrect Type on first param "() {
		setup: " a value of I64  value 1  and a value of F32 of value 2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(3));  // value 1

		F32_abs function = new F32_abs(instance);

		when: "run the opcode"
		function.execute();

		then: "Verify thrown exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value type is incorrect. ");
		exception.message.contains("Value should be of type 'F32'. ");
		exception.message.contains("The input type is 'I64'. ");
		exception.message.contains("The input value is '");
		exception.getUuid().toString().contains("316c2f0b-0a48-42d9-89a7-d7863bb9af3f");
	}

}
