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

/**
 * A unsigned Integer of 7 bits.
 * <br>
 * Stored in the wasm file as a LEB128 variable-length integer, limited to N bits (i.e., the
 * values [0,
 * 2^N-1]), represented by at most ceil(N/7) bytes that may contain padding 0x80
 * bytes.
 * <br>
 * Used to read and write to the wasm file. This project tends to use the 'main' integer types
 * I32, I64, U32,
 * U64.  The recommend use is to convert to a 'main' type as soon as possible.
 * <br>
 * Usage:
 * <pre>
 *      {@code
 *          UInt32 number = new VarUInt7(bytesFile);
 *      }
 * </pre>
 * <br>
 * Source:  <a href="http://webassembly.org/docs/binary-encoding/#varuintn" target="_top">
 * http://webassembly.org/docs/binary-encoding/#varuintn
 * </a>
 */
public final class VarUInt7 extends UInt8 {
	// TODO change to extend UInt32

	@SuppressWarnings("unused")
	private VarUInt7() {
		super();
	}

	public VarUInt7(BytesFile bytesFile) {
		value = convert(bytesFile).longValue();
	}

	/**
	 * Create using a Integer.  Used mainly in testing.
	 *
	 * @param value value
	 */
	public VarUInt7(Integer value) {
		this.value = value.longValue();
	}

	/**
	 * Create using a Byte. Used mainly in testing.
	 *
	 * @param value value
	 */
	public VarUInt7(Byte value) {
		this.value = value.longValue();
	}

	//    public Integer convert(BytesFile bytesFile) {
	//        Integer cur;
	//        Integer count = 0;
	//        Integer result = 0;
	//
	//        do {
	//            cur = bytesFile.readByte() & 0xff;
	//            result |= (cur & 0x7f) << (count * 7);
	//            count++;
	//        } while (((cur & 0x80) != 0) && count < maxBytes());
	//
	//        return result;
	//    }

	@Override
	public Integer maxBits() {
		return 7;
	}


	@Override
	public Long maxValue() {
		return (1L << maxBits()) - 1;
	}


	@Override
	public String toString() {
		return "VarUInt7{" + "value=" + value + "} ";
	}
}
