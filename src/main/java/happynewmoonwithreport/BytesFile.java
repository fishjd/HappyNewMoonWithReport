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
package happynewmoonwithreport;

import happynewmoonwithreport.type.utility.Hex;
import java.util.Arrays;

/**
 * An byte array with an index. Created by James Haring on 2017-07-18.
 */
public class BytesFile {
	private byte[] bytesAll;
	private Integer index = 0;

	public BytesFile(byte[] bytesAll) {
		this(bytesAll, 0);
	}

	//    public BytesFile(byte... bytes) {
	//        this.bytesAll = bytes;
	//    }

	public BytesFile(byte[] bytesAll, Integer index) {
		this.bytesAll = bytesAll;
		this.index = index;
	}

	public byte readByte() {
		byte result = bytesAll[index];
		index++;
		return result;
	}

	public BytesFile copy(Integer length) {
		byte[] tempBytesAll = getBytes(length);
		return new BytesFile(tempBytesAll);
	}

	/**
	 * Get the bytes starting at index.
	 *
	 * @param length bytes desired
	 * @return bytes array
	 */
	public byte[] getBytes(Integer length) {
		byte[] tempBytesAll = Arrays.copyOfRange(bytesAll, index, index + length);
		index += length;
		return tempBytesAll;
	}

	public byte[] getBytesAll() {
		return bytesAll;
	}

	public void setBytesAll(byte[] bytesAll) {
		this.bytesAll = bytesAll;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public void addToIndex(Integer size) {
		this.index += size;
	}

	public Boolean isNotEmpty() {
		return (index < bytesAll.length);
	}

	public Boolean atEndOfFile() {
		return (index == bytesAll.length);
	}

	public Boolean longEnough(Integer size) {
		return (index + size <= bytesAll.length);
	}

	@Override
	public String toString() {
		String result =
			"BytesFile{" + "current byte = " + Hex.byteToHex(bytesAll[index]) + ", index = " + index
			+ " (0x" + Integer.toHexString(index) + ") " + ", bytesAll = " + Arrays.toString(
				bytesAll) + ", bytesAll in Hex = " + Hex.bytesToHex(bytesAll);
		result += ", bytesAll = ";
		for (Byte myByte : bytesAll) {
			result += myByte.toString() + "(" + Hex.byteToHex(myByte) + ") ";
		}
		result += '}';
		return result;
	}
}
