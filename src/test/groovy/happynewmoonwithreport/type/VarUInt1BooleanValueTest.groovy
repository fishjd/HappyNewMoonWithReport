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

class VarUInt1BooleanValueTest extends Specification {

	VarUInt1 varTrue, varFalse;

	void setup() {
		varTrue = new VarUInt1(1)
		varFalse = new VarUInt1(0)
	}

	void cleanup() {
	}


	def "BooleanValue"() {

		expect:
		varTrue.booleanValue() == true
		varFalse.booleanValue() == false

	}
}
