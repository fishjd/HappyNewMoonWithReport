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
package happynewmoonwithreport;

import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.WasmString;

/**
 * Export Entry - The details of an export.
 * <br>
 * source:  <a href="http://webassembly.org/docs/binary-encoding/#export-entry" target="_top">
 * http://webassembly.org/docs/binary-encoding/#export-entry
 * </a>
 */
public class ExportEntry {

	private UInt32 fieldLength;
	private WasmString fieldName;
	/**
	 * The 'kind' of export.  Function, Memory,
	 */
	private ExternalKind externalKind;
	/**
	 * The index to the table. For example if externalKind is 'function' then this is the index to
	 * the function table
	 */
	private UInt32 index;

	public ExportEntry(BytesFile payload) {
		fieldLength = new VarUInt32(payload);
		fieldName = new WasmString(payload, fieldLength);
		externalKind = new ExternalKind(payload);
		index = new VarUInt32(payload);
	}

	public UInt32 getFieldLength() {
		return fieldLength;
	}

	public WasmString getFieldName() {
		return fieldName;
	}

	public ExternalKind getExternalKind() {
		return externalKind;
	}

	public UInt32 getIndex() {
		return index;
	}

}
