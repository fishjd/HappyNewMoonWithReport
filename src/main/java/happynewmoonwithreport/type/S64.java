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
 * An signed integer of 64 bits,
 */
public class S64 extends I64 {

	public S64() {
		super();
	}

	public S64(Integer value) {
		this.value = value.longValue();
	}

	public S64(Long value) {
		this.value = value;
	}


	public I32 lessThan(S64 other) {
		I32 result;
		Integer iResult;
		if (value < other.value) {
			iResult = 1;
		} else {
			iResult = 0;
		}
		result = new I32(iResult);
		return result;
	}

	public I32 lessThanEqual(S64 other) {
		I32 result;
		Integer iResult;
		if (value <= other.value) {
			iResult = 1;
		} else {
			iResult = 0;
		}
		result = new I32(iResult);
		return result;
	}

	public I32 greaterThan(S64 other) {
		I32 result;
		Integer iResult;
		if (value > other.value) {
			iResult = 1;
		} else {
			iResult = 0;
		}
		result = new I32(iResult);
		return result;
	}

	public I32 greaterThanEqual(S64 other) {
		I32 result;
		Integer iResult;
		if (value >= other.value) {
			iResult = 1;
		} else {
			iResult = 0;
		}
		result = new I32(iResult);
		return result;
	}


	@Override
	public Integer maxBits() {
		return 64;
	}

}
