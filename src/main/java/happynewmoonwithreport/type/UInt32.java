/*
 *  Copyright 2017 Whole Bean Software, LTD.
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
 * An unsigned integer of 32 bits.  In what ever order (big endian or little endian) Java uses.
 * Usually it is Big Endian.
 */
public class UInt32 extends U32<Long> implements DataTypeNumber<Long> {

    public Long value;

    public UInt32() {
        super();
    }

    public UInt32(BytesFile bytesFile) {
        assert (bytesFile.longEnough(minBytes()));
        value = convert(bytesFile);
    }

    public UInt32(byte in1, byte in2, byte in3, byte in4) {
        byte[] byteAll = new byte[]{in1, in2, in3, in4};
        BytesFile bytesFile = new BytesFile(byteAll);
        value = convert(bytesFile);
    }

    public UInt32(byte[] in) {
        if (minBytes() < in.length) {
            throw new IllegalArgumentException("Must be length of 4");
        }
        BytesFile bytesFile = new BytesFile(in);
        value = convert(bytesFile);
    }

    public UInt32(Long value) {
        assertInRange(value);
        this.value = value;
    }

    public UInt32(Integer value) {
        this.value = value.longValue();
    }

    public Long convert(BytesFile bytesFile) {
        Long result = 0L;
        // little Endian!
        for (Integer i = 0; i < maxBits(); i = i + 8) {
            result += Byte.toUnsignedLong(bytesFile.readByte()) << i;
        }
        return result;
    }

    @Override
    public Integer integerValue() {
        checkIfTooLarge();
        return value.intValue();
    }

    @Override
    public Boolean isBoundByInteger() {
        return (Integer.MIN_VALUE <= value && value <= Integer.MAX_VALUE);
    }

    private void assertInRange(Long value) {
        if ((minValue() <= value) == false) {
            throw new IllegalArgumentException("87765c72-a4b0-437f-ae27-9b57e702dc50 " + "Value " + value + " must be greater or equal to " + minValue());
        }
        if ((value <= maxValue()) == false) {
            throw new IllegalArgumentException("f4ac4150-12c7-40c1-bd25-47f5dc4a28ba " + "Value " + value + " must be less than or equal to " + maxValue());
        }
    }


    public void checkIfTooLarge() {
        if (isBoundByInteger() == false) {
            throw new RuntimeException("Value is too large!");
        }
    }

    /**
     * Return a signed Int32.
     *
     * @return a signed Int32.
     */
    public S32 signed() {
        return new S32(value.intValue());
    }

    /**
     * Return a unsigned UInt32.
     *
     * @return a unsigned UInt32 value.
     */
    public UInt32 unsigned() {
        // UInt32 is unsigned so return self/this.
        return this;
    }


	/* Override DataTypeNumber */

    @Override
    public Integer maxBits() {
        return 32;
    }

    @Override
    public Long minValue() {
        return 0L;
    }

    @Override
    public Long maxValue() {
        return (1L << maxBits()) - 1;
    }

	/* override of Object **/

    @Override
    public String toString() {
        return "UInt32{" +
                "value=" + value +
                "} ";
    }
}
