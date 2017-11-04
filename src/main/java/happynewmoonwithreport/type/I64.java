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
public abstract  class I64 extends Int<Long> {

    public I64() {
        super();
    }

//    public I64(Integer value) {
//        super(value);
//    }
//
//    public I64(Long value) {
//        super(value);
//    }


}
