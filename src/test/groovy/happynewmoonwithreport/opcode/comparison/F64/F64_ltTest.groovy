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
package happynewmoonwithreport.opcode.comparison.F64

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.opcode.WasmInstanceStub
import happynewmoonwithreport.type.F64
import happynewmoonwithreport.type.I32
import happynewmoonwithreport.type.I64
import spock.lang.Specification

/**
 * Created on 2020-09-13
 */
class F64_ltTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}


	def "Execute F64 less than to #val1 | #val2 || #expected "(Float val1, Float val2, Integer expected) {
		setup: " push two values on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F64(val1));
		instance.stack().push(new F64(val2));

		F64_lt opcode = new F64_lt(instance);

		when: "run the opcode"
		opcode.execute();

		then: " verify results"
		I32 result = instance.stack().pop();

		then: " verify result equals value of expected"
		new I32(expected) == result

		where: "val1 equals val2 returns #expected"
		val1 | val2 || expected
		4    | 3    || 0
		3    | 4    || 1
		4    | 4    || 0
		0    | 0    || 0
		1    | 0    || 0
		0    | 1    || 1

	}

	/**
	 * https://github.com/WebAssembly/spec/blob/7526564b56c30250b66504fe795e9c1e88a938af/test/core/f64_cmp.wast
	 * @param val1_s
	 * @param val2_s
	 * @param expected
	 * @return
	 */
	def "Execute F64 less than #count | #val1_s | #val2_s || #expected "(Integer count, String val1_s, String val2_s, Integer expected) {
		setup: " push two values on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(F64.valueOf(val1_s));
		instance.stack().push(F64.valueOf(val2_s));

		F64_lt opcode = new F64_lt(instance);

		when: "run the opcode"
		opcode.execute();
		I32 result = instance.stack().pop();

		then: " verify result equals value of expected"
		new I32(expected) == result

		where: "val1 equals val2 returns #expected"
		count | val1_s                     | val2_s                     || expected
		1     | " -0x0p+0"                 | "-0x0p+0"                  || 0
		2     | "-0x0p+0"                  | "0x0p+0"                   || 0
		3     | "0x0p+0"                   | "-0x0p+0"                  || 0
		4     | "0x0p+0"                   | "0x0p+0"                   || 0
		5     | "-0x0p+0"                  | "-0x0.0000000000001p-1022" || 0
		6     | "-0x0p+0"                  | "0x0.0000000000001p-1022"  || 1
		7     | "0x0p+0"                   | "-0x0.0000000000001p-1022" || 0
		8     | "0x0p+0"                   | "0x0.0000000000001p-1022"  || 1
		9     | "-0x0p+0"                  | "-0x1p-1022"               || 0
		10    | "-0x0p+0"                  | "0x1p-1022"                || 1
		11    | "0x0p+0"                   | "-0x1p-1022"               || 0
		12    | "0x0p+0"                   | "0x1p-1022"                || 1
		13    | "-0x0p+0"                  | "-0x1p-1"                  || 0
		14    | "-0x0p+0"                  | "0x1p-1"                   || 1
		15    | "0x0p+0"                   | "-0x1p-1"                  || 0
		16    | "0x0p+0"                   | "0x1p-1"                   || 1
		17    | "-0x0p+0"                  | "-0x1p+0"                  || 0
		18    | "-0x0p+0"                  | "0x1p+0"                   || 1
		19    | "0x0p+0"                   | "-0x1p+0"                  || 0
		20    | "0x0p+0"                   | "0x1p+0"                   || 1
		21    | "-0x0p+0"                  | "-0x1.921fb54442d18p+2"    || 0
		22    | "-0x0p+0"                  | "0x1.921fb54442d18p+2"     || 1
		23    | "0x0p+0"                   | "-0x1.921fb54442d18p+2"    || 0
		24    | "0x0p+0"                   | "0x1.921fb54442d18p+2"     || 1
		25    | "-0x0p+0"                  | "-0x1.fffffffffffffp+1023" || 0
		26    | "-0x0p+0"                  | "0x1.fffffffffffffp+1023"  || 1
		27    | "0x0p+0"                   | "-0x1.fffffffffffffp+1023" || 0
		28    | "0x0p+0"                   | "0x1.fffffffffffffp+1023"  || 1
		29    | "-0x0p+0"                  | "-inf"                     || 0
		30    | "-0x0p+0"                  | "inf"                      || 1
		31    | "0x0p+0"                   | "-inf"                     || 0
		32    | "0x0p+0"                   | "inf"                      || 1
		33    | "-0x0p+0"                  | "-nan"                     || 0
		34    | "-0x0p+0"                  | "-nan:0x4000000000000"     || 0
		35    | "-0x0p+0"                  | "nan"                      || 0
		36    | "-0x0p+0"                  | "nan:0x4000000000000"      || 0
		37    | "0x0p+0"                   | "-nan"                     || 0
		38    | "0x0p+0"                   | "-nan:0x4000000000000"     || 0
		39    | "0x0p+0"                   | "nan"                      || 0
		40    | "0x0p+0"                   | "nan:0x4000000000000"      || 0
		41    | "-0x0.0000000000001p-1022" | "-0x0p+0"                  || 1
		42    | "-0x0.0000000000001p-1022" | "0x0p+0"                   || 1
		43    | "0x0.0000000000001p-1022"  | "-0x0p+0"                  || 0
		44    | "0x0.0000000000001p-1022"  | "0x0p+0"                   || 0
		45    | "-0x0.0000000000001p-1022" | "-0x0.0000000000001p-1022" || 0
		46    | "-0x0.0000000000001p-1022" | "0x0.0000000000001p-1022"  || 1
		47    | "0x0.0000000000001p-1022"  | "-0x0.0000000000001p-1022" || 0
		48    | "0x0.0000000000001p-1022"  | "0x0.0000000000001p-1022"  || 0
		49    | "-0x0.0000000000001p-1022" | "-0x1p-1022"               || 0
		50    | "-0x0.0000000000001p-1022" | "0x1p-1022"                || 1
		51    | "0x0.0000000000001p-1022"  | "-0x1p-1022"               || 0
		52    | "0x0.0000000000001p-1022"  | "0x1p-1022"                || 1
		53    | "-0x0.0000000000001p-1022" | "-0x1p-1"                  || 0
		54    | "-0x0.0000000000001p-1022" | "0x1p-1"                   || 1
		55    | "0x0.0000000000001p-1022"  | "-0x1p-1"                  || 0
		56    | "0x0.0000000000001p-1022"  | "0x1p-1"                   || 1
		57    | "-0x0.0000000000001p-1022" | "-0x1p+0"                  || 0
		58    | "-0x0.0000000000001p-1022" | "0x1p+0"                   || 1
		59    | "0x0.0000000000001p-1022"  | "-0x1p+0"                  || 0
		60    | "0x0.0000000000001p-1022"  | "0x1p+0"                   || 1
		61    | "-0x0.0000000000001p-1022" | "-0x1.921fb54442d18p+2"    || 0
		62    | "-0x0.0000000000001p-1022" | "0x1.921fb54442d18p+2"     || 1
		63    | "0x0.0000000000001p-1022"  | "-0x1.921fb54442d18p+2"    || 0
		64    | "0x0.0000000000001p-1022"  | "0x1.921fb54442d18p+2"     || 1
		65    | "-0x0.0000000000001p-1022" | "-0x1.fffffffffffffp+1023" || 0
		66    | "-0x0.0000000000001p-1022" | "0x1.fffffffffffffp+1023"  || 1
		67    | "0x0.0000000000001p-1022"  | "-0x1.fffffffffffffp+1023" || 0
		68    | "0x0.0000000000001p-1022"  | "0x1.fffffffffffffp+1023"  || 1
		69    | "-0x0.0000000000001p-1022" | "-inf"                     || 0
		70    | "-0x0.0000000000001p-1022" | "inf"                      || 1
		71    | "0x0.0000000000001p-1022"  | "-inf"                     || 0
		72    | "0x0.0000000000001p-1022"  | "inf"                      || 1
		73    | "-0x0.0000000000001p-1022" | "-nan"                     || 0
		74    | "-0x0.0000000000001p-1022" | "-nan:0x4000000000000"     || 0
		75    | "-0x0.0000000000001p-1022" | "nan"                      || 0
		76    | "-0x0.0000000000001p-1022" | "nan:0x4000000000000"      || 0
		77    | "0x0.0000000000001p-1022"  | "-nan"                     || 0
		78    | "0x0.0000000000001p-1022"  | "-nan:0x4000000000000"     || 0
		79    | "0x0.0000000000001p-1022"  | "nan"                      || 0
		80    | "0x0.0000000000001p-1022"  | "nan:0x4000000000000"      || 0
		81    | "-0x1p-1022"               | "-0x0p+0"                  || 1
		82    | "-0x1p-1022"               | "0x0p+0"                   || 1
		83    | "0x1p-1022"                | "-0x0p+0"                  || 0
		84    | "0x1p-1022"                | "0x0p+0"                   || 0
		85    | "-0x1p-1022"               | "-0x0.0000000000001p-1022" || 1
		86    | "-0x1p-1022"               | "0x0.0000000000001p-1022"  || 1
		87    | "0x1p-1022"                | "-0x0.0000000000001p-1022" || 0
		88    | "0x1p-1022"                | "0x0.0000000000001p-1022"  || 0
		89    | "-0x1p-1022"               | "-0x1p-1022"               || 0
		90    | "-0x1p-1022"               | "0x1p-1022"                || 1
		91    | "0x1p-1022"                | "-0x1p-1022"               || 0
		92    | "0x1p-1022"                | "0x1p-1022"                || 0
		93    | "-0x1p-1022"               | "-0x1p-1"                  || 0
		94    | "-0x1p-1022"               | "0x1p-1"                   || 1
		95    | "0x1p-1022"                | "-0x1p-1"                  || 0
		96    | "0x1p-1022"                | "0x1p-1"                   || 1
		97    | "-0x1p-1022"               | "-0x1p+0"                  || 0
		98    | "-0x1p-1022"               | "0x1p+0"                   || 1
		99    | "0x1p-1022"                | "-0x1p+0"                  || 0
		100   | "0x1p-1022"                | "0x1p+0"                   || 1
		101   | "-0x1p-1022"               | "-0x1.921fb54442d18p+2"    || 0
		102   | "-0x1p-1022"               | "0x1.921fb54442d18p+2"     || 1
		103   | "0x1p-1022"                | "-0x1.921fb54442d18p+2"    || 0
		104   | "0x1p-1022"                | "0x1.921fb54442d18p+2"     || 1
		105   | "-0x1p-1022"               | "-0x1.fffffffffffffp+1023" || 0
		106   | "-0x1p-1022"               | "0x1.fffffffffffffp+1023"  || 1
		107   | "0x1p-1022"                | "-0x1.fffffffffffffp+1023" || 0
		108   | "0x1p-1022"                | "0x1.fffffffffffffp+1023"  || 1
		109   | "-0x1p-1022"               | "-inf"                     || 0
		110   | "-0x1p-1022"               | "inf"                      || 1
		111   | "0x1p-1022"                | "-inf"                     || 0
		112   | "0x1p-1022"                | "inf"                      || 1
		113   | "-0x1p-1022"               | "-nan"                     || 0
		114   | "-0x1p-1022"               | "-nan:0x4000000000000"     || 0
		115   | "-0x1p-1022"               | "nan"                      || 0
		116   | "-0x1p-1022"               | "nan:0x4000000000000"      || 0
		117   | "0x1p-1022"                | "-nan"                     || 0
		118   | "0x1p-1022"                | "-nan:0x4000000000000"     || 0
		119   | "0x1p-1022"                | "nan"                      || 0
		120   | "0x1p-1022"                | "nan:0x4000000000000"      || 0
		121   | "-0x1p-1"                  | "-0x0p+0"                  || 1
		122   | "-0x1p-1"                  | "0x0p+0"                   || 1
		123   | "0x1p-1"                   | "-0x0p+0"                  || 0
		124   | "0x1p-1"                   | "0x0p+0"                   || 0
		125   | "-0x1p-1"                  | "-0x0.0000000000001p-1022" || 1
		126   | "-0x1p-1"                  | "0x0.0000000000001p-1022"  || 1
		127   | "0x1p-1"                   | "-0x0.0000000000001p-1022" || 0
		128   | "0x1p-1"                   | "0x0.0000000000001p-1022"  || 0
		129   | "-0x1p-1"                  | "-0x1p-1022"               || 1
		130   | "-0x1p-1"                  | "0x1p-1022"                || 1
		131   | "0x1p-1"                   | "-0x1p-1022"               || 0
		132   | "0x1p-1"                   | "0x1p-1022"                || 0
		133   | "-0x1p-1"                  | "-0x1p-1"                  || 0
		134   | "-0x1p-1"                  | "0x1p-1"                   || 1
		135   | "0x1p-1"                   | "-0x1p-1"                  || 0
		136   | "0x1p-1"                   | "0x1p-1"                   || 0
		137   | "-0x1p-1"                  | "-0x1p+0"                  || 0
		138   | "-0x1p-1"                  | "0x1p+0"                   || 1
		139   | "0x1p-1"                   | "-0x1p+0"                  || 0
		140   | "0x1p-1"                   | "0x1p+0"                   || 1
		141   | "-0x1p-1"                  | "-0x1.921fb54442d18p+2"    || 0
		142   | "-0x1p-1"                  | "0x1.921fb54442d18p+2"     || 1
		143   | "0x1p-1"                   | "-0x1.921fb54442d18p+2"    || 0
		144   | "0x1p-1"                   | "0x1.921fb54442d18p+2"     || 1
		145   | "-0x1p-1"                  | "-0x1.fffffffffffffp+1023" || 0
		146   | "-0x1p-1"                  | "0x1.fffffffffffffp+1023"  || 1
		147   | "0x1p-1"                   | "-0x1.fffffffffffffp+1023" || 0
		148   | "0x1p-1"                   | "0x1.fffffffffffffp+1023"  || 1
		149   | "-0x1p-1"                  | "-inf"                     || 0
		150   | "-0x1p-1"                  | "inf"                      || 1
		151   | "0x1p-1"                   | "-inf"                     || 0
		152   | "0x1p-1"                   | "inf"                      || 1
		153   | "-0x1p-1"                  | "-nan"                     || 0
		154   | "-0x1p-1"                  | "-nan:0x4000000000000"     || 0
		155   | "-0x1p-1"                  | "nan"                      || 0
		156   | "-0x1p-1"                  | "nan:0x4000000000000"      || 0
		157   | "0x1p-1"                   | "-nan"                     || 0
		158   | "0x1p-1"                   | "-nan:0x4000000000000"     || 0
		159   | "0x1p-1"                   | "nan"                      || 0
		160   | "0x1p-1"                   | "nan:0x4000000000000"      || 0
		161   | "-0x1p+0"                  | "-0x0p+0"                  || 1
		162   | "-0x1p+0"                  | "0x0p+0"                   || 1
		163   | "0x1p+0"                   | "-0x0p+0"                  || 0
		164   | "0x1p+0"                   | "0x0p+0"                   || 0
		165   | "-0x1p+0"                  | "-0x0.0000000000001p-1022" || 1
		166   | "-0x1p+0"                  | "0x0.0000000000001p-1022"  || 1
		167   | "0x1p+0"                   | "-0x0.0000000000001p-1022" || 0
		168   | "0x1p+0"                   | "0x0.0000000000001p-1022"  || 0
		169   | "-0x1p+0"                  | "-0x1p-1022"               || 1
		170   | "-0x1p+0"                  | "0x1p-1022"                || 1
		171   | "0x1p+0"                   | "-0x1p-1022"               || 0
		172   | "0x1p+0"                   | "0x1p-1022"                || 0
		173   | "-0x1p+0"                  | "-0x1p-1"                  || 1
		174   | "-0x1p+0"                  | "0x1p-1"                   || 1
		175   | "0x1p+0"                   | "-0x1p-1"                  || 0
		176   | "0x1p+0"                   | "0x1p-1"                   || 0
		177   | "-0x1p+0"                  | "-0x1p+0"                  || 0
		178   | "-0x1p+0"                  | "0x1p+0"                   || 1
		179   | "0x1p+0"                   | "-0x1p+0"                  || 0
		180   | "0x1p+0"                   | "0x1p+0"                   || 0
		181   | "-0x1p+0"                  | "-0x1.921fb54442d18p+2"    || 0
		182   | "-0x1p+0"                  | "0x1.921fb54442d18p+2"     || 1
		183   | "0x1p+0"                   | "-0x1.921fb54442d18p+2"    || 0
		184   | "0x1p+0"                   | "0x1.921fb54442d18p+2"     || 1
		185   | "-0x1p+0"                  | "-0x1.fffffffffffffp+1023" || 0
		186   | "-0x1p+0"                  | "0x1.fffffffffffffp+1023"  || 1
		187   | "0x1p+0"                   | "-0x1.fffffffffffffp+1023" || 0
		188   | "0x1p+0"                   | "0x1.fffffffffffffp+1023"  || 1
		189   | "-0x1p+0"                  | "-inf"                     || 0
		190   | "-0x1p+0"                  | "inf"                      || 1
		191   | "0x1p+0"                   | "-inf"                     || 0
		192   | "0x1p+0"                   | "inf"                      || 1
		193   | "-0x1p+0"                  | "-nan"                     || 0
		194   | "-0x1p+0"                  | "-nan:0x4000000000000"     || 0
		195   | "-0x1p+0"                  | "nan"                      || 0
		196   | "-0x1p+0"                  | "nan:0x4000000000000"      || 0
		197   | "0x1p+0"                   | "-nan"                     || 0
		198   | "0x1p+0"                   | "-nan:0x4000000000000"     || 0
		199   | "0x1p+0"                   | "nan"                      || 0
		200   | "0x1p+0"                   | "nan:0x4000000000000"      || 0
		201   | "-0x1.921fb54442d18p+2"    | "-0x0p+0"                  || 1
		202   | "-0x1.921fb54442d18p+2"    | "0x0p+0"                   || 1
		203   | "0x1.921fb54442d18p+2"     | "-0x0p+0"                  || 0
		204   | "0x1.921fb54442d18p+2"     | "0x0p+0"                   || 0
		205   | "-0x1.921fb54442d18p+2"    | "-0x0.0000000000001p-1022" || 1
		206   | "-0x1.921fb54442d18p+2"    | "0x0.0000000000001p-1022"  || 1
		207   | "0x1.921fb54442d18p+2"     | "-0x0.0000000000001p-1022" || 0
		208   | "0x1.921fb54442d18p+2"     | "0x0.0000000000001p-1022"  || 0
		209   | "-0x1.921fb54442d18p+2"    | "-0x1p-1022"               || 1
		210   | "-0x1.921fb54442d18p+2"    | "0x1p-1022"                || 1
		211   | "0x1.921fb54442d18p+2"     | "-0x1p-1022"               || 0
		212   | "0x1.921fb54442d18p+2"     | "0x1p-1022"                || 0
		213   | "-0x1.921fb54442d18p+2"    | "-0x1p-1"                  || 1
		214   | "-0x1.921fb54442d18p+2"    | "0x1p-1"                   || 1
		215   | "0x1.921fb54442d18p+2"     | "-0x1p-1"                  || 0
		216   | "0x1.921fb54442d18p+2"     | "0x1p-1"                   || 0
		217   | "-0x1.921fb54442d18p+2"    | "-0x1p+0"                  || 1
		218   | "-0x1.921fb54442d18p+2"    | "0x1p+0"                   || 1
		219   | "0x1.921fb54442d18p+2"     | "-0x1p+0"                  || 0
		220   | "0x1.921fb54442d18p+2"     | "0x1p+0"                   || 0
		221   | "-0x1.921fb54442d18p+2"    | "-0x1.921fb54442d18p+2"    || 0
		222   | "-0x1.921fb54442d18p+2"    | "0x1.921fb54442d18p+2"     || 1
		223   | "0x1.921fb54442d18p+2"     | "-0x1.921fb54442d18p+2"    || 0
		224   | "0x1.921fb54442d18p+2"     | "0x1.921fb54442d18p+2"     || 0
		225   | "-0x1.921fb54442d18p+2"    | "-0x1.fffffffffffffp+1023" || 0
		226   | "-0x1.921fb54442d18p+2"    | "0x1.fffffffffffffp+1023"  || 1
		227   | "0x1.921fb54442d18p+2"     | "-0x1.fffffffffffffp+1023" || 0
		228   | "0x1.921fb54442d18p+2"     | "0x1.fffffffffffffp+1023"  || 1
		229   | "-0x1.921fb54442d18p+2"    | "-inf"                     || 0
		230   | "-0x1.921fb54442d18p+2"    | "inf"                      || 1
		231   | "0x1.921fb54442d18p+2"     | "-inf"                     || 0
		232   | "0x1.921fb54442d18p+2"     | "inf"                      || 1
		233   | "-0x1.921fb54442d18p+2"    | "-nan"                     || 0
		234   | "-0x1.921fb54442d18p+2"    | "-nan:0x4000000000000"     || 0
		235   | "-0x1.921fb54442d18p+2"    | "nan"                      || 0
		236   | "-0x1.921fb54442d18p+2"    | "nan:0x4000000000000"      || 0
		237   | "0x1.921fb54442d18p+2"     | "-nan"                     || 0
		238   | "0x1.921fb54442d18p+2"     | "-nan:0x4000000000000"     || 0
		239   | "0x1.921fb54442d18p+2"     | "nan"                      || 0
		240   | "0x1.921fb54442d18p+2"     | "nan:0x4000000000000"      || 0
		241   | "-0x1.fffffffffffffp+1023" | "-0x0p+0"                  || 1
		242   | "-0x1.fffffffffffffp+1023" | "0x0p+0"                   || 1
		243   | "0x1.fffffffffffffp+1023"  | "-0x0p+0"                  || 0
		244   | "0x1.fffffffffffffp+1023"  | "0x0p+0"                   || 0
		245   | "-0x1.fffffffffffffp+1023" | "-0x0.0000000000001p-1022" || 1
		246   | "-0x1.fffffffffffffp+1023" | "0x0.0000000000001p-1022"  || 1
		247   | "0x1.fffffffffffffp+1023"  | "-0x0.0000000000001p-1022" || 0
		248   | "0x1.fffffffffffffp+1023"  | "0x0.0000000000001p-1022"  || 0
		249   | "-0x1.fffffffffffffp+1023" | "-0x1p-1022"               || 1
		250   | "-0x1.fffffffffffffp+1023" | "0x1p-1022"                || 1
		251   | "0x1.fffffffffffffp+1023"  | "-0x1p-1022"               || 0
		252   | "0x1.fffffffffffffp+1023"  | "0x1p-1022"                || 0
		253   | "-0x1.fffffffffffffp+1023" | "-0x1p-1"                  || 1
		254   | "-0x1.fffffffffffffp+1023" | "0x1p-1"                   || 1
		255   | "0x1.fffffffffffffp+1023"  | "-0x1p-1"                  || 0
		256   | "0x1.fffffffffffffp+1023"  | "0x1p-1"                   || 0
		257   | "-0x1.fffffffffffffp+1023" | "-0x1p+0"                  || 1
		258   | "-0x1.fffffffffffffp+1023" | "0x1p+0"                   || 1
		259   | "0x1.fffffffffffffp+1023"  | "-0x1p+0"                  || 0
		260   | "0x1.fffffffffffffp+1023"  | "0x1p+0"                   || 0
		261   | "-0x1.fffffffffffffp+1023" | "-0x1.921fb54442d18p+2"    || 1
		262   | "-0x1.fffffffffffffp+1023" | "0x1.921fb54442d18p+2"     || 1
		263   | "0x1.fffffffffffffp+1023"  | "-0x1.921fb54442d18p+2"    || 0
		264   | "0x1.fffffffffffffp+1023"  | "0x1.921fb54442d18p+2"     || 0
		265   | "-0x1.fffffffffffffp+1023" | "-0x1.fffffffffffffp+1023" || 0
		266   | "-0x1.fffffffffffffp+1023" | "0x1.fffffffffffffp+1023"  || 1
		267   | "0x1.fffffffffffffp+1023"  | "-0x1.fffffffffffffp+1023" || 0
		268   | "0x1.fffffffffffffp+1023"  | "0x1.fffffffffffffp+1023"  || 0
		269   | "-0x1.fffffffffffffp+1023" | "-inf"                     || 0
		270   | "-0x1.fffffffffffffp+1023" | "inf"                      || 1
		271   | "0x1.fffffffffffffp+1023"  | "-inf"                     || 0
		272   | "0x1.fffffffffffffp+1023"  | "inf"                      || 1
		273   | "-0x1.fffffffffffffp+1023" | "-nan"                     || 0
		274   | "-0x1.fffffffffffffp+1023" | "-nan:0x4000000000000"     || 0
		275   | "-0x1.fffffffffffffp+1023" | "nan"                      || 0
		276   | "-0x1.fffffffffffffp+1023" | "nan:0x4000000000000"      || 0
		277   | "0x1.fffffffffffffp+1023"  | "-nan"                     || 0
		278   | "0x1.fffffffffffffp+1023"  | "-nan:0x4000000000000"     || 0
		279   | "0x1.fffffffffffffp+1023"  | "nan"                      || 0
		280   | "0x1.fffffffffffffp+1023"  | "nan:0x4000000000000"      || 0
		281   | "-inf"                     | "-0x0p+0"                  || 1
		282   | "-inf"                     | "0x0p+0"                   || 1
		283   | "inf"                      | "-0x0p+0"                  || 0
		284   | "inf"                      | "0x0p+0"                   || 0
		285   | "-inf"                     | "-0x0.0000000000001p-1022" || 1
		286   | "-inf"                     | "0x0.0000000000001p-1022"  || 1
		287   | "inf"                      | "-0x0.0000000000001p-1022" || 0
		288   | "inf"                      | "0x0.0000000000001p-1022"  || 0
		289   | "-inf"                     | "-0x1p-1022"               || 1
		290   | "-inf"                     | "0x1p-1022"                || 1
		291   | "inf"                      | "-0x1p-1022"               || 0
		292   | "inf"                      | "0x1p-1022"                || 0
		293   | "-inf"                     | "-0x1p-1"                  || 1
		294   | "-inf"                     | "0x1p-1"                   || 1
		295   | "inf"                      | "-0x1p-1"                  || 0
		296   | "inf"                      | "0x1p-1"                   || 0
		297   | "-inf"                     | "-0x1p+0"                  || 1
		298   | "-inf"                     | "0x1p+0"                   || 1
		299   | "inf"                      | "-0x1p+0"                  || 0
		300   | "inf"                      | "0x1p+0"                   || 0
		301   | "-inf"                     | "-0x1.921fb54442d18p+2"    || 1
		302   | "-inf"                     | "0x1.921fb54442d18p+2"     || 1
		303   | "inf"                      | "-0x1.921fb54442d18p+2"    || 0
		304   | "inf"                      | "0x1.921fb54442d18p+2"     || 0
		305   | "-inf"                     | "-0x1.fffffffffffffp+1023" || 1
		306   | "-inf"                     | "0x1.fffffffffffffp+1023"  || 1
		307   | "inf"                      | "-0x1.fffffffffffffp+1023" || 0
		308   | "inf"                      | "0x1.fffffffffffffp+1023"  || 0
		309   | "-inf"                     | "-inf"                     || 0
		310   | "-inf"                     | "inf"                      || 1
		311   | "inf"                      | "-inf"                     || 0
		312   | "inf"                      | "inf"                      || 0
		313   | "-inf"                     | "-nan"                     || 0
		314   | "-inf"                     | "-nan:0x4000000000000"     || 0
		315   | "-inf"                     | "nan"                      || 0
		316   | "-inf"                     | "nan:0x4000000000000"      || 0
		317   | "inf"                      | "-nan"                     || 0
		318   | "inf"                      | "-nan:0x4000000000000"     || 0
		319   | "inf"                      | "nan"                      || 0
		320   | "inf"                      | "nan:0x4000000000000"      || 0
		321   | "-nan"                     | "-0x0p+0"                  || 0
		322   | "-nan:0x4000000000000"     | "-0x0p+0"                  || 0
		323   | "-nan"                     | "0x0p+0"                   || 0
		324   | "-nan:0x4000000000000"     | "0x0p+0"                   || 0
		325   | "nan"                      | "-0x0p+0"                  || 0
		326   | "nan:0x4000000000000"      | "-0x0p+0"                  || 0
		327   | "nan"                      | "0x0p+0"                   || 0
		328   | "nan:0x4000000000000"      | "0x0p+0"                   || 0
		329   | "-nan"                     | "-0x0.0000000000001p-1022" || 0
		330   | "-nan:0x4000000000000"     | "-0x0.0000000000001p-1022" || 0
		331   | "-nan"                     | "0x0.0000000000001p-1022"  || 0
		332   | "-nan:0x4000000000000"     | "0x0.0000000000001p-1022"  || 0
		333   | "nan"                      | "-0x0.0000000000001p-1022" || 0
		334   | "nan:0x4000000000000"      | "-0x0.0000000000001p-1022" || 0
		335   | "nan"                      | "0x0.0000000000001p-1022"  || 0
		336   | "nan:0x4000000000000"      | "0x0.0000000000001p-1022"  || 0
		337   | "-nan"                     | "-0x1p-1022"               || 0
		338   | "-nan:0x4000000000000"     | "-0x1p-1022"               || 0
		339   | "-nan"                     | "0x1p-1022"                || 0
		340   | "-nan:0x4000000000000"     | "0x1p-1022"                || 0
		341   | "nan"                      | "-0x1p-1022"               || 0
		342   | "nan:0x4000000000000"      | "-0x1p-1022"               || 0
		343   | "nan"                      | "0x1p-1022"                || 0
		344   | "nan:0x4000000000000"      | "0x1p-1022"                || 0
		345   | "-nan"                     | "-0x1p-1"                  || 0
		346   | "-nan:0x4000000000000"     | "-0x1p-1"                  || 0
		347   | "-nan"                     | "0x1p-1"                   || 0
		348   | "-nan:0x4000000000000"     | "0x1p-1"                   || 0
		349   | "nan"                      | "-0x1p-1"                  || 0
		350   | "nan:0x4000000000000"      | "-0x1p-1"                  || 0
		351   | "nan"                      | "0x1p-1"                   || 0
		352   | "nan:0x4000000000000"      | "0x1p-1"                   || 0
		353   | "-nan"                     | "-0x1p+0"                  || 0
		354   | "-nan:0x4000000000000"     | "-0x1p+0"                  || 0
		355   | "-nan"                     | "0x1p+0"                   || 0
		356   | "-nan:0x4000000000000"     | "0x1p+0"                   || 0
		357   | "nan"                      | "-0x1p+0"                  || 0
		358   | "nan:0x4000000000000"      | "-0x1p+0"                  || 0
		359   | "nan"                      | "0x1p+0"                   || 0
		360   | "nan:0x4000000000000"      | "0x1p+0"                   || 0
		361   | "-nan"                     | "-0x1.921fb54442d18p+2"    || 0
		362   | "-nan:0x4000000000000"     | "-0x1.921fb54442d18p+2"    || 0
		363   | "-nan"                     | "0x1.921fb54442d18p+2"     || 0
		364   | "-nan:0x4000000000000"     | "0x1.921fb54442d18p+2"     || 0
		365   | "nan"                      | "-0x1.921fb54442d18p+2"    || 0
		366   | "nan:0x4000000000000"      | "-0x1.921fb54442d18p+2"    || 0
		367   | "nan"                      | "0x1.921fb54442d18p+2"     || 0
		368   | "nan:0x4000000000000"      | "0x1.921fb54442d18p+2"     || 0
		369   | "-nan"                     | "-0x1.fffffffffffffp+1023" || 0
		370   | "-nan:0x4000000000000"     | "-0x1.fffffffffffffp+1023" || 0
		371   | "-nan"                     | "0x1.fffffffffffffp+1023"  || 0
		372   | "-nan:0x4000000000000"     | "0x1.fffffffffffffp+1023"  || 0
		373   | "nan"                      | "-0x1.fffffffffffffp+1023" || 0
		374   | "nan:0x4000000000000"      | "-0x1.fffffffffffffp+1023" || 0
		375   | "nan"                      | "0x1.fffffffffffffp+1023"  || 0
		376   | "nan:0x4000000000000"      | "0x1.fffffffffffffp+1023"  || 0
		377   | "-nan"                     | "-inf"                     || 0
		378   | "-nan:0x4000000000000"     | "-inf"                     || 0
		379   | "-nan"                     | "inf"                      || 0
		380   | "-nan:0x4000000000000"     | "inf"                      || 0
		381   | "nan"                      | "-inf"                     || 0
		382   | "nan:0x4000000000000"      | "-inf"                     || 0
		383   | "nan"                      | "inf"                      || 0
		384   | "nan:0x4000000000000"      | "inf"                      || 0
		385   | "-nan"                     | "-nan"                     || 0
		386   | "-nan:0x4000000000000"     | "-nan"                     || 0
		387   | "-nan"                     | "-nan:0x4000000000000"     || 0
		388   | "-nan:0x4000000000000"     | "-nan:0x4000000000000"     || 0
		389   | "-nan"                     | "nan"                      || 0
		390   | "-nan:0x4000000000000"     | "nan"                      || 0
		391   | "-nan"                     | "nan:0x4000000000000"      || 0
		392   | "-nan:0x4000000000000"     | "nan:0x4000000000000"      || 0
		393   | "nan"                      | "-nan"                     || 0
		394   | "nan:0x4000000000000"      | "-nan"                     || 0
		395   | "nan"                      | "-nan:0x4000000000000"     || 0
		396   | "nan:0x4000000000000"      | "-nan:0x4000000000000"     || 0
		397   | "nan"                      | "nan"                      || 0
		398   | "nan:0x4000000000000"      | "nan"                      || 0
		399   | "nan"                      | "nan:0x4000000000000"      || 0
		400   | "nan:0x4000000000000"      | "nan:0x4000000000000"      || 0

	}


	def "Execute F64_lt throw exception on incorrect Type on second param "() {
		setup: " a value of F64  value 1  and a value of I64 of value 2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F64(3));  // value 1
		instance.stack().push(new I64(4));  // value 2

		F64_lt function = new F64_lt(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.getUuid().toString().contains("aaaead39-dcd6-44e6-ba95-8585384922a5");
	}

	def "Execute F64_lt throw exception on incorrect Type on first param "() {
		setup: " a value of I64  value 1  and a value of F64 of value 2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(3));  // value 1
		instance.stack().push(new F64(4));  // value 2

		F64_lt function = new F64_lt(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.getUuid().toString().contains("4d837af2-4ac6-4f13-abf9-dc4fad553ad4");
	}

}
