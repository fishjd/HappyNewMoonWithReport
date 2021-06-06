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
package happynewmoonwithreport.type

import spock.lang.Specification

/**
 * Created on 2018-01-24.
 */
class RelativeOperator_U64_Test extends Specification {
	void setup() {
	}

	void cleanup() {
	}

	def "U64 Less than"() {
		setup: "Create an I64"
		U64 u64val1 = new U64(val1);
		U64 u64val2 = new U64(val2);

		when: "Compare "
		I32 i32Result = u64val1.lessThan(u64val2)

		then: "Test"
		i32Result.equals(new I32(expected));

		where: "expected = 1 when val1 < val2"
		val1           | val2           || expected
		0L             | 1L             || 1
		1L             | 0L             || 0
		1L             | 1L             || 0
		0              | Long.MAX_VALUE || 1
		Long.MAX_VALUE | Long.MAX_VALUE || 0
	}


	def "U64 Less than or Equal"() {
		setup: "Create an I64"
		U64 u64val1 = new U64(val1);
		U64 u64val2 = new U64(val2);

		when: "Compare "
		I32 i32Result = u64val1.lessThanEqual(u64val2)

		then: "Test"
		i32Result.equals(new I32(expected));

		where: "expected = 1 when val1 < val2"
		val1           | val2           || expected
		0L             | 1L             || 1
		1L             | 0L             || 0
		1L             | 1L             || 1
		0              | Long.MAX_VALUE || 1
		Long.MAX_VALUE | Long.MAX_VALUE || 1

	}

	def "U64 Greater than"() {
		setup: "Create an I64"
		U64 u64val1 = new U64(val1);
		U64 u64val2 = new U64(val2);

		when: "Compare "
		I32 i32Result = u64val1.greaterThan(u64val2)

		then: "Test"
		i32Result.equals(new I32(expected));

		where: "expected = 1 when val1 > val2"
		val1           | val2           || expected
		0L             | 1L             || 0
		1L             | 0L             || 1
		1L             | 1L             || 0
		0              | Long.MAX_VALUE || 0
		Long.MAX_VALUE | Long.MAX_VALUE || 0
	}

	def "U64 Greater than or equal"() {
		setup: "Create an I64"
		U64 u64val1 = new U64(val1);
		U64 u64val2 = new U64(val2);

		when: "Compare "
		I32 i32Result = u64val1.greaterThanEqual(u64val2)

		then: "Test"
		i32Result.equals(new I32(expected));

		where: "expected = 1 when val1 > val2"
		val1           | val2           || expected
		0L             | 1L             || 0
		1L             | 0L             || 1
		1L             | 1L             || 1
		0              | Long.MAX_VALUE || 0
		Long.MAX_VALUE | Long.MAX_VALUE || 1
	}
}

