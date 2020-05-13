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

import happynewmoonwithreport.type.S32;
import happynewmoonwithreport.type.VarInt7;

/**
 * The data types in Web Assembly. int32, int64, f32, f64
 * <p>
 * Source : <a href= "http://webassembly.org/docs/semantics/#types" target="_top">
 * http://webassembly.org/docs/semantics/#types
 * </a>
 * </p>
 * <p>
 * Source : <a href="http://webassembly.org/docs/binary-encoding/#language-types" target="_top">
 * http://webassembly.org/docs/binary-encoding/#language-types
 * </a>
 * </p>
 */
public class ValueType extends ValueBase implements Validation {

	public static final String int32 = "int32";
	public static final String int64 = "int64";
	public static final String f32 = "f32";
	public static final String f64 = "f64";
	public static final String anyFunc = "anyFunc";
	public static final String func = "func";
	public static final String emptyBlock = "emptyBlock";


	private void setup() {
		mapAll = new HashMap<>();

		// int32(-0x01)      byte value  0x7F
		mapAll.put(-0x01, int32);
		// int64(-0x02)      byte value  0x7e
		mapAll.put(-0x02, int64);
		// f32(-0x03)        byte value  0x7d
		mapAll.put(-0x03, f32);
		// f64(-0x04)        byte value  0x7C
		mapAll.put(-0x04, f64);
		// anyFunc(-0x10)    byte value  0x70
		mapAll.put(-0x10, anyFunc);
		// func(-0x20)       byte value  0x60
		mapAll.put(-0x20, func);
		// emptyBlock(-0x40) byte value  0x40
		mapAll.put(-0x40, emptyBlock);

	}


	private ValueType() {
		className = Mutability.class.getName();
		setup();
	}

	public ValueType(Integer type) {
		this();
		this.type = type;
		this.value = calcValue(type);
	}

	public ValueType(String value) {
		this();
		this.value = value;
		this.type = calcType(value);
	}

	public ValueType(VarInt7 input) {
		this();
		this.type = input.integerValue();
		this.value = calcValue(this.type);
	}

	public ValueType(BytesFile payload) {
		this();
		S32 vt = new VarInt7(payload);
		this.type = vt.integerValue();
		this.value = calcValue(type);
	}

	@Override
	public Boolean valid() {
		Boolean result = true;

		return result;
	}

	public VarInt7 getTypeVarInt7() {
		return new VarInt7(type);
	}

}
