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
package happynewmoonwithreport.opcode.math.F64

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.opcode.math.f64.F64_ceil
import happynewmoonwithreport.type.F64
import happynewmoonwithreport.type.I64
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Test F64_ceil opcode.
 * <p>
 * Created on 2021-06-24
 */
class F64_ceilTest extends Specification {
	String inputType;
	String returnType;

	void setup() {
		inputType = "F64";
		returnType = "F64"
	}

	void cleanup() {
	}

/**
 * F64_ceil unit test.
 * @param count What line of parameters is executing. Only used for debugging.
 * @param val1 The test value.   The input for the opcode.
 * @param expected The expected value.  What the opcode should return.
 * @return None.
 */
	def "Execute F64_ceil with ##count -> #val1 || #expected "(Integer count, Double val1, Double expected) {
		setup: " push two values on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F64(val1));

		F64_ceil opcode = new F64_ceil(instance);

		when: "run the opcode"
		opcode.execute();

		then: " verify results"
		F64 result = instance.stack().pop();

		then: " verify result equals value of expected"
		F64.valueOf(expected) == result

		where: "val1 equals val2 returns #expected"
		count | val1       || expected
		1     | 4.1        || 5.0
		2     | -4.1       || -4.0
		3     | Double.NaN || Double.NaN
	}

	/**
	 * F64_ceil unit test.
	 * <p>
	 * <a href="https://github.com/WebAssembly/spec/blob/7526564b56c30250b66504fe795e9c1e88a938af/test/core/f64.wast">
	 *     Official Web Assembly test code.
	 * </a>
	 * @param val1_s The test value.   The input for the opcode.
	 * @param expected The expected value.  What the opcode should return.
	 * @return None.
	 */
	@Unroll
	def "Execute F64 ceil #count | #val1_s  || #expected "(Integer count, String val1_s, String expected) {
		setup: " push one value on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(F64.valueOf(val1_s));

		F64_ceil opcode = new F64_ceil(instance);

		when: "run the opcode"
		opcode.execute();

		then: " verify result equals value of expected"
		F64 result = instance.stack().pop();
		F64.valueOf(expected) == result

		where: "val1 returns #expected"
		count | val1_s                     || expected
		1     | "-0x0p+0"                  || "-0x0p+0"
		2     | "0x0p+0"                   || "0x0p+0"
		3     | "-0x0.0000000000001p-1022" || "-0x0p+0"
		4     | "0x0.0000000000001p-1022"  || "0x1p+0"
		5     | "-0x1p-1022"               || "-0x0p+0"
		6     | "0x1p-1022"                || "0x1p+0"
		7     | "-0x1p-1"                  || "-0x0p+0"
		8     | "0x1p-1"                   || "0x1p+0"
		9     | "-0x1p+0"                  || "-0x1p+0"
		10    | "0x1p+0"                   || "0x1p+0"
		11    | "-0x1.921fb54442d18p+2"    || "-0x1.8p+2"
		12    | "0x1.921fb54442d18p+2"     || "0x1.cp+2"
		13    | "-0x1.fffffffffffffp+1023" || "-0x1.fffffffffffffp+1023"
		14    | "0x1.fffffffffffffp+1023"  || "0x1.fffffffffffffp+1023"
		15    | "-inf"                     || "-inf"
		16    | "inf"                      || "inf"
		17    | "-nan"                     || "nan:canonical"
		18    | "-nan:0x4000000000000"     || "nan:arithmetic"
		19    | "nan"                      || "nan:canonical"
		20    | "nan:0x4000000000000"      || "nan:arithmetic"
	}

	def "Execute F64_ceil Canonical"() {
		setup: " push ONE value on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(F64.Nan);

		F64_ceil opcode = new F64_ceil(instance);

		when: "run the opcode"
		opcode.execute();

		then: " verify results"
		F64 result = instance.stack().pop();

		then: " verify result equals value of expected"
		F64.Nan == result
	}

	def "Execute F64_ceil throws exception on incorrect Type on first param "() {
		setup: " a value of F64  value"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(3));  // value 1

		F64_ceil function = new F64_ceil(instance);

		when: "run the opcode"
		function.execute();

		then: "Verify thrown exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value type is incorrect. ");
		exception.message.contains("Value should be of type '" + inputType + "'. ");
		exception.message.contains("The input type is 'I64'.");
		exception.message.contains("The input value is '");
		exception.getUuid().toString().contains("a49abe1b-075c-471e-9685-b668417c6704");
	}

}
