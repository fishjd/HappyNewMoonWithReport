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


import java.util.HashMap;

import happynewmoonwithreport.type.VarUInt1;

/**
 * Represent the Mutability flag.
 * <br>
 * <br>
 * Source:  <a href="http://webassembly.org/docs/binary-encoding/#global_type" target="_top">
 * http://webassembly.org/docs/binary-encoding/#global_type
 * </a>
 */
public class Mutability extends ValueBase implements Validation {

	public static final String immutable = "immutable";
	public static final String mutable = "mutable";

	public void setup() {
		mapAll = new HashMap<>();
		mapAll.put(0, immutable);
		mapAll.put(1, mutable);
	}

	private Mutability() {
		className = Mutability.class.getName();
		setup();
	}

	public Mutability(Integer type) {
		this();
		this.type = type;
		this.value = calcValue(type);
	}

	public Mutability(VarUInt1 type) {
		this();
		this.type = type.integerValue();
		this.value = calcValue(this.type);
	}

	public Mutability(BytesFile payload) {
		this();
		VarUInt1 vt = new VarUInt1(payload);
		this.type = vt.integerValue();
		this.value = calcValue(type);
	}

	public Mutability(String value) {
		this();
		this.value = value;
		this.type = calcType(value);
	}

}