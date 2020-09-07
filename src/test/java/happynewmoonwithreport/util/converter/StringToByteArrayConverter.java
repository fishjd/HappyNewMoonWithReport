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
package happynewmoonwithreport.util.converter;

import happynewmoonwithreport.type.JavaType.ByteUnsigned;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

/**
 * jUnit 5 argument converter.  Converts a String to a Byte array;
 * <p>
 * <b>Source:</b>
 * <a href="http://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-argument-conversion-explicit"
 * target="_top"> http://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized
 * -tests-argument-conversion-explicit
 * </a>
 *
 * @see Integer#decode(String)
 */
public class StringToByteArrayConverter extends SimpleArgumentConverter {


	@Override
	protected Object convert(Object source, Class<?> targetType) {
		// assertEquals(Byte[].class, targetType, "Can only convert to byte Array");
		String strSource = (String) source;
		int length = (int) Math.ceil(strSource.length() / 2);
		ByteUnsigned[] result = new ByteUnsigned[length];

		// convert to Byte[].
		for (int i = 0; i < strSource.length(); i = i + 2) {
			String strByte1 = strSource.substring(i, i + 1);
			String strByte2 = strSource.substring(i + 1, i + 2);
			int byte1 = Integer.parseInt(strByte1, 16);
			int byte2 = Byte.parseByte(strByte2, 16);

			int byteAll = byte1 << 4 | byte2;
			result[i / 2] = new ByteUnsigned(byteAll);
		}
		return result;
	}
}
