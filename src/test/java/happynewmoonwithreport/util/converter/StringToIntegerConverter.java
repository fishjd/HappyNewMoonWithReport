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
package happynewmoonwithreport.util.converter;

import org.junit.jupiter.params.converter.SimpleArgumentConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * jUnit 5 argument converter.  Converts a String to an integer where integers can be decimal <b>or</b> hexadecimal
 * <b>or</b> octal.
 * The String may include "_" which will be stripped out.
 * <p>
 * Example Hexadecimal: {@code "0x0FFF_FFFF","0xAC" "-0x0FFF_FFFF"}<br>
 * Example Decimal: {@code "1",  "100_000",  "-100_000"}<br>
 * Example Octal: {@code "01",  "0_100_000",  "-0_100_000"}<br>
 * <p>
 * <p>
 * <b>Source:</b>  <a href="http://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-argument-conversion-explicit"
 * target="_top">
 * http://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-argument-conversion-explicit
 * </a>
 *
 * @see java.lang.Integer#decode(String)
 */
public class StringToIntegerConverter extends SimpleArgumentConverter {

	@Override
	protected Object convert(Object source, Class<?> targetType) {
		assertEquals(Integer.class, targetType, "Can only convert to Integer");
		String strSource = (String) source;
		Integer result;

		// remove "_"
		strSource = strSource.replace("_", "");
		// convert to Integer.
		result = Integer.decode(strSource);
		return result;
	}
}
