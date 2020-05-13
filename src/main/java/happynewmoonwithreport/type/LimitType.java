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

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.Validation;
import happynewmoonwithreport.WasmRuntimeException;

import java.util.UUID;

/**
 * Limit also known as resizable limit.
 * Limits classify the size range of resizeable storage associated with memory types and table types.
 * If no maximum is given, the respective storage can grow to any size.
 * <p>
 * Source : <a href= "http://webassembly.org/docs/binary-encoding/#resizable_limits" target="_top">
 * http://webassembly.org/docs/binary-encoding/#resizable_limits</a>
 * <p>
 * source:  <a href="https://webassembly.github.io/spec/core/syntax/types.html#limits" target="_top">
 * https://webassembly.github.io/spec/core/syntax/types.html#limits
 * </a>
 */
public class LimitType implements Validation {

	/**
	 * does the limit have max?
	 */
	private UInt8 hasMaximum;
	private U32 minimum;
	private U32 maximum;

	private LimitType() {
		maximum = null;
	}

	public LimitType(UInt32 minimum) {
		this();
		this.hasMaximum = new UInt8(0);  // must be zero.
		this.minimum = minimum;
		this.maximum = null;  // not set!
	}

	public LimitType(UInt32 minimum, UInt32 maximum) {
		this();
		this.hasMaximum = new UInt8(1);
		this.minimum = minimum;
		this.maximum = maximum;
	}

	public LimitType(UInt8 hasMaximum, UInt32 minimum) {
		this();
		if (hasMaximum.integerValue() != 0) {
			throw new WasmRuntimeException(UUID.fromString("123ceab2-9d6f-44b6-83ca-49eee187726b"), "Has Maximum must be zero.");
		}
		this.hasMaximum = hasMaximum;
		this.minimum = minimum;
		this.maximum = null;
	}

	public LimitType(U32 hasMaximum, U32 minimum) {
		this();
		if (hasMaximum.integerValue() != 0) {
			throw new WasmRuntimeException(UUID.fromString("eef166ee-db1f-47ff-bacf-5125195b3dca"), "Has Maximum must be zero.");
		}
		this.hasMaximum = new UInt8(hasMaximum);
		this.minimum = minimum;
		this.maximum = null;
	}

	public LimitType(UInt8 hasMaximum, UInt32 minimum, UInt32 maximum) {
		this();
		this.hasMaximum = hasMaximum;
		this.minimum = minimum;
		this.maximum = maximum;
	}


	public LimitType(BytesFile payload) {
		this();
		hasMaximum = new VarUInt1(payload);
		minimum = new VarUInt32(payload);
		if (hasMaximum.booleanValue() == true) {
			maximum = new VarUInt32(payload);
		}
	}

	/**
	 * Does the limit set the maximum value?   Limits without a maximum are legal.
	 *
	 * @return hasMaximum   nonZero indicates true.
	 */
	public UInt8 hasMaximum() {
		return hasMaximum;
	}

	/**
	 * Get the Minimum.
	 *
	 * @return minimum
	 */
	public U32 minimum() {
		return minimum;
	}

	/* code in Javadoc source: https://stackoverflow.com/questions/541920/multiple-line-code-example-in-javadoc-comment
	 */

	/**
	 * Get the Maximum
	 * <p>
	 * Usage:
	 * <pre>
	 * {@code
	 * if (hasMaximum()) {
	 *      max = maximum();
	 * }
	 * }
	 * </pre>
	 * <p>
	 * Throws RuntimeException is maximum is not set.
	 *
	 * @return maximum
	 */
	public U32 maximum() {
		if (maximum == null) {
			throw new RuntimeException("Calling maximum when it is not set!");
		}
		assert (maximum != null);
		return maximum;
	}

	/**
	 * Limits must have meaningful bounds.
	 * <ul>
	 * <li>If the maximum m? is not empty, then its value must not be smaller than n</li>
	 * <li>Then the limit is valid.</li>
	 * </ul>
	 * source:  <a href="https://webassembly.github.io/spec/core/valid/types.html#limits" target="_top">
	 * https://webassembly.github.io/spec/core/valid/types.html#limits</a>
	 *
	 * @return true if limit is valid.
	 */
	@Override
	public Boolean valid() {
		Boolean result = false;

		if (hasMaximum.booleanValue() == true) {
			result = minimum.integerValue() <= maximum.integerValue();
		} else {
			return true;
		}
		return result;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("LimitType{");
		sb.append("hasMaximum=").append(hasMaximum);
		sb.append(", minimum=").append(minimum);
		sb.append(", maximum=").append(maximum);
		sb.append(", valid=").append(valid());
		sb.append('}');
		return sb.toString();
	}
}
