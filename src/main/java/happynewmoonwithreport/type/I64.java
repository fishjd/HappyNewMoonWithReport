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

/**
 * I64 is the WebAssembly runtime 64 bit Integer.  It can be interpreted as either signed or unsigned.
 * <p>
 * source https://webassembly.github.io/spec/text/values.html#integers
 */
public class I64 extends Int {
    protected Long value;

    public I64() {
        super();
    }

    public I64 (Integer value) {
        this.value = value.longValue();
    }

    @Override
    public Integer maxBits() {
        return 64;
    }

    @Override
    public Long minValue() {
        Long minValue = -1L * (1L << (maxBits() - 1L));
        return minValue;

    }

    @Override
    public Long maxValue() {
        Long maxValue = (1L << (maxBits() - 1L)) - 1L;
        return maxValue;
    }

    @Override
    public Boolean isBoundByInteger() {
        return (Integer.MIN_VALUE <= value.longValue() && value.longValue() <= Integer.MAX_VALUE);
    }

    @Override
    public Boolean booleanValue() {
        return value != 0;
    }

    @Override
    public Byte byteValue() {
        return value.byteValue();
    }

    @Override
    public Integer integerValue() {
        return value.intValue();
    }

    @Override
    public Long longValue() {
        return value.longValue();
    }


}
