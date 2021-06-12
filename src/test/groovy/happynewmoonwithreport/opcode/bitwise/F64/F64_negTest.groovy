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
package happynewmoonwithreport.opcode.bitwise.F64

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.type.F64
import happynewmoonwithreport.type.I64
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Test F64_neg opcode.
 * <p>
 * Created on 2020-11-28
 */
class F64_negTest extends Specification {

	String inputType;
	String returnType;

	void setup() {
		inputType = "F64";
		returnType = "F64"
	}

	void cleanup() {
	}

/**
 * F64_neg unit test.
 * @param count What line of parameters is executing. Only used for debugging.
 * @param val1 The test value.   The input for the opcode.
 * @param expected The expected value.  What the opcode should return.
 * @return None.
 */
	@Unroll
	def "Execute F64_neg with #count | #val1 || #expected "(Integer count, Double val1, Double expected) {
		setup: " push two values on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F64(val1));

		F64_neg opcode = new F64_neg(instance);

		when: "run the opcode"
		opcode.execute();

		then: " verify results"
		F64 result = instance.stack().pop();

		then: " verify result equals value of expected"
		F64.valueOf(expected) == result

		where: "val1 equals val2 returns #expected"
		count | val1        || expected
		1     | 4.1D        || -4.1D
		2     | -4.1D       || 4.1D
		/*  Spock/Groovy  does not support -0F **/
//		3     | 0D          || -0.0D
//		4     | -0D         || 0D
		/*  Java does not support -Nan   todo **/
		5     | -Double.NaN || Double.NaN
		6     | Double.NaN  || -Double.NaN
	}

/**
 * F64_neg unit test.
 * <p>
 * <a href="https://github.com/WebAssembly/spec/blob/7526564b56c30250b66504fe795e9c1e88a938af/test/core/f64_bitwise.wast">
 *     Official Web Assembly test code.
 * </a>
 * @param val1_s The test value.   The input for the opcode.
 * @param expected The expected value.  What the opcode should return.
 * @return None.
 */
	def "Execute F64 less than #count | #val1_s  || # expected "(Integer count, String val1_s, String expected) {
		setup: " push one value on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(F64.valueOf(val1_s));


		F64_neg opcode = new F64_neg(instance);

		when: "run the opcode"
		opcode.execute();

		then: " verify result equals value of expected"
		F64 result = instance.stack().pop();
		F64.valueOf(expected) == result

		where: "val1 returns #expected"
		count | val1_s                     || expected
		1     | "-0x0p+0"                  || "0x0p+0"
		2     | "0x0p+0"                   || "-0x0p+0"
		3     | "-0x0.0000000000001p-1022" || "0x0.0000000000001p-1022"
		4     | "0x0.0000000000001p-1022"  || "-0x0.0000000000001p-1022"
		5     | "-0x1p-1022"               || "0x1p-1022"
		6     | "0x1p-1022"                || "-0x1p-1022"
		7     | "-0x1p-1"                  || "0x1p-1"
		8     | "0x1p-1"                   || "-0x1p-1"
		9     | "-0x1p+0"                  || "0x1p+0"
		10    | "0x1p+0"                   || "-0x1p+0"
		11    | "-0x1.921fb54442d18p+2"    || "0x1.921fb54442d18p+2"
		12    | "0x1.921fb54442d18p+2"     || "-0x1.921fb54442d18p+2"
		13    | "-0x1.fffffffffffffp+1023" || "0x1.fffffffffffffp+1023"
		14    | "0x1.fffffffffffffp+1023"  || "-0x1.fffffffffffffp+1023"
		15    | "-inf"                     || "inf"
		16    | "inf"                      || "-inf"
		17    | "-nan"                     || "nan"
		18    | "nan"                      || "-nan"

	}

	def "Execute F64_neg throws exception on incorrect Type on first param "() {
		setup: " a value of F64  value"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(3));  // value 1

		F64_neg function = new F64_neg(instance);

		when: "run the opcode"
		function.execute();

		then: "Verify thrown exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value type is incorrect. ");
		exception.message.contains("Value should be of type '" + inputType + "'. ");
		exception.message.contains("The input type is 'I64'.");
		exception.message.contains("The input value is '");
		exception.getUuid().toString().contains("3d5a5528-901e-4008-937d-2e2820c28cf7");
	}

}
