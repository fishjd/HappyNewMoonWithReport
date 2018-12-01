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

	def "constructor of type Integer"() {
		setup: "Set up a value of 1"
		ByteUnsigned byteUnsigned;
		for (int k = 0; k <= 255; k++) {
			byteUnsigned = new ByteUnsigned(k);

			when: ""
			Integer actual = byteUnsigned.intValue();

			then: "then does not work in loops, use explicit assert"
			assert actual == k;
		}
	}


	def "constructor with out of range value"() {
		setup: " Set up"

		when: ""
		ByteUnsigned byteUnsigned = new ByteUnsigned(input1);

		then: ""
		thrown(NumberFormatException);


		where: ""
		input1 || notUsed
		"-2"   || 0
		"-1"   || 0
		"256"  || 0

	}

	def "static factory/constructor Signed Byte"() {
		setup: " Set up"

		when: ""
		ByteUnsigned actual = ByteUnsigned.fromSignedByte((byte) input1);

		then: ""
		actual == expected;


		where: ""
		input1 || expected
		-2     || 0
		-1     || 0
		0      || 0
		1      || 1
		127    || 127
		0x7F   || 0x7F

	}

	def "static factory/constructor UnSigned Byte"() {
		setup: " Set up"

		when: ""
		ByteUnsigned actual = ByteUnsigned.from_Un_SignedByte((byte) input1);

		then: ""
		actual == expected;


		where: ""
		input1 || expected
		-2     || 254
		-1     || 255
		0      || 0
		1      || 1
		127    || 127
		0x7F   || 0x7F
		0xFF   || 0xFF

	}


	def "inRange"() {
		setup: " Set up"

		when: ""
		Boolean actual = ByteUnsigned.inRange(val1.shortValue());

		then: ""
		actual == expected;


		where: ""
		val1 || expected
		-1   || false
		0    || true
		1    || true
		255  || true
		256  || false
	}

	def "isSignBitSet"() {
		setup: " Set up"
		ByteUnsigned buVal1 = new ByteUnsigned(val1);

		when: ""
		Boolean actual = buVal1.isSignBitSet();

		then: ""
		actual == expected;


		where: ""
		val1 || expected
		0xFF || true
		0    || false
		1    || false
		0x80 || true
		0x7F || false
	}

	def "Compare"() {
		setup: " Set up"

		ByteUnsigned byteUnsigned = new ByteUnsigned(val1);

		when: ""
		int actual = byteUnsigned.compareTo(val2);

		then: ""
		actual == expected;


		where: ""
		val1 || val2 || expected
		1    || 0    || 1
		0    || 1    || -1
		0    || 0    || 0
		1    || 1    || 0
		255  || 255  || 0
		5    || 5    || 0
	}

	def "ShiftRight"() {
		setup: " Set up"

		ByteUnsigned byteUnsigned = new ByteUnsigned(val1);

		when: ""
		int actual = byteUnsigned.shiftRight();

		then: ""
		actual == expected;


		where: ""
		val1        || expected
		1           || 0
		0b0000_0010 || 0b0000_0001
		0b1000_0010 || 0b0100_0001
		0b1111_1111 || 0b0111_1111
	}

	def "ShiftLeft"() {
		setup: " Set up"

		ByteUnsigned byteUnsigned = new ByteUnsigned(val1);

		when: "Shift Left"
		int actual = byteUnsigned.shiftLeft();

		then: ""
		actual == expected;

		where: ""
		val1        || expected
		0b0000_0001 || 0b0000_0010
		0b0000_1000 || 0b0001_0000
		0b1000_0000 || 0b0000_0000
		0b1111_1111 || 0b1111_1110
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
			assert actual == k;

		}

	}


	def "toString Decimal"() {
		setup: "Set up a value all values "
		ByteUnsigned byteUnsigned;
		for (int k = 0; k <= 255; k++) {
			byteUnsigned = new ByteUnsigned(k);

			when: ""
			String actual = byteUnsigned.toString();

			then: "then does not work in loops, use explicit assert"
			String expected = Long.toString(k);
			assert actual == expected;

		}

	}


	def "toString base 16 Hex "() {
		setup: "Set up a value all values "
		ByteUnsigned byteUnsigned;
		for (int k = 0; k <= 255; k++) {
			byteUnsigned = new ByteUnsigned(k);

			when: ""
			String actual = byteUnsigned.toString(16);

			then: "then does not work in loops, use explicit assert"
			String expected = Long.toString(k, 16);
			assert actual == expected;
		}

	}

}
