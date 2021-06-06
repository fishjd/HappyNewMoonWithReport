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
/**
 * A class to stash code that helps out and doesn't fit anywhere yet.
 *
 * Sorry about the name of the class.
 */
class UtilHappy {

	/**
	 * Format an Integer with its name and hexadecimal value.
	 * @param inputName The name of the value.
	 * @param input The value to format.
	 * @return a String with the name, value and hexadecimal value.
	 */
	static GString formatInteger(String inputName, Integer input) {
		return "${inputName} = ${input} (0x${Integer.toHexString(input).toUpperCase()})"
	}

	/**
	 * Format a Long with its name and hexadecimal value.
	 * @param inputName The name of the value.
	 * @param input The value to format.
	 * @return a String with the name, value and hexadecimal value.
	 */
	static GString formatInteger(String inputName, Long input) {
		return "${inputName} = ${input} (0x${Long.toHexString(input).toUpperCase()})"
	}
}
