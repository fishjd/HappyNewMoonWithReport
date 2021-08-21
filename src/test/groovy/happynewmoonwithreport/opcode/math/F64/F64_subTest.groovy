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
import happynewmoonwithreport.opcode.math.f64.F64_sub
import happynewmoonwithreport.type.F64
import happynewmoonwithreport.type.I64
import spock.lang.Specification

/**
 * Test F64_sub opcode.
 * <p>
 * Created on 2021-08-21
 */
class F64_subTest extends Specification {
	String inputType;
	String returnType;

	void setup() {
		inputType = "F64";
		returnType = "F64"
	}

	void cleanup() {
	}

	/**
	 * F64_sub unit test.
	 * @param count What line of parameters is executing. Only used for debugging.
	 * @param val1 The test value.   The input for the opcode.
	 * @param val1 The test value.   The input for the opcode.
	 * @param expected The expected value.  What the opcode should return.
	 * @return None.
	 */
	def "Execute F64_sub with #count -> #val1 | #val2 || #expected "(Integer count, Float val1, Float val2, Float expected) {
		setup: " push two values on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F64(val1));
		instance.stack().push(new F64(val2));

		F64_sub opcode = new F64_sub(instance);

		when: "run the opcode"
		opcode.execute();

		then: " verify results"
		F64 result = instance.stack().pop();

		then: " verify result equals value of expected"
		F64.valueOf(expected) == result

		where: "val1  val2 returns #expected"
		count | val1 | val2 || expected
		1     | 8.0  | 4.0  || 4.0
		2     | 300  | 256  || 44
		3     | -10  | -6   || -4
		4     | 0    | 0    || 0
		5     | 1.0  | 2.0  || -1.0
	}

