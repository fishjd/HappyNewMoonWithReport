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
 * Signed Integer
 *
 * @param
 */
public abstract class I32<ValueType extends Number> extends Int<ValueType> {



//    /**
//     * The value of the number
//     */
//    protected ValueType value;
//
//    @Override
//    public Integer maxBytes() {
//        Integer maxBytes = maxBits() / 8;
//        return maxBytes;
//    }
//
//    @Override
//    public Integer minBytes() {
//        Integer maxBytes = maxBits() / 8;
//        return maxBytes;
//    }

//    @Override
//    public ValueType value() {
//        return value;
//    }
//
//    @Override
//    public byte byteValue() {
//        return value.byteValue();
//    }
//
//    @Override
//    public Integer integerValue() {
//        return value.intValue();
//    }
//
//    @Override
//    public Long longValue() {
//        return value.longValue();
//    }
//
//    @Override
//    public Boolean isBoundByInteger() {
//        return (Integer.MIN_VALUE <= value.longValue() && value.longValue() <= Integer.MAX_VALUE);
//    }
//

//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((value == null) ? 0 : value.hashCode());
//        return result;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof I32)) return false;
//        if (!super.equals(o)) return false;
//
//        I32 i32 = (I32) o;
//
//        return value.equals(i32.value);
//    }
}
