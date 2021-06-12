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
import spock.lang.Unroll

/**
 * Created on 2020-09-13
 */
class F64_neTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	// @Unroll
	def "Execute F64 Equal to #val1 | #val2 || #expected}   "(Float val1, Float val2, Integer expected) {
		setup: " push two values on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F64(val1));
		instance.stack().push(new F64(val2));

		F64_ne function = new F64_ne(instance);

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
		0    | 1    || 1

	}

	/**
	 * <a href="https://github.com/WebAssembly/spec/blob/7526564b56c30250b66504fe795e9c1e88a938af/test/core/f64_cmp.wast">
	 *     source of tests</a>
	 * @param val1_s
	 * @param val2_s
	 * @param expected
	 * @return
	 */
	// @Unroll
	def "Execute F64 Equal #count | #val1_s | #val2_s || #expected "(Integer count, String val1_s, String val2_s, Integer expected) {
		setup: " push two values on stack."

		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(F64.valueOf(val1_s));
		instance.stack().push(F64.valueOf(val2_s));

		F64_ne opcode = new F64_ne(instance);

		when: "run the opcode"
		opcode.execute();
		I32 result = instance.stack().pop();

		then: " verify result equals value of expected"
		new I32(expected) == result

		where: "val1 equals val2 returns #expected"
		count | val1_s                     | val2_s                     || expected
		1     | "-0x0p+0"                  | "-0x0p+0"                  || 0
		2     | "-0x0p+0"                  | "0x0p+0"                   || 0
		3     | "0x0p+0"                   | "-0x0p+0"                  || 0
		4     | "0x0p+0"                   | "0x0p+0"                   || 0
		5     | "-0x0p+0"                  | "-0x0.0000000000001p-1022" || 1
		6     | "-0x0p+0"                  | "0x0.0000000000001p-1022"  || 1
		7     | "0x0p+0"                   | "-0x0.0000000000001p-1022" || 1
		8     | "0x0p+0"                   | "0x0.0000000000001p-1022"  || 1
		9     | "-0x0p+0"                  | "-0x1p-1022"               || 1
		10    | "-0x0p+0"                  | "0x1p-1022"                || 1
		11    | "0x0p+0"                   | "-0x1p-1022"               || 1
		12    | "0x0p+0"                   | "0x1p-1022"                || 1
		13    | "-0x0p+0"                  | "-0x1p-1"                  || 1
		14    | "-0x0p+0"                  | "0x1p-1"                   || 1
		15    | "0x0p+0"                   | "-0x1p-1"                  || 1
		16    | "0x0p+0"                   | "0x1p-1"                   || 1
		17    | "-0x0p+0"                  | "-0x1p+0"                  || 1
		18    | "-0x0p+0"                  | "0x1p+0"                   || 1
		19    | "0x0p+0"                   | "-0x1p+0"                  || 1
		20    | "0x0p+0"                   | "0x1p+0"                   || 1
		21    | "-0x0p+0"                  | "-0x1.921fb54442d18p+2"    || 1
		22    | "-0x0p+0"                  | "0x1.921fb54442d18p+2"     || 1
		23    | "0x0p+0"                   | "-0x1.921fb54442d18p+2"    || 1
		24    | "0x0p+0"                   | "0x1.921fb54442d18p+2"     || 1
		25    | "-0x0p+0"                  | "-0x1.fffffffffffffp+1023" || 1
		26    | "-0x0p+0"                  | "0x1.fffffffffffffp+1023"  || 1
		27    | "0x0p+0"                   | "-0x1.fffffffffffffp+1023" || 1
		28    | "0x0p+0"                   | "0x1.fffffffffffffp+1023"  || 1
		29    | "-0x0p+0"                  | "-inf"                     || 1
		30    | "-0x0p+0"                  | "inf"                      || 1
		31    | "0x0p+0"                   | "-inf"                     || 1
		32    | "0x0p+0"                   | "inf"                      || 1
		33    | "-0x0p+0"                  | "-nan"                     || 1
		34    | "-0x0p+0"                  | "-nan:0x4000000000000"     || 1
		35    | "-0x0p+0"                  | "nan"                      || 1
		36    | "-0x0p+0"                  | "nan:0x4000000000000"      || 1
		37    | "0x0p+0"                   | "-nan"                     || 1
		38    | "0x0p+0"                   | "-nan:0x4000000000000"     || 1
		39    | "0x0p+0"                   | "nan"                      || 1
		40    | "0x0p+0"                   | "nan:0x4000000000000"      || 1
		41    | "-0x0.0000000000001p-1022" | "-0x0p+0"                  || 1
		42    | "-0x0.0000000000001p-1022" | "0x0p+0"                   || 1
		43    | "0x0.0000000000001p-1022"  | "-0x0p+0"                  || 1
		44    | "0x0.0000000000001p-1022"  | "0x0p+0"                   || 1
		45    | "-0x0.0000000000001p-1022" | "-0x0.0000000000001p-1022" || 0
		46    | "-0x0.0000000000001p-1022" | "0x0.0000000000001p-1022"  || 1
		47    | "0x0.0000000000001p-1022"  | "-0x0.0000000000001p-1022" || 1
		48    | "0x0.0000000000001p-1022"  | "0x0.0000000000001p-1022"  || 0
		49    | "-0x0.0000000000001p-1022" | "-0x1p-1022"               || 1
		50    | "-0x0.0000000000001p-1022" | "0x1p-1022"                || 1
		51    | "0x0.0000000000001p-1022"  | "-0x1p-1022"               || 1
		52    | "0x0.0000000000001p-1022"  | "0x1p-1022"                || 1
		53    | "-0x0.0000000000001p-1022" | "-0x1p-1"                  || 1
		54    | "-0x0.0000000000001p-1022" | "0x1p-1"                   || 1
		55    | "0x0.0000000000001p-1022"  | "-0x1p-1"                  || 1
		56    | "0x0.0000000000001p-1022"  | "0x1p-1"                   || 1
		57    | "-0x0.0000000000001p-1022" | "-0x1p+0"                  || 1
		58    | "-0x0.0000000000001p-1022" | "0x1p+0"                   || 1
		59    | "0x0.0000000000001p-1022"  | "-0x1p+0"                  || 1
		60    | "0x0.0000000000001p-1022"  | "0x1p+0"                   || 1
		61    | "-0x0.0000000000001p-1022" | "-0x1.921fb54442d18p+2"    || 1
		62    | "-0x0.0000000000001p-1022" | "0x1.921fb54442d18p+2"     || 1
		63    | "0x0.0000000000001p-1022"  | "-0x1.921fb54442d18p+2"    || 1
		64    | "0x0.0000000000001p-1022"  | "0x1.921fb54442d18p+2"     || 1
		65    | "-0x0.0000000000001p-1022" | "-0x1.fffffffffffffp+1023" || 1
		66    | "-0x0.0000000000001p-1022" | "0x1.fffffffffffffp+1023"  || 1
		67    | "0x0.0000000000001p-1022"  | "-0x1.fffffffffffffp+1023" || 1
		68    | "0x0.0000000000001p-1022"  | "0x1.fffffffffffffp+1023"  || 1
		69    | "-0x0.0000000000001p-1022" | "-inf"                     || 1
		70    | "-0x0.0000000000001p-1022" | "inf"                      || 1
		71    | "0x0.0000000000001p-1022"  | "-inf"                     || 1
		72    | "0x0.0000000000001p-1022"  | "inf"                      || 1
		73    | "-0x0.0000000000001p-1022" | "-nan"                     || 1
		74    | "-0x0.0000000000001p-1022" | "-nan:0x4000000000000"     || 1
		75    | "-0x0.0000000000001p-1022" | "nan"                      || 1
		76    | "-0x0.0000000000001p-1022" | "nan:0x4000000000000"      || 1
		77    | "0x0.0000000000001p-1022"  | "-nan"                     || 1
		78    | "0x0.0000000000001p-1022"  | "-nan:0x4000000000000"     || 1
		79    | "0x0.0000000000001p-1022"  | "nan"                      || 1
		80    | "0x0.0000000000001p-1022"  | "nan:0x4000000000000"      || 1
		81    | "-0x1p-1022"               | "-0x0p+0"                  || 1
		82    | "-0x1p-1022"               | "0x0p+0"                   || 1
		83    | "0x1p-1022"                | "-0x0p+0"                  || 1
		84    | "0x1p-1022"                | "0x0p+0"                   || 1
		85    | "-0x1p-1022"               | "-0x0.0000000000001p-1022" || 1
		86    | "-0x1p-1022"               | "0x0.0000000000001p-1022"  || 1
		87    | "0x1p-1022"                | "-0x0.0000000000001p-1022" || 1
		88    | "0x1p-1022"                | "0x0.0000000000001p-1022"  || 1
		89    | "-0x1p-1022"               | "-0x1p-1022"               || 0
		90    | "-0x1p-1022"               | "0x1p-1022"                || 1
		91    | "0x1p-1022"                | "-0x1p-1022"               || 1
		92    | "0x1p-1022"                | "0x1p-1022"                || 0
		93    | "-0x1p-1022"               | "-0x1p-1"                  || 1
		94    | "-0x1p-1022"               | "0x1p-1"                   || 1
		95    | "0x1p-1022"                | "-0x1p-1"                  || 1
		96    | "0x1p-1022"                | "0x1p-1"                   || 1
		97    | "-0x1p-1022"               | "-0x1p+0"                  || 1
		98    | "-0x1p-1022"               | "0x1p+0"                   || 1
		99    | "0x1p-1022"                | "-0x1p+0"                  || 1
		100   | "0x1p-1022"                | "0x1p+0"                   || 1
		101   | "-0x1p-1022"               | "-0x1.921fb54442d18p+2"    || 1
		102   | "-0x1p-1022"               | "0x1.921fb54442d18p+2"     || 1
		103   | "0x1p-1022"                | "-0x1.921fb54442d18p+2"    || 1
		104   | "0x1p-1022"                | "0x1.921fb54442d18p+2"     || 1
		105   | "-0x1p-1022"               | "-0x1.fffffffffffffp+1023" || 1
		106   | "-0x1p-1022"               | "0x1.fffffffffffffp+1023"  || 1
		107   | "0x1p-1022"                | "-0x1.fffffffffffffp+1023" || 1
		108   | "0x1p-1022"                | "0x1.fffffffffffffp+1023"  || 1
		109   | "-0x1p-1022"               | "-inf"                     || 1
		110   | "-0x1p-1022"               | "inf"                      || 1
		111   | "0x1p-1022"                | "-inf"                     || 1
		112   | "0x1p-1022"                | "inf"                      || 1
		113   | "-0x1p-1022"               | "-nan"                     || 1
		114   | "-0x1p-1022"               | "-nan:0x4000000000000"     || 1
		115   | "-0x1p-1022"               | "nan"                      || 1
		116   | "-0x1p-1022"               | "nan:0x4000000000000"      || 1
		117   | "0x1p-1022"                | "-nan"                     || 1
		118   | "0x1p-1022"                | "-nan:0x4000000000000"     || 1
		119   | "0x1p-1022"                | "nan"                      || 1
		120   | "0x1p-1022"                | "nan:0x4000000000000"      || 1
		121   | "-0x1p-1"                  | "-0x0p+0"                  || 1
		122   | "-0x1p-1"                  | "0x0p+0"                   || 1
		123   | "0x1p-1"                   | "-0x0p+0"                  || 1
		124   | "0x1p-1"                   | "0x0p+0"                   || 1
		125   | "-0x1p-1"                  | "-0x0.0000000000001p-1022" || 1
		126   | "-0x1p-1"                  | "0x0.0000000000001p-1022"  || 1
		127   | "0x1p-1"                   | "-0x0.0000000000001p-1022" || 1
		128   | "0x1p-1"                   | "0x0.0000000000001p-1022"  || 1
		129   | "-0x1p-1"                  | "-0x1p-1022"               || 1
		130   | "-0x1p-1"                  | "0x1p-1022"                || 1
		131   | "0x1p-1"                   | "-0x1p-1022"               || 1
		132   | "0x1p-1"                   | "0x1p-1022"                || 1
		133   | "-0x1p-1"                  | "-0x1p-1"                  || 0
		134   | "-0x1p-1"                  | "0x1p-1"                   || 1
		135   | "0x1p-1"                   | "-0x1p-1"                  || 1
		136   | "0x1p-1"                   | "0x1p-1"                   || 0
		137   | "-0x1p-1"                  | "-0x1p+0"                  || 1
		138   | "-0x1p-1"                  | "0x1p+0"                   || 1
		139   | "0x1p-1"                   | "-0x1p+0"                  || 1
		140   | "0x1p-1"                   | "0x1p+0"                   || 1
		141   | "-0x1p-1"                  | "-0x1.921fb54442d18p+2"    || 1
		142   | "-0x1p-1"                  | "0x1.921fb54442d18p+2"     || 1
		143   | "0x1p-1"                   | "-0x1.921fb54442d18p+2"    || 1
		144   | "0x1p-1"                   | "0x1.921fb54442d18p+2"     || 1
		145   | "-0x1p-1"                  | "-0x1.fffffffffffffp+1023" || 1
		146   | "-0x1p-1"                  | "0x1.fffffffffffffp+1023"  || 1
		147   | "0x1p-1"                   | "-0x1.fffffffffffffp+1023" || 1
		148   | "0x1p-1"                   | "0x1.fffffffffffffp+1023"  || 1
		149   | "-0x1p-1"                  | "-inf"                     || 1
		150   | "-0x1p-1"                  | "inf"                      || 1
		151   | "0x1p-1"                   | "-inf"                     || 1
		152   | "0x1p-1"                   | "inf"                      || 1
		153   | "-0x1p-1"                  | "-nan"                     || 1
		154   | "-0x1p-1"                  | "-nan:0x4000000000000"     || 1
		155   | "-0x1p-1"                  | "nan"                      || 1
		156   | "-0x1p-1"                  | "nan:0x4000000000000"      || 1
		157   | "0x1p-1"                   | "-nan"                     || 1
		158   | "0x1p-1"                   | "-nan:0x4000000000000"     || 1
		159   | "0x1p-1"                   | "nan"                      || 1
		160   | "0x1p-1"                   | "nan:0x4000000000000"      || 1
		161   | "-0x1p+0"                  | "-0x0p+0"                  || 1
		162   | "-0x1p+0"                  | "0x0p+0"                   || 1
		163   | "0x1p+0"                   | "-0x0p+0"                  || 1
		164   | "0x1p+0"                   | "0x0p+0"                   || 1
		165   | "-0x1p+0"                  | "-0x0.0000000000001p-1022" || 1
		166   | "-0x1p+0"                  | "0x0.0000000000001p-1022"  || 1
		167   | "0x1p+0"                   | "-0x0.0000000000001p-1022" || 1
		168   | "0x1p+0"                   | "0x0.0000000000001p-1022"  || 1
		169   | "-0x1p+0"                  | "-0x1p-1022"               || 1
		170   | "-0x1p+0"                  | "0x1p-1022"                || 1
		171   | "0x1p+0"                   | "-0x1p-1022"               || 1
		172   | "0x1p+0"                   | "0x1p-1022"                || 1
		173   | "-0x1p+0"                  | "-0x1p-1"                  || 1
		174   | "-0x1p+0"                  | "0x1p-1"                   || 1
		175   | "0x1p+0"                   | "-0x1p-1"                  || 1
		176   | "0x1p+0"                   | "0x1p-1"                   || 1
		177   | "-0x1p+0"                  | "-0x1p+0"                  || 0
		178   | "-0x1p+0"                  | "0x1p+0"                   || 1
		179   | "0x1p+0"                   | "-0x1p+0"                  || 1
		180   | "0x1p+0"                   | "0x1p+0"                   || 0
		181   | "-0x1p+0"                  | "-0x1.921fb54442d18p+2"    || 1
		182   | "-0x1p+0"                  | "0x1.921fb54442d18p+2"     || 1
		183   | "0x1p+0"                   | "-0x1.921fb54442d18p+2"    || 1
		184   | "0x1p+0"                   | "0x1.921fb54442d18p+2"     || 1
		185   | "-0x1p+0"                  | "-0x1.fffffffffffffp+1023" || 1
		186   | "-0x1p+0"                  | "0x1.fffffffffffffp+1023"  || 1
		187   | "0x1p+0"                   | "-0x1.fffffffffffffp+1023" || 1
		188   | "0x1p+0"                   | "0x1.fffffffffffffp+1023"  || 1
		189   | "-0x1p+0"                  | "-inf"                     || 1
		190   | "-0x1p+0"                  | "inf"                      || 1
		191   | "0x1p+0"                   | "-inf"                     || 1
		192   | "0x1p+0"                   | "inf"                      || 1
		193   | "-0x1p+0"                  | "-nan"                     || 1
		194   | "-0x1p+0"                  | "-nan:0x4000000000000"     || 1
		195   | "-0x1p+0"                  | "nan"                      || 1
		196   | "-0x1p+0"                  | "nan:0x4000000000000"      || 1
		197   | "0x1p+0"                   | "-nan"                     || 1
		198   | "0x1p+0"                   | "-nan:0x4000000000000"     || 1
		199   | "0x1p+0"                   | "nan"                      || 1
		200   | "0x1p+0"                   | "nan:0x4000000000000"      || 1
		201   | "-0x1.921fb54442d18p+2"    | "-0x0p+0"                  || 1
		202   | "-0x1.921fb54442d18p+2"    | "0x0p+0"                   || 1
		203   | "0x1.921fb54442d18p+2"     | "-0x0p+0"                  || 1
		204   | "0x1.921fb54442d18p+2"     | "0x0p+0"                   || 1
		205   | "-0x1.921fb54442d18p+2"    | "-0x0.0000000000001p-1022" || 1
		206   | "-0x1.921fb54442d18p+2"    | "0x0.0000000000001p-1022"  || 1
		207   | "0x1.921fb54442d18p+2"     | "-0x0.0000000000001p-1022" || 1
		208   | "0x1.921fb54442d18p+2"     | "0x0.0000000000001p-1022"  || 1
		209   | "-0x1.921fb54442d18p+2"    | "-0x1p-1022"               || 1
		210   | "-0x1.921fb54442d18p+2"    | "0x1p-1022"                || 1
		211   | "0x1.921fb54442d18p+2"     | "-0x1p-1022"               || 1
		212   | "0x1.921fb54442d18p+2"     | "0x1p-1022"                || 1
		213   | "-0x1.921fb54442d18p+2"    | "-0x1p-1"                  || 1
		214   | "-0x1.921fb54442d18p+2"    | "0x1p-1"                   || 1
		215   | "0x1.921fb54442d18p+2"     | "-0x1p-1"                  || 1
		216   | "0x1.921fb54442d18p+2"     | "0x1p-1"                   || 1
		217   | "-0x1.921fb54442d18p+2"    | "-0x1p+0"                  || 1
		218   | "-0x1.921fb54442d18p+2"    | "0x1p+0"                   || 1
		219   | "0x1.921fb54442d18p+2"     | "-0x1p+0"                  || 1
		220   | "0x1.921fb54442d18p+2"     | "0x1p+0"                   || 1
		221   | "-0x1.921fb54442d18p+2"    | "-0x1.921fb54442d18p+2"    || 0
		222   | "-0x1.921fb54442d18p+2"    | "0x1.921fb54442d18p+2"     || 1
		223   | "0x1.921fb54442d18p+2"     | "-0x1.921fb54442d18p+2"    || 1
		224   | "0x1.921fb54442d18p+2"     | "0x1.921fb54442d18p+2"     || 0
		225   | "-0x1.921fb54442d18p+2"    | "-0x1.fffffffffffffp+1023" || 1
		226   | "-0x1.921fb54442d18p+2"    | "0x1.fffffffffffffp+1023"  || 1
		227   | "0x1.921fb54442d18p+2"     | "-0x1.fffffffffffffp+1023" || 1
		228   | "0x1.921fb54442d18p+2"     | "0x1.fffffffffffffp+1023"  || 1
		229   | "-0x1.921fb54442d18p+2"    | "-inf"                     || 1
		230   | "-0x1.921fb54442d18p+2"    | "inf"                      || 1
		231   | "0x1.921fb54442d18p+2"     | "-inf"                     || 1
		232   | "0x1.921fb54442d18p+2"     | "inf"                      || 1
		233   | "-0x1.921fb54442d18p+2"    | "-nan"                     || 1
		234   | "-0x1.921fb54442d18p+2"    | "-nan:0x4000000000000"     || 1
		235   | "-0x1.921fb54442d18p+2"    | "nan"                      || 1
		236   | "-0x1.921fb54442d18p+2"    | "nan:0x4000000000000"      || 1
		237   | "0x1.921fb54442d18p+2"     | "-nan"                     || 1
		238   | "0x1.921fb54442d18p+2"     | "-nan:0x4000000000000"     || 1
		239   | "0x1.921fb54442d18p+2"     | "nan"                      || 1
		240   | "0x1.921fb54442d18p+2"     | "nan:0x4000000000000"      || 1
		241   | "-0x1.fffffffffffffp+1023" | "-0x0p+0"                  || 1
		242   | "-0x1.fffffffffffffp+1023" | "0x0p+0"                   || 1
		243   | "0x1.fffffffffffffp+1023"  | "-0x0p+0"                  || 1
		244   | "0x1.fffffffffffffp+1023"  | "0x0p+0"                   || 1
		245   | "-0x1.fffffffffffffp+1023" | "-0x0.0000000000001p-1022" || 1
		246   | "-0x1.fffffffffffffp+1023" | "0x0.0000000000001p-1022"  || 1
		247   | "0x1.fffffffffffffp+1023"  | "-0x0.0000000000001p-1022" || 1
		248   | "0x1.fffffffffffffp+1023"  | "0x0.0000000000001p-1022"  || 1
		249   | "-0x1.fffffffffffffp+1023" | "-0x1p-1022"               || 1
		250   | "-0x1.fffffffffffffp+1023" | "0x1p-1022"                || 1
		251   | "0x1.fffffffffffffp+1023"  | "-0x1p-1022"               || 1
		252   | "0x1.fffffffffffffp+1023"  | "0x1p-1022"                || 1
		253   | "-0x1.fffffffffffffp+1023" | "-0x1p-1"                  || 1
		254   | "-0x1.fffffffffffffp+1023" | "0x1p-1"                   || 1
		255   | "0x1.fffffffffffffp+1023"  | "-0x1p-1"                  || 1
		256   | "0x1.fffffffffffffp+1023"  | "0x1p-1"                   || 1
		257   | "-0x1.fffffffffffffp+1023" | "-0x1p+0"                  || 1
		258   | "-0x1.fffffffffffffp+1023" | "0x1p+0"                   || 1
		259   | "0x1.fffffffffffffp+1023"  | "-0x1p+0"                  || 1
		260   | "0x1.fffffffffffffp+1023"  | "0x1p+0"                   || 1
		261   | "-0x1.fffffffffffffp+1023" | "-0x1.921fb54442d18p+2"    || 1
		262   | "-0x1.fffffffffffffp+1023" | "0x1.921fb54442d18p+2"     || 1
		263   | "0x1.fffffffffffffp+1023"  | "-0x1.921fb54442d18p+2"    || 1
		264   | "0x1.fffffffffffffp+1023"  | "0x1.921fb54442d18p+2"     || 1
		265   | "-0x1.fffffffffffffp+1023" | "-0x1.fffffffffffffp+1023" || 0
		266   | "-0x1.fffffffffffffp+1023" | "0x1.fffffffffffffp+1023"  || 1
		267   | "0x1.fffffffffffffp+1023"  | "-0x1.fffffffffffffp+1023" || 1
		268   | "0x1.fffffffffffffp+1023"  | "0x1.fffffffffffffp+1023"  || 0
		269   | "-0x1.fffffffffffffp+1023" | "-inf"                     || 1
		270   | "-0x1.fffffffffffffp+1023" | "inf"                      || 1
		271   | "0x1.fffffffffffffp+1023"  | "-inf"                     || 1
		272   | "0x1.fffffffffffffp+1023"  | "inf"                      || 1
		273   | "-0x1.fffffffffffffp+1023" | "-nan"                     || 1
		274   | "-0x1.fffffffffffffp+1023" | "-nan:0x4000000000000"     || 1
		275   | "-0x1.fffffffffffffp+1023" | "nan"                      || 1
		276   | "-0x1.fffffffffffffp+1023" | "nan:0x4000000000000"      || 1
		277   | "0x1.fffffffffffffp+1023"  | "-nan"                     || 1
		278   | "0x1.fffffffffffffp+1023"  | "-nan:0x4000000000000"     || 1
		279   | "0x1.fffffffffffffp+1023"  | "nan"                      || 1
		280   | "0x1.fffffffffffffp+1023"  | "nan:0x4000000000000"      || 1
		281   | "-inf"                     | "-0x0p+0"                  || 1
		282   | "-inf"                     | "0x0p+0"                   || 1
		283   | "inf"                      | "-0x0p+0"                  || 1
		284   | "inf"                      | "0x0p+0"                   || 1
		285   | "-inf"                     | "-0x0.0000000000001p-1022" || 1
		286   | "-inf"                     | "0x0.0000000000001p-1022"  || 1
		287   | "inf"                      | "-0x0.0000000000001p-1022" || 1
		288   | "inf"                      | "0x0.0000000000001p-1022"  || 1
		289   | "-inf"                     | "-0x1p-1022"               || 1
		290   | "-inf"                     | "0x1p-1022"                || 1
		291   | "inf"                      | "-0x1p-1022"               || 1
		292   | "inf"                      | "0x1p-1022"                || 1
		293   | "-inf"                     | "-0x1p-1"                  || 1
		294   | "-inf"                     | "0x1p-1"                   || 1
		295   | "inf"                      | "-0x1p-1"                  || 1
		296   | "inf"                      | "0x1p-1"                   || 1
		297   | "-inf"                     | "-0x1p+0"                  || 1
		298   | "-inf"                     | "0x1p+0"                   || 1
		299   | "inf"                      | "-0x1p+0"                  || 1
		300   | "inf"                      | "0x1p+0"                   || 1
		301   | "-inf"                     | "-0x1.921fb54442d18p+2"    || 1
		302   | "-inf"                     | "0x1.921fb54442d18p+2"     || 1
		303   | "inf"                      | "-0x1.921fb54442d18p+2"    || 1
		304   | "inf"                      | "0x1.921fb54442d18p+2"     || 1
		305   | "-inf"                     | "-0x1.fffffffffffffp+1023" || 1
		306   | "-inf"                     | "0x1.fffffffffffffp+1023"  || 1
		307   | "inf"                      | "-0x1.fffffffffffffp+1023" || 1
		308   | "inf"                      | "0x1.fffffffffffffp+1023"  || 1
		309   | "-inf"                     | "-inf"                     || 0
		310   | "-inf"                     | "inf"                      || 1
		311   | "inf"                      | "-inf"                     || 1
		312   | "inf"                      | "inf"                      || 0
		313   | "-inf"                     | "-nan"                     || 1
		314   | "-inf"                     | "-nan:0x4000000000000"     || 1
		315   | "-inf"                     | "nan"                      || 1
		316   | "-inf"                     | "nan:0x4000000000000"      || 1
		317   | "inf"                      | "-nan"                     || 1
		318   | "inf"                      | "-nan:0x4000000000000"     || 1
		319   | "inf"                      | "nan"                      || 1
		320   | "inf"                      | "nan:0x4000000000000"      || 1
		321   | "-nan"                     | "-0x0p+0"                  || 1
		322   | "-nan:0x4000000000000"     | "-0x0p+0"                  || 1
		323   | "-nan"                     | "0x0p+0"                   || 1
		324   | "-nan:0x4000000000000"     | "0x0p+0"                   || 1
		325   | "nan"                      | "-0x0p+0"                  || 1
		326   | "nan:0x4000000000000"      | "-0x0p+0"                  || 1
		327   | "nan"                      | "0x0p+0"                   || 1
		328   | "nan:0x4000000000000"      | "0x0p+0"                   || 1
		329   | "-nan"                     | "-0x0.0000000000001p-1022" || 1
		330   | "-nan:0x4000000000000"     | "-0x0.0000000000001p-1022" || 1
		331   | "-nan"                     | "0x0.0000000000001p-1022"  || 1
		332   | "-nan:0x4000000000000"     | "0x0.0000000000001p-1022"  || 1
		333   | "nan"                      | "-0x0.0000000000001p-1022" || 1
		334   | "nan:0x4000000000000"      | "-0x0.0000000000001p-1022" || 1
		335   | "nan"                      | "0x0.0000000000001p-1022"  || 1
		336   | "nan:0x4000000000000"      | "0x0.0000000000001p-1022"  || 1
		337   | "-nan"                     | "-0x1p-1022"               || 1
		338   | "-nan:0x4000000000000"     | "-0x1p-1022"               || 1
		339   | "-nan"                     | "0x1p-1022"                || 1
		340   | "-nan:0x4000000000000"     | "0x1p-1022"                || 1
		341   | "nan"                      | "-0x1p-1022"               || 1
		342   | "nan:0x4000000000000"      | "-0x1p-1022"               || 1
		343   | "nan"                      | "0x1p-1022"                || 1
		344   | "nan:0x4000000000000"      | "0x1p-1022"                || 1
		345   | "-nan"                     | "-0x1p-1"                  || 1
		346   | "-nan:0x4000000000000"     | "-0x1p-1"                  || 1
		347   | "-nan"                     | "0x1p-1"                   || 1
		348   | "-nan:0x4000000000000"     | "0x1p-1"                   || 1
		349   | "nan"                      | "-0x1p-1"                  || 1
		350   | "nan:0x4000000000000"      | "-0x1p-1"                  || 1
		351   | "nan"                      | "0x1p-1"                   || 1
		352   | "nan:0x4000000000000"      | "0x1p-1"                   || 1
		353   | "-nan"                     | "-0x1p+0"                  || 1
		354   | "-nan:0x4000000000000"     | "-0x1p+0"                  || 1
		355   | "-nan"                     | "0x1p+0"                   || 1
		356   | "-nan:0x4000000000000"     | "0x1p+0"                   || 1
		357   | "nan"                      | "-0x1p+0"                  || 1
		358   | "nan:0x4000000000000"      | "-0x1p+0"                  || 1
		359   | "nan"                      | "0x1p+0"                   || 1
		360   | "nan:0x4000000000000"      | "0x1p+0"                   || 1
		361   | "-nan"                     | "-0x1.921fb54442d18p+2"    || 1
		362   | "-nan:0x4000000000000"     | "-0x1.921fb54442d18p+2"    || 1
		363   | "-nan"                     | "0x1.921fb54442d18p+2"     || 1
		364   | "-nan:0x4000000000000"     | "0x1.921fb54442d18p+2"     || 1
		365   | "nan"                      | "-0x1.921fb54442d18p+2"    || 1
		366   | "nan:0x4000000000000"      | "-0x1.921fb54442d18p+2"    || 1
		367   | "nan"                      | "0x1.921fb54442d18p+2"     || 1
		368   | "nan:0x4000000000000"      | "0x1.921fb54442d18p+2"     || 1
		369   | "-nan"                     | "-0x1.fffffffffffffp+1023" || 1
		370   | "-nan:0x4000000000000"     | "-0x1.fffffffffffffp+1023" || 1
		371   | "-nan"                     | "0x1.fffffffffffffp+1023"  || 1
		372   | "-nan:0x4000000000000"     | "0x1.fffffffffffffp+1023"  || 1
		373   | "nan"                      | "-0x1.fffffffffffffp+1023" || 1
		374   | "nan:0x4000000000000"      | "-0x1.fffffffffffffp+1023" || 1
		375   | "nan"                      | "0x1.fffffffffffffp+1023"  || 1
		376   | "nan:0x4000000000000"      | "0x1.fffffffffffffp+1023"  || 1
		377   | "-nan"                     | "-inf"                     || 1
		378   | "-nan:0x4000000000000"     | "-inf"                     || 1
		379   | "-nan"                     | "inf"                      || 1
		380   | "-nan:0x4000000000000"     | "inf"                      || 1
		381   | "nan"                      | "-inf"                     || 1
		382   | "nan:0x4000000000000"      | "-inf"                     || 1
		383   | "nan"                      | "inf"                      || 1
		384   | "nan:0x4000000000000"      | "inf"                      || 1
		385   | "-nan"                     | "-nan"                     || 1
		386   | "-nan:0x4000000000000"     | "-nan"                     || 1
		387   | "-nan"                     | "-nan:0x4000000000000"     || 1
		388   | "-nan:0x4000000000000"     | "-nan:0x4000000000000"     || 1
		389   | "-nan"                     | "nan"                      || 1
		390   | "-nan:0x4000000000000"     | "nan"                      || 1
		391   | "-nan"                     | "nan:0x4000000000000"      || 1
		392   | "-nan:0x4000000000000"     | "nan:0x4000000000000"      || 1
		393   | "nan"                      | "-nan"                     || 1
		394   | "nan:0x4000000000000"      | "-nan"                     || 1
		395   | "nan"                      | "-nan:0x4000000000000"     || 1
		396   | "nan:0x4000000000000"      | "-nan:0x4000000000000"     || 1
		397   | "nan"                      | "nan"                      || 1
		398   | "nan:0x4000000000000"      | "nan"                      || 1
		399   | "nan"                      | "nan:0x4000000000000"      || 1
		400   | "nan:0x4000000000000"      | "nan:0x4000000000000"      || 1


	}


	def "Execute F64_ne throw exception on incorrect Type on second param "() {
		setup: " a value of F64  value 1  and a value of I64 of value 2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new F64(3));  // value 1
		instance.stack().push(new I64(4));  // value 2

		F64_ne function = new F64_ne(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value2");
		exception.getUuid().toString().contains("b34836dc-5029-4438-9453-5c43609f52a1");
	}

	def "Execute F64_ne throw exception on incorrect Type on first param "() {
		setup: " a value of I64  value 1  and a value of F64 of value 2"
		WasmInstanceInterface instance = new WasmInstanceStub();
		instance.stack().push(new I64(3));  // value 1
		instance.stack().push(new F64(4));  // value 2

		F64_ne function = new F64_ne(instance);

		when: "run the opcode"
		function.execute();

		then: " Thrown Exception"
		WasmRuntimeException exception = thrown();
		exception.message.contains("Value1");
		exception.getUuid().toString().contains("6279ddd3-ab6f-402f-be03-ae22f98326fa");
	}

}
