/*
 *  Copyright 2017 Whole Bean Software, LTD.
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
package happynewmoonwithreport.validation

import happynewmoonwithreport.type.LimitType
import happynewmoonwithreport.type.UInt32
import happynewmoonwithreport.type.VarUInt1
import spock.lang.Specification

class ResizeableLimitsTest extends Specification {

	LimitType limits;

	void setup() {

	}

	void cleanup() {
	}


	def "Valid "() {

		given: "Valid limit with min of 1 and max of 20"
		def limit = new LimitType(new VarUInt1(1), new UInt32(1), new UInt32(20));


		when: " assert limit is valid. "
		Boolean var = limit.valid();

		then:
		true == var;
	}


	def "Valid no Max "() {

		given: "Valid limit with min of 1, no max set"
		def limit = new LimitType(new VarUInt1(0), new UInt32(1));


		when: " assert limit is valid. "
		Boolean var = limit.valid();

		then:
		true == var;
	}


	def "InValid  with min larger than max"() {

		given: "invalid limit with min larger than max"
		def limit = new LimitType(new VarUInt1(1), new UInt32(400), new UInt32(20));


		when: " assert limit is valid. "
		Boolean var = limit.valid();

		then:
		false == var;
	}


}
