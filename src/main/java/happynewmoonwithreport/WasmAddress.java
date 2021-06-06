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

import happynewmoonwithreport.type.I32;
import java.util.UUID;

/**
 * Function instances, table instances, memory instances, and global instances in the store are
 * referenced with
 * abstract addresses. These are simply indices into the respective store component.
* <br>
 * Note:
 * <br>
 * Addresses are dynamic, globally unique references to runtime objects, in contrast to indices,
 * which are static,
 * module-local references to their original definitions. A memory address memaddr
 * <br>
 * denotes the abstract address of a memory instance in the store, not an offset inside a memory
 * instance.
 * <br>
 * There is no specific limit on the number of allocations of store objects, hence logical
 * addresses can be arbitrarily
 * large natural numbers.
 * <br>
 * This class is just the single Integer.  Limited in range from zero to Integer.MAX_VALUE.
 * <br>
 * Source:
 * <a href="https://webassembly.github.io/spec/core/exec/runtime.html#addresses" target="_top">
 * https://webassembly.github.io/spec/core/exec/runtime.html#addresses
 * </a>
 */
public class WasmAddress {
	private Integer address;

	private WasmAddress() {
		super();
	}

	public WasmAddress(Integer address) {
		this();
		if (address < 0) {
			throw new WasmRuntimeException(UUID.fromString("bfc55b11-467b-4a66-a8e7-70c02432c97a"),
				"Address may not be less than zero. Address = %d", address);
		}
		this.address = address;
	}

	public WasmAddress(I32 address) {
		this(address.integerValue());
	}

	public Integer getAddress() {
		return address;
	}

	public void setAddress(Integer address) {
		if (address < 0) {
			throw new WasmRuntimeException(UUID.fromString("8cde5e2d-58ae-4b84-8955-406e6a97cfc0"),
				"Address may not be less than zero. Address = %d", address);
		}
		this.address = address;
	}

	public void setAddress(I32 address) {
		setAddress(address.integerValue());
	}
}
