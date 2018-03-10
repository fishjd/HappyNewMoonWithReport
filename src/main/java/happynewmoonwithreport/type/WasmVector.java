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
package happynewmoonwithreport.type;


import happynewmoonwithreport.WasmRuntimeException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

/**
 * Vectors are bounded sequences of the form A^n  (or A^*), where the A can either be values or complex constructions. A
 * vector can have at most (2^32)-1 elements.
 * <p>
 * vec(A)::=An(if n &lt; (2^32))
 * <p>
 * Source: <a href="https://webassembly.github.io/spec/syntax/conventions.html#vectors" target="_top" > Vectors</a>
 * <p>
 * Note this implementation can only have 2^31 (Integer.MAX_VALUE) elements.
 * Any attempt to store a larger index will throw an Exception.  The wasm documents specify (2^32)-1  values.
 *
 * @param <Type> may be any Object.   Known to be used with FunctionType, WasmFunction, TableType, ....
 */
public class WasmVector<Type> extends ArrayList<Type> {

	public WasmVector() {
		super();
	}

	public WasmVector(Integer initialCapacity) {
		super(initialCapacity);
	}

	public Type get(DataTypeNumber index) {
		checkIfTooLarge(index);

		return super.get(index.integerValue());
	}

	/**
	 * Find the <i>Type</i> you are looking for.
	 *
	 * @param needle the one to find.
	 *
	 * @return the one you are looking for.
	 *
	 * @throws WasmRuntimeException if not found.
	 */
	public Type get(Type needle) {
		final Iterator<Type> iterator = this.iterator();
		while (iterator.hasNext()) {
			Type item = iterator.next();
			if (needle.equals(item)) {
				return item;
			}
		}
		throw new WasmRuntimeException(UUID.fromString("ea356805-d4d7-4eaa-bd50-2b81ec168a6c"),
				"Item %s was not found", needle);
	}

	public Boolean exists(DataTypeNumber index) {
		checkIfTooLarge(index);
		return (index.integerValue() < size());
	}

	public Type set(DataTypeNumber index, Type element) {
		checkIfTooLarge(index);

		return super.set(index.integerValue(), element);
	}

	private void checkIfTooLarge(DataTypeNumber index) {
		if (index.isBoundByInteger() == false) {
			throw new WasmRuntimeException(UUID.fromString("86f2807d-ab1b-42c0-8342-86407c615e91"),
					"Value to Large for Index.  Index = " + index.longValue());
		}
	}


}
