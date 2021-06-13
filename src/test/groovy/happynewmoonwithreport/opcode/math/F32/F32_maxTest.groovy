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
package happynewmoonwithreport.opcode.math.F32

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.opcode.math.f32.F32_max
import happynewmoonwithreport.type.F32
import happynewmoonwithreport.type.I64
import spock.lang.Specification

/**
 * Test F32_max opcode.
 * <p>
 * Created on 2021-06-13
 */
class F32_maxTest extends Specification {
	String inputType;
	String returnType;

	void setup() {
		inputType = "F32";
		returnType = "F32"
	}

	void cleanup() {
	}

	/**
	 * F32_max unit test.
	 * @param count What line of parameters is executing. Only used for debugging.
	 * @param val1 The test value.   The input for the opcode.
	 * @param val1 The test value.   The input for the opcode.
	 * @param expected The expected value.  What the opcode should return.
	 * @return None.
	 */
	def "Execute F32_max with #count -> #val1 | #val2 || #expected "(Integer count, Float val1, Float val2, Float expected) {
		setup: " push two values on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F32(val1));
		instance.stack().push(new F32(val2));

		F32_max opcode = new F32_max(instance);

		when: "run the opcode"
		opcode.execute();

		then: " verify results"
		F32 result = instance.stack().pop();

		then: " verify result equals value of expected"
		F32.valueOf(expected) == result

		where: "val1  val2 returns #expected"
		count | val1 | val2 || expected
		1     | 8.0  | 4.0  || 8.0
		2     | 300  | 256  || 300
		3     | -10  | -6   || -6
		4     | 0    | 0    || 0
		5     | 1.0  | 2.0  || 2
	}