	/**
	 * F64_sub unit test.
	 * <p>
	 * <a href="https://github.com/WebAssembly/spec/blob/7526564b56c30250b66504fe795e9c1e88a938af/test/core/f64.wast">
	 *     Official Web Assembly test code.
	 * </a>
	 * @param val1_s The test value.   The input for the opcode.
	 * @param expected The expected value.  What the opcode should return.
	 * @return None.
	 */
	def "Execute F64 sub  #count | #val1_s | #val2_s  || #expected"(Integer count, String val1_s, String val2_s, String expected) {
		setup: " push one value on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(F64.valueOf(val1_s));
		instance.stack().push(F64.valueOf(val2_s));

		F64_sub opcode = new F64_sub(instance);

		when: "run the opcode"
		opcode.execute();

		then: " verify result equals value of expected"
		F64 result = instance.stack().pop();
		F64.valueOf(expected) == result

		where: "#val1 sub #val2 returns #expected"
		count | val1_s                     | val2_s                     || expected
		1     | "-0x0p+0"                  | "-0x0p+0"                  || "0x0p+0"
		1     | "-0x0p+0"                  | "0x0p+0"                   || "-0x0p+0"
		1     | "0x0p+0"                   | "-0x0p+0"                  || "0x0p+0"
		1     | "0x0p+0"                   | "0x0p+0"                   || "0x0p+0"
		1     | "-0x0p+0"                  | "-0x0.0000000000001p-1022" || "0x0.0000000000001p-1022"
		1     | "-0x0p+0"                  | "0x0.0000000000001p-1022"  || "-0x0.0000000000001p-1022"
		1     | "0x0p+0"                   | "-0x0.0000000000001p-1022" || "0x0.0000000000001p-1022"
		1     | "0x0p+0"                   | "0x0.0000000000001p-1022"  || "-0x0.0000000000001p-1022"
		1     | "-0x0p+0"                  | "-0x1p-1022"               || "0x1p-1022"
		1     | "-0x0p+0"                  | "0x1p-1022"                || "-0x1p-1022"
		1     | "0x0p+0"                   | "-0x1p-1022"               || "0x1p-1022"
		1     | "0x0p+0"                   | "0x1p-1022"                || "-0x1p-1022"
		1     | "-0x0p+0"                  | "-0x1p-1"                  || "0x1p-1"
		1     | "-0x0p+0"                  | "0x1p-1"                   || "-0x1p-1"
		1     | "0x0p+0"                   | "-0x1p-1"                  || "0x1p-1"
		1     | "0x0p+0"                   | "0x1p-1"                   || "-0x1p-1"
		1     | "-0x0p+0"                  | "-0x1p+0"                  || "0x1p+0"
		1     | "-0x0p+0"                  | "0x1p+0"                   || "-0x1p+0"
		1     | "0x0p+0"                   | "-0x1p+0"                  || "0x1p+0"
		1     | "0x0p+0"                   | "0x1p+0"                   || "-0x1p+0"
		1     | "-0x0p+0"                  | "-0x1.921fb54442d18p+2"    || "0x1.921fb54442d18p+2"
		1     | "-0x0p+0"                  | "0x1.921fb54442d18p+2"     || "-0x1.921fb54442d18p+2"
		1     | "0x0p+0"                   | "-0x1.921fb54442d18p+2"    || "0x1.921fb54442d18p+2"
		1     | "0x0p+0"                   | "0x1.921fb54442d18p+2"     || "-0x1.921fb54442d18p+2"
		1     | "-0x0p+0"                  | "-0x1.fffffffffffffp+1023" || "0x1.fffffffffffffp+1023"
		1     | "-0x0p+0"                  | "0x1.fffffffffffffp+1023"  || "-0x1.fffffffffffffp+1023"
		1     | "0x0p+0"                   | "-0x1.fffffffffffffp+1023" || "0x1.fffffffffffffp+1023"
		1     | "0x0p+0"                   | "0x1.fffffffffffffp+1023"  || "-0x1.fffffffffffffp+1023"
		1     | "-0x0p+0"                  | "-inf"                     || "inf"
		1     | "-0x0p+0"                  | "inf"                      || "-inf"
		1     | "0x0p+0"                   | "-inf"                     || "inf"
		1     | "0x0p+0"                   | "inf"                      || "-inf"
		1     | "-0x0p+0"                  | "-nan"                     || "nan:canonical"
		1     | "-0x0p+0"                  | "-nan:0x4000000000000"     || "nan:arithmetic"
		1     | "-0x0p+0"                  | "nan"                      || "nan:canonical"
		1     | "-0x0p+0"                  | "nan:0x4000000000000"      || "nan:arithmetic"
		1     | "0x0p+0"                   | "-nan"                     || "nan:canonical"
		1     | "0x0p+0"                   | "-nan:0x4000000000000"     || "nan:arithmetic"
		1     | "0x0p+0"                   | "nan"                      || "nan:canonical"
		1     | "0x0p+0"                   | "nan:0x4000000000000"      || "nan:arithmetic"
		1     | "-0x0.0000000000001p-1022" | "-0x0p+0"                  || "-0x0.0000000000001p-1022"
		1     | "-0x0.0000000000001p-1022" | "0x0p+0"                   || "-0x0.0000000000001p-1022"
		1     | "0x0.0000000000001p-1022"  | "-0x0p+0"                  || "0x0.0000000000001p-1022"
		1     | "0x0.0000000000001p-1022"  | "0x0p+0"                   || "0x0.0000000000001p-1022"
		1     | "-0x0.0000000000001p-1022" | "-0x0.0000000000001p-1022" || "0x0p+0"
		1     | "-0x0.0000000000001p-1022" | "0x0.0000000000001p-1022"  || "-0x0.0000000000002p-1022"
		1     | "0x0.0000000000001p-1022"  | "-0x0.0000000000001p-1022" || "0x0.0000000000002p-1022"
		1     | "0x0.0000000000001p-1022"  | "0x0.0000000000001p-1022"  || "0x0p+0"
		1     | "-0x0.0000000000001p-1022" | "-0x1p-1022"               || "0x0.fffffffffffffp-1022"
		1     | "-0x0.0000000000001p-1022" | "0x1p-1022"                || "-0x1.0000000000001p-1022"
		1     | "0x0.0000000000001p-1022"  | "-0x1p-1022"               || "0x1.0000000000001p-1022"
		1     | "0x0.0000000000001p-1022"  | "0x1p-1022"                || "-0x0.fffffffffffffp-1022"
		1     | "-0x0.0000000000001p-1022" | "-0x1p-1"                  || "0x1p-1"
		1     | "-0x0.0000000000001p-1022" | "0x1p-1"                   || "-0x1p-1"
		1     | "0x0.0000000000001p-1022"  | "-0x1p-1"                  || "0x1p-1"
		1     | "0x0.0000000000001p-1022"  | "0x1p-1"                   || "-0x1p-1"
		1     | "-0x0.0000000000001p-1022" | "-0x1p+0"                  || "0x1p+0"
		1     | "-0x0.0000000000001p-1022" | "0x1p+0"                   || "-0x1p+0"
		1     | "0x0.0000000000001p-1022"  | "-0x1p+0"                  || "0x1p+0"
		1     | "0x0.0000000000001p-1022"  | "0x1p+0"                   || "-0x1p+0"
		1     | "-0x0.0000000000001p-1022" | "-0x1.921fb54442d18p+2"    || "0x1.921fb54442d18p+2"
		1     | "-0x0.0000000000001p-1022" | "0x1.921fb54442d18p+2"     || "-0x1.921fb54442d18p+2"
		1     | "0x0.0000000000001p-1022"  | "-0x1.921fb54442d18p+2"    || "0x1.921fb54442d18p+2"
		1     | "0x0.0000000000001p-1022"  | "0x1.921fb54442d18p+2"     || "-0x1.921fb54442d18p+2"
		1     | "-0x0.0000000000001p-1022" | "-0x1.fffffffffffffp+1023" || "0x1.fffffffffffffp+1023"
		1     | "-0x0.0000000000001p-1022" | "0x1.fffffffffffffp+1023"  || "-0x1.fffffffffffffp+1023"
		1     | "0x0.0000000000001p-1022"  | "-0x1.fffffffffffffp+1023" || "0x1.fffffffffffffp+1023"
		1     | "0x0.0000000000001p-1022"  | "0x1.fffffffffffffp+1023"  || "-0x1.fffffffffffffp+1023"
		1     | "-0x0.0000000000001p-1022" | "-inf"                     || "inf"
		1     | "-0x0.0000000000001p-1022" | "inf"                      || "-inf"
		1     | "0x0.0000000000001p-1022"  | "-inf"                     || "inf"
		1     | "0x0.0000000000001p-1022"  | "inf"                      || "-inf"
		1     | "-0x0.0000000000001p-1022" | "-nan"                     || "nan:canonical"
		1     | "-0x0.0000000000001p-1022" | "-nan:0x4000000000000"     || "nan:arithmetic"
		1     | "-0x0.0000000000001p-1022" | "nan"                      || "nan:canonical"
		1     | "-0x0.0000000000001p-1022" | "nan:0x4000000000000"      || "nan:arithmetic"
		1     | "0x0.0000000000001p-1022"  | "-nan"                     || "nan:canonical"
		1     | "0x0.0000000000001p-1022"  | "-nan:0x4000000000000"     || "nan:arithmetic"
		1     | "0x0.0000000000001p-1022"  | "nan"                      || "nan:canonical"
		1     | "0x0.0000000000001p-1022"  | "nan:0x4000000000000"      || "nan:arithmetic"
		1     | "-0x1p-1022"               | "-0x0p+0"                  || "-0x1p-1022"
		1     | "-0x1p-1022"               | "0x0p+0"                   || "-0x1p-1022"
		1     | "0x1p-1022"                | "-0x0p+0"                  || "0x1p-1022"
		1     | "0x1p-1022"                | "0x0p+0"                   || "0x1p-1022"
		1     | "-0x1p-1022"               | "-0x0.0000000000001p-1022" || "-0x0.fffffffffffffp-1022"
		1     | "-0x1p-1022"               | "0x0.0000000000001p-1022"  || "-0x1.0000000000001p-1022"
		1     | "0x1p-1022"                | "-0x0.0000000000001p-1022" || "0x1.0000000000001p-1022"
		1     | "0x1p-1022"                | "0x0.0000000000001p-1022"  || "0x0.fffffffffffffp-1022"
		1     | "-0x1p-1022"               | "-0x1p-1022"               || "0x0p+0"
		1     | "-0x1p-1022"               | "0x1p-1022"                || "-0x1p-1021"
		1     | "0x1p-1022"                | "-0x1p-1022"               || "0x1p-1021"
		1     | "0x1p-1022"                | "0x1p-1022"                || "0x0p+0"
		1     | "-0x1p-1022"               | "-0x1p-1"                  || "0x1p-1"
		1     | "-0x1p-1022"               | "0x1p-1"                   || "-0x1p-1"
		1     | "0x1p-1022"                | "-0x1p-1"                  || "0x1p-1"
		1     | "0x1p-1022"                | "0x1p-1"                   || "-0x1p-1"
		1     | "-0x1p-1022"               | "-0x1p+0"                  || "0x1p+0"
		1     | "-0x1p-1022"               | "0x1p+0"                   || "-0x1p+0"
		1     | "0x1p-1022"                | "-0x1p+0"                  || "0x1p+0"
		1     | "0x1p-1022"                | "0x1p+0"                   || "-0x1p+0"
		1     | "-0x1p-1022"               | "-0x1.921fb54442d18p+2"    || "0x1.921fb54442d18p+2"
		1     | "-0x1p-1022"               | "0x1.921fb54442d18p+2"     || "-0x1.921fb54442d18p+2"
		1     | "0x1p-1022"                | "-0x1.921fb54442d18p+2"    || "0x1.921fb54442d18p+2"
		1     | "0x1p-1022"                | "0x1.921fb54442d18p+2"     || "-0x1.921fb54442d18p+2"
		1     | "-0x1p-1022"               | "-0x1.fffffffffffffp+1023" || "0x1.fffffffffffffp+1023"
		1     | "-0x1p-1022"               | "0x1.fffffffffffffp+1023"  || "-0x1.fffffffffffffp+1023"
		1     | "0x1p-1022"                | "-0x1.fffffffffffffp+1023" || "0x1.fffffffffffffp+1023"
		1     | "0x1p-1022"                | "0x1.fffffffffffffp+1023"  || "-0x1.fffffffffffffp+1023"
		1     | "-0x1p-1022"               | "-inf"                     || "inf"
		1     | "-0x1p-1022"               | "inf"                      || "-inf"
		1     | "0x1p-1022"                | "-inf"                     || "inf"
		1     | "0x1p-1022"                | "inf"                      || "-inf"
		1     | "-0x1p-1022"               | "-nan"                     || "nan:canonical"
		1     | "-0x1p-1022"               | "-nan:0x4000000000000"     || "nan:arithmetic"
		1     | "-0x1p-1022"               | "nan"                      || "nan:canonical"
		1     | "-0x1p-1022"               | "nan:0x4000000000000"      || "nan:arithmetic"
		1     | "0x1p-1022"                | "-nan"                     || "nan:canonical"
		1     | "0x1p-1022"                | "-nan:0x4000000000000"     || "nan:arithmetic"
		1     | "0x1p-1022"                | "nan"                      || "nan:canonical"
		1     | "0x1p-1022"                | "nan:0x4000000000000"      || "nan:arithmetic"
		1     | "-0x1p-1"                  | "-0x0p+0"                  || "-0x1p-1"
		1     | "-0x1p-1"                  | "0x0p+0"                   || "-0x1p-1"
		1     | "0x1p-1"                   | "-0x0p+0"                  || "0x1p-1"
		1     | "0x1p-1"                   | "0x0p+0"                   || "0x1p-1"
		1     | "-0x1p-1"                  | "-0x0.0000000000001p-1022" || "-0x1p-1"
		1     | "-0x1p-1"                  | "0x0.0000000000001p-1022"  || "-0x1p-1"
		1     | "0x1p-1"                   | "-0x0.0000000000001p-1022" || "0x1p-1"
		1     | "0x1p-1"                   | "0x0.0000000000001p-1022"  || "0x1p-1"
		1     | "-0x1p-1"                  | "-0x1p-1022"               || "-0x1p-1"
		1     | "-0x1p-1"                  | "0x1p-1022"                || "-0x1p-1"
		1     | "0x1p-1"                   | "-0x1p-1022"               || "0x1p-1"
		1     | "0x1p-1"                   | "0x1p-1022"                || "0x1p-1"
		1     | "-0x1p-1"                  | "-0x1p-1"                  || "0x0p+0"
		1     | "-0x1p-1"                  | "0x1p-1"                   || "-0x1p+0"
		1     | "0x1p-1"                   | "-0x1p-1"                  || "0x1p+0"
		1     | "0x1p-1"                   | "0x1p-1"                   || "0x0p+0"
		1     | "-0x1p-1"                  | "-0x1p+0"                  || "0x1p-1"
		1     | "-0x1p-1"                  | "0x1p+0"                   || "-0x1.8p+0"
		1     | "0x1p-1"                   | "-0x1p+0"                  || "0x1.8p+0"
		1     | "0x1p-1"                   | "0x1p+0"                   || "-0x1p-1"
		1     | "-0x1p-1"                  | "-0x1.921fb54442d18p+2"    || "0x1.721fb54442d18p+2"
		1     | "-0x1p-1"                  | "0x1.921fb54442d18p+2"     || "-0x1.b21fb54442d18p+2"
		1     | "0x1p-1"                   | "-0x1.921fb54442d18p+2"    || "0x1.b21fb54442d18p+2"
		1     | "0x1p-1"                   | "0x1.921fb54442d18p+2"     || "-0x1.721fb54442d18p+2"
		1     | "-0x1p-1"                  | "-0x1.fffffffffffffp+1023" || "0x1.fffffffffffffp+1023"
		1     | "-0x1p-1"                  | "0x1.fffffffffffffp+1023"  || "-0x1.fffffffffffffp+1023"
		1     | "0x1p-1"                   | "-0x1.fffffffffffffp+1023" || "0x1.fffffffffffffp+1023"
		1     | "0x1p-1"                   | "0x1.fffffffffffffp+1023"  || "-0x1.fffffffffffffp+1023"
		1     | "-0x1p-1"                  | "-inf"                     || "inf"
		1     | "-0x1p-1"                  | "inf"                      || "-inf"
		1     | "0x1p-1"                   | "-inf"                     || "inf"
		1     | "0x1p-1"                   | "inf"                      || "-inf"
		1     | "-0x1p-1"                  | "-nan"                     || "nan:canonical"
		1     | "-0x1p-1"                  | "-nan:0x4000000000000"     || "nan:arithmetic"
		1     | "-0x1p-1"                  | "nan"                      || "nan:canonical"
		1     | "-0x1p-1"                  | "nan:0x4000000000000"      || "nan:arithmetic"
		1     | "0x1p-1"                   | "-nan"                     || "nan:canonical"
		1     | "0x1p-1"                   | "-nan:0x4000000000000"     || "nan:arithmetic"
		1     | "0x1p-1"                   | "nan"                      || "nan:canonical"
		1     | "0x1p-1"                   | "nan:0x4000000000000"      || "nan:arithmetic"
		1     | "-0x1p+0"                  | "-0x0p+0"                  || "-0x1p+0"
		1     | "-0x1p+0"                  | "0x0p+0"                   || "-0x1p+0"
		1     | "0x1p+0"                   | "-0x0p+0"                  || "0x1p+0"
		1     | "0x1p+0"                   | "0x0p+0"                   || "0x1p+0"
		1     | "-0x1p+0"                  | "-0x0.0000000000001p-1022" || "-0x1p+0"
		1     | "-0x1p+0"                  | "0x0.0000000000001p-1022"  || "-0x1p+0"
		1     | "0x1p+0"                   | "-0x0.0000000000001p-1022" || "0x1p+0"
		1     | "0x1p+0"                   | "0x0.0000000000001p-1022"  || "0x1p+0"
		1     | "-0x1p+0"                  | "-0x1p-1022"               || "-0x1p+0"
		1     | "-0x1p+0"                  | "0x1p-1022"                || "-0x1p+0"
		1     | "0x1p+0"                   | "-0x1p-1022"               || "0x1p+0"
		1     | "0x1p+0"                   | "0x1p-1022"                || "0x1p+0"
		1     | "-0x1p+0"                  | "-0x1p-1"                  || "-0x1p-1"
		1     | "-0x1p+0"                  | "0x1p-1"                   || "-0x1.8p+0"
		1     | "0x1p+0"                   | "-0x1p-1"                  || "0x1.8p+0"
		1     | "0x1p+0"                   | "0x1p-1"                   || "0x1p-1"
		1     | "-0x1p+0"                  | "-0x1p+0"                  || "0x0p+0"
		1     | "-0x1p+0"                  | "0x1p+0"                   || "-0x1p+1"
		1     | "0x1p+0"                   | "-0x1p+0"                  || "0x1p+1"
		1     | "0x1p+0"                   | "0x1p+0"                   || "0x0p+0"
		1     | "-0x1p+0"                  | "-0x1.921fb54442d18p+2"    || "0x1.521fb54442d18p+2"
		1     | "-0x1p+0"                  | "0x1.921fb54442d18p+2"     || "-0x1.d21fb54442d18p+2"
		1     | "0x1p+0"                   | "-0x1.921fb54442d18p+2"    || "0x1.d21fb54442d18p+2"
		1     | "0x1p+0"                   | "0x1.921fb54442d18p+2"     || "-0x1.521fb54442d18p+2"
		1     | "-0x1p+0"                  | "-0x1.fffffffffffffp+1023" || "0x1.fffffffffffffp+1023"
		1     | "-0x1p+0"                  | "0x1.fffffffffffffp+1023"  || "-0x1.fffffffffffffp+1023"
		1     | "0x1p+0"                   | "-0x1.fffffffffffffp+1023" || "0x1.fffffffffffffp+1023"
		1     | "0x1p+0"                   | "0x1.fffffffffffffp+1023"  || "-0x1.fffffffffffffp+1023"
		1     | "-0x1p+0"                  | "-inf"                     || "inf"
		1     | "-0x1p+0"                  | "inf"                      || "-inf"
		1     | "0x1p+0"                   | "-inf"                     || "inf"
		1     | "0x1p+0"                   | "inf"                      || "-inf"
		1     | "-0x1p+0"                  | "-nan"                     || "nan:canonical"
		1     | "-0x1p+0"                  | "-nan:0x4000000000000"     || "nan:arithmetic"
		1     | "-0x1p+0"                  | "nan"                      || "nan:canonical"
		1     | "-0x1p+0"                  | "nan:0x4000000000000"      || "nan:arithmetic"
		1     | "0x1p+0"                   | "-nan"                     || "nan:canonical"
		1     | "0x1p+0"                   | "-nan:0x4000000000000"     || "nan:arithmetic"
		1     | "0x1p+0"                   | "nan"                      || "nan:canonical"
		1     | "0x1p+0"                   | "nan:0x4000000000000"      || "nan:arithmetic"
		1     | "-0x1.921fb54442d18p+2"    | "-0x0p+0"                  || "-0x1.921fb54442d18p+2"
		1     | "-0x1.921fb54442d18p+2"    | "0x0p+0"                   || "-0x1.921fb54442d18p+2"
		1     | "0x1.921fb54442d18p+2"     | "-0x0p+0"                  || "0x1.921fb54442d18p+2"
		1     | "0x1.921fb54442d18p+2"     | "0x0p+0"                   || "0x1.921fb54442d18p+2"
		1     | "-0x1.921fb54442d18p+2"    | "-0x0.0000000000001p-1022" || "-0x1.921fb54442d18p+2"
		1     | "-0x1.921fb54442d18p+2"    | "0x0.0000000000001p-1022"  || "-0x1.921fb54442d18p+2"
		1     | "0x1.921fb54442d18p+2"     | "-0x0.0000000000001p-1022" || "0x1.921fb54442d18p+2"
		1     | "0x1.921fb54442d18p+2"     | "0x0.0000000000001p-1022"  || "0x1.921fb54442d18p+2"
		1     | "-0x1.921fb54442d18p+2"    | "-0x1p-1022"               || "-0x1.921fb54442d18p+2"
		1     | "-0x1.921fb54442d18p+2"    | "0x1p-1022"                || "-0x1.921fb54442d18p+2"
		1     | "0x1.921fb54442d18p+2"     | "-0x1p-1022"               || "0x1.921fb54442d18p+2"
		1     | "0x1.921fb54442d18p+2"     | "0x1p-1022"                || "0x1.921fb54442d18p+2"
		1     | "-0x1.921fb54442d18p+2"    | "-0x1p-1"                  || "-0x1.721fb54442d18p+2"
		1     | "-0x1.921fb54442d18p+2"    | "0x1p-1"                   || "-0x1.b21fb54442d18p+2"
		1     | "0x1.921fb54442d18p+2"     | "-0x1p-1"                  || "0x1.b21fb54442d18p+2"
		1     | "0x1.921fb54442d18p+2"     | "0x1p-1"                   || "0x1.721fb54442d18p+2"
		1     | "-0x1.921fb54442d18p+2"    | "-0x1p+0"                  || "-0x1.521fb54442d18p+2"
		1     | "-0x1.921fb54442d18p+2"    | "0x1p+0"                   || "-0x1.d21fb54442d18p+2"
		1     | "0x1.921fb54442d18p+2"     | "-0x1p+0"                  || "0x1.d21fb54442d18p+2"
		1     | "0x1.921fb54442d18p+2"     | "0x1p+0"                   || "0x1.521fb54442d18p+2"
		1     | "-0x1.921fb54442d18p+2"    | "-0x1.921fb54442d18p+2"    || "0x0p+0"
		1     | "-0x1.921fb54442d18p+2"    | "0x1.921fb54442d18p+2"     || "-0x1.921fb54442d18p+3"
		1     | "0x1.921fb54442d18p+2"     | "-0x1.921fb54442d18p+2"    || "0x1.921fb54442d18p+3"
		1     | "0x1.921fb54442d18p+2"     | "0x1.921fb54442d18p+2"     || "0x0p+0"
		1     | "-0x1.921fb54442d18p+2"    | "-0x1.fffffffffffffp+1023" || "0x1.fffffffffffffp+1023"
		1     | "-0x1.921fb54442d18p+2"    | "0x1.fffffffffffffp+1023"  || "-0x1.fffffffffffffp+1023"
		1     | "0x1.921fb54442d18p+2"     | "-0x1.fffffffffffffp+1023" || "0x1.fffffffffffffp+1023"
		1     | "0x1.921fb54442d18p+2"     | "0x1.fffffffffffffp+1023"  || "-0x1.fffffffffffffp+1023"
		1     | "-0x1.921fb54442d18p+2"    | "-inf"                     || "inf"
		1     | "-0x1.921fb54442d18p+2"    | "inf"                      || "-inf"
		1     | "0x1.921fb54442d18p+2"     | "-inf"                     || "inf"
		1     | "0x1.921fb54442d18p+2"     | "inf"                      || "-inf"
		1     | "-0x1.921fb54442d18p+2"    | "-nan"                     || "nan:canonical"
		1     | "-0x1.921fb54442d18p+2"    | "-nan:0x4000000000000"     || "nan:arithmetic"
		1     | "-0x1.921fb54442d18p+2"    | "nan"                      || "nan:canonical"
		1     | "-0x1.921fb54442d18p+2"    | "nan:0x4000000000000"      || "nan:arithmetic"
		1     | "0x1.921fb54442d18p+2"     | "-nan"                     || "nan:canonical"
		1     | "0x1.921fb54442d18p+2"     | "-nan:0x4000000000000"     || "nan:arithmetic"
		1     | "0x1.921fb54442d18p+2"     | "nan"                      || "nan:canonical"
		1     | "0x1.921fb54442d18p+2"     | "nan:0x4000000000000"      || "nan:arithmetic"
		1     | "-0x1.fffffffffffffp+1023" | "-0x0p+0"                  || "-0x1.fffffffffffffp+1023"
		1     | "-0x1.fffffffffffffp+1023" | "0x0p+0"                   || "-0x1.fffffffffffffp+1023"
		1     | "0x1.fffffffffffffp+1023"  | "-0x0p+0"                  || "0x1.fffffffffffffp+1023"
		1     | "0x1.fffffffffffffp+1023"  | "0x0p+0"                   || "0x1.fffffffffffffp+1023"
		1     | "-0x1.fffffffffffffp+1023" | "-0x0.0000000000001p-1022" || "-0x1.fffffffffffffp+1023"
		1     | "-0x1.fffffffffffffp+1023" | "0x0.0000000000001p-1022"  || "-0x1.fffffffffffffp+1023"
		1     | "0x1.fffffffffffffp+1023"  | "-0x0.0000000000001p-1022" || "0x1.fffffffffffffp+1023"
		1     | "0x1.fffffffffffffp+1023"  | "0x0.0000000000001p-1022"  || "0x1.fffffffffffffp+1023"
		1     | "-0x1.fffffffffffffp+1023" | "-0x1p-1022"               || "-0x1.fffffffffffffp+1023"
		1     | "-0x1.fffffffffffffp+1023" | "0x1p-1022"                || "-0x1.fffffffffffffp+1023"
		1     | "0x1.fffffffffffffp+1023"  | "-0x1p-1022"               || "0x1.fffffffffffffp+1023"
		1     | "0x1.fffffffffffffp+1023"  | "0x1p-1022"                || "0x1.fffffffffffffp+1023"
		1     | "-0x1.fffffffffffffp+1023" | "-0x1p-1"                  || "-0x1.fffffffffffffp+1023"
		1     | "-0x1.fffffffffffffp+1023" | "0x1p-1"                   || "-0x1.fffffffffffffp+1023"
		1     | "0x1.fffffffffffffp+1023"  | "-0x1p-1"                  || "0x1.fffffffffffffp+1023"
		1     | "0x1.fffffffffffffp+1023"  | "0x1p-1"                   || "0x1.fffffffffffffp+1023"
		1     | "-0x1.fffffffffffffp+1023" | "-0x1p+0"                  || "-0x1.fffffffffffffp+1023"
		1     | "-0x1.fffffffffffffp+1023" | "0x1p+0"                   || "-0x1.fffffffffffffp+1023"
		1     | "0x1.fffffffffffffp+1023"  | "-0x1p+0"                  || "0x1.fffffffffffffp+1023"
		1     | "0x1.fffffffffffffp+1023"  | "0x1p+0"                   || "0x1.fffffffffffffp+1023"
		1     | "-0x1.fffffffffffffp+1023" | "-0x1.921fb54442d18p+2"    || "-0x1.fffffffffffffp+1023"
		1     | "-0x1.fffffffffffffp+1023" | "0x1.921fb54442d18p+2"     || "-0x1.fffffffffffffp+1023"
		1     | "0x1.fffffffffffffp+1023"  | "-0x1.921fb54442d18p+2"    || "0x1.fffffffffffffp+1023"
		1     | "0x1.fffffffffffffp+1023"  | "0x1.921fb54442d18p+2"     || "0x1.fffffffffffffp+1023"
		1     | "-0x1.fffffffffffffp+1023" | "-0x1.fffffffffffffp+1023" || "0x0p+0"
		1     | "-0x1.fffffffffffffp+1023" | "0x1.fffffffffffffp+1023"  || "-inf"
		1     | "0x1.fffffffffffffp+1023"  | "-0x1.fffffffffffffp+1023" || "inf"
		1     | "0x1.fffffffffffffp+1023"  | "0x1.fffffffffffffp+1023"  || "0x0p+0"
		1     | "-0x1.fffffffffffffp+1023" | "-inf"                     || "inf"
		1     | "-0x1.fffffffffffffp+1023" | "inf"                      || "-inf"
		1     | "0x1.fffffffffffffp+1023"  | "-inf"                     || "inf"
		1     | "0x1.fffffffffffffp+1023"  | "inf"                      || "-inf"
		1     | "-0x1.fffffffffffffp+1023" | "-nan"                     || "nan:canonical"
		1     | "-0x1.fffffffffffffp+1023" | "-nan:0x4000000000000"     || "nan:arithmetic"
		1     | "-0x1.fffffffffffffp+1023" | "nan"                      || "nan:canonical"
		1     | "-0x1.fffffffffffffp+1023" | "nan:0x4000000000000"      || "nan:arithmetic"
		1     | "0x1.fffffffffffffp+1023"  | "-nan"                     || "nan:canonical"
		1     | "0x1.fffffffffffffp+1023"  | "-nan:0x4000000000000"     || "nan:arithmetic"
		1     | "0x1.fffffffffffffp+1023"  | "nan"                      || "nan:canonical"
		1     | "0x1.fffffffffffffp+1023"  | "nan:0x4000000000000"      || "nan:arithmetic"
		1     | "-inf"                     | "-0x0p+0"                  || "-inf"
		1     | "-inf"                     | "0x0p+0"                   || "-inf"
		1     | "inf"                      | "-0x0p+0"                  || "inf"
		1     | "inf"                      | "0x0p+0"                   || "inf"
		1     | "-inf"                     | "-0x0.0000000000001p-1022" || "-inf"
		1     | "-inf"                     | "0x0.0000000000001p-1022"  || "-inf"
		1     | "inf"                      | "-0x0.0000000000001p-1022" || "inf"
		1     | "inf"                      | "0x0.0000000000001p-1022"  || "inf"
		1     | "-inf"                     | "-0x1p-1022"               || "-inf"
		1     | "-inf"                     | "0x1p-1022"                || "-inf"
		1     | "inf"                      | "-0x1p-1022"               || "inf"
		1     | "inf"                      | "0x1p-1022"                || "inf"
		1     | "-inf"                     | "-0x1p-1"                  || "-inf"
		1     | "-inf"                     | "0x1p-1"                   || "-inf"
		1     | "inf"                      | "-0x1p-1"                  || "inf"
		1     | "inf"                      | "0x1p-1"                   || "inf"
		1     | "-inf"                     | "-0x1p+0"                  || "-inf"
		1     | "-inf"                     | "0x1p+0"                   || "-inf"
		1     | "inf"                      | "-0x1p+0"                  || "inf"
		1     | "inf"                      | "0x1p+0"                   || "inf"
		1     | "-inf"                     | "-0x1.921fb54442d18p+2"    || "-inf"
		1     | "-inf"                     | "0x1.921fb54442d18p+2"     || "-inf"
		1     | "inf"                      | "-0x1.921fb54442d18p+2"    || "inf"
		1     | "inf"                      | "0x1.921fb54442d18p+2"     || "inf"
		1     | "-inf"                     | "-0x1.fffffffffffffp+1023" || "-inf"
		1     | "-inf"                     | "0x1.fffffffffffffp+1023"  || "-inf"
		1     | "inf"                      | "-0x1.fffffffffffffp+1023" || "inf"
		1     | "inf"                      | "0x1.fffffffffffffp+1023"  || "inf"
		1     | "-inf"                     | "-inf"                     || "nan:canonical"
		1     | "-inf"                     | "inf"                      || "-inf"
		1     | "inf"                      | "-inf"                     || "inf"
		1     | "inf"                      | "inf"                      || "nan:canonical"
		1     | "-inf"                     | "-nan"                     || "nan:canonical"
		1     | "-inf"                     | "-nan:0x4000000000000"     || "nan:arithmetic"
		1     | "-inf"                     | "nan"                      || "nan:canonical"
		1     | "-inf"                     | "nan:0x4000000000000"      || "nan:arithmetic"
		1     | "inf"                      | "-nan"                     || "nan:canonical"
		1     | "inf"                      | "-nan:0x4000000000000"     || "nan:arithmetic"
		1     | "inf"                      | "nan"                      || "nan:canonical"
		1     | "inf"                      | "nan:0x4000000000000"      || "nan:arithmetic"
		1     | "-nan"                     | "-0x0p+0"                  || "nan:canonical"
		1     | "-nan:0x4000000000000"     | "-0x0p+0"                  || "nan:arithmetic"
		1     | "-nan"                     | "0x0p+0"                   || "nan:canonical"
		1     | "-nan:0x4000000000000"     | "0x0p+0"                   || "nan:arithmetic"
		1     | "nan"                      | "-0x0p+0"                  || "nan:canonical"
		1     | "nan:0x4000000000000"      | "-0x0p+0"                  || "nan:arithmetic"
		1     | "nan"                      | "0x0p+0"                   || "nan:canonical"
		1     | "nan:0x4000000000000"      | "0x0p+0"                   || "nan:arithmetic"
		1     | "-nan"                     | "-0x0.0000000000001p-1022" || "nan:canonical"
		1     | "-nan:0x4000000000000"     | "-0x0.0000000000001p-1022" || "nan:arithmetic"
		1     | "-nan"                     | "0x0.0000000000001p-1022"  || "nan:canonical"
		1     | "-nan:0x4000000000000"     | "0x0.0000000000001p-1022"  || "nan:arithmetic"
		1     | "nan"                      | "-0x0.0000000000001p-1022" || "nan:canonical"
		1     | "nan:0x4000000000000"      | "-0x0.0000000000001p-1022" || "nan:arithmetic"
		1     | "nan"                      | "0x0.0000000000001p-1022"  || "nan:canonical"
		1     | "nan:0x4000000000000"      | "0x0.0000000000001p-1022"  || "nan:arithmetic"
		1     | "-nan"                     | "-0x1p-1022"               || "nan:canonical"
		1     | "-nan:0x4000000000000"     | "-0x1p-1022"               || "nan:arithmetic"
		1     | "-nan"                     | "0x1p-1022"                || "nan:canonical"
		1     | "-nan:0x4000000000000"     | "0x1p-1022"                || "nan:arithmetic"
		1     | "nan"                      | "-0x1p-1022"               || "nan:canonical"
		1     | "nan:0x4000000000000"      | "-0x1p-1022"               || "nan:arithmetic"
		1     | "nan"                      | "0x1p-1022"                || "nan:canonical"
		1     | "nan:0x4000000000000"      | "0x1p-1022"                || "nan:arithmetic"
		1     | "-nan"                     | "-0x1p-1"                  || "nan:canonical"
		1     | "-nan:0x4000000000000"     | "-0x1p-1"                  || "nan:arithmetic"
		1     | "-nan"                     | "0x1p-1"                   || "nan:canonical"
		1     | "-nan:0x4000000000000"     | "0x1p-1"                   || "nan:arithmetic"
		1     | "nan"                      | "-0x1p-1"                  || "nan:canonical"
		1     | "nan:0x4000000000000"      | "-0x1p-1"                  || "nan:arithmetic"
		1     | "nan"                      | "0x1p-1"                   || "nan:canonical"
		1     | "nan:0x4000000000000"      | "0x1p-1"                   || "nan:arithmetic"
		1     | "-nan"                     | "-0x1p+0"                  || "nan:canonical"
		1     | "-nan:0x4000000000000"     | "-0x1p+0"                  || "nan:arithmetic"
		1     | "-nan"                     | "0x1p+0"                   || "nan:canonical"
		1     | "-nan:0x4000000000000"     | "0x1p+0"                   || "nan:arithmetic"
		1     | "nan"                      | "-0x1p+0"                  || "nan:canonical"
		1     | "nan:0x4000000000000"      | "-0x1p+0"                  || "nan:arithmetic"
		1     | "nan"                      | "0x1p+0"                   || "nan:canonical"
		1     | "nan:0x4000000000000"      | "0x1p+0"                   || "nan:arithmetic"
		1     | "-nan"                     | "-0x1.921fb54442d18p+2"    || "nan:canonical"
		1     | "-nan:0x4000000000000"     | "-0x1.921fb54442d18p+2"    || "nan:arithmetic"
		1     | "-nan"                     | "0x1.921fb54442d18p+2"     || "nan:canonical"
		1     | "-nan:0x4000000000000"     | "0x1.921fb54442d18p+2"     || "nan:arithmetic"
		1     | "nan"                      | "-0x1.921fb54442d18p+2"    || "nan:canonical"
		1     | "nan:0x4000000000000"      | "-0x1.921fb54442d18p+2"    || "nan:arithmetic"
		1     | "nan"                      | "0x1.921fb54442d18p+2"     || "nan:canonical"
		1     | "nan:0x4000000000000"      | "0x1.921fb54442d18p+2"     || "nan:arithmetic"
		1     | "-nan"                     | "-0x1.fffffffffffffp+1023" || "nan:canonical"
		1     | "-nan:0x4000000000000"     | "-0x1.fffffffffffffp+1023" || "nan:arithmetic"
		1     | "-nan"                     | "0x1.fffffffffffffp+1023"  || "nan:canonical"
		1     | "-nan:0x4000000000000"     | "0x1.fffffffffffffp+1023"  || "nan:arithmetic"
		1     | "nan"                      | "-0x1.fffffffffffffp+1023" || "nan:canonical"
		1     | "nan:0x4000000000000"      | "-0x1.fffffffffffffp+1023" || "nan:arithmetic"
		1     | "nan"                      | "0x1.fffffffffffffp+1023"  || "nan:canonical"
		1     | "nan:0x4000000000000"      | "0x1.fffffffffffffp+1023"  || "nan:arithmetic"
		1     | "-nan"                     | "-inf"                     || "nan:canonical"
		1     | "-nan:0x4000000000000"     | "-inf"                     || "nan:arithmetic"
		1     | "-nan"                     | "inf"                      || "nan:canonical"
		1     | "-nan:0x4000000000000"     | "inf"                      || "nan:arithmetic"
		1     | "nan"                      | "-inf"                     || "nan:canonical"
		1     | "nan:0x4000000000000"      | "-inf"                     || "nan:arithmetic"
		1     | "nan"                      | "inf"                      || "nan:canonical"
		1     | "nan:0x4000000000000"      | "inf"                      || "nan:arithmetic"
		1     | "-nan"                     | "-nan"                     || "nan:canonical"
		1     | "-nan:0x4000000000000"     | "-nan"                     || "nan:arithmetic"
		1     | "-nan"                     | "-nan:0x4000000000000"     || "nan:arithmetic"
		1     | "-nan:0x4000000000000"     | "-nan:0x4000000000000"     || "nan:arithmetic"
		1     | "-nan"                     | "nan"                      || "nan:canonical"
		1     | "-nan:0x4000000000000"     | "nan"                      || "nan:arithmetic"
		1     | "-nan"                     | "nan:0x4000000000000"      || "nan:arithmetic"
		1     | "-nan:0x4000000000000"     | "nan:0x4000000000000"      || "nan:arithmetic"
		1     | "nan"                      | "-nan"                     || "nan:canonical"
		1     | "nan:0x4000000000000"      | "-nan"                     || "nan:arithmetic"
		1     | "nan"                      | "-nan:0x4000000000000"     || "nan:arithmetic"
		1     | "nan:0x4000000000000"      | "-nan:0x4000000000000"     || "nan:arithmetic"
		1     | "nan"                      | "nan"                      || "nan:canonical"
		1     | "nan:0x4000000000000"      | "nan"                      || "nan:arithmetic"
		1     | "nan"                      | "nan:0x4000000000000"      || "nan:arithmetic"
		1     | "nan:0x4000000000000"      | "nan:0x4000000000000"      || "nan:arithmetic"


	}


