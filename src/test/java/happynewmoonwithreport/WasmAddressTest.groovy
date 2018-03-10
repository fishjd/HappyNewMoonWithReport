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


import happynewmoonwithreport.WasmAddress
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.type.I32
import spock.lang.Specification

/**
 * Created on 2017-12-28.
 */
class WasmAddressTest extends Specification {
	def "GetAddress"() {
		setup: "Good Address"
		WasmAddress address = new WasmAddress(new I32(5))

		when: ""

		then: ""
		address.getAddress() == 5;

		// expect: ""

		// cleanup: ""

		// where: ""

	}

	def "GetAddress less than zero throws exception"() {

		when: ""
		WasmAddress address = new WasmAddress(new I32(-5))

		then: "Throw less than zero exception "

		WasmRuntimeException exception = thrown();
		exception.getUuid().toString().contains("bfc55b11-467b-4a66-a8e7-70c02432c97a");
	}

	def "SetAddress"() {
		setup: "Good Address"
		WasmAddress address = new WasmAddress(new I32(5))

		when: ""
		address.setAddress(new I32(6))
		then: ""
		address.getAddress() == 6;
	}

	def "SetAddress less than zero throws exception"() {
		setup: "Good Address"
		WasmAddress address = new WasmAddress(new I32(0))

		when: "Set to a negative number "
		address.setAddress(new I32(-5));

		then: "Throw less than zero exception "
		WasmRuntimeException exception = thrown();
		exception.getUuid().toString().contains("8cde5e2d-58ae-4b84-8955-406e6a97cfc0");
	}

}