	/**
	 * F32_max unit test.
	 * <p>
	 * <a href="https://github.com/WebAssembly/spec/blob/7526564b56c30250b66504fe795e9c1e88a938af/test/core/f32.wast">
	 *     Official Web Assembly test code.
	 * </a>
	 * @param val1_s The test value.   The input for the opcode.
	 * @param expected The expected value.  What the opcode should return.
	 * @return None.
	 */
	def "Execute F32 max #count | #val1_s | #val2_s  || #expected"(Integer count, String val1_s, String val2_s, String expected) {
		setup: " push two values on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(F32.valueOf(val1_s));
		instance.stack().push(F32.valueOf(val2_s));

		F32_max opcode = new F32_max(instance);

		when: "run the opcode"
		opcode.execute();

		then: " verify result equals value of expected"
		F32 result = instance.stack().pop();
		F32.valueOf(expected) == result

		where: "#val1 max #val2 returns #expected"
		count | val1_s             | val2_s             || expected
		1     | "-0x0p+0"          | "-0x0p+0"          || "-0x0p+0"
		2     | "-0x0p+0"          | "0x0p+0"           || "0x0p+0"
		3     | "0x0p+0"           | "-0x0p+0"          || "0x0p+0"
		4     | "0x0p+0"           | "0x0p+0"           || "0x0p+0"
		5     | "-0x0p+0"          | "-0x1p-149"        || "-0x0p+0"
		6     | "-0x0p+0"          | "0x1p-149"         || "0x1p-149"
		7     | "0x0p+0"           | "-0x1p-149"        || "0x0p+0"
		8     | "0x0p+0"           | "0x1p-149"         || "0x1p-149"
		9     | "-0x0p+0"          | "-0x1p-126"        || "-0x0p+0"
		10    | "-0x0p+0"          | "0x1p-126"         || "0x1p-126"
		11    | "0x0p+0"           | "-0x1p-126"        || "0x0p+0"
		12    | "0x0p+0"           | "0x1p-126"         || "0x1p-126"
		13    | "-0x0p+0"          | "-0x1p-1"          || "-0x0p+0"
		14    | "-0x0p+0"          | "0x1p-1"           || "0x1p-1"
		15    | "0x0p+0"           | "-0x1p-1"          || "0x0p+0"
		16    | "0x0p+0"           | "0x1p-1"           || "0x1p-1"
		17    | "-0x0p+0"          | "-0x1p+0"          || "-0x0p+0"
		18    | "-0x0p+0"          | "0x1p+0"           || "0x1p+0"
		19    | "0x0p+0"           | "-0x1p+0"          || "0x0p+0"
		20    | "0x0p+0"           | "0x1p+0"           || "0x1p+0"
		21    | "-0x0p+0"          | "-0x1.921fb6p+2"   || "-0x0p+0"
		22    | "-0x0p+0"          | "0x1.921fb6p+2"    || "0x1.921fb6p+2"
		23    | "0x0p+0"           | "-0x1.921fb6p+2"   || "0x0p+0"
		24    | "0x0p+0"           | "0x1.921fb6p+2"    || "0x1.921fb6p+2"
		25    | "-0x0p+0"          | "-0x1.fffffep+127" || "-0x0p+0"
		26    | "-0x0p+0"          | "0x1.fffffep+127"  || "0x1.fffffep+127"
		27    | "0x0p+0"           | "-0x1.fffffep+127" || "0x0p+0"
		28    | "0x0p+0"           | "0x1.fffffep+127"  || "0x1.fffffep+127"
		29    | "-0x0p+0"          | "-inf"             || "-0x0p+0"
		30    | "-0x0p+0"          | "inf"              || "inf"
		31    | "0x0p+0"           | "-inf"             || "0x0p+0"
		32    | "0x0p+0"           | "inf"              || "inf"
		33    | "-0x0p+0"          | "-nan"             || "nan:canonical"
		34    | "-0x0p+0"          | "-nan:0x200000"    || "nan:arithmetic"
		35    | "-0x0p+0"          | "nan"              || "nan:canonical"
		36    | "-0x0p+0"          | "nan:0x200000"     || "nan:arithmetic"
		37    | "0x0p+0"           | "-nan"             || "nan:canonical"
		38    | "0x0p+0"           | "-nan:0x200000"    || "nan:arithmetic"
		39    | "0x0p+0"           | "nan"              || "nan:canonical"
		40    | "0x0p+0"           | "nan:0x200000"     || "nan:arithmetic"
		41    | "-0x1p-149"        | "-0x0p+0"          || "-0x0p+0"
		42    | "-0x1p-149"        | "0x0p+0"           || "0x0p+0"
		43    | "0x1p-149"         | "-0x0p+0"          || "0x1p-149"
		44    | "0x1p-149"         | "0x0p+0"           || "0x1p-149"
		45    | "-0x1p-149"        | "-0x1p-149"        || "-0x1p-149"
		46    | "-0x1p-149"        | "0x1p-149"         || "0x1p-149"
		47    | "0x1p-149"         | "-0x1p-149"        || "0x1p-149"
		48    | "0x1p-149"         | "0x1p-149"         || "0x1p-149"
		49    | "-0x1p-149"        | "-0x1p-126"        || "-0x1p-149"
		50    | "-0x1p-149"        | "0x1p-126"         || "0x1p-126"
		51    | "0x1p-149"         | "-0x1p-126"        || "0x1p-149"
		52    | "0x1p-149"         | "0x1p-126"         || "0x1p-126"
		53    | "-0x1p-149"        | "-0x1p-1"          || "-0x1p-149"
		54    | "-0x1p-149"        | "0x1p-1"           || "0x1p-1"
		55    | "0x1p-149"         | "-0x1p-1"          || "0x1p-149"
		56    | "0x1p-149"         | "0x1p-1"           || "0x1p-1"
		57    | "-0x1p-149"        | "-0x1p+0"          || "-0x1p-149"
		58    | "-0x1p-149"        | "0x1p+0"           || "0x1p+0"
		59    | "0x1p-149"         | "-0x1p+0"          || "0x1p-149"
		60    | "0x1p-149"         | "0x1p+0"           || "0x1p+0"
		61    | "-0x1p-149"        | "-0x1.921fb6p+2"   || "-0x1p-149"
		62    | "-0x1p-149"        | "0x1.921fb6p+2"    || "0x1.921fb6p+2"
		63    | "0x1p-149"         | "-0x1.921fb6p+2"   || "0x1p-149"
		64    | "0x1p-149"         | "0x1.921fb6p+2"    || "0x1.921fb6p+2"
		65    | "-0x1p-149"        | "-0x1.fffffep+127" || "-0x1p-149"
		66    | "-0x1p-149"        | "0x1.fffffep+127"  || "0x1.fffffep+127"
		67    | "0x1p-149"         | "-0x1.fffffep+127" || "0x1p-149"
		68    | "0x1p-149"         | "0x1.fffffep+127"  || "0x1.fffffep+127"
		69    | "-0x1p-149"        | "-inf"             || "-0x1p-149"
		70    | "-0x1p-149"        | "inf"              || "inf"
		71    | "0x1p-149"         | "-inf"             || "0x1p-149"
		72    | "0x1p-149"         | "inf"              || "inf"
		73    | "-0x1p-149"        | "-nan"             || "nan:canonical"
		74    | "-0x1p-149"        | "-nan:0x200000"    || "nan:arithmetic"
		75    | "-0x1p-149"        | "nan"              || "nan:canonical"
		76    | "-0x1p-149"        | "nan:0x200000"     || "nan:arithmetic"
		77    | "0x1p-149"         | "-nan"             || "nan:canonical"
		78    | "0x1p-149"         | "-nan:0x200000"    || "nan:arithmetic"
		79    | "0x1p-149"         | "nan"              || "nan:canonical"
		80    | "0x1p-149"         | "nan:0x200000"     || "nan:arithmetic"
		81    | "-0x1p-126"        | "-0x0p+0"          || "-0x0p+0"
		82    | "-0x1p-126"        | "0x0p+0"           || "0x0p+0"
		83    | "0x1p-126"         | "-0x0p+0"          || "0x1p-126"
		84    | "0x1p-126"         | "0x0p+0"           || "0x1p-126"
		85    | "-0x1p-126"        | "-0x1p-149"        || "-0x1p-149"
		86    | "-0x1p-126"        | "0x1p-149"         || "0x1p-149"
		87    | "0x1p-126"         | "-0x1p-149"        || "0x1p-126"
		88    | "0x1p-126"         | "0x1p-149"         || "0x1p-126"
		89    | "-0x1p-126"        | "-0x1p-126"        || "-0x1p-126"
		90    | "-0x1p-126"        | "0x1p-126"         || "0x1p-126"
		91    | "0x1p-126"         | "-0x1p-126"        || "0x1p-126"
		92    | "0x1p-126"         | "0x1p-126"         || "0x1p-126"
		93    | "-0x1p-126"        | "-0x1p-1"          || "-0x1p-126"
		94    | "-0x1p-126"        | "0x1p-1"           || "0x1p-1"
		95    | "0x1p-126"         | "-0x1p-1"          || "0x1p-126"
		96    | "0x1p-126"         | "0x1p-1"           || "0x1p-1"
		97    | "-0x1p-126"        | "-0x1p+0"          || "-0x1p-126"
		98    | "-0x1p-126"        | "0x1p+0"           || "0x1p+0"
		99    | "0x1p-126"         | "-0x1p+0"          || "0x1p-126"
		100   | "0x1p-126"         | "0x1p+0"           || "0x1p+0"
		101   | "-0x1p-126"        | "-0x1.921fb6p+2"   || "-0x1p-126"
		102   | "-0x1p-126"        | "0x1.921fb6p+2"    || "0x1.921fb6p+2"
		103   | "0x1p-126"         | "-0x1.921fb6p+2"   || "0x1p-126"
		104   | "0x1p-126"         | "0x1.921fb6p+2"    || "0x1.921fb6p+2"
		105   | "-0x1p-126"        | "-0x1.fffffep+127" || "-0x1p-126"
		106   | "-0x1p-126"        | "0x1.fffffep+127"  || "0x1.fffffep+127"
		107   | "0x1p-126"         | "-0x1.fffffep+127" || "0x1p-126"
		108   | "0x1p-126"         | "0x1.fffffep+127"  || "0x1.fffffep+127"
		109   | "-0x1p-126"        | "-inf"             || "-0x1p-126"
		110   | "-0x1p-126"        | "inf"              || "inf"
		111   | "0x1p-126"         | "-inf"             || "0x1p-126"
		112   | "0x1p-126"         | "inf"              || "inf"
		113   | "-0x1p-126"        | "-nan"             || "nan:canonical"
		114   | "-0x1p-126"        | "-nan:0x200000"    || "nan:arithmetic"
		115   | "-0x1p-126"        | "nan"              || "nan:canonical"
		116   | "-0x1p-126"        | "nan:0x200000"     || "nan:arithmetic"
		117   | "0x1p-126"         | "-nan"             || "nan:canonical"
		118   | "0x1p-126"         | "-nan:0x200000"    || "nan:arithmetic"
		119   | "0x1p-126"         | "nan"              || "nan:canonical"
		120   | "0x1p-126"         | "nan:0x200000"     || "nan:arithmetic"
		121   | "-0x1p-1"          | "-0x0p+0"          || "-0x0p+0"
		122   | "-0x1p-1"          | "0x0p+0"           || "0x0p+0"
		123   | "0x1p-1"           | "-0x0p+0"          || "0x1p-1"
		124   | "0x1p-1"           | "0x0p+0"           || "0x1p-1"
		125   | "-0x1p-1"          | "-0x1p-149"        || "-0x1p-149"
		126   | "-0x1p-1"          | "0x1p-149"         || "0x1p-149"
		127   | "0x1p-1"           | "-0x1p-149"        || "0x1p-1"
		128   | "0x1p-1"           | "0x1p-149"         || "0x1p-1"
		129   | "-0x1p-1"          | "-0x1p-126"        || "-0x1p-126"
		130   | "-0x1p-1"          | "0x1p-126"         || "0x1p-126"
		131   | "0x1p-1"           | "-0x1p-126"        || "0x1p-1"
		132   | "0x1p-1"           | "0x1p-126"         || "0x1p-1"
		133   | "-0x1p-1"          | "-0x1p-1"          || "-0x1p-1"
		134   | "-0x1p-1"          | "0x1p-1"           || "0x1p-1"
		135   | "0x1p-1"           | "-0x1p-1"          || "0x1p-1"
		136   | "0x1p-1"           | "0x1p-1"           || "0x1p-1"
		137   | "-0x1p-1"          | "-0x1p+0"          || "-0x1p-1"
		138   | "-0x1p-1"          | "0x1p+0"           || "0x1p+0"
		139   | "0x1p-1"           | "-0x1p+0"          || "0x1p-1"
		140   | "0x1p-1"           | "0x1p+0"           || "0x1p+0"
		141   | "-0x1p-1"          | "-0x1.921fb6p+2"   || "-0x1p-1"
		142   | "-0x1p-1"          | "0x1.921fb6p+2"    || "0x1.921fb6p+2"
		143   | "0x1p-1"           | "-0x1.921fb6p+2"   || "0x1p-1"
		144   | "0x1p-1"           | "0x1.921fb6p+2"    || "0x1.921fb6p+2"
		145   | "-0x1p-1"          | "-0x1.fffffep+127" || "-0x1p-1"
		146   | "-0x1p-1"          | "0x1.fffffep+127"  || "0x1.fffffep+127"
		147   | "0x1p-1"           | "-0x1.fffffep+127" || "0x1p-1"
		148   | "0x1p-1"           | "0x1.fffffep+127"  || "0x1.fffffep+127"
		149   | "-0x1p-1"          | "-inf"             || "-0x1p-1"
		150   | "-0x1p-1"          | "inf"              || "inf"
		151   | "0x1p-1"           | "-inf"             || "0x1p-1"
		152   | "0x1p-1"           | "inf"              || "inf"
		153   | "-0x1p-1"          | "-nan"             || "nan:canonical"
		154   | "-0x1p-1"          | "-nan:0x200000"    || "nan:arithmetic"
		155   | "-0x1p-1"          | "nan"              || "nan:canonical"
		156   | "-0x1p-1"          | "nan:0x200000"     || "nan:arithmetic"
		157   | "0x1p-1"           | "-nan"             || "nan:canonical"
		158   | "0x1p-1"           | "-nan:0x200000"    || "nan:arithmetic"
		159   | "0x1p-1"           | "nan"              || "nan:canonical"
		160   | "0x1p-1"           | "nan:0x200000"     || "nan:arithmetic"
		161   | "-0x1p+0"          | "-0x0p+0"          || "-0x0p+0"
		162   | "-0x1p+0"          | "0x0p+0"           || "0x0p+0"
		163   | "0x1p+0"           | "-0x0p+0"          || "0x1p+0"
		164   | "0x1p+0"           | "0x0p+0"           || "0x1p+0"
		165   | "-0x1p+0"          | "-0x1p-149"        || "-0x1p-149"
		166   | "-0x1p+0"          | "0x1p-149"         || "0x1p-149"
		167   | "0x1p+0"           | "-0x1p-149"        || "0x1p+0"
		168   | "0x1p+0"           | "0x1p-149"         || "0x1p+0"
		169   | "-0x1p+0"          | "-0x1p-126"        || "-0x1p-126"
		170   | "-0x1p+0"          | "0x1p-126"         || "0x1p-126"
		171   | "0x1p+0"           | "-0x1p-126"        || "0x1p+0"
		172   | "0x1p+0"           | "0x1p-126"         || "0x1p+0"
		173   | "-0x1p+0"          | "-0x1p-1"          || "-0x1p-1"
		174   | "-0x1p+0"          | "0x1p-1"           || "0x1p-1"
		175   | "0x1p+0"           | "-0x1p-1"          || "0x1p+0"
		176   | "0x1p+0"           | "0x1p-1"           || "0x1p+0"
		177   | "-0x1p+0"          | "-0x1p+0"          || "-0x1p+0"
		178   | "-0x1p+0"          | "0x1p+0"           || "0x1p+0"
		179   | "0x1p+0"           | "-0x1p+0"          || "0x1p+0"
		180   | "0x1p+0"           | "0x1p+0"           || "0x1p+0"
		181   | "-0x1p+0"          | "-0x1.921fb6p+2"   || "-0x1p+0"
		182   | "-0x1p+0"          | "0x1.921fb6p+2"    || "0x1.921fb6p+2"
		183   | "0x1p+0"           | "-0x1.921fb6p+2"   || "0x1p+0"
		184   | "0x1p+0"           | "0x1.921fb6p+2"    || "0x1.921fb6p+2"
		185   | "-0x1p+0"          | "-0x1.fffffep+127" || "-0x1p+0"
		186   | "-0x1p+0"          | "0x1.fffffep+127"  || "0x1.fffffep+127"
		187   | "0x1p+0"           | "-0x1.fffffep+127" || "0x1p+0"
		188   | "0x1p+0"           | "0x1.fffffep+127"  || "0x1.fffffep+127"
		189   | "-0x1p+0"          | "-inf"             || "-0x1p+0"
		190   | "-0x1p+0"          | "inf"              || "inf"
		191   | "0x1p+0"           | "-inf"             || "0x1p+0"
		192   | "0x1p+0"           | "inf"              || "inf"
		193   | "-0x1p+0"          | "-nan"             || "nan:canonical"
		194   | "-0x1p+0"          | "-nan:0x200000"    || "nan:arithmetic"
		195   | "-0x1p+0"          | "nan"              || "nan:canonical"
		196   | "-0x1p+0"          | "nan:0x200000"     || "nan:arithmetic"
		197   | "0x1p+0"           | "-nan"             || "nan:canonical"
		198   | "0x1p+0"           | "-nan:0x200000"    || "nan:arithmetic"
		199   | "0x1p+0"           | "nan"              || "nan:canonical"
		200   | "0x1p+0"           | "nan:0x200000"     || "nan:arithmetic"
		201   | "-0x1.921fb6p+2"   | "-0x0p+0"          || "-0x0p+0"
		202   | "-0x1.921fb6p+2"   | "0x0p+0"           || "0x0p+0"
		203   | "0x1.921fb6p+2"    | "-0x0p+0"          || "0x1.921fb6p+2"
		204   | "0x1.921fb6p+2"    | "0x0p+0"           || "0x1.921fb6p+2"
		205   | "-0x1.921fb6p+2"   | "-0x1p-149"        || "-0x1p-149"
		206   | "-0x1.921fb6p+2"   | "0x1p-149"         || "0x1p-149"
		207   | "0x1.921fb6p+2"    | "-0x1p-149"        || "0x1.921fb6p+2"
		208   | "0x1.921fb6p+2"    | "0x1p-149"         || "0x1.921fb6p+2"
		209   | "-0x1.921fb6p+2"   | "-0x1p-126"        || "-0x1p-126"
		210   | "-0x1.921fb6p+2"   | "0x1p-126"         || "0x1p-126"
		211   | "0x1.921fb6p+2"    | "-0x1p-126"        || "0x1.921fb6p+2"
		212   | "0x1.921fb6p+2"    | "0x1p-126"         || "0x1.921fb6p+2"
		213   | "-0x1.921fb6p+2"   | "-0x1p-1"          || "-0x1p-1"
		214   | "-0x1.921fb6p+2"   | "0x1p-1"           || "0x1p-1"
		215   | "0x1.921fb6p+2"    | "-0x1p-1"          || "0x1.921fb6p+2"
		216   | "0x1.921fb6p+2"    | "0x1p-1"           || "0x1.921fb6p+2"
		217   | "-0x1.921fb6p+2"   | "-0x1p+0"          || "-0x1p+0"
		218   | "-0x1.921fb6p+2"   | "0x1p+0"           || "0x1p+0"
		219   | "0x1.921fb6p+2"    | "-0x1p+0"          || "0x1.921fb6p+2"
		220   | "0x1.921fb6p+2"    | "0x1p+0"           || "0x1.921fb6p+2"
		221   | "-0x1.921fb6p+2"   | "-0x1.921fb6p+2"   || "-0x1.921fb6p+2"
		222   | "-0x1.921fb6p+2"   | "0x1.921fb6p+2"    || "0x1.921fb6p+2"
		223   | "0x1.921fb6p+2"    | "-0x1.921fb6p+2"   || "0x1.921fb6p+2"
		224   | "0x1.921fb6p+2"    | "0x1.921fb6p+2"    || "0x1.921fb6p+2"
		225   | "-0x1.921fb6p+2"   | "-0x1.fffffep+127" || "-0x1.921fb6p+2"
		226   | "-0x1.921fb6p+2"   | "0x1.fffffep+127"  || "0x1.fffffep+127"
		227   | "0x1.921fb6p+2"    | "-0x1.fffffep+127" || "0x1.921fb6p+2"
		228   | "0x1.921fb6p+2"    | "0x1.fffffep+127"  || "0x1.fffffep+127"
		229   | "-0x1.921fb6p+2"   | "-inf"             || "-0x1.921fb6p+2"
		230   | "-0x1.921fb6p+2"   | "inf"              || "inf"
		231   | "0x1.921fb6p+2"    | "-inf"             || "0x1.921fb6p+2"
		232   | "0x1.921fb6p+2"    | "inf"              || "inf"
		233   | "-0x1.921fb6p+2"   | "-nan"             || "nan:canonical"
		234   | "-0x1.921fb6p+2"   | "-nan:0x200000"    || "nan:arithmetic"
		235   | "-0x1.921fb6p+2"   | "nan"              || "nan:canonical"
		236   | "-0x1.921fb6p+2"   | "nan:0x200000"     || "nan:arithmetic"
		237   | "0x1.921fb6p+2"    | "-nan"             || "nan:canonical"
		238   | "0x1.921fb6p+2"    | "-nan:0x200000"    || "nan:arithmetic"
		239   | "0x1.921fb6p+2"    | "nan"              || "nan:canonical"
		240   | "0x1.921fb6p+2"    | "nan:0x200000"     || "nan:arithmetic"
		241   | "-0x1.fffffep+127" | "-0x0p+0"          || "-0x0p+0"
		242   | "-0x1.fffffep+127" | "0x0p+0"           || "0x0p+0"
		243   | "0x1.fffffep+127"  | "-0x0p+0"          || "0x1.fffffep+127"
		244   | "0x1.fffffep+127"  | "0x0p+0"           || "0x1.fffffep+127"
		245   | "-0x1.fffffep+127" | "-0x1p-149"        || "-0x1p-149"
		246   | "-0x1.fffffep+127" | "0x1p-149"         || "0x1p-149"
		247   | "0x1.fffffep+127"  | "-0x1p-149"        || "0x1.fffffep+127"
		248   | "0x1.fffffep+127"  | "0x1p-149"         || "0x1.fffffep+127"
		249   | "-0x1.fffffep+127" | "-0x1p-126"        || "-0x1p-126"
		250   | "-0x1.fffffep+127" | "0x1p-126"         || "0x1p-126"
		251   | "0x1.fffffep+127"  | "-0x1p-126"        || "0x1.fffffep+127"
		252   | "0x1.fffffep+127"  | "0x1p-126"         || "0x1.fffffep+127"
		253   | "-0x1.fffffep+127" | "-0x1p-1"          || "-0x1p-1"
		254   | "-0x1.fffffep+127" | "0x1p-1"           || "0x1p-1"
		255   | "0x1.fffffep+127"  | "-0x1p-1"          || "0x1.fffffep+127"
		256   | "0x1.fffffep+127"  | "0x1p-1"           || "0x1.fffffep+127"
		257   | "-0x1.fffffep+127" | "-0x1p+0"          || "-0x1p+0"
		258   | "-0x1.fffffep+127" | "0x1p+0"           || "0x1p+0"
		259   | "0x1.fffffep+127"  | "-0x1p+0"          || "0x1.fffffep+127"
		260   | "0x1.fffffep+127"  | "0x1p+0"           || "0x1.fffffep+127"
		261   | "-0x1.fffffep+127" | "-0x1.921fb6p+2"   || "-0x1.921fb6p+2"
		262   | "-0x1.fffffep+127" | "0x1.921fb6p+2"    || "0x1.921fb6p+2"
		263   | "0x1.fffffep+127"  | "-0x1.921fb6p+2"   || "0x1.fffffep+127"
		264   | "0x1.fffffep+127"  | "0x1.921fb6p+2"    || "0x1.fffffep+127"
		265   | "-0x1.fffffep+127" | "-0x1.fffffep+127" || "-0x1.fffffep+127"
		266   | "-0x1.fffffep+127" | "0x1.fffffep+127"  || "0x1.fffffep+127"
		267   | "0x1.fffffep+127"  | "-0x1.fffffep+127" || "0x1.fffffep+127"
		268   | "0x1.fffffep+127"  | "0x1.fffffep+127"  || "0x1.fffffep+127"
		269   | "-0x1.fffffep+127" | "-inf"             || "-0x1.fffffep+127"
		270   | "-0x1.fffffep+127" | "inf"              || "inf"
		271   | "0x1.fffffep+127"  | "-inf"             || "0x1.fffffep+127"
		272   | "0x1.fffffep+127"  | "inf"              || "inf"
		273   | "-0x1.fffffep+127" | "-nan"             || "nan:canonical"
		274   | "-0x1.fffffep+127" | "-nan:0x200000"    || "nan:arithmetic"
		275   | "-0x1.fffffep+127" | "nan"              || "nan:canonical"
		276   | "-0x1.fffffep+127" | "nan:0x200000"     || "nan:arithmetic"
		277   | "0x1.fffffep+127"  | "-nan"             || "nan:canonical"
		278   | "0x1.fffffep+127"  | "-nan:0x200000"    || "nan:arithmetic"
		279   | "0x1.fffffep+127"  | "nan"              || "nan:canonical"
		280   | "0x1.fffffep+127"  | "nan:0x200000"     || "nan:arithmetic"
		281   | "-inf"             | "-0x0p+0"          || "-0x0p+0"
		282   | "-inf"             | "0x0p+0"           || "0x0p+0"
		283   | "inf"              | "-0x0p+0"          || "inf"
		284   | "inf"              | "0x0p+0"           || "inf"
		285   | "-inf"             | "-0x1p-149"        || "-0x1p-149"
		286   | "-inf"             | "0x1p-149"         || "0x1p-149"
		287   | "inf"              | "-0x1p-149"        || "inf"
		288   | "inf"              | "0x1p-149"         || "inf"
		289   | "-inf"             | "-0x1p-126"        || "-0x1p-126"
		290   | "-inf"             | "0x1p-126"         || "0x1p-126"
		291   | "inf"              | "-0x1p-126"        || "inf"
		292   | "inf"              | "0x1p-126"         || "inf"
		293   | "-inf"             | "-0x1p-1"          || "-0x1p-1"
		294   | "-inf"             | "0x1p-1"           || "0x1p-1"
		295   | "inf"              | "-0x1p-1"          || "inf"
		296   | "inf"              | "0x1p-1"           || "inf"
		297   | "-inf"             | "-0x1p+0"          || "-0x1p+0"
		298   | "-inf"             | "0x1p+0"           || "0x1p+0"
		299   | "inf"              | "-0x1p+0"          || "inf"
		300   | "inf"              | "0x1p+0"           || "inf"
		301   | "-inf"             | "-0x1.921fb6p+2"   || "-0x1.921fb6p+2"
		302   | "-inf"             | "0x1.921fb6p+2"    || "0x1.921fb6p+2"
		303   | "inf"              | "-0x1.921fb6p+2"   || "inf"
		304   | "inf"              | "0x1.921fb6p+2"    || "inf"
		305   | "-inf"             | "-0x1.fffffep+127" || "-0x1.fffffep+127"
		306   | "-inf"             | "0x1.fffffep+127"  || "0x1.fffffep+127"
		307   | "inf"              | "-0x1.fffffep+127" || "inf"
		308   | "inf"              | "0x1.fffffep+127"  || "inf"
		309   | "-inf"             | "-inf"             || "-inf"
		310   | "-inf"             | "inf"              || "inf"
		311   | "inf"              | "-inf"             || "inf"
		312   | "inf"              | "inf"              || "inf"
		313   | "-inf"             | "-nan"             || "nan:canonical"
		314   | "-inf"             | "-nan:0x200000"    || "nan:arithmetic"
		315   | "-inf"             | "nan"              || "nan:canonical"
		316   | "-inf"             | "nan:0x200000"     || "nan:arithmetic"
		317   | "inf"              | "-nan"             || "nan:canonical"
		318   | "inf"              | "-nan:0x200000"    || "nan:arithmetic"
		319   | "inf"              | "nan"              || "nan:canonical"
		320   | "inf"              | "nan:0x200000"     || "nan:arithmetic"
		321   | "-nan"             | "-0x0p+0"          || "nan:canonical"
		322   | "-nan:0x200000"    | "-0x0p+0"          || "nan:arithmetic"
		323   | "-nan"             | "0x0p+0"           || "nan:canonical"
		324   | "-nan:0x200000"    | "0x0p+0"           || "nan:arithmetic"
		325   | "nan"              | "-0x0p+0"          || "nan:canonical"
		326   | "nan:0x200000"     | "-0x0p+0"          || "nan:arithmetic"
		327   | "nan"              | "0x0p+0"           || "nan:canonical"
		328   | "nan:0x200000"     | "0x0p+0"           || "nan:arithmetic"
		329   | "-nan"             | "-0x1p-149"        || "nan:canonical"
		330   | "-nan:0x200000"    | "-0x1p-149"        || "nan:arithmetic"
		331   | "-nan"             | "0x1p-149"         || "nan:canonical"
		332   | "-nan:0x200000"    | "0x1p-149"         || "nan:arithmetic"
		333   | "nan"              | "-0x1p-149"        || "nan:canonical"
		334   | "nan:0x200000"     | "-0x1p-149"        || "nan:arithmetic"
		335   | "nan"              | "0x1p-149"         || "nan:canonical"
		336   | "nan:0x200000"     | "0x1p-149"         || "nan:arithmetic"
		337   | "-nan"             | "-0x1p-126"        || "nan:canonical"
		338   | "-nan:0x200000"    | "-0x1p-126"        || "nan:arithmetic"
		339   | "-nan"             | "0x1p-126"         || "nan:canonical"
		340   | "-nan:0x200000"    | "0x1p-126"         || "nan:arithmetic"
		341   | "nan"              | "-0x1p-126"        || "nan:canonical"
		342   | "nan:0x200000"     | "-0x1p-126"        || "nan:arithmetic"
		343   | "nan"              | "0x1p-126"         || "nan:canonical"
		344   | "nan:0x200000"     | "0x1p-126"         || "nan:arithmetic"
		345   | "-nan"             | "-0x1p-1"          || "nan:canonical"
		346   | "-nan:0x200000"    | "-0x1p-1"          || "nan:arithmetic"
		347   | "-nan"             | "0x1p-1"           || "nan:canonical"
		348   | "-nan:0x200000"    | "0x1p-1"           || "nan:arithmetic"
		349   | "nan"              | "-0x1p-1"          || "nan:canonical"
		350   | "nan:0x200000"     | "-0x1p-1"          || "nan:arithmetic"
		351   | "nan"              | "0x1p-1"           || "nan:canonical"
		352   | "nan:0x200000"     | "0x1p-1"           || "nan:arithmetic"
		353   | "-nan"             | "-0x1p+0"          || "nan:canonical"
		354   | "-nan:0x200000"    | "-0x1p+0"          || "nan:arithmetic"
		355   | "-nan"             | "0x1p+0"           || "nan:canonical"
		356   | "-nan:0x200000"    | "0x1p+0"           || "nan:arithmetic"
		357   | "nan"              | "-0x1p+0"          || "nan:canonical"
		358   | "nan:0x200000"     | "-0x1p+0"          || "nan:arithmetic"
		359   | "nan"              | "0x1p+0"           || "nan:canonical"
		360   | "nan:0x200000"     | "0x1p+0"           || "nan:arithmetic"
		361   | "-nan"             | "-0x1.921fb6p+2"   || "nan:canonical"
		362   | "-nan:0x200000"    | "-0x1.921fb6p+2"   || "nan:arithmetic"
		363   | "-nan"             | "0x1.921fb6p+2"    || "nan:canonical"
		364   | "-nan:0x200000"    | "0x1.921fb6p+2"    || "nan:arithmetic"
		365   | "nan"              | "-0x1.921fb6p+2"   || "nan:canonical"
		366   | "nan:0x200000"     | "-0x1.921fb6p+2"   || "nan:arithmetic"
		367   | "nan"              | "0x1.921fb6p+2"    || "nan:canonical"
		368   | "nan:0x200000"     | "0x1.921fb6p+2"    || "nan:arithmetic"
		369   | "-nan"             | "-0x1.fffffep+127" || "nan:canonical"
		370   | "-nan:0x200000"    | "-0x1.fffffep+127" || "nan:arithmetic"
		371   | "-nan"             | "0x1.fffffep+127"  || "nan:canonical"
		372   | "-nan:0x200000"    | "0x1.fffffep+127"  || "nan:arithmetic"
		373   | "nan"              | "-0x1.fffffep+127" || "nan:canonical"
		374   | "nan:0x200000"     | "-0x1.fffffep+127" || "nan:arithmetic"
		375   | "nan"              | "0x1.fffffep+127"  || "nan:canonical"
		376   | "nan:0x200000"     | "0x1.fffffep+127"  || "nan:arithmetic"
		377   | "-nan"             | "-inf"             || "nan:canonical"
		378   | "-nan:0x200000"    | "-inf"             || "nan:arithmetic"
		379   | "-nan"             | "inf"              || "nan:canonical"
		380   | "-nan:0x200000"    | "inf"              || "nan:arithmetic"
		381   | "nan"              | "-inf"             || "nan:canonical"
		382   | "nan:0x200000"     | "-inf"             || "nan:arithmetic"
		383   | "nan"              | "inf"              || "nan:canonical"
		384   | "nan:0x200000"     | "inf"              || "nan:arithmetic"
		385   | "-nan"             | "-nan"             || "nan:canonical"
		386   | "-nan:0x200000"    | "-nan"             || "nan:arithmetic"
		387   | "-nan"             | "-nan:0x200000"    || "nan:arithmetic"
		388   | "-nan:0x200000"    | "-nan:0x200000"    || "nan:arithmetic"
		389   | "-nan"             | "nan"              || "nan:canonical"
		390   | "-nan:0x200000"    | "nan"              || "nan:arithmetic"
		391   | "-nan"             | "nan:0x200000"     || "nan:arithmetic"
		392   | "-nan:0x200000"    | "nan:0x200000"     || "nan:arithmetic"
		393   | "nan"              | "-nan"             || "nan:canonical"
		394   | "nan:0x200000"     | "-nan"             || "nan:arithmetic"
		395   | "nan"              | "-nan:0x200000"    || "nan:arithmetic"
		396   | "nan:0x200000"     | "-nan:0x200000"    || "nan:arithmetic"
		397   | "nan"              | "nan"              || "nan:canonical"
		398   | "nan:0x200000"     | "nan"              || "nan:arithmetic"
		399   | "nan"              | "nan:0x200000"     || "nan:arithmetic"
		400   | "nan:0x200000"     | "nan:0x200000"     || "nan:arithmetic"


	}


