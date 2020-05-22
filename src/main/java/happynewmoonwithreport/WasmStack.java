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
package happynewmoonwithreport;

import java.util.Stack;
import java.util.UUID;

import happynewmoonwithreport.type.DataTypeNumber;

/**
 * This is the web assembly runtime stack.
 * <p>
 * May contain {@link happynewmoonwithreport.type.DataTypeNumber} Int32,Int64,UInt32,UInt64,F32,
 * F64... or {@link
 * WasmLabel}
 */
public class WasmStack<StackType> extends Stack<StackType> {

	@Override
	public StackType push(StackType item) {
		if (typeOK(item) == false) {
			throw new WasmRuntimeException(UUID.fromString("12f47ca5-6313-4610-9616-ef10ef3861ee"),
				"Item is not of correct type for Stack.  Item = " + item.toString());
		}
		return super.push(item);
	}

	//    @Override
	//    public synchronized StackType pop() {
	//        return super.pop();
	//    }
	//
	//    @Override
	//    public synchronized StackType peek() {
	//        return super.peek();
	//    }

	/**
	 * Peek at the element at index
	 *
	 * @param index <p>
	 *              <code>Index  = 0 </code> top of the stack, ie the most recent value pushed on
	 *              the stack. .
	 *              <p>
	 *              <code>index =  1</code> is the second item on the stack.
	 *              <p>
	 *              note: <code> peek(0) </code>  is the same as <code>peek()</code>
	 *              </p>
	 * @return the element at index.
	 */
	public StackType peek(Integer index) {
		Integer stackIndex = size() - index - 1;
		return get(stackIndex);
	}

	public Boolean typeOK(Object item) {
		Boolean result = false;
		result |= item instanceof DataTypeNumber;
		result |= item instanceof WasmLabel;
		return result;
	}
}
