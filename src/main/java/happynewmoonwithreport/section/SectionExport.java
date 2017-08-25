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
package happynewmoonwithreport.section;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.ExportEntry;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.WasmVector;

/**
 * Export Section.
 * <p>
 * <p>
 * Source <a href="http://webassembly.org/docs/binary-encoding/#export-section" target="_top">
 * http://webassembly.org/docs/binary-encoding/#export-section
 * </a>
 * <p>
 * Source:  <a href="http://webassembly.org/docs/modules/#exports" target="_top">
 * http://webassembly.org/docs/modules/#exports
 * </a>
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/binary/modules.html#export-section" target="_top">
 * https://webassembly.github.io/spec/binary/modules.html#export-section
 * </a>
 */
public class SectionExport implements Section {

    private UInt32 count;
    private WasmVector<ExportEntry> exports;

    /**
     * @param payload the input BytesFile.
     */
    @Override
    public void instantiate(BytesFile payload) {

        //* Count
        count = new VarUInt32(payload);

        //* Entries of Global Variables.
        exports = new WasmVector<>(count.integerValue());
        for (Integer index = 0; index < count.integerValue(); index++) {
            ExportEntry export = new ExportEntry(payload);
            exports.add(index, export);
        }
    }

    public UInt32 getCount() {
        return count;
    }

    public WasmVector<ExportEntry> getExports() {
        return exports;
    }
}
