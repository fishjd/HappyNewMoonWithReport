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
package happynewmoonwithreport.type;

/**
 * An unsigned integer of 64 bits.
 * <p>
 * Use U64 instead.
 */
@Deprecated
public class UInt64 extends U64 {

	public UInt64() {
		super();
	}

	public UInt64(Long value) {
		this.value = value;
	}

	public UInt64(Integer value) {
		this.value = value.longValue();
	}

	public Integer integerValue() {
		checkIfTooLarge();
		return value.intValue();
	}

	public void checkIfTooLarge() {
		if (isBoundByInteger() == false) {
			throw new RuntimeException("Value is too large!");
		}
	}


	/* Override DataTypeNumber */

	@Override
	public Integer maxBits() {
		return 63;  // should be 64 but that is not possible using Java type Long.
	}


	/* override of Object **/

	@Override
	public String toString() {
		return "UInt64{" +
				"value=" + value +
				"} ";
	}
}
