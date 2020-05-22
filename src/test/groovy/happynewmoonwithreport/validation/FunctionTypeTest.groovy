/*
 *  Copyright 2017 - 2020 Whole Bean Software, LTD.
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
package happynewmoonwithreport.validation

import happynewmoonwithreport.FunctionType
import happynewmoonwithreport.ValueType
import happynewmoonwithreport.type.LimitType
import happynewmoonwithreport.type.UInt32
import happynewmoonwithreport.type.UInt8
import happynewmoonwithreport.type.WasmVector
import spock.lang.Specification

class FunctionTypeTest extends Specification {

	LimitType limits;

	void setup() {

	}

	void cleanup() {
	}


	def "Valid "() {

		given: "Valid function type with one returnType"
		WasmVector<ValueType> paramTypeAll = new WasmVector<>(1);
		paramTypeAll.add(new ValueType(ValueType.int32));

		WasmVector<ValueType> returnTypeAll = new WasmVector<>(1);
		returnTypeAll.add(new ValueType(ValueType.int64));

		FunctionType functionType = new FunctionType(new UInt32(1), paramTypeAll, new UInt8(1), returnTypeAll);


		when: " assert limit is valid. "
		Boolean var = functionType.valid();

		then:
		true == var;
	}


	def "Valid with zero return type "() {

		given: "Valid function type with one returnType"
		WasmVector<ValueType> paramTypeAll = new WasmVector<>(1);
		paramTypeAll.add(new ValueType(ValueType.int32));

		WasmVector<ValueType> returnTypeAll = new WasmVector<>(0);

		FunctionType functionType = new FunctionType(new UInt32(1), paramTypeAll, new UInt8(0), returnTypeAll);


		when: " assert is valid. "
		Boolean var = functionType.valid();

		then:
		true == var;
	}

	def "InValid with two return type "() {

		given: "Valid function type with two returnType"
		WasmVector<ValueType> paramTypeAll = new WasmVector<>(1);
		paramTypeAll.add(new ValueType(ValueType.int32));

		WasmVector<ValueType> returnTypeAll = new WasmVector<>(2);
		returnTypeAll.add(new ValueType(ValueType.int64));
		returnTypeAll.add(new ValueType(ValueType.int64));

		FunctionType functionType = new FunctionType(new UInt32(1), paramTypeAll, new UInt8(2), returnTypeAll);


		when: " assert is Invalid. "
		Boolean isValid = functionType.valid();

		then:
		false == isValid;
	}


}