	def "Execute F64_sub throws exception on incorrect Type on second param "() {
		setup: " a value of F64  value"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F64(3));  // value 3  // correct type
		instance.stack().push(new I64(4));  // value 4  // incorrect type

		F64_sub function = new F64_sub(instance);

		when: "run the opcode"
		function.execute();

		then: "Verify thrown exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2 type is incorrect. ");
		exception.message.contains("Value should be of type '" + inputType + "'. ");
		exception.message.contains("The input type is 'I64'.");
		exception.message.contains("The input value is '");
		exception.getUuid().toString().contains("384a85bf-c88b-4a16-868d-3e860d1747f3");
	}

	def "Execute F64_sub throws exception on incorrect Type on first param "() {
		setup: " a value of F64  value"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(3));  // value 3  // incorrect type
		instance.stack().push(new F64(4));  // value 4  // correct type

		F64_sub function = new F64_sub(instance);

		when: "run the opcode"
		function.execute();

		then: "Verify thrown exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1 type is incorrect. ");
		exception.message.contains("Value should be of type '" + inputType + "'. ");
		exception.message.contains("The input type is 'I64'.");
		exception.message.contains("The input value is '");
		exception.getUuid().toString().contains("b5101f4f-74ef-41dc-a0cb-225e850daad2");
	}

}
