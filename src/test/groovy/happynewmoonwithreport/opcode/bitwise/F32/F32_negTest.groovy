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
import happynewmoonwithreport.type.I64
import spock.lang.Specification

/**
 * Test F32_neg opcode.
 * <p>
 * Created on 2020-11-28
 */
class F32_negTest extends Specification {
	String inputType;
	String returnType;

	void setup() {
		inputType = "F32";
		returnType = "F32"
	}

	void cleanup() {
	}

/**
 * F32_neg unit test.
 * @param count What line of parameters is executing. Only used for debugging.
 * @param val1 The test value.   The input for the opcode.
 * @param expected The expected value.  What the opcode should return.
 * @return None.
 */
	def "Execute F32_neg with ##count -> #val1 || #expected "(Integer count, Float val1, Float expected) {
		setup: " push two values on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F32(val1));

		F32_neg opcode = new F32_neg(instance);

		when: "run the opcode"
		opcode.execute();

		then: " verify results"
		F32 result = instance.stack().pop();

		then: " verify result equals value of expected"
		F32.valueOf(expected) == result

		where: "val1 equals val2 returns #expected"
		count | val1 || expected
		1     | 4.1  || -4.1
		2     | -4.1 || 4.1
		/*  Spock/Groovy  does not support -0F **/
		// 4     | 0F   || -0F
		// 5     | -0F  || 0F

	}

	def "Execute F32_neg with F32 Input Nan Test #count -> #val1 || #expected "(Integer count, F32 val1, F32 expected) {
		setup: " push one value on stack."
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F32(val1));

		when: "run the opcode"
		F32_neg opcode = new F32_neg(instance);
		opcode.execute();

		then: "verify result equals value of expected"
		F32 result = instance.stack().pop();
		expected == result

		where: "val1 equals val2 returns #expected"
		count | val1                 || expected
		1     | F32.Nan              || F32.NanNeg
		2     | F32.NanNeg           || F32.Nan
		3     | F32.ZeroPositive     || F32.ZeroNegative
		4     | F32.ZeroNegative     || F32.ZeroPositive
		5     | F32.NanArithmeticPos || F32.NanArithmeticNeg
		6     | F32.NanArithmeticNeg || F32.NanArithmeticPos
		7     | F32.Nan0x20_0000Pos  || F32.Nan0x20_0000Neg
		8     | F32.Nan0x20_0000Neg  || F32.Nan0x20_0000Pos
		9     | F32.InfinityPositive || F32.InfinityNegative
		10    | F32.InfinityNegative || F32.InfinityPositive
	}


/**
 * F32_neg unit test.
 * <p>
 * <a href="https://github.com/WebAssembly/spec/blob/7526564b56c30250b66504fe795e9c1e88a938af/test/core/f32_bitwise.wast">
 *     Official Web Assembly test code.
 * </a>
 * @param val1_s The test value.   The input for the opcode.
 * @param expected The expected value.  What the opcode should return.
 * @return None.
 */
	def "Execute F32 less than #count | #val1_s  || # expected "(Integer count, String val1_s, String expected) {
		setup: " push one value on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(F32.valueOf(val1_s));

		F32_neg opcode = new F32_neg(instance);

		when: "run the opcode"
		opcode.execute();

		then: " verify result equals value of expected"
		F32 result = instance.stack().pop();
		F32.valueOf(expected) == result

		where: "val1 returns #expected"
		count | val1_s             || expected
		1     | "-0x0p+0"          || "0x0p+0"
		2     | "0x0p+0"           || "-0x0p+0"
		3     | "-0x1p-149"        || "0x1p-149"
		4     | "0x1p-149"         || "-0x1p-149"
		5     | "-0x1p-126"        || "0x1p-126"
		6     | "0x1p-126"         || "-0x1p-126"
		7     | "-0x1p-1"          || "0x1p-1"
		8     | "0x1p-1"           || "-0x1p-1"
		9     | "-0x1p+0"          || "0x1p+0"
		10    | "0x1p+0"           || "-0x1p+0"
		11    | "-0x1.921fb6p+2"   || "0x1.921fb6p+2"
		12    | "0x1.921fb6p+2"    || "-0x1.921fb6p+2"
		13    | "-0x1.fffffep+127" || "0x1.fffffep+127"
		14    | "0x1.fffffep+127"  || "-0x1.fffffep+127"
		15    | "-inf"             || "inf"
		16    | "inf"              || "-inf"
		17    | "-nan"             || "nan"
		18    | "nan"              || "-nan"

	}

	def "Execute F32_neg throws exception on incorrect Type on first param "() {
		setup: " a value of F32  value"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(3));  // value 1

		F32_neg function = new F32_neg(instance);

		when: "run the opcode"
		function.execute();

		then: "Verify thrown exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value type is incorrect. ");
		exception.message.contains("Value should be of type '" + inputType + "'. ");
		exception.message.contains("The input type is 'I64'.");
		exception.message.contains("The input value is '");
		exception.getUuid().toString().contains("fdbccf78-288d-4842-ae67-5f918e9a1604");
	}

}
