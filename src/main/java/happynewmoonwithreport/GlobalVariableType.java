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
package happynewmoonwithreport;

/**
 * <p>
 * Also know as Global Entry
 * </p>
 * Source : <a href= "http://webassembly.org/docs/binary-encoding/#global-entry" target="_top">
 * http://webassembly.org/docs/binary-encoding/#global-entry
 * </a>
 */
public class GlobalVariableType implements Validation {

	/**
	 * may only be "anyFunc" in MVP.
	 */
	private GlobalType type;

	// TODO
	// private ? InitialExpression;

	public GlobalVariableType(BytesFile payload) {
		type = new GlobalType(payload);
	}

	public GlobalType getType() {
		return type;
	}

	@Override
	public Boolean valid() {
		Boolean result = true;
		result &= type.valid();
		return result;
	}
}
