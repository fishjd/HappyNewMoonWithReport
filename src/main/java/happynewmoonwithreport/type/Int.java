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
 * Web Assembly Integer
 */
public abstract class Int implements DataTypeNumber{


    @Override
    public Integer maxBytes() {
        return maxBits() / 8;
    }

    @Override
    public Integer minBytes() {
        return maxBits() / 8;
    }



    public abstract Boolean booleanValue();

    //    public abstract Byte byteValue();
//    public abstract Short shortValue();



///**
//     * Return a signed
//     *
//     * @return a signed
//     */
//    public abstract Int signedValue();
//
//    /**
//     * Return a unsigned
//     *
//     * @return a s
//     */
//    public abstract Int unsignedValue();


    // public abstract Boolean equals(Int other) ;

}
