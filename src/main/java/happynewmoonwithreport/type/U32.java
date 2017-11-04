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
 * An unsigned integer of N bits, represented in the *.wasm file as N/8 bytes in little endian
 * order. N is either 8, 16, or 32.  In the Java code represented by one of the integer types Byte, Integer, Long.
 * <p>
 * Source:  <a href="http://webassembly.org/docs/binary-encoding/#uintn" target="_top">
 * http://webassembly.org/docs/binary-encoding/#uintn
 * </a>
 *
 * @param <ValueType> a type that extends Number. An Integer type ex:  Long, Integer, Short, Byte.
 */
public abstract class U32<Long> extends I32<Number> {
    //
//    /**
//     * The value of the number
//     */
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
//
//    @Override
//    public ValueType value() {
//        return value;
//    }

    // I don't think it is worth adding minValue() and maxValue() if the price
    // is you are required to add the Class<ValueType> as a parameter. It is
    // easier to add in the sub classes.
    //
    // public <ValueType extends Number> ValueType minValue(Class<ValueType>
    // valueType) {
    // return valueType.cast(0);
    // }
    //

    public Boolean isTrue() {
        return value.intValue() != 0;
    }

    public Boolean booleanValue() {
        return value.intValue() != 0;
    }

    @Override
    public byte byteValue() {
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

    /**
     * Does the <code>value</code> lay between Integer.minValue and Integer.maxValue.  i.e
     * <code>Integer.minValue &lt;= value &lt;= Integer.maxValue;</code>
     **/
    @Override
    public Boolean isBoundByInteger() {
        return (Integer.MIN_VALUE <= value.longValue() && value.longValue() <= Integer.MAX_VALUE);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof U32)) return false;

        U32<?> uInt = (U32<?>) o;

        return value.equals(uInt.value);
    }
}
