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
package happynewmoonwithreport.type;

/**
 * Memory Argument aka memarg
 * contains an U32 offset and a U32 alignment hint
 * <p>
 * <b>Source:</b>
 * <a href="https://webassembly.github.io/spec/core/syntax/instructions.html#syntax-memarg" target="_top">
 * https://webassembly.github.io/spec/core/syntax/instructions.html#syntax-memarg
 * </a> .
 */
public class MemoryArgument {

	private U32 offest;
	private U32 align;

	public MemoryArgument() {
		this.offest = new U32(0L);
		this.align = new U32(0L);
	}

	public MemoryArgument(U32 offest, U32 align) {
		this.offest = offest;
		this.align = align;
	}

	public U32 getOffest() {
		return offest;
	}

	public void setOffest(U32 offest) {
		this.offest = offest;
	}

	public U32 getAlign() {
		return align;
	}

	public void setAlign(U32 align) {
		this.align = align;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("MemoryArgument{");
		sb.append("offest=").append(offest);
		sb.append(", align=").append(align);
		sb.append('}');
		return sb.toString();
	}
}