	def "Execute F32_max throws exception on incorrect Type on second param "() {
		setup: " a value of F32  value"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F32(3));  // value 3  // correct type
		instance.stack().push(new I64(4));  // value 4  // incorrect type

		F32_max function = new F32_max(instance);

		when: "run the opcode"
		function.execute();

		then: "Verify thrown exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2 type is incorrect. ");
		exception.message.contains("Value should be of type '" + inputType + "'. ");
		exception.message.contains("The input type is 'I64'.");
		exception.message.contains("The input value is '");
		exception.getUuid().toString().contains("50a0e29a-da8b-4d6d-b9b5-b1b3c38feb37");
	}

	def "Execute F32_max throws exception on incorrect Type on first param "() {
		setup: " a value of F32  value"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(3));  // value 3  // incorrect type
		instance.stack().push(new F32(4));  // value 4  // correct type

		F32_max function = new F32_max(instance);

		when: "run the opcode"
		function.execute();

		then: "Verify thrown exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1 type is incorrect. ");
		exception.message.contains("Value should be of type '" + inputType + "'. ");
		exception.message.contains("The input type is 'I64'.");
		exception.message.contains("The input value is '");
		exception.getUuid().toString().contains("07399c82-61af-4818-a03d-cdedac2a8dd4");
	}

}
