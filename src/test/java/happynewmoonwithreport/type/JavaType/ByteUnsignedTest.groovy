/*
 *  Copyright 2018 Whole Bean Software, LTD.
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
package happynewmoonwithreport.type.JavaType

import spock.lang.Specification

/**
 * Created on 09/04/2018.
 */
class ByteUnsignedTest extends Specification {
	void setup() {
	}

	void cleanup() {
	}


	def "IntValueRadix10"() {
		setup: "Set up a value of 1"
		ByteUnsigned byteUnsigned;
		for (int k = 0; k <= 255; k++) {
			String sValue = Integer.toString(k);
			byteUnsigned = new ByteUnsigned(sValue);

			when: ""
			Integer actual = byteUnsigned.intValue();

			then: "then does not work in loops, use explicit assert"
			assert actual == k;
		}
		// expect: ""

		// cleanup: ""

		// where: ""

	}

	def "IntValueRadix16"() {
		setup: "Set up a value all values "
		ByteUnsigned byteUnsigned;
		for (int k = 0; k <= 255; k++) {
			String sValue = Integer.toString(k, 16).toUpperCase();
			// System.out.println("sValue = " + sValue);
			byteUnsigned = new ByteUnsigned(sValue, 16);

			when: ""
			Integer actual = byteUnsigned.intValue();

			then: "then does not work in loops, use explicit assert"
			assert actual == k;
		}
	}

	def "LongValue"() {
		setup: "Set up a value all values "
		ByteUnsigned byteUnsigned;
		for (int k = 0; k <= 255; k++) {
			String sValue = Integer.toString(k, 16).toUpperCase();
			byteUnsigned = new ByteUnsigned(sValue, 16);

			when: ""
			Long actual = byteUnsigned.longValue();

			then: "then does not work in loops, use explicit assert"
			assert actual == k;

		}

	}


	def "FloatValue"() {
		setup: "Set up a value all values "
		ByteUnsigned byteUnsigned;
		for (int k = 0; k <= 255; k++) {
			String sValue = Integer.toString(k, 16).toUpperCase();
			byteUnsigned = new ByteUnsigned(sValue, 16);

			when: ""
			Float actual = byteUnsigned.floatValue();

			then: "then does not work in loops, use explicit assert"
			assert actual == k;

		}

	}

	def "DoubleValue"() {
		setup: "Set up a value all values "
		ByteUnsigned byteUnsigned;
		for (int k = 0; k <= 255; k++) {
			String sValue = Integer.toString(k, 16).toUpperCase();
			byteUnsigned = new ByteUnsigned(sValue, 16);

			when: ""
			Double actual = byteUnsigned.doubleValue();

			then: "then does not work in loops, use explicit assert"
			System.out.println("Double value sValue = " + sValue + " actual = " + actual.toString());
			assert actual == k;

		}

	}

}
