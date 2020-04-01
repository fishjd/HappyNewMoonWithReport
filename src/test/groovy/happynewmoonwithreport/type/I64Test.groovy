/*
 *  Copyright 2017 - 2019 Whole Bean Software, LTD.
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

import happynewmoonwithreport.WasmRuntimeException
import spock.lang.Specification

/**
 * Created on 2018-01-24.
 */
class I64Test extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "SignedValue"() {
		setup: "Create an I64"
		I64 val = new I64(val1);

		when: "convert to Signed value"
		S64 s64Actual = val.signedValue();

		then: "Test"
		s64Actual.longValue() == expected;

		where: ""
		val1                       || expected
		0L                         || 0L
		1L                         || 1L
		-1L                        || -1L
		0x7FFF_FFFF_FFFF_FFFFL     || Long.MAX_VALUE
		9_223_372_036_854_775_807L || Long.MAX_VALUE
		0x8000_0000_0000_0000L     || Long.MIN_VALUE
		Long.MIN_VALUE             || Long.MIN_VALUE
		// May be a bug in Spock or groovy?
		// -9_223_372_036_854_775_808L || Long.MIN_VALUE
	}

	def "UnsignedValue"() {
		setup: "Create an I64"
		I64 val = new I64(val1);

		when: "convert to Signed value"
		U64 u64Expected = val.unsignedValue();

		then: "Test"
		u64Expected.longValue() == expected;

		where: ""
		val1                       || expected
		0                          || 0
		1                          || 1
		0x7FFF_FFFF_FFFF_FFFFL     || Long.MAX_VALUE
		9_223_372_036_854_775_807L || Long.MAX_VALUE
		0x0000_0000_8000_0000L     || 2147483648  //Integer.MIN_VALUE * -1
		0x0000_0000_7FFF_FFFFL     || Integer.MAX_VALUE
	}

	def "UnsignedValue Too Large"() {
		setup: "Create an I64"
		I64 val = new I64(val1);

		when: "convert to Signed value"
		U64 u64Expected = val.unsignedValue();

		then:
		def wre = thrown(WasmRuntimeException)
		wre.getUuid().toString() == "1c282299-6b1b-4d02-9e81-a29c279840d3";

		where: "any with high bit set is error"
		val1                   || expected
		0x8000_0000_0000_0000L || _   // "_" is don't care.  aka null  aka any value
		-1L                    || _
		Long.MIN_VALUE         || _
	}
}
