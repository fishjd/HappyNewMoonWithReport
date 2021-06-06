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
package happynewmoonwithreport;


import java.util.Map;

/**
 * An extension of java.Enum all entries contain a Key and Value.   Constructors provided for
 * both key and value.
 */
public class ValueBase implements Validation {

	protected String className;
	protected Integer type;
	protected String value;

	protected static Map<Integer, String> mapAll;

	/**
	 * get the value
	 *
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	public Integer getType() {
		return type;
	}

	/**
	 * Find the value and set type and value.  Used in constructors.
	 *
	 * @param value value
	 * @return the Key.
	 */
	protected Integer calcType(String value) {
		Integer result = null;
		Boolean found = false;
		for (Map.Entry<Integer, String> entry : mapAll.entrySet()) {
			if (value.equals(entry.getValue())) {
				result = entry.getKey();
				found = true;
			}
		}
		if (found == false) {
			throw new RuntimeException(className + " " + value + " not valid/found");
		}
		return result;
	}

	/**
	 * Find the value.
	 *
	 * @param input what value to find.
	 * @return Value.
	 */
	protected String calcValue(Integer input) {
		String result = mapAll.get(input);
		if (result == null) {
			throw new RuntimeException(
				"Type in " + className + " is not valid type = " + type + " hex = 0x"
				+ Integer.toHexString(input));
		}
		return result;
	}

	@Override
	public Boolean valid() {
		Boolean result = false;
		result = mapAll.containsKey(type);
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		ValueBase that = (ValueBase) o;

		return type.equals(that.type);
	}

	@Override
	public int hashCode() {
		return type.hashCode();
	}

	@Override
	public String toString() {
		return className + "{" + "type = " + type + ", value = " + value + '}';
	}

}
