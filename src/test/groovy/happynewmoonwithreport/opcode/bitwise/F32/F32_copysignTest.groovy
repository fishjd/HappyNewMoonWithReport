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
package happynewmoonwithreport.opcode.bitwise.F32

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.type.F32
import happynewmoonwithreport.type.I64
import spock.lang.Specification

/**
 * Test F32_copysign opcode.
 * <p>
 * Created on 2020-11-28
 */
class F32_copysignTest extends Specification {
	String inputType;
	String returnType;

	void setup() {
		inputType = "F32";
		returnType = "F32"
	}

	void cleanup() {
	}

/**
 * F32_copysign unit test.
 * @param count What line of parameters is executing. Only used for debugging.
 * @param val1 The test value.   The input for the opcode.
 * @param expected The expected value.  What the opcode should return.
 * @return None.
 */
	def "Execute F32_copysign with #count | #val1 | #val2 || #expected "(Integer count, Float val1, Float val2, Float expected) {
		setup: " push two values on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F32(val1));

		instance.stack().push(new F32(val2));

		F32_copysign opcode = new F32_copysign(instance);

		when: "run the opcode"
		opcode.execute();

		then: " verify results"
		F32 result = instance.stack().pop();

		then: " verify result equals value of expected"
		F32.valueOf(expected) == result

		where: "val1 equals val2 returns #expected"
		count | val1 | val2 || expected
		1     | 4.1  | 1    || 4.1
		2     | 4.1  | -1   || -4.1
		3     | 0F   | 1    || 0F
	}


	def "Execute F32_copysign with F32 Input Nan Test #count -> #val1 || #expected "(Integer count, F32 val1, F32 val2, F32 expected) {

		setup: " push two values on stack."
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F32(val1));
		instance.stack().push(new F32(val2));

		when: "run the opcode"
		new F32_copysign(instance).execute();

		then: "verify result equals value of expected"
		F32 result = instance.stack().pop();
		expected == result

