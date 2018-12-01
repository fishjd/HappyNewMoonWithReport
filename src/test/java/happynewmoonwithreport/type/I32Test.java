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
package happynewmoonwithreport.type;

import happynewmoonwithreport.util.converter.StringToByteArrayConverter;
import happynewmoonwithreport.util.converter.StringToIntegerConverter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 2018-03-20.
 */
class I32Test {

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

//	@CsvSource({
//			"  0x01,  0x01"
//			, "0x70,  0x70"
//			, "0x80,  -0x00"
//			, "0xE0,  -0x60"
//			, "0xF0,  -0x70"
//	})
//	@ParameterizedTest(name = "signExtendByteTest() index = {index} execute( input input =  {0}, expected = {1})")
//	void signExtendByteTest(
//			@ConvertWith(StringToIntegerConverter.class) Integer input,
//			@ConvertWith(StringToIntegerConverter.class) Integer expected
//	) {
//
//		// run
//		Integer actual = I32.signExtend(input.byteValue());
//
//		// verify
//		assertThat(actual).isEqualTo(expected);
//
//	}

}