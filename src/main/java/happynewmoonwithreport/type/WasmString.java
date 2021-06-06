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


import happynewmoonwithreport.BytesFile;
import java.nio.charset.StandardCharsets;

/**
 * A String read from the *.wasm file.
 */
public class WasmString {

	private String value;

	/**
	 * Construct using a BytesFile and the length in bytes.
	 *
	 * @param payload     the input BytesFile.
	 * @param sizeInBytes size in bytes.
	 */
	public WasmString(BytesFile payload, UInt32 sizeInBytes) {
		Integer size = sizeInBytes.integerValue();
		byte[] byteAll = new byte[size];
		for (Integer i = 0; i < size; i++) {
			byteAll[i] = payload.readByte();
		}
		value = new String(byteAll, StandardCharsets.UTF_8);

	}

	/**
	 * Used in unit testing.
	 *
	 * @param value value
	 */
	public WasmString(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		WasmString that = (WasmString) o;

		return value.equals(that.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public String toString() {
		return "WasmString{" + "value='" + value + '\'' + '}';
	}
}
