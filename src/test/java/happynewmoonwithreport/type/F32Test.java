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

package happynewmoonwithreport.type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class F32Test {


	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void nanNegate() {
		assertThat(F32.Nan.negate()).isEqualTo(F32.NanNeg);
		assertThat(F32.Nan0x20_0000Pos.negate()).isEqualTo(F32.Nan0x20_0000Neg);
		assertThat(F32.NanNeg.negate()).isEqualTo(F32.Nan);
		assertThat(F32.Nan0x20_0000Neg.negate()).isEqualTo(F32.Nan0x20_0000Pos);
	}

	@Test
	void nanPrintNegCanonical() {
		F32 val = F32.NanNeg;

		// execute
		String actual = val.nanPrint();

		// verify
		assertThat(actual).isEqualTo("Nan Negative Canonical");
	}

	@Test
	void nanPrintPositive20_0000() {
		F32 val = F32.Nan0x20_0000Pos;

		// execute
		String actual = val.nanPrint();

		// verify
		assertThat(actual).isEqualTo("Nan Positive 0x20_0000");
	}

	@Test
	void testValueOfFloatInputFloat() {
		// Groovy/Spock does not handle -0F, Java/jUnit does. So we moved this test to jUnit.
		assertEquals(F32.ZeroPositive, F32.valueOf(0F));
		assertEquals(F32.ZeroNegative, F32.valueOf(-0F));

		assertEquals(F32.ZeroNegative, F32.valueOf(-0F));


		assertEquals(F32.Nan, F32.valueOf(Float.NaN));
		assertEquals(F32.InfinityNegative, F32.valueOf(Float.NEGATIVE_INFINITY));
		assertEquals(F32.InfinityPositive, F32.valueOf(Float.POSITIVE_INFINITY));
	}

	@Test
	void testValueOfFloatInputString() {
		assertEquals(F32.ZeroPositive, F32.valueOf("+0x0p+0"));
		assertEquals(F32.ZeroNegative, F32.valueOf("-0x0p+0"));
	}

	@Test
	void testNanValueOfNanPos200000() {
		// run
		F32 valueF = F32.valueOf("nan:0x200000");

		// verify
		assertThat(valueF).isEqualTo(F32.Nan0x20_0000Pos);
	}

	@Test
	void testNanValueOfNanNeg200000() {
		// run
		F32 valueF = F32.valueOf("-nan:0x200000");

		// verify
		assertThat(valueF).isEqualTo(F32.Nan0x20_0000Neg);
	}

	@Test
	void testNanValueOfNanNeg() {
		// run
		F32 valueF = F32.valueOf("-nan");

		// verify
		assertThat(valueF).isEqualTo(F32.NanNeg);
	}

	@Test
	void testNanValueOfNanArithmetic() {
		// run
		F32 valueF = F32.valueOf("nan:arithmetic");

		// verify
		assertThat(valueF).isEqualTo(F32.NanArithmeticPos);

	}

	@Test
	void testIsNanCanonicalWithCanonical() {
		Boolean actual = F32.NanCanonicalPos.isNanCanonical();
		assertTrue(actual);
	}

//	@Test
//	void testNonCanonical() {
//		Integer NanArithmeticAS_bits = F32.NanArithmeticPos.toBits();
//		assertThat(F32.NanArithmeticPos_Bits).isEqualTo(NanArithmeticAS_bits);
//	}


	@Test
	void testIsNanCanonicalWithArith() {
		Boolean actual = F32.NanArithmeticPos.isNanCanonical();
		assertFalse(actual);
	}

	@Test
	void testNanPropogationAllCanonical() {
		// run
		F32 actual = F32.nanPropagation(F32.NanCanonicalPos, F32.NanCanonicalPos);

		// verify
		assertTrue(actual.isNan());
		assertThat(F32.NanCanonicalPos).isEqualTo(actual);
	}

	@Test
	void testNanPropogationAll_NOT_Canonical() {
		// run
		F32 actual = F32.nanPropagation(F32.NanArithmeticPos, F32.Nan0x20_0000Pos);

		// verify
		assertTrue(actual.isNan());
		assertThat(F32.NanCanonicalPos.toBits()).isNotEqualTo(actual.toBits());
	}


	@Test
	void testNanPropogationAllNotNaN() {
		F32 zero = new F32(0.0F);
		F32 one = new F32(1.0F);

		// run
		F32 actual = F32.nanPropagation(zero, one, zero);

		// verify
		assertTrue(actual.isNan());
		assertThat(F32.NanCanonicalPos).isEqualTo(actual);
	}

	@Test
	void testNanPropogationMixed() {
		F32 zero = new F32(0.0F);
		F32 one = new F32(1.0F);

		// run
		F32 actual = F32.nanPropagation(zero, one, zero, F32.NanCanonicalPos, F32.NanArithmeticPos);

		// verify
		assertTrue(actual.isNan());
		assertThat(F32.NanCanonicalPos.toBits()).isNotEqualTo(actual.toBits());
	}


}