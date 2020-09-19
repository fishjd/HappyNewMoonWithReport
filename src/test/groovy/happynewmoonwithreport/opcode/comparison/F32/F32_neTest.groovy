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
package happynewmoonwithreport.opcode.comparison.F32

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.opcode.comparison.F32.F32_ne
import happynewmoonwithreport.type.F32
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.I64
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created on 2020-09-13
 */
class F32_neTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	@Unroll
	def "Execute F32 not Equal to #val1  #val2 #expected "(Float val1, Float val2, Integer expected) {
		setup: " push two values on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F32(val1));
		instance.stack().push(new F32(val2));

		F32_ne function = new F32_ne(instance);

		when: "run the opcode"
		function.execute();
		I32 result = instance.stack().pop();

		then: " verify result equals value of expected"
		new I32(expected) == result

		where: "val1 equals val2 returns #expected"
		val1 | val2 || expected
		4    | 3    || 1
		3    | 4    || 1
		4    | 4    || 0
		0    | 0    || 0
		1    | 0    || 1
		0    | 1    || 1

	}

	/**
	 * https://github.com/WebAssembly/spec/blob/7526564b56c30250b66504fe795e9c1e88a938af/test/core/f32_cmp.wast
	 * @param val1_s
	 * @param val2_s
	 * @param expected
	 * @return
	 */
	def "Execute F32 Not Equal #val1_s | #val2_s || #expected "(String val1_s, String val2_s, Integer expected) {
		setup: " push two values on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(F32.valueOf(val1_s));
		instance.stack().push(F32.valueOf(val2_s));

		F32_ne function = new F32_ne(instance);

		when: "run the opcode"
		function.execute();
		I32 result = instance.stack().pop();

		then: " verify result equals value of expected"
		new I32(expected) == result

		where: "val1 equals val2 returns #expected"
		val1_s             | val2_s             || expected
		"-0x0p+0"          | "0x0p+0"           || 0
		"-0x0p+0"          | "0x0p+0"           || 0
		"0x0p+0"           | "-0x0p+0"          || 0
		"0x0p+0"           | "0x0p+0"           || 0
		"-0x0p+0"          | "-0x1p-149"        || 1
		"-0x0p+0"          | "0x1p-149"         || 1
		"0x0p+0"           | "-0x1p-149"        || 1
		"0x0p+0"           | "0x1p-149"         || 1
		"-0x0p+0"          | "-0x1p-126"        || 1
		"-0x0p+0"          | "0x1p-126"         || 1
		"0x0p+0"           | "-0x1p-126"        || 1
		"0x0p+0"           | "0x1p-126"         || 1
		"-0x0p+0"          | "-0x1p-1"          || 1
		"-0x0p+0"          | "0x1p-1"           || 1
		"0x0p+0"           | "-0x1p-1"          || 1
		"0x0p+0"           | "0x1p-1"           || 1
		"-0x0p+0"          | "-0x1p+0"          || 1
		"-0x0p+0"          | "0x1p+0"           || 1
		"0x0p+0"           | "-0x1p+0"          || 1
		"0x0p+0"           | "0x1p+0"           || 1
		"-0x0p+0"          | "-0x1.921fb6p+2"   || 1
		"-0x0p+0"          | "0x1.921fb6p+2"    || 1
		"0x0p+0"           | "-0x1.921fb6p+2"   || 1
		"0x0p+0"           | "0x1.921fb6p+2"    || 1
		"-0x0p+0"          | "-0x1.fffffep+127" || 1
		"-0x0p+0"          | "0x1.fffffep+127"  || 1
		"0x0p+0"           | "-0x1.fffffep+127" || 1
		"0x0p+0"           | "0x1.fffffep+127"  || 1
		"-0x0p+0"          | "-inf"             || 1
		"-0x0p+0"          | "inf"              || 1
		"0x0p+0"           | "-inf"             || 1
		"0x0p+0"           | "inf"              || 1
		"-0x0p+0"          | "-nan"             || 1
		"-0x0p+0"          | "-nan:0x200000"    || 1
		"-0x0p+0"          | "nan"              || 1
		"-0x0p+0"          | "nan:0x200000"     || 1
		"0x0p+0"           | "-nan"             || 1
		"0x0p+0"           | "-nan:0x200000"    || 1
		"0x0p+0"           | "nan"              || 1
		"0x0p+0"           | "nan:0x200000"     || 1
		"-0x1p-149"        | "-0x0p+0"          || 1
		"-0x1p-149"        | "0x0p+0"           || 1
		"0x1p-149"         | "-0x0p+0"          || 1
		"0x1p-149"         | "0x0p+0"           || 1
		"-0x1p-149"        | "-0x1p-149"        || 0
		"-0x1p-149"        | "0x1p-149"         || 1
		"0x1p-149"         | "-0x1p-149"        || 1
		"0x1p-149"         | "0x1p-149"         || 0
		"-0x1p-149"        | "-0x1p-126"        || 1
		"-0x1p-149"        | "0x1p-126"         || 1
		"0x1p-149"         | "-0x1p-126"        || 1
		"0x1p-149"         | "0x1p-126"         || 1
		"-0x1p-149"        | "-0x1p-1"          || 1
		"-0x1p-149"        | "0x1p-1"           || 1
		"0x1p-149"         | "-0x1p-1"          || 1
		"0x1p-149"         | "0x1p-1"           || 1
		"-0x1p-149"        | "-0x1p+0"          || 1
		"-0x1p-149"        | "0x1p+0"           || 1
		"0x1p-149"         | "-0x1p+0"          || 1
		"0x1p-149"         | "0x1p+0"           || 1
		"-0x1p-149"        | "-0x1.921fb6p+2"   || 1
		"-0x1p-149"        | "0x1.921fb6p+2"    || 1
		"0x1p-149"         | "-0x1.921fb6p+2"   || 1
		"0x1p-149"         | "0x1.921fb6p+2"    || 1
		"-0x1p-149"        | "-0x1.fffffep+127" || 1
		"-0x1p-149"        | "0x1.fffffep+127"  || 1
		"0x1p-149"         | "-0x1.fffffep+127" || 1
		"0x1p-149"         | "0x1.fffffep+127"  || 1
		"-0x1p-149"        | "-inf"             || 1
		"-0x1p-149"        | "inf"              || 1
		"0x1p-149"         | "-inf"             || 1
		"0x1p-149"         | "inf"              || 1
		"-0x1p-149"        | "-nan"             || 1
		"-0x1p-149"        | "-nan:0x200000"    || 1
		"-0x1p-149"        | "nan"              || 1
		"-0x1p-149"        | "nan:0x200000"     || 1
		"0x1p-149"         | "-nan"             || 1
		"0x1p-149"         | "-nan:0x200000"    || 1
		"0x1p-149"         | "nan"              || 1
		"0x1p-149"         | "nan:0x200000"     || 1
		"-0x1p-126"        | "-0x0p+0"          || 1
		"-0x1p-126"        | "0x0p+0"           || 1
		"0x1p-126"         | "-0x0p+0"          || 1
		"0x1p-126"         | "0x0p+0"           || 1
		"-0x1p-126"        | "-0x1p-149"        || 1
		"-0x1p-126"        | "0x1p-149"         || 1
		"0x1p-126"         | "-0x1p-149"        || 1
		"0x1p-126"         | "0x1p-149"         || 1
		"-0x1p-126"        | "-0x1p-126"        || 0
		"-0x1p-126"        | "0x1p-126"         || 1
		"0x1p-126"         | "-0x1p-126"        || 1
		"0x1p-126"         | "0x1p-126"         || 0
		"-0x1p-126"        | "-0x1p-1"          || 1
		"-0x1p-126"        | "0x1p-1"           || 1
		"0x1p-126"         | "-0x1p-1"          || 1
		"0x1p-126"         | "0x1p-1"           || 1
		"-0x1p-126"        | "-0x1p+0"          || 1
		"-0x1p-126"        | "0x1p+0"           || 1
		"0x1p-126"         | "-0x1p+0"          || 1
		"0x1p-126"         | "0x1p+0"           || 1
		"-0x1p-126"        | "-0x1.921fb6p+2"   || 1
		"-0x1p-126"        | "0x1.921fb6p+2"    || 1
		"0x1p-126"         | "-0x1.921fb6p+2"   || 1
		"0x1p-126"         | "0x1.921fb6p+2"    || 1
		"-0x1p-126"        | "-0x1.fffffep+127" || 1
		"-0x1p-126"        | "0x1.fffffep+127"  || 1
		"0x1p-126"         | "-0x1.fffffep+127" || 1
		"0x1p-126"         | "0x1.fffffep+127"  || 1
		"-0x1p-126"        | "-inf"             || 1
		"-0x1p-126"        | "inf"              || 1
		"0x1p-126"         | "-inf"             || 1
		"0x1p-126"         | "inf"              || 1
		"-0x1p-126"        | "-nan"             || 1
		"-0x1p-126"        | "-nan:0x200000"    || 1
		"-0x1p-126"        | "nan"              || 1
		"-0x1p-126"        | "nan:0x200000"     || 1
		"0x1p-126"         | "-nan"             || 1
		"0x1p-126"         | "-nan:0x200000"    || 1
		"0x1p-126"         | "nan"              || 1
		"0x1p-126"         | "nan:0x200000"     || 1
		"-0x1p-1"          | "-0x0p+0"          || 1
		"-0x1p-1"          | "0x0p+0"           || 1
		"0x1p-1"           | "-0x0p+0"          || 1
		"0x1p-1"           | "0x0p+0"           || 1
		"-0x1p-1"          | "-0x1p-149"        || 1
		"-0x1p-1"          | "0x1p-149"         || 1
		"0x1p-1"           | "-0x1p-149"        || 1
		"0x1p-1"           | "0x1p-149"         || 1
		"-0x1p-1"          | "-0x1p-126"        || 1
		"-0x1p-1"          | "0x1p-126"         || 1
		"0x1p-1"           | "-0x1p-126"        || 1
		"0x1p-1"           | "0x1p-126"         || 1
		"-0x1p-1"          | "-0x1p-1"          || 0
		"-0x1p-1"          | "0x1p-1"           || 1
		"0x1p-1"           | "-0x1p-1"          || 1
		"0x1p-1"           | "0x1p-1"           || 0
		"-0x1p-1"          | "-0x1p+0"          || 1
		"-0x1p-1"          | "0x1p+0"           || 1
		"0x1p-1"           | "-0x1p+0"          || 1
		"0x1p-1"           | "0x1p+0"           || 1
		"-0x1p-1"          | "-0x1.921fb6p+2"   || 1
		"-0x1p-1"          | "0x1.921fb6p+2"    || 1
		"0x1p-1"           | "-0x1.921fb6p+2"   || 1
		"0x1p-1"           | "0x1.921fb6p+2"    || 1
		"-0x1p-1"          | "-0x1.fffffep+127" || 1
		"-0x1p-1"          | "0x1.fffffep+127"  || 1
		"0x1p-1"           | "-0x1.fffffep+127" || 1
		"0x1p-1"           | "0x1.fffffep+127"  || 1
		"-0x1p-1"          | "-inf"             || 1
		"-0x1p-1"          | "inf"              || 1
		"0x1p-1"           | "-inf"             || 1
		"0x1p-1"           | "inf"              || 1
		"-0x1p-1"          | "-nan"             || 1
		"-0x1p-1"          | "-nan:0x200000"    || 1
		"-0x1p-1"          | "nan"              || 1
		"-0x1p-1"          | "nan:0x200000"     || 1
		"0x1p-1"           | "-nan"             || 1
		"0x1p-1"           | "-nan:0x200000"    || 1
		"0x1p-1"           | "nan"              || 1
		"0x1p-1"           | "nan:0x200000"     || 1
		"-0x1p+0"          | "-0x0p+0"          || 1
		"-0x1p+0"          | "0x0p+0"           || 1
		"0x1p+0"           | "-0x0p+0"          || 1
		"0x1p+0"           | "0x0p+0"           || 1
		"-0x1p+0"          | "-0x1p-149"        || 1
		"-0x1p+0"          | "0x1p-149"         || 1
		"0x1p+0"           | "-0x1p-149"        || 1
		"0x1p+0"           | "0x1p-149"         || 1
		"-0x1p+0"          | "-0x1p-126"        || 1
		"-0x1p+0"          | "0x1p-126"         || 1
		"0x1p+0"           | "-0x1p-126"        || 1
		"0x1p+0"           | "0x1p-126"         || 1
		"-0x1p+0"          | "-0x1p-1"          || 1
		"-0x1p+0"          | "0x1p-1"           || 1
		"0x1p+0"           | "-0x1p-1"          || 1
		"0x1p+0"           | "0x1p-1"           || 1
		"-0x1p+0"          | "-0x1p+0"          || 0
		"-0x1p+0"          | "0x1p+0"           || 1
		"0x1p+0"           | "-0x1p+0"          || 1
		"0x1p+0"           | "0x1p+0"           || 0
		"-0x1p+0"          | "-0x1.921fb6p+2"   || 1
		"-0x1p+0"          | "0x1.921fb6p+2"    || 1
		"0x1p+0"           | "-0x1.921fb6p+2"   || 1
		"0x1p+0"           | "0x1.921fb6p+2"    || 1
		"-0x1p+0"          | "-0x1.fffffep+127" || 1
		"-0x1p+0"          | "0x1.fffffep+127"  || 1
		"0x1p+0"           | "-0x1.fffffep+127" || 1
		"0x1p+0"           | "0x1.fffffep+127"  || 1
		"-0x1p+0"          | "-inf"             || 1
		"-0x1p+0"          | "inf"              || 1
		"0x1p+0"           | "-inf"             || 1
		"0x1p+0"           | "inf"              || 1
		"-0x1p+0"          | "-nan"             || 1
		"-0x1p+0"          | "-nan:0x200000"    || 1
		"-0x1p+0"          | "nan"              || 1
		"-0x1p+0"          | "nan:0x200000"     || 1
		"0x1p+0"           | "-nan"             || 1
		"0x1p+0"           | "-nan:0x200000"    || 1
		"0x1p+0"           | "nan"              || 1
		"0x1p+0"           | "nan:0x200000"     || 1
		"-0x1.921fb6p+2"   | "-0x0p+0"          || 1
		"-0x1.921fb6p+2"   | "0x0p+0"           || 1
		"0x1.921fb6p+2"    | "-0x0p+0"          || 1
		"0x1.921fb6p+2"    | "0x0p+0"           || 1
		"-0x1.921fb6p+2"   | "-0x1p-149"        || 1
		"-0x1.921fb6p+2"   | "0x1p-149"         || 1
		"0x1.921fb6p+2"    | "-0x1p-149"        || 1
		"0x1.921fb6p+2"    | "0x1p-149"         || 1
		"-0x1.921fb6p+2"   | "-0x1p-126"        || 1
		"-0x1.921fb6p+2"   | "0x1p-126"         || 1
		"0x1.921fb6p+2"    | "-0x1p-126"        || 1
		"0x1.921fb6p+2"    | "0x1p-126"         || 1
		"-0x1.921fb6p+2"   | "-0x1p-1"          || 1
		"-0x1.921fb6p+2"   | "0x1p-1"           || 1
		"0x1.921fb6p+2"    | "-0x1p-1"          || 1
		"0x1.921fb6p+2"    | "0x1p-1"           || 1
		"-0x1.921fb6p+2"   | "-0x1p+0"          || 1
		"-0x1.921fb6p+2"   | "0x1p+0"           || 1
		"0x1.921fb6p+2"    | "-0x1p+0"          || 1
		"0x1.921fb6p+2"    | "0x1p+0"           || 1
		"-0x1.921fb6p+2"   | "-0x1.921fb6p+2"   || 0
		"-0x1.921fb6p+2"   | "0x1.921fb6p+2"    || 1
		"0x1.921fb6p+2"    | "-0x1.921fb6p+2"   || 1
		"0x1.921fb6p+2"    | "0x1.921fb6p+2"    || 0
		"-0x1.921fb6p+2"   | "-0x1.fffffep+127" || 1
		"-0x1.921fb6p+2"   | "0x1.fffffep+127"  || 1
		"0x1.921fb6p+2"    | "-0x1.fffffep+127" || 1
		"0x1.921fb6p+2"    | "0x1.fffffep+127"  || 1
		"-0x1.921fb6p+2"   | "-inf"             || 1
		"-0x1.921fb6p+2"   | "inf"              || 1
		"0x1.921fb6p+2"    | "-inf"             || 1
		"0x1.921fb6p+2"    | "inf"              || 1
		"-0x1.921fb6p+2"   | "-nan"             || 1
		"-0x1.921fb6p+2"   | "-nan:0x200000"    || 1
		"-0x1.921fb6p+2"   | "nan"              || 1
		"-0x1.921fb6p+2"   | "nan:0x200000"     || 1
		"0x1.921fb6p+2"    | "-nan"             || 1
		"0x1.921fb6p+2"    | "-nan:0x200000"    || 1
		"0x1.921fb6p+2"    | "nan"              || 1
		"0x1.921fb6p+2"    | "nan:0x200000"     || 1
		"-0x1.fffffep+127" | "-0x0p+0"          || 1
		"-0x1.fffffep+127" | "0x0p+0"           || 1
		"0x1.fffffep+127"  | "-0x0p+0"          || 1
		"0x1.fffffep+127"  | "0x0p+0"           || 1
		"-0x1.fffffep+127" | "-0x1p-149"        || 1
		"-0x1.fffffep+127" | "0x1p-149"         || 1
		"0x1.fffffep+127"  | "-0x1p-149"        || 1
		"0x1.fffffep+127"  | "0x1p-149"         || 1
		"-0x1.fffffep+127" | "-0x1p-126"        || 1
		"-0x1.fffffep+127" | "0x1p-126"         || 1
		"0x1.fffffep+127"  | "-0x1p-126"        || 1
		"0x1.fffffep+127"  | "0x1p-126"         || 1
		"-0x1.fffffep+127" | "-0x1p-1"          || 1
		"-0x1.fffffep+127" | "0x1p-1"           || 1
		"0x1.fffffep+127"  | "-0x1p-1"          || 1
		"0x1.fffffep+127"  | "0x1p-1"           || 1
		"-0x1.fffffep+127" | "-0x1p+0"          || 1
		"-0x1.fffffep+127" | "0x1p+0"           || 1
		"0x1.fffffep+127"  | "-0x1p+0"          || 1
		"0x1.fffffep+127"  | "0x1p+0"           || 1
		"-0x1.fffffep+127" | "-0x1.921fb6p+2"   || 1
		"-0x1.fffffep+127" | "0x1.921fb6p+2"    || 1
		"0x1.fffffep+127"  | "-0x1.921fb6p+2"   || 1
		"0x1.fffffep+127"  | "0x1.921fb6p+2"    || 1
		"-0x1.fffffep+127" | "-0x1.fffffep+127" || 0
		"-0x1.fffffep+127" | "0x1.fffffep+127"  || 1
		"0x1.fffffep+127"  | "-0x1.fffffep+127" || 1
		"0x1.fffffep+127"  | "0x1.fffffep+127"  || 0
		"-0x1.fffffep+127" | "-inf"             || 1
		"-0x1.fffffep+127" | "inf"              || 1
		"0x1.fffffep+127"  | "-inf"             || 1
		"0x1.fffffep+127"  | "inf"              || 1
		"-0x1.fffffep+127" | "-nan"             || 1
		"-0x1.fffffep+127" | "-nan:0x200000"    || 1
		"-0x1.fffffep+127" | "nan"              || 1
		"-0x1.fffffep+127" | "nan:0x200000"     || 1
		"0x1.fffffep+127"  | "-nan"             || 1
		"0x1.fffffep+127"  | "-nan:0x200000"    || 1
		"0x1.fffffep+127"  | "nan"              || 1
		"0x1.fffffep+127"  | "nan:0x200000"     || 1
		"-inf"             | "-0x0p+0"          || 1
		"-inf"             | "0x0p+0"           || 1
		"inf"              | "-0x0p+0"          || 1
		"inf"              | "0x0p+0"           || 1
		"-inf"             | "-0x1p-149"        || 1
		"-inf"             | "0x1p-149"         || 1
		"inf"              | "-0x1p-149"        || 1
		"inf"              | "0x1p-149"         || 1
		"-inf"             | "-0x1p-126"        || 1
		"-inf"             | "0x1p-126"         || 1
		"inf"              | "-0x1p-126"        || 1
		"inf"              | "0x1p-126"         || 1
		"-inf"             | "-0x1p-1"          || 1
		"-inf"             | "0x1p-1"           || 1
		"inf"              | "-0x1p-1"          || 1
		"inf"              | "0x1p-1"           || 1
		"-inf"             | "-0x1p+0"          || 1
		"-inf"             | "0x1p+0"           || 1
		"inf"              | "-0x1p+0"          || 1
		"inf"              | "0x1p+0"           || 1
		"-inf"             | "-0x1.921fb6p+2"   || 1
		"-inf"             | "0x1.921fb6p+2"    || 1
		"inf"              | "-0x1.921fb6p+2"   || 1
		"inf"              | "0x1.921fb6p+2"    || 1
		"-inf"             | "-0x1.fffffep+127" || 1
		"-inf"             | "0x1.fffffep+127"  || 1
		"inf"              | "-0x1.fffffep+127" || 1
		"inf"              | "0x1.fffffep+127"  || 1
		"-inf"             | "-inf"             || 0
		"-inf"             | "inf"              || 1
		"inf"              | "-inf"             || 1
		"inf"              | "inf"              || 0
		"-inf"             | "-nan"             || 1
		"-inf"             | "-nan:0x200000"    || 1
		"-inf"             | "nan"              || 1
		"-inf"             | "nan:0x200000"     || 1
		"inf"              | "-nan"             || 1
		"inf"              | "-nan:0x200000"    || 1
		"inf"              | "nan"              || 1
		"inf"              | "nan:0x200000"     || 1
		"-nan"             | "-0x0p+0"          || 1
		"-nan:0x200000"    | "-0x0p+0"          || 1
		"-nan"             | "0x0p+0"           || 1
		"-nan:0x200000"    | "0x0p+0"           || 1
		"nan"              | "-0x0p+0"          || 1
		"nan:0x200000"     | "-0x0p+0"          || 1
		"nan"              | "0x0p+0"           || 1
		"nan:0x200000"     | "0x0p+0"           || 1
		"-nan"             | "-0x1p-149"        || 1
		"-nan:0x200000"    | "-0x1p-149"        || 1
		"-nan"             | "0x1p-149"         || 1
		"-nan:0x200000"    | "0x1p-149"         || 1
		"nan"              | "-0x1p-149"        || 1
		"nan:0x200000"     | "-0x1p-149"        || 1
		"nan"              | "0x1p-149"         || 1
		"nan:0x200000"     | "0x1p-149"         || 1
		"-nan"             | "-0x1p-126"        || 1
		"-nan:0x200000"    | "-0x1p-126"        || 1
		"-nan"             | "0x1p-126"         || 1
		"-nan:0x200000"    | "0x1p-126"         || 1
		"nan"              | "-0x1p-126"        || 1
		"nan:0x200000"     | "-0x1p-126"        || 1
		"nan"              | "0x1p-126"         || 1
		"nan:0x200000"     | "0x1p-126"         || 1
		"-nan"             | "-0x1p-1"          || 1
		"-nan:0x200000"    | "-0x1p-1"          || 1
		"-nan"             | "0x1p-1"           || 1
		"-nan:0x200000"    | "0x1p-1"           || 1
		"nan"              | "-0x1p-1"          || 1
		"nan:0x200000"     | "-0x1p-1"          || 1
		"nan"              | "0x1p-1"           || 1
		"nan:0x200000"     | "0x1p-1"           || 1
		"-nan"             | "-0x1p+0"          || 1
		"-nan:0x200000"    | "-0x1p+0"          || 1
		"-nan"             | "0x1p+0"           || 1
		"-nan:0x200000"    | "0x1p+0"           || 1
		"nan"              | "-0x1p+0"          || 1
		"nan:0x200000"     | "-0x1p+0"          || 1
		"nan"              | "0x1p+0"           || 1
		"nan:0x200000"     | "0x1p+0"           || 1
		"-nan"             | "-0x1.921fb6p+2"   || 1
		"-nan:0x200000"    | "-0x1.921fb6p+2"   || 1
		"-nan"             | "0x1.921fb6p+2"    || 1
		"-nan:0x200000"    | "0x1.921fb6p+2"    || 1
		"nan"              | "-0x1.921fb6p+2"   || 1
		"nan:0x200000"     | "-0x1.921fb6p+2"   || 1
		"nan"              | "0x1.921fb6p+2"    || 1
		"nan:0x200000"     | "0x1.921fb6p+2"    || 1
		"-nan"             | "-0x1.fffffep+127" || 1
		"-nan:0x200000"    | "-0x1.fffffep+127" || 1
		"-nan"             | "0x1.fffffep+127"  || 1
		"-nan:0x200000"    | "0x1.fffffep+127"  || 1
		"nan"              | "-0x1.fffffep+127" || 1
		"nan:0x200000"     | "-0x1.fffffep+127" || 1
		"nan"              | "0x1.fffffep+127"  || 1
		"nan:0x200000"     | "0x1.fffffep+127"  || 1
		"-nan"             | "-inf"             || 1
		"-nan:0x200000"    | "-inf"             || 1
		"-nan"             | "inf"              || 1
		"-nan:0x200000"    | "inf"              || 1
		"nan"              | "-inf"             || 1
		"nan:0x200000"     | "-inf"             || 1
		"nan"              | "inf"              || 1
		"nan:0x200000"     | "inf"              || 1
		"-nan"             | "-nan"             || 1
		"-nan:0x200000"    | "-nan"             || 1
		"-nan"             | "-nan:0x200000"    || 1
		"-nan:0x200000"    | "-nan:0x200000"    || 1
		"-nan"             | "nan"              || 1
		"-nan:0x200000"    | "nan"              || 1
		"-nan"             | "nan:0x200000"     || 1
		"-nan:0x200000"    | "nan:0x200000"     || 1
		"nan"              | "-nan"             || 1
		"nan:0x200000"     | "-nan"             || 1
		"nan"              | "-nan:0x200000"    || 1
		"nan:0x200000"     | "-nan:0x200000"    || 1
		"nan"              | "nan"              || 1
		"nan:0x200000"     | "nan"              || 1
		"nan"              | "nan:0x200000"     || 1
		"nan:0x200000"     | "nan:0x200000"     || 1


	}


	def "Execute F32_ne throw exception on incorrect Type on second param "() {
		setup: " a value of F32  value 1  and a value of I64 of value 2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F32(3));  // value 1
		instance.stack().push(new I64(4));  // value 2

		F32_ne function = new F32_ne(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.getUuid().toString().contains("ebe2ffe2-97fa-4fe2-a73c-1d9c2a6d3bf2");
	}

	def "Execute F32_ne throw exception on incorrect Type on first param "() {
		setup: " a value of I64  value 1  and a value of F32 of value 2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(3));  // value 1
		instance.stack().push(new F32(4));  // value 2

		F32_ne function = new F32_ne(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.getUuid().toString().contains("12a49c3e-30d5-4487-ac40-c3f1c6780612");
	}

}
