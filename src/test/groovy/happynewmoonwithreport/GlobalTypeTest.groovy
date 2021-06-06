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
package happynewmoonwithreport

import spock.lang.Specification

class GlobalTypeTest extends Specification {
	def "Global Type Valid"() {
		setup: "A valid GlobalType "
		ValueType contentType = new ValueType(ValueType.int32);
		Mutability mutability = new Mutability(Mutability.immutable);
		GlobalType globalType = new GlobalType(contentType, mutability);

		when: ""

		then: "Should be Valid"
		globalType.valid();

	}
}
