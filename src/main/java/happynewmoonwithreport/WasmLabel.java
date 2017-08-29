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
package happynewmoonwithreport;

import happynewmoonwithreport.type.DataTypeNumber;
import happynewmoonwithreport.type.WasmVector;

/**
 * Labels carry an argument arity n and their associated branch target, which is expressed syntactically as an
 * instruction sequence:
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/exec/runtime.html#syntax-label" target="_top">
 * https://webassembly.github.io/spec/exec/runtime.html#syntax-label
 * </a>
 */
public class WasmLabel {
    /**
     * The arity,  The types of the return values
     */
    private WasmVector<DataTypeNumber> returnTypeAll;

}
