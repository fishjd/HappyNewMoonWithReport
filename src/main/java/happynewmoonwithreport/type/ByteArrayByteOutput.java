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
package happynewmoonwithreport.type;

import java.util.Arrays;

/**
 * Write bytes to this one at a time to create an byte array.
 * <p>
 * Similar to a output stream of bytes.  Used mainly for unit testing.
 */
public final class ByteArrayByteOutput implements ByteOutput {

	private byte[] bytes;
	private int position;

	public ByteArrayByteOutput(Integer size) {
		this.bytes = new byte[size];
		this.position = 0;
	}

	@Override
	public void writeByte(byte i) {
		bytes[position] = i;
		position++;
	}

	@Override
	public byte[] bytes() {
		return bytes;
	}

	public void reset() {
		position = 0;
	}

	@Override
	public String toString() {
		return "ByteArrayByteOutput [bytes=" + Arrays.toString(bytes) + "]";
	}

}