		where: "val1 equals val2 returns #expected"
		count | val1                 | val2                 || expected
		1     | F32.Nan              | F32.Nan              || F32.Nan
		2     | F32.Nan              | F32.NanNeg           || F32.NanNeg
		3     | F32.NanCanonicalNeg  | F32.NanCanonicalPos  || F32.NanCanonicalPos
		4     | F32.NanCanonicalNeg  | F32.NanCanonicalNeg  || F32.NanCanonicalNeg
		5     | F32.NanCanonicalNeg  | F32.valueOf(1)       || F32.NanCanonicalPos
		6     | F32.NanCanonicalNeg  | F32.valueOf(-1)      || F32.NanCanonicalNeg
		7     | F32.ZeroPositive     | F32.ZeroPositive     || F32.ZeroPositive
		8     | F32.ZeroNegative     | F32.ZeroNegative     || F32.ZeroNegative
		9     | F32.NanArithmeticPos | F32.NanArithmeticPos || F32.NanArithmeticPos
		10    | F32.NanArithmeticNeg | F32.NanArithmeticNeg || F32.NanArithmeticNeg
		11    | F32.Nan0x20_0000Pos  | F32.Nan0x20_0000Pos  || F32.Nan0x20_0000Pos
		12    | F32.Nan0x20_0000Neg  | F32.Nan0x20_0000Neg  || F32.Nan0x20_0000Neg
		13    | F32.InfinityPositive | F32.InfinityPositive || F32.InfinityPositive
		14    | F32.InfinityNegative | F32.InfinityNegative || F32.InfinityNegative
	}


	/**
	 * F32_copysign unit test.
	 * <p>
	 * <a href="https://github.com/WebAssembly/spec/blob/7526564b56c30250b66504fe795e9c1e88a938af/test/core/f32_bitwise.wast">
	 *     Official Web Assembly test code.
	 * </a>
	 * @param val1_s The test value.   The input for the opcode.
	 * @param expected The expected value.  What the opcode should return.
	 * @return None.
	 */
	def "Execute F32 copysign  #count | #val1_s  || # expected "(Integer count, String val1, String val2, String expected) {

		setup: " push one value on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(F32.valueOf(val1));
		instance.stack().push(F32.valueOf(val2));

		F32_copysign opcode = new F32_copysign(instance);

		when: "run the opcode"
		opcode.execute();

		then: " verify result equals value of expected"
		F32 result = instance.stack().pop();
		F32.valueOf(expected) == result

		where: "val1 returns #expected"
		count | val1               | val2               || expected
		1     | "-0x0p+0"          | "-0x0p+0"          || "-0x0p+0"
		2     | "-0x0p+0"          | "0x0p+0"           || "0x0p+0"
		3     | "0x0p+0"           | "-0x0p+0"          || "-0x0p+0"
		4     | "0x0p+0"           | "0x0p+0"           || "0x0p+0"
		5     | "-0x0p+0"          | "-0x1p-149"        || "-0x0p+0"
		6     | "-0x0p+0"          | "0x1p-149"         || "0x0p+0"
		7     | "0x0p+0"           | "-0x1p-149"        || "-0x0p+0"
		8     | "0x0p+0"           | "0x1p-149"         || "0x0p+0"
		9     | "-0x0p+0"          | "-0x1p-126"        || "-0x0p+0"
		10    | "-0x0p+0"          | "0x1p-126"         || "0x0p+0"
		11    | "0x0p+0"           | "-0x1p-126"        || "-0x0p+0"
		12    | "0x0p+0"           | "0x1p-126"         || "0x0p+0"
		13    | "-0x0p+0"          | "-0x1p-1"          || "-0x0p+0"
		14    | "-0x0p+0"          | "0x1p-1"           || "0x0p+0"
		15    | "0x0p+0"           | "-0x1p-1"          || "-0x0p+0"
		16    | "0x0p+0"           | "0x1p-1"           || "0x0p+0"
		17    | "-0x0p+0"          | "-0x1p+0"          || "-0x0p+0"
		18    | "-0x0p+0"          | "0x1p+0"           || "0x0p+0"
		19    | "0x0p+0"           | "-0x1p+0"          || "-0x0p+0"
		20    | "0x0p+0"           | "0x1p+0"           || "0x0p+0"
		21    | "-0x0p+0"          | "-0x1.921fb6p+2"   || "-0x0p+0"
		22    | "-0x0p+0"          | "0x1.921fb6p+2"    || "0x0p+0"
		23    | "0x0p+0"           | "-0x1.921fb6p+2"   || "-0x0p+0"
		24    | "0x0p+0"           | "0x1.921fb6p+2"    || "0x0p+0"
		25    | "-0x0p+0"          | "-0x1.fffffep+127" || "-0x0p+0"
		26    | "-0x0p+0"          | "0x1.fffffep+127"  || "0x0p+0"
		27    | "0x0p+0"           | "-0x1.fffffep+127" || "-0x0p+0"
		28    | "0x0p+0"           | "0x1.fffffep+127"  || "0x0p+0"
		29    | "-0x0p+0"          | "-inf"             || "-0x0p+0"
		30    | "-0x0p+0"          | "inf"              || "0x0p+0"
		31    | "0x0p+0"           | "-inf"             || "-0x0p+0"
		32    | "0x0p+0"           | "inf"              || "0x0p+0"
		33    | "-0x0p+0"          | "-nan"             || "-0x0p+0"
		34    | "-0x0p+0"          | "nan"              || "0x0p+0"
		35    | "0x0p+0"           | "-nan"             || "-0x0p+0"
		36    | "0x0p+0"           | "nan"              || "0x0p+0"
		37    | "-0x1p-149"        | "-0x0p+0"          || "-0x1p-149"
		38    | "-0x1p-149"        | "0x0p+0"           || "0x1p-149"
		39    | "0x1p-149"         | "-0x0p+0"          || "-0x1p-149"
		40    | "0x1p-149"         | "0x0p+0"           || "0x1p-149"
		41    | "-0x1p-149"        | "-0x1p-149"        || "-0x1p-149"
		42    | "-0x1p-149"        | "0x1p-149"         || "0x1p-149"
		43    | "0x1p-149"         | "-0x1p-149"        || "-0x1p-149"
		44    | "0x1p-149"         | "0x1p-149"         || "0x1p-149"
		45    | "-0x1p-149"        | "-0x1p-126"        || "-0x1p-149"
		46    | "-0x1p-149"        | "0x1p-126"         || "0x1p-149"
		47    | "0x1p-149"         | "-0x1p-126"        || "-0x1p-149"
		48    | "0x1p-149"         | "0x1p-126"         || "0x1p-149"
		49    | "-0x1p-149"        | "-0x1p-1"          || "-0x1p-149"
		50    | "-0x1p-149"        | "0x1p-1"           || "0x1p-149"
		51    | "0x1p-149"         | "-0x1p-1"          || "-0x1p-149"
		52    | "0x1p-149"         | "0x1p-1"           || "0x1p-149"
		53    | "-0x1p-149"        | "-0x1p+0"          || "-0x1p-149"
		54    | "-0x1p-149"        | "0x1p+0"           || "0x1p-149"
		55    | "0x1p-149"         | "-0x1p+0"          || "-0x1p-149"
		56    | "0x1p-149"         | "0x1p+0"           || "0x1p-149"
		57    | "-0x1p-149"        | "-0x1.921fb6p+2"   || "-0x1p-149"
		58    | "-0x1p-149"        | "0x1.921fb6p+2"    || "0x1p-149"
		59    | "0x1p-149"         | "-0x1.921fb6p+2"   || "-0x1p-149"
		60    | "0x1p-149"         | "0x1.921fb6p+2"    || "0x1p-149"
		61    | "-0x1p-149"        | "-0x1.fffffep+127" || "-0x1p-149"
		62    | "-0x1p-149"        | "0x1.fffffep+127"  || "0x1p-149"
		63    | "0x1p-149"         | "-0x1.fffffep+127" || "-0x1p-149"
		64    | "0x1p-149"         | "0x1.fffffep+127"  || "0x1p-149"
		65    | "-0x1p-149"        | "-inf"             || "-0x1p-149"
		66    | "-0x1p-149"        | "inf"              || "0x1p-149"
		67    | "0x1p-149"         | "-inf"             || "-0x1p-149"
		68    | "0x1p-149"         | "inf"              || "0x1p-149"
		69    | "-0x1p-149"        | "-nan"             || "-0x1p-149"
		70    | "-0x1p-149"        | "nan"              || "0x1p-149"
		71    | "0x1p-149"         | "-nan"             || "-0x1p-149"
		72    | "0x1p-149"         | "nan"              || "0x1p-149"
		73    | "-0x1p-126"        | "-0x0p+0"          || "-0x1p-126"
		74    | "-0x1p-126"        | "0x0p+0"           || "0x1p-126"
		75    | "0x1p-126"         | "-0x0p+0"          || "-0x1p-126"
		76    | "0x1p-126"         | "0x0p+0"           || "0x1p-126"
		77    | "-0x1p-126"        | "-0x1p-149"        || "-0x1p-126"
		78    | "-0x1p-126"        | "0x1p-149"         || "0x1p-126"
		79    | "0x1p-126"         | "-0x1p-149"        || "-0x1p-126"
		80    | "0x1p-126"         | "0x1p-149"         || "0x1p-126"
		81    | "-0x1p-126"        | "-0x1p-126"        || "-0x1p-126"
		82    | "-0x1p-126"        | "0x1p-126"         || "0x1p-126"
		83    | "0x1p-126"         | "-0x1p-126"        || "-0x1p-126"
		84    | "0x1p-126"         | "0x1p-126"         || "0x1p-126"
		85    | "-0x1p-126"        | "-0x1p-1"          || "-0x1p-126"
		86    | "-0x1p-126"        | "0x1p-1"           || "0x1p-126"
		87    | "0x1p-126"         | "-0x1p-1"          || "-0x1p-126"
		88    | "0x1p-126"         | "0x1p-1"           || "0x1p-126"
		89    | "-0x1p-126"        | "-0x1p+0"          || "-0x1p-126"
		90    | "-0x1p-126"        | "0x1p+0"           || "0x1p-126"
		91    | "0x1p-126"         | "-0x1p+0"          || "-0x1p-126"
		92    | "0x1p-126"         | "0x1p+0"           || "0x1p-126"
		93    | "-0x1p-126"        | "-0x1.921fb6p+2"   || "-0x1p-126"
		94    | "-0x1p-126"        | "0x1.921fb6p+2"    || "0x1p-126"
		95    | "0x1p-126"         | "-0x1.921fb6p+2"   || "-0x1p-126"
		96    | "0x1p-126"         | "0x1.921fb6p+2"    || "0x1p-126"
		97    | "-0x1p-126"        | "-0x1.fffffep+127" || "-0x1p-126"
		98    | "-0x1p-126"        | "0x1.fffffep+127"  || "0x1p-126"
		99    | "0x1p-126"         | "-0x1.fffffep+127" || "-0x1p-126"
		100   | "0x1p-126"         | "0x1.fffffep+127"  || "0x1p-126"
		101   | "-0x1p-126"        | "-inf"             || "-0x1p-126"
		102   | "-0x1p-126"        | "inf"              || "0x1p-126"
		103   | "0x1p-126"         | "-inf"             || "-0x1p-126"
		104   | "0x1p-126"         | "inf"              || "0x1p-126"
		105   | "-0x1p-126"        | "-nan"             || "-0x1p-126"
		106   | "-0x1p-126"        | "nan"              || "0x1p-126"
		107   | "0x1p-126"         | "-nan"             || "-0x1p-126"
		108   | "0x1p-126"         | "nan"              || "0x1p-126"
		109   | "-0x1p-1"          | "-0x0p+0"          || "-0x1p-1"
		110   | "-0x1p-1"          | "0x0p+0"           || "0x1p-1"
		111   | "0x1p-1"           | "-0x0p+0"          || "-0x1p-1"
		112   | "0x1p-1"           | "0x0p+0"           || "0x1p-1"
		113   | "-0x1p-1"          | "-0x1p-149"        || "-0x1p-1"
		114   | "-0x1p-1"          | "0x1p-149"         || "0x1p-1"
		115   | "0x1p-1"           | "-0x1p-149"        || "-0x1p-1"
		116   | "0x1p-1"           | "0x1p-149"         || "0x1p-1"
		117   | "-0x1p-1"          | "-0x1p-126"        || "-0x1p-1"
		118   | "-0x1p-1"          | "0x1p-126"         || "0x1p-1"
		119   | "0x1p-1"           | "-0x1p-126"        || "-0x1p-1"
		120   | "0x1p-1"           | "0x1p-126"         || "0x1p-1"
		121   | "-0x1p-1"          | "-0x1p-1"          || "-0x1p-1"
		122   | "-0x1p-1"          | "0x1p-1"           || "0x1p-1"
		123   | "0x1p-1"           | "-0x1p-1"          || "-0x1p-1"
		124   | "0x1p-1"           | "0x1p-1"           || "0x1p-1"
		125   | "-0x1p-1"          | "-0x1p+0"          || "-0x1p-1"
		126   | "-0x1p-1"          | "0x1p+0"           || "0x1p-1"
		127   | "0x1p-1"           | "-0x1p+0"          || "-0x1p-1"
		128   | "0x1p-1"           | "0x1p+0"           || "0x1p-1"
		129   | "-0x1p-1"          | "-0x1.921fb6p+2"   || "-0x1p-1"
		130   | "-0x1p-1"          | "0x1.921fb6p+2"    || "0x1p-1"
		131   | "0x1p-1"           | "-0x1.921fb6p+2"   || "-0x1p-1"
		132   | "0x1p-1"           | "0x1.921fb6p+2"    || "0x1p-1"
		133   | "-0x1p-1"          | "-0x1.fffffep+127" || "-0x1p-1"
		134   | "-0x1p-1"          | "0x1.fffffep+127"  || "0x1p-1"
		135   | "0x1p-1"           | "-0x1.fffffep+127" || "-0x1p-1"
		136   | "0x1p-1"           | "0x1.fffffep+127"  || "0x1p-1"
		137   | "-0x1p-1"          | "-inf"             || "-0x1p-1"
		138   | "-0x1p-1"          | "inf"              || "0x1p-1"
		139   | "0x1p-1"           | "-inf"             || "-0x1p-1"
		140   | "0x1p-1"           | "inf"              || "0x1p-1"
		141   | "-0x1p-1"          | "-nan"             || "-0x1p-1"
		142   | "-0x1p-1"          | "nan"              || "0x1p-1"
		143   | "0x1p-1"           | "-nan"             || "-0x1p-1"
		144   | "0x1p-1"           | "nan"              || "0x1p-1"
		145   | "-0x1p+0"          | "-0x0p+0"          || "-0x1p+0"
		146   | "-0x1p+0"          | "0x0p+0"           || "0x1p+0"
		147   | "0x1p+0"           | "-0x0p+0"          || "-0x1p+0"
		148   | "0x1p+0"           | "0x0p+0"           || "0x1p+0"
		149   | "-0x1p+0"          | "-0x1p-149"        || "-0x1p+0"
		150   | "-0x1p+0"          | "0x1p-149"         || "0x1p+0"
		151   | "0x1p+0"           | "-0x1p-149"        || "-0x1p+0"
		152   | "0x1p+0"           | "0x1p-149"         || "0x1p+0"
		153   | "-0x1p+0"          | "-0x1p-126"        || "-0x1p+0"
		154   | "-0x1p+0"          | "0x1p-126"         || "0x1p+0"
		155   | "0x1p+0"           | "-0x1p-126"        || "-0x1p+0"
		156   | "0x1p+0"           | "0x1p-126"         || "0x1p+0"
		157   | "-0x1p+0"          | "-0x1p-1"          || "-0x1p+0"
		158   | "-0x1p+0"          | "0x1p-1"           || "0x1p+0"
		159   | "0x1p+0"           | "-0x1p-1"          || "-0x1p+0"
		160   | "0x1p+0"           | "0x1p-1"           || "0x1p+0"
		161   | "-0x1p+0"          | "-0x1p+0"          || "-0x1p+0"
		162   | "-0x1p+0"          | "0x1p+0"           || "0x1p+0"
		163   | "0x1p+0"           | "-0x1p+0"          || "-0x1p+0"
		164   | "0x1p+0"           | "0x1p+0"           || "0x1p+0"
		165   | "-0x1p+0"          | "-0x1.921fb6p+2"   || "-0x1p+0"
		166   | "-0x1p+0"          | "0x1.921fb6p+2"    || "0x1p+0"
		167   | "0x1p+0"           | "-0x1.921fb6p+2"   || "-0x1p+0"
		168   | "0x1p+0"           | "0x1.921fb6p+2"    || "0x1p+0"
		169   | "-0x1p+0"          | "-0x1.fffffep+127" || "-0x1p+0"
		170   | "-0x1p+0"          | "0x1.fffffep+127"  || "0x1p+0"
		171   | "0x1p+0"           | "-0x1.fffffep+127" || "-0x1p+0"
		172   | "0x1p+0"           | "0x1.fffffep+127"  || "0x1p+0"
		173   | "-0x1p+0"          | "-inf"             || "-0x1p+0"
		174   | "-0x1p+0"          | "inf"              || "0x1p+0"
		175   | "0x1p+0"           | "-inf"             || "-0x1p+0"
		176   | "0x1p+0"           | "inf"              || "0x1p+0"
		177   | "-0x1p+0"          | "-nan"             || "-0x1p+0"
		178   | "-0x1p+0"          | "nan"              || "0x1p+0"
		179   | "0x1p+0"           | "-nan"             || "-0x1p+0"
		180   | "0x1p+0"           | "nan"              || "0x1p+0"
		181   | "-0x1.921fb6p+2"   | "-0x0p+0"          || "-0x1.921fb6p+2"
		182   | "-0x1.921fb6p+2"   | "0x0p+0"           || "0x1.921fb6p+2"
		183   | "0x1.921fb6p+2"    | "-0x0p+0"          || "-0x1.921fb6p+2"
		184   | "0x1.921fb6p+2"    | "0x0p+0"           || "0x1.921fb6p+2"
		185   | "-0x1.921fb6p+2"   | "-0x1p-149"        || "-0x1.921fb6p+2"
		186   | "-0x1.921fb6p+2"   | "0x1p-149"         || "0x1.921fb6p+2"
		187   | "0x1.921fb6p+2"    | "-0x1p-149"        || "-0x1.921fb6p+2"
		188   | "0x1.921fb6p+2"    | "0x1p-149"         || "0x1.921fb6p+2"
		189   | "-0x1.921fb6p+2"   | "-0x1p-126"        || "-0x1.921fb6p+2"
		190   | "-0x1.921fb6p+2"   | "0x1p-126"         || "0x1.921fb6p+2"
		191   | "0x1.921fb6p+2"    | "-0x1p-126"        || "-0x1.921fb6p+2"
		192   | "0x1.921fb6p+2"    | "0x1p-126"         || "0x1.921fb6p+2"
		193   | "-0x1.921fb6p+2"   | "-0x1p-1"          || "-0x1.921fb6p+2"
		194   | "-0x1.921fb6p+2"   | "0x1p-1"           || "0x1.921fb6p+2"
		195   | "0x1.921fb6p+2"    | "-0x1p-1"          || "-0x1.921fb6p+2"
		196   | "0x1.921fb6p+2"    | "0x1p-1"           || "0x1.921fb6p+2"
		197   | "-0x1.921fb6p+2"   | "-0x1p+0"          || "-0x1.921fb6p+2"
		198   | "-0x1.921fb6p+2"   | "0x1p+0"           || "0x1.921fb6p+2"
		199   | "0x1.921fb6p+2"    | "-0x1p+0"          || "-0x1.921fb6p+2"
		200   | "0x1.921fb6p+2"    | "0x1p+0"           || "0x1.921fb6p+2"
		201   | "-0x1.921fb6p+2"   | "-0x1.921fb6p+2"   || "-0x1.921fb6p+2"
		202   | "-0x1.921fb6p+2"   | "0x1.921fb6p+2"    || "0x1.921fb6p+2"
		203   | "0x1.921fb6p+2"    | "-0x1.921fb6p+2"   || "-0x1.921fb6p+2"
		204   | "0x1.921fb6p+2"    | "0x1.921fb6p+2"    || "0x1.921fb6p+2"
		205   | "-0x1.921fb6p+2"   | "-0x1.fffffep+127" || "-0x1.921fb6p+2"
		206   | "-0x1.921fb6p+2"   | "0x1.fffffep+127"  || "0x1.921fb6p+2"
		207   | "0x1.921fb6p+2"    | "-0x1.fffffep+127" || "-0x1.921fb6p+2"
		208   | "0x1.921fb6p+2"    | "0x1.fffffep+127"  || "0x1.921fb6p+2"
		209   | "-0x1.921fb6p+2"   | "-inf"             || "-0x1.921fb6p+2"
		210   | "-0x1.921fb6p+2"   | "inf"              || "0x1.921fb6p+2"
		211   | "0x1.921fb6p+2"    | "-inf"             || "-0x1.921fb6p+2"
		212   | "0x1.921fb6p+2"    | "inf"              || "0x1.921fb6p+2"
		213   | "-0x1.921fb6p+2"   | "-nan"             || "-0x1.921fb6p+2"
		214   | "-0x1.921fb6p+2"   | "nan"              || "0x1.921fb6p+2"
		215   | "0x1.921fb6p+2"    | "-nan"             || "-0x1.921fb6p+2"
		216   | "0x1.921fb6p+2"    | "nan"              || "0x1.921fb6p+2"
		217   | "-0x1.fffffep+127" | "-0x0p+0"          || "-0x1.fffffep+127"
		218   | "-0x1.fffffep+127" | "0x0p+0"           || "0x1.fffffep+127"
		219   | "0x1.fffffep+127"  | "-0x0p+0"          || "-0x1.fffffep+127"
		220   | "0x1.fffffep+127"  | "0x0p+0"           || "0x1.fffffep+127"
		221   | "-0x1.fffffep+127" | "-0x1p-149"        || "-0x1.fffffep+127"
		222   | "-0x1.fffffep+127" | "0x1p-149"         || "0x1.fffffep+127"
		223   | "0x1.fffffep+127"  | "-0x1p-149"        || "-0x1.fffffep+127"
		224   | "0x1.fffffep+127"  | "0x1p-149"         || "0x1.fffffep+127"
		225   | "-0x1.fffffep+127" | "-0x1p-126"        || "-0x1.fffffep+127"
		226   | "-0x1.fffffep+127" | "0x1p-126"         || "0x1.fffffep+127"
		227   | "0x1.fffffep+127"  | "-0x1p-126"        || "-0x1.fffffep+127"
		228   | "0x1.fffffep+127"  | "0x1p-126"         || "0x1.fffffep+127"
		229   | "-0x1.fffffep+127" | "-0x1p-1"          || "-0x1.fffffep+127"
		230   | "-0x1.fffffep+127" | "0x1p-1"           || "0x1.fffffep+127"
		231   | "0x1.fffffep+127"  | "-0x1p-1"          || "-0x1.fffffep+127"
		232   | "0x1.fffffep+127"  | "0x1p-1"           || "0x1.fffffep+127"
		233   | "-0x1.fffffep+127" | "-0x1p+0"          || "-0x1.fffffep+127"
		234   | "-0x1.fffffep+127" | "0x1p+0"           || "0x1.fffffep+127"
		235   | "0x1.fffffep+127"  | "-0x1p+0"          || "-0x1.fffffep+127"
		236   | "0x1.fffffep+127"  | "0x1p+0"           || "0x1.fffffep+127"
		237   | "-0x1.fffffep+127" | "-0x1.921fb6p+2"   || "-0x1.fffffep+127"
		238   | "-0x1.fffffep+127" | "0x1.921fb6p+2"    || "0x1.fffffep+127"
		239   | "0x1.fffffep+127"  | "-0x1.921fb6p+2"   || "-0x1.fffffep+127"
		240   | "0x1.fffffep+127"  | "0x1.921fb6p+2"    || "0x1.fffffep+127"
		241   | "-0x1.fffffep+127" | "-0x1.fffffep+127" || "-0x1.fffffep+127"
		242   | "-0x1.fffffep+127" | "0x1.fffffep+127"  || "0x1.fffffep+127"
		243   | "0x1.fffffep+127"  | "-0x1.fffffep+127" || "-0x1.fffffep+127"
		244   | "0x1.fffffep+127"  | "0x1.fffffep+127"  || "0x1.fffffep+127"
		245   | "-0x1.fffffep+127" | "-inf"             || "-0x1.fffffep+127"
		246   | "-0x1.fffffep+127" | "inf"              || "0x1.fffffep+127"
		247   | "0x1.fffffep+127"  | "-inf"             || "-0x1.fffffep+127"
		248   | "0x1.fffffep+127"  | "inf"              || "0x1.fffffep+127"
		249   | "-0x1.fffffep+127" | "-nan"             || "-0x1.fffffep+127"
		250   | "-0x1.fffffep+127" | "nan"              || "0x1.fffffep+127"
		251   | "0x1.fffffep+127"  | "-nan"             || "-0x1.fffffep+127"
		252   | "0x1.fffffep+127"  | "nan"              || "0x1.fffffep+127"
		253   | "-inf"             | "-0x0p+0"          || "-inf"
		254   | "-inf"             | "0x0p+0"           || "inf"
		255   | "inf"              | "-0x0p+0"          || "-inf"
		256   | "inf"              | "0x0p+0"           || "inf"
		257   | "-inf"             | "-0x1p-149"        || "-inf"
		258   | "-inf"             | "0x1p-149"         || "inf"
		259   | "inf"              | "-0x1p-149"        || "-inf"
		260   | "inf"              | "0x1p-149"         || "inf"
		261   | "-inf"             | "-0x1p-126"        || "-inf"
		262   | "-inf"             | "0x1p-126"         || "inf"
		263   | "inf"              | "-0x1p-126"        || "-inf"
		264   | "inf"              | "0x1p-126"         || "inf"
		265   | "-inf"             | "-0x1p-1"          || "-inf"
		266   | "-inf"             | "0x1p-1"           || "inf"
		267   | "inf"              | "-0x1p-1"          || "-inf"
		268   | "inf"              | "0x1p-1"           || "inf"
		269   | "-inf"             | "-0x1p+0"          || "-inf"
		270   | "-inf"             | "0x1p+0"           || "inf"
		271   | "inf"              | "-0x1p+0"          || "-inf"
		272   | "inf"              | "0x1p+0"           || "inf"
		273   | "-inf"             | "-0x1.921fb6p+2"   || "-inf"
		274   | "-inf"             | "0x1.921fb6p+2"    || "inf"
		275   | "inf"              | "-0x1.921fb6p+2"   || "-inf"
		276   | "inf"              | "0x1.921fb6p+2"    || "inf"
		277   | "-inf"             | "-0x1.fffffep+127" || "-inf"
		278   | "-inf"             | "0x1.fffffep+127"  || "inf"
		279   | "inf"              | "-0x1.fffffep+127" || "-inf"
		280   | "inf"              | "0x1.fffffep+127"  || "inf"
		281   | "-inf"             | "-inf"             || "-inf"
		282   | "-inf"             | "inf"              || "inf"
		283   | "inf"              | "-inf"             || "-inf"
		284   | "inf"              | "inf"              || "inf"
		285   | "-inf"             | "-nan"             || "-inf"
		286   | "-inf"             | "nan"              || "inf"
		287   | "inf"              | "-nan"             || "-inf"
		288   | "inf"              | "nan"              || "inf"
		289   | "-nan"             | "-0x0p+0"          || "-nan"
		290   | "-nan"             | "0x0p+0"           || "nan"
		291   | "nan"              | "-0x0p+0"          || "-nan"
		292   | "nan"              | "0x0p+0"           || "nan"
		293   | "-nan"             | "-0x1p-149"        || "-nan"
		294   | "-nan"             | "0x1p-149"         || "nan"
		295   | "nan"              | "-0x1p-149"        || "-nan"
		296   | "nan"              | "0x1p-149"         || "nan"
		297   | "-nan"             | "-0x1p-126"        || "-nan"
		298   | "-nan"             | "0x1p-126"         || "nan"
		299   | "nan"              | "-0x1p-126"        || "-nan"
		300   | "nan"              | "0x1p-126"         || "nan"
		301   | "-nan"             | "-0x1p-1"          || "-nan"
		302   | "-nan"             | "0x1p-1"           || "nan"
		303   | "nan"              | "-0x1p-1"          || "-nan"
		304   | "nan"              | "0x1p-1"           || "nan"
		305   | "-nan"             | "-0x1p+0"          || "-nan"
		306   | "-nan"             | "0x1p+0"           || "nan"
		307   | "nan"              | "-0x1p+0"          || "-nan"
		308   | "nan"              | "0x1p+0"           || "nan"
		309   | "-nan"             | "-0x1.921fb6p+2"   || "-nan"
		310   | "-nan"             | "0x1.921fb6p+2"    || "nan"
		311   | "nan"              | "-0x1.921fb6p+2"   || "-nan"
		312   | "nan"              | "0x1.921fb6p+2"    || "nan"
		313   | "-nan"             | "-0x1.fffffep+127" || "-nan"
		314   | "-nan"             | "0x1.fffffep+127"  || "nan"
		315   | "nan"              | "-0x1.fffffep+127" || "-nan"
		316   | "nan"              | "0x1.fffffep+127"  || "nan"
		317   | "-nan"             | "-inf"             || "-nan"
		318   | "-nan"             | "inf"              || "nan"
		319   | "nan"              | "-inf"             || "-nan"
		320   | "nan"              | "inf"              || "nan"
		321   | "-nan"             | "-nan"             || "-nan"
		322   | "-nan"             | "nan"              || "nan"
		323   | "nan"              | "-nan"             || "-nan"
		324   | "nan"              | "nan"              || "nan"
	}

	def "Execute F32_copysign throws exception on incorrect Type on second param "() {
		setup: " a value of F32 value and and illegal I64 value"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F32(3));  // value 1
		instance.stack().push(new I64(567)); // wrong type

		F32_copysign function = new F32_copysign(instance);

		when: "run the opcode"
		function.execute();

		then: "Verify thrown exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2 type is incorrect. ");
		exception.message.contains("Value should be of type '" + inputType + "'. ");
		exception.message.contains("The input type is 'I64'.");
		exception.message.contains("The input value is '");
		exception.getUuid().toString().contains("316c2f0b-0a48-42d9-89a7-d7863bb9af3f");
	}


	def "Execute F32_copysign throws exception on incorrect Type on first param "() {
		setup: " a value of F32 value and and illegal I64 value"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(567)); // Value 1 wrong type
		instance.stack().push(new F32(3));  // value 2

		F32_copysign function = new F32_copysign(instance);

		when: "run the opcode"
		function.execute();

		then: "Verify thrown exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1 type is incorrect. ");
		exception.message.contains("Value should be of type '" + inputType + "'. ");
		exception.message.contains("The input type is 'I64'.");
		exception.message.contains("The input value is '");
		exception.getUuid().toString().contains("73b380b9-23ed-44cf-9b96-f224922415fd");
	}
}
