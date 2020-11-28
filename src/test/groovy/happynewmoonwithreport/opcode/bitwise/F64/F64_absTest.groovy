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
import happynewmoonwithreport.opcode.comparison.F64.F64_abs
import happynewmoonwithreport.type.F64
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.I64
import spock.lang.Specification

/**
 * Test F64_abs opcode.
 * <p>
 * Created on 2020-11-28
 */
class F32_absTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

/**
 * F64_abs unit test.
 * @param count What line of parameters is executing. Only used for debugging.
 * @param val1 The test value.   The input for the opcode.
 * @param expected The expected value.  What the opcode should return.
 * @return None.
 */
	def "Execute F64_abs with #count | #val1 || #expected "(Integer count, Float val1, Integer expected) {
		setup: " push two values on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F64(val1));

		F64_abs opcode = new F64_abs(instance);

		when: "run the opcode"
		opcode.execute();

		then: " verify results"
		I32 result = instance.stack().pop();

		then: " verify result equals value of expected"
		new I32(expected) == result

		where: "val1 equals val2 returns #expected"
		count | val1 || expected
		1     | 4.1  || 4.1
		2     | -4.1 || 4.1
		3     | 0F   || 0F
		4     | -0F  || 0
	}

/**
 * F64_abs unit test.
 * <p>
 * <a href="https://github.com/WebAssembly/spec/blob/7526564b56c30250b66504fe795e9c1e88a938af/test/core/f64_bitwise.wast">
 *     Official Web Assembly test code.
 * </a>
 * @param val1_s The test value.   The input for the opcode.
 * @param expected The expected value.  What the opcode should return.
 * @return None.
 */
	def "Execute F64 less than #count | #val1_s  || # expected "(Integer count, String val1_s, Integer expected) {
		setup: " push two values on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(F64.valueOf(val1_s));
		instance.stack().push(F64.valueOf(val2_s));

		F64_abs opcode = new F64_abs(instance);

		when: "run the opcode"
		opcode.execute();
		I32 result = instance.stack().pop();

		then: " verify result equals value of expected"
		new I32(expected) == result

		where: "val1 returns #expected"
		count | val1_s                     || expected
		1     | "-0x0p+0"                  || "0x0p+0"
		2     | "0x0p+0"                   || "0x0p+0"
		3     | "-0x0.0000000000001p-1022" || "0x0.0000000000001p-1022"
		4     | "0x0.0000000000001p-1022"  || "0x0.0000000000001p-1022"
		5     | "-0x1p-1022"               || "0x1p-1022"
		6     | "0x1p-1022"                || "0x1p-1022"
		7     | "-0x1p-1"                  || "0x1p-1"
		8     | "0x1p-1"                   || "0x1p-1"
		9     | "-0x1p+0"                  || "0x1p+0"
		10    | "0x1p+0"                   || "0x1p+0"
		11    | "-0x1.921fb54442d18p+2"    || "0x1.921fb54442d18p+2"
		12    | "0x1.921fb54442d18p+2"     || "0x1.921fb54442d18p+2"
		13    | "-0x1.fffffffffffffp+1023" || "0x1.fffffffffffffp+1023"
		14    | "0x1.fffffffffffffp+1023"  || "0x1.fffffffffffffp+1023"
		15    | "-inf"                     || "inf"
		16    | "inf"                      || "inf"
		17    | "-nan"                     || "nan"
		18    | "nan"                      || "nan"

	}

	def "Execute F64_abs throws exception on incorrect Type on first param "() {
		setup: " a value of I64  value 1  and a value of F64 of value 2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(3));  // value 1
		instance.stack().push(new F64(4));  // value 2

		F64_abs function = new F64_abs(instance);

		when: "run the opcode"
		function.execute();

		then: "Verify thrown exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.getUuid().toString().contains("ef1980dc-d622-4733-8486-b8ee543136cc");
	}

}
