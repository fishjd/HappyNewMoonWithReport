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

package happynewmoonwithreport.type

import spock.lang.Specification

class I32Test extends Specification {

	def "test toI64Unsigned"() {
		given: "Create An I32"
		I32 val = new I32((int) input);

		when: "Convert to I64 unsigned"
		I64 actual = val.toI64Unsigned();

		then: "verify the CUT"
		actual == new I64(expected);
		actual.longValue() == expected;

		where: ""
		input /* int */   || expected  /* long */
		0                 || 0L
		1                 || 1L
		100               || 100L
		-100              || 0xFFFF_FF9CL
		-1                || 0xFFFF_FFFFL
		// Integer Max Value
		0x7FFF_FFFF       || 0x7FFF_FFFFL
		2147483647        || 0x7FFF_FFFFL
		Integer.MAX_VALUE || 0x7FFF_FFFFL
		// Integer Min Value
		0x8000_0000       || 0x8000_0000L
		-2147483648       || 0x8000_0000L
		Integer.MIN_VALUE || 0x8000_0000L


	}
}
