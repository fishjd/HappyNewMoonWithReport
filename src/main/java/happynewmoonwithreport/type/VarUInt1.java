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
package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;

/**
 * A unsigned Integer of 1 bits.
 * <p>
 * Stored in the wasm file as a LEB128 variable-length integer, limited to N bits (i.e., the values
 * [0, 2^N-1]), represented by at most ceil(N/7) bytes that may contain padding 0x80 bytes.
 * <p>
 * Used to read and write to the wasm file. This project tends to use the 'main' integer types
 * Int32, Int64, UInt32, UInt64.  The recommend use is to convert to a 'main' type as soon as
 * possible.
 * <p>
 * Usage:
 * <pre>
 *      {@code
 *          UInt32 number = new VarUInt1(bytesFile);
 *      }
 * </pre>
 * <p>
 * Source:  <a href="http://webassembly.org/docs/binary-encoding/#varuintn" target="_top">
 * http://webassembly.org/docs/binary-encoding/#varuintn
 * </a>
 */

public final class VarUInt1 extends UInt8 {

	@SuppressWarnings("unused")
	private VarUInt1() {
		super();
	}

	public VarUInt1(BytesFile bytesFile) {
		value = convert(bytesFile).longValue();
	}

	/**
	 * Create using a Integer.  Used mainly in testing.
	 *
	 * @param value value
	 */
	public VarUInt1(Integer value) {
		this.value = value.longValue();
	}

	/**
	 * Create using a Long.  Used mainly in testing.
	 *
	 * @param value value
	 */
	public VarUInt1(Long value) {
		this.value = value.longValue();
	}

	/**
	 * Create using a Byte.  Used mainly in testing.
	 *
	 * @param value value
	 */
	public VarUInt1(Byte value) {
		this.value = value.longValue();
	}

	//    @Override
	//    public Long convert(BytesFile bytesFile) {
	//        Integer cur;
	//        Integer count = 0;
	//        Long result = 0L;
	//
	//        do {
	//            cur = bytesFile.readByte() & 0xff;
	//            result |= (cur & 0x7f) << (count * 7);
	//            count++;
	//        } while (((cur & 0x80) != 0) && count < maxBytes());
	//
	//        return result;
	//    }

	public Boolean isFalse() {
		return value == 0;
	}

	@Override
	public Integer maxBits() {
		return 1;
	}

	@Override
	public Long minValue() {
		return 0L;
	}

	@Override
	public Long maxValue() {
		return (1 << maxBits()) - 1L;
	}


	@Override
	public String toString() {
		return "VarUInt1{" + "value=" + value + "} ";
	}
}
