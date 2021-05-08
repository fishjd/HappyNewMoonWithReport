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
import happynewmoonwithreport.opcode.math.f32.F32_trunk
import happynewmoonwithreport.type.F32
import happynewmoonwithreport.type.I64
import spock.lang.Specification

/**
 * Test F32_trunk opcode.
 * <p>
 * Created on 2020-11-28
 */
class F32_trunkTest extends Specification {
	String inputType;
	String returnType;

	void setup() {
		inputType = "F32";
		returnType = "F32"
	}

	void cleanup() {
	}

/**
 * F32_trunk unit test.
 * @param count What line of parameters is executing. Only used for debugging.
 * @param val1 The test value.   The input for the opcode.
 * @param expected The expected value.  What the opcode should return.
 * @return None.
 */
	def "Execute F32_trunk with #count -> #val1 || #expected "(Integer count, Float val1, Float expected) {
		setup: " push two values on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F32(val1));

		F32_trunk opcode = new F32_trunk(instance);

		when: "run the opcode"
		opcode.execute();

		then: " verify results"
		F32 result = instance.stack().pop();

		then: " verify result equals value of expected"
		F32.valueOf(expected) == result

		where: "val1 equals val2 returns #expected"
		count | val1      || expected
		1     | 4.1       || 4.0
		2     | -4.1      || -4.0
		3     | Float.NaN || Float.NaN
		4     | 4.99      || 4
	}

	/**
	 * F32_trunk unit test.
	 * <p>
	 * <a href="https://github.com/WebAssembly/spec/blob/7526564b56c30250b66504fe795e9c1e88a938af/test/core/f32.wast">
	 *     Official Web Assembly test code.
	 * </a>
	 * @param val1_s The test value.   The input for the opcode.
	 * @param expected The expected value.  What the opcode should return.
	 * @return None.
	 */
	def "Execute F32 trunk #count | #val1_s  || #expected"(Integer count, String val1_s, String expected) {
		setup: " push one value on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(F32.valueOf(val1_s));

		F32_trunk opcode = new F32_trunk(instance);

		when: "run the opcode"
		opcode.execute();

		then: " verify result equals value of expected"
		F32 result = instance.stack().pop();
		F32.valueOf(expected) == result

		where: "val1 returns #expected"
		count | val1_s             || expected
		1     | "-0x0p+0"          || "-0x0p+0"
		2     | "0x0p+0"           || "0x0p+0"
		3     | "-0x1p-149"        || "-0x0p+0"
		4     | "0x1p-149"         || "0x0p+0"
		5     | "-0x1p-126"        || "-0x0p+0"
		6     | "0x1p-126"         || "0x0p+0"
		7     | "-0x1p-1"          || "-0x0p+0"
		8     | "0x1p-1"           || "0x0p+0"
		9     | "-0x1p+0"          || "-0x1p+0"
		10    | "0x1p+0"           || "0x1p+0"
		11    | "-0x1.921fb6p+2"   || "-0x1.8p+2"
		12    | "0x1.921fb6p+2"    || "0x1.8p+2"
		13    | "-0x1.fffffep+127" || "-0x1.fffffep+127"
		14    | "0x1.fffffep+127"  || "0x1.fffffep+127"
		15    | "-inf"             || "-inf"
		16    | "inf"              || "inf"
		17    | "-nan"             || "nan:canonical"
		18    | "-nan:0x200000"    || "nan:arithmetic"
		19    | "nan"              || "nan:canonical"
		20    | "nan:0x200000"     || "nan:arithmetic"

		//  "-nan:0x200000" is a quite NAN.
		//  0x_0200_0000  is 0b_0010_0000_0000_0000_0000_0000
		//
		//  the most significant bit of the significand field is the is_quiet bit.

		// IEEE 754 - 2008 standard See: https://en.wikipedia.org/wiki/NaN
		// For binary formats, the most significant bit of the significand field should be an
		// is_quiet flag. That is, this bit is
		// non-zero if the NaN is quiet,
		// and
		// zero if the NaN is signaling.

		// WASM states it uses IEEE 754 - 2019.  So the 2008 should also hold 2019.
	}

	def "Execute F32_trunk Canonical"() {
		setup: " push ONE value on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(F32.NAN);

		F32_trunk opcode = new F32_trunk(instance);

		when: "run the opcode"
		opcode.execute();

		then: " verify results"
		F32 result = instance.stack().pop();

		then: " verify result equals value of expected"
		F32.NAN == result
	}

	def "Execute F32_trunk throws exception on incorrect Type on first param "() {
		setup: " a value of F32  value"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(3));  // value 3

		F32_trunk function = new F32_trunk(instance);

		when: "run the opcode"
		function.execute();

		then: "Verify thrown exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value type is incorrect. ");
		exception.message.contains("Value should be of type '" + inputType + "'. ");
		exception.message.contains("The input type is 'I64'.");
		exception.message.contains("The input value is '");
		exception.getUuid().toString().contains("5da01a08-cc08-4ca5-8880-ba5511dc52eb");
	}

}
