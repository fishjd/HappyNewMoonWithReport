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

class F64Test {


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
	void nanPrintPositive4000000000000() {
		F64 val = F64.Nan0x4_0000_0000_0000Pos;

		// execute
		String actual = val.nanPrint();

		// verify
		assertThat(actual).isEqualTo("Nan Positive 0x4_0000_0000_0000");
	}

	@Test
	void testValueOfDouble() {
		// Groovy/Spock does not handle -0F, Java/jUnit does. So we moved this test to jUnit.
		assertEquals(F64.ZeroPositive,F64.valueOf(0D));
		assertEquals(F64.ZeroNegative,F64.valueOf(-0D));

		assertEquals(F64.Nan,F64.valueOf(Double.NaN));
		assertEquals(F64.InfinityNegative,F64.valueOf(Double.NEGATIVE_INFINITY));
		assertEquals(F64.InfinityPositive,F64.valueOf(Double.POSITIVE_INFINITY));
	}

	@Test
	void testValueOfFloatInputString() {
		assertEquals(F64.ZeroPositive,F64.valueOf("+0x0p+0"));
		assertEquals(F64.ZeroNegative,F64.valueOf("-0x0p+0"));
	}

	@Test
	void testNanValueOfNanPos4000000000000() {
		// run
		F64 valueF = F64.valueOf("nan:0x4000000000000");

		// verify
		assertThat(valueF).isEqualTo(F64.Nan0x4_0000_0000_0000Pos);
	}

	@Test
	void testNanValueOfNanNeg4000000000000() {
		// run
		F64 valueF = F64.valueOf("-nan:0x4000000000000");

		// verify
		assertThat(valueF).isEqualTo(F64.Nan0x4_0000_0000_0000Neg);
	}

	@Test
	void testNanValueOfNanNeg() {
		// run
		F64 valueF = F64.valueOf("-nan");

		// verify
		assertThat(valueF).isEqualTo(F64.NanNeg);
	}

	@Test
	void testNanValueOfNanArithmetic() {
		// run
		F64 valueF = F64.valueOf("nan:arithmetic");

		// verify
		assertThat(valueF).isEqualTo(F64.NanArithmeticPos);

	}

	@Test
	void testIsNanCanonicalWithCanonical() {
		Boolean actual = F64.NanCanonicalPos.isNanCanonical();
		assertTrue(actual);
	}

//	@Test
//	void testNonCanonical() {
//		Integer NanArithmeticAS_bits = F64.NanArithmeticPos.toBits();
//		assertThat(F64.NanArithmeticPos_Bits).isEqualTo(NanArithmeticAS_bits);
//	}


	@Test
	void testIsNanCanonicalWithArith() {
		Boolean actual = F64.NanArithmeticPos.isNanCanonical();
		assertFalse(actual);
	}

	@Test
	void testNanPropogationAllCanonical() {
		// run
		F64 actual = F64.nanPropagation(F64.NanCanonicalPos, F64.NanCanonicalPos);

		// verify
		assertTrue(actual.isNan());
		assertThat(F64.NanCanonicalPos).isEqualTo(actual);
	}

	@Test
	void testNanPropogationAll_NOT_Canonical() {
		// run
		F64 actual = F64.nanPropagation(F64.NanArithmeticPos, F64.Nan0x4_0000_0000_0000Pos);

		// verify
		assertTrue(actual.isNan());
		assertThat(F64.NanCanonicalPos.toBits()).isNotEqualTo(actual.toBits());
	}


	@Test
	void testNanPropogationAllNotNaN() {
		F64 zero = new F64(0.0D);
		F64 one = new F64(1.0D);

		// run
		F64 actual = F64.nanPropagation(zero, one, zero);

		// verify
		assertTrue(actual.isNan());
		assertThat(F64.NanCanonicalPos).isEqualTo(actual);
	}

	@Test
	void testNanPropogationMixed() {
		F64 zero = new F64(0.0D);
		F64 one = new F64(1.0D);

		// run
		F64 actual = F64.nanPropagation(zero, one, zero, F64.NanCanonicalPos, F64.NanArithmeticPos);

		// verify
		assertTrue(actual.isNan());
		assertThat(F64.NanCanonicalPos.toBits()).isNotEqualTo(actual.toBits());
	}


}