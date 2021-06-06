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
package happynewmoonwithreport.opcode.math.F32

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.opcode.math.f32.F32_sqrt
import happynewmoonwithreport.type.F32
import happynewmoonwithreport.type.I64
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Test F32_sqrt opcode.
 * <p>
 * Created on 2020-11-28
 */
class F32_sqrtTest extends Specification {
	String inputType;
	String returnType;

	void setup() {
		inputType = "F32";
		returnType = "F32"
	}

	void cleanup() {
	}

	/**
	 * F32_sqrt unit test.
	 * @param count What line of parameters is executing. Only used for debugging.
	 * @param val1 The test value.   The input for the opcode.
	 * @param expected The expected value.  What the opcode should return.
	 * @return None.
	 */
	def "Execute F32_sqrt with #count -> #val1 || #expected "(Integer count, Float val1, Float expected) {
		setup: " push two values on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F32(val1));

		F32_sqrt opcode = new F32_sqrt(instance);

		when: "run the opcode"
		opcode.execute();

		then: " verify results"
		F32 result = instance.stack().pop();

		then: " verify result equals value of expected"
		F32.valueOf(expected) == result

		where: "val1 equals val2 returns #expected"
		count | val1 || expected
		1     | 4.0  || 2.0
		2     | 256  || 16
		3     | -1   || Float.NaN
		4     | 0    || 0
	}

	/**
	 * F32_sqrt unit test.
	 * <p>
	 * <a href="https://github.com/WebAssembly/spec/blob/7526564b56c30250b66504fe795e9c1e88a938af/test/core/f32.wast">
	 *     Official Web Assembly test code.
	 * </a>
	 * @param val1_s The test value.   The input for the opcode.
	 * @param expected The expected value.  What the opcode should return.
	 * @return None.
	 */
	@Unroll
	def "Execute F32 trunk #count | #val1_s  || #expected"(Integer count, String val1_s, String expected) {
		setup: " push one value on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(F32.valueOf(val1_s));

		F32_sqrt opcode = new F32_sqrt(instance);

		when: "run the opcode"
		opcode.execute();

		then: " verify result equals value of expected"
		F32 result = instance.stack().pop();
		F32.valueOf(expected) == result

		where: "val1 returns #expected"
		count | val1_s             || expected
		1     | "-0x0p+0"          || "-0x0p+0"
		2     | "0x0p+0"           || "0x0p+0"
		3     | "-0x1p-149"        || "nan:canonical"
		4     | "0x1p-149"         || "0x1.6a09e6p-75"
		5     | "-0x1p-126"        || "nan:canonical"
		6     | "0x1p-126"         || "0x1p-63"
		7     | "-0x1p-1"          || "nan:canonical"
		8     | "0x1p-1"           || "0x1.6a09e6p-1"
		9     | "-0x1p+0"          || "nan:canonical"
		10    | "0x1p+0"           || "0x1p+0"
		11    | "-0x1.921fb6p+2"   || "nan:canonical"
		12    | "0x1.921fb6p+2"    || "0x1.40d932p+1"
		13    | "-0x1.fffffep+127" || "nan:canonical"
		14    | "0x1.fffffep+127"  || "0x1.fffffep+63"
		15    | "-inf"             || "nan:canonical"
		16    | "inf"              || "inf"
		17    | "-nan"             || "nan:canonical"
		18    | "-nan:0x200000"    || "nan:arithmetic"
		19    | "nan"              || "nan:canonical"
		20    | "nan:0x200000"     || "nan:arithmetic"
	}

	def "Execute F32_sqrt Canonical"() {
		setup: " push ONE value on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(F32.Nan);

		F32_sqrt opcode = new F32_sqrt(instance);

		when: "run the opcode"
		opcode.execute();

		then: " verify results"
		F32 result = instance.stack().pop();

		then: " verify result equals value of expected"
		F32.Nan == result
	}

	def "Execute F32_sqrt throws exception on incorrect Type on first param "() {
		setup: " a value of F32  value"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(3));  // value 3

		F32_sqrt function = new F32_sqrt(instance);

		when: "run the opcode"
		function.execute();

		then: "Verify thrown exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value type is incorrect. ");
		exception.message.contains("Value should be of type '" + inputType + "'. ");
		exception.message.contains("The input type is 'I64'.");
		exception.message.contains("The input value is '");
		exception.getUuid().toString().contains("0a4f1202-51b5-43dd-9baa-37476274b0d0");
	}

}
