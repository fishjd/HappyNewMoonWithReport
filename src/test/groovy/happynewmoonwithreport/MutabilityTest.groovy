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
package happynewmoonwithreport

import happynewmoonwithreport.type.VarUInt1
import spock.lang.Specification

/**
 * Created on 2017-08-18.
 */
class MutabilityTest extends Specification {

	def immutableExpect, mutableExpect;

	void setup() {
		immutableExpect = new Mutability(0);
		mutableExpect = new Mutability(1);
	}

	def "Valid passes "() {
		when: "an immutable object"
		Mutability immutable = new Mutability(Mutability.immutable);
		then:
		immutable.valid();
	}

	def "GetType immutable"() {

		when: "an immutable object"
		Mutability immutable = new Mutability(Mutability.immutable);
		then:
		0 == immutable.getType();
		Mutability.immutable == immutable.getValue();
		immutableExpect == immutable;

	}

	def "GetType mutable"() {
		when: "an mutable object"
		Mutability mutable = new Mutability(Mutability.mutable);
		then:
		1 == mutable.getType();
		Mutability.mutable == mutable.getValue();
		mutableExpect == mutable;
	}

	def "Constructor varUInt1"() {
		setup: ""
		VarUInt1 VUI1Mutable = new VarUInt1(1);

		when: ""
		Mutability mutable = new Mutability(VUI1Mutable);

		then: ""
		mutableExpect == mutable;

	}

	def "Constructor BytesFile"() {
		setup: "a byteFile with a value of 1."
		byte[] byteAll = [(byte) 0x01];
		BytesFile payload = new BytesFile(byteAll)

		when: ""
		Mutability mutable = new Mutability(payload);

		then: ""
		mutableExpect == mutable;

	}


	def "Equals on two unequal objects"() {
		when: ""
		then: ""
		immutableExpect != mutableExpect
	}

	def "Constructor String bad value"() {
		when: "Invalid Value in constructor(String)"
		//noinspection GroovyUnusedAssignment
		Mutability invalid = new Mutability("Not Valid");
		then: "Throw Runtime Exception!!!"
		RuntimeException ex = thrown();
		null != ex;
		ex.getMessage().contains("Mutability");

	}

	def "CalcValue() with invalid input value"() {
		setup:
		Integer invalidValue = -1;
		when: "Invalid Value in constructor(Integer) "
		//noinspection GroovyUnusedAssignment
		Mutability invalid = new Mutability(invalidValue);
		then: "Throw Runtime Exception!!!"
		RuntimeException ex = thrown();
		null != ex;
		ex.getMessage().contains("Mutability");
	}

	// need this to reach 100% coverage.
	def "Hash Code "() {
		when: "an mutable object"
		Mutability mutable = new Mutability(Mutability.mutable);
		Integer hashCode = mutable.hashCode();

		then: "Hash code "
		null != hashCode;

	}

	// need this to reach 100% coverage.
	def "toString Test"() {
		when: "an mutable object"
		Mutability mutable = new Mutability(Mutability.mutable);
		String toString = mutable.toString();

		then: "String "
		null != toString;
		toString.contains("Mutability");

	}
}
