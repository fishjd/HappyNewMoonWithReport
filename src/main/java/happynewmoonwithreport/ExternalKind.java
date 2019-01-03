/*
 *  Copyright 2017 - 2019 Whole Bean Software, LTD.
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

import happynewmoonwithreport.type.UInt8;

import java.util.HashMap;

/**
 * A single-byte unsigned integer indicating the kind of definition being imported or defined:
 * <pre>
 *  0 indicating a Function import or definition
 *  1 indicating a Table import or definition
 *  2 indicating a Memory import or definition
 *  3 indicating a Global import or definition
 * </pre>
 * <p>
 * Source : <a href= "http://webassembly.org/docs/binary-encoding/#external_kind" target="_top">
 * http://webassembly.org/docs/binary-encoding/#external_kind
 * </a>
 * </p>
 */
public class ExternalKind extends ValueBase {

	public static final String function = "function";
	public static final String table = "table";
	public static final String memory = "memory";
	public static final String global = "global ";

	private void setup() {
		mapAll = new HashMap<>();
		mapAll.put(0, function);
		mapAll.put(1, table);
		mapAll.put(2, memory);
		mapAll.put(3, global);
	}

	private ExternalKind() {
		className = ExternalKind.class.getName();
		setup();
	}

	public ExternalKind(Integer type) {
		this();
		this.type = type;
		this.value = calcValue(type);
	}

	public ExternalKind(UInt8 type) {
		this();
		this.type = type.integerValue();
		this.value = calcValue(this.type);
	}

	public ExternalKind(BytesFile payload) {
		this();
		UInt8 vt = new UInt8(payload);
		this.type = vt.integerValue();
		this.value = calcValue(type);
	}

	public ExternalKind(String value) {
		this();
		this.value = value;
		this.type = calcType(value);
	}

	public UInt8 getTypeUInt8() {
		return new UInt8(type);
	}

}
