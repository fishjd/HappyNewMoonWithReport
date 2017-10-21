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
 * I32 is the WebAssembly runtime 32 bit Integer.  It can be interpreted as either signed or unsigned.
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/text/values.html#integers" target="_top">
 * https://webassembly.github.io/spec/text/values.html#integers
 * </a>
 */
public class I32 extends UInt32 {

    public I32() {
        super();
    }

    public I32(BytesFile bytesFile) {
        super(bytesFile);
    }

    public I32(byte in1, byte in2, byte in3, byte in4) {
        super(in1, in2, in3, in4);
    }

    public I32(byte[] in) {
        super(in);
    }

    public I32(Long value) {
        super(value);
    }

    public I32(Integer value) {
        super(value);
    }

    public I32(UInt uInt) {
        this.value = uInt.value.longValue();
    }


}
