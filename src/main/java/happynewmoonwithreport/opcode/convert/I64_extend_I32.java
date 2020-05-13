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

package happynewmoonwithreport.opcode.convert;

/**
 * <h1>I64_extend_I32</h1>
 * <p>
 * Extend an I32 to an I64
 * </p>
 * <h2>Source:</h2>
 * <p>
 * <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-extend-s"
 * target="_top"> https://webassembly.github.io/spec/core/exec/numerics.html#op-extend-s
 * </a>
 * </p>
 * extendsM,N(i)
 * <p>
 * Let j be the signed interpretation of i of size M.
 * <p>
 * Return the twos complement of j relative to size N.
 * <p>
 * <h2>Execution:</h2>
 * <a href="https://webassembly.github.io/spec/core/exec/instructions.html#exec-cvtop"
 * target="_top"> https://webassembly.github.io/spec/core/exec/instructions.html#exec-cvtop
 * </a>
 * <p>
 * t2.cvtop_t1_sx?
 * </p>
 *	<ol>
 *		<li>Assert: due to validation, a value of value type t1 is on the top of the stack.</li>
 * 		<li>Pop the value t1.const c1 from the stack.</li>
 * 		<li>If cvtopsx?t1,t2(c1) is defined:
 * 				<ol type="a">
 * 					<li>Let c2 be a possible result of computing cvtopsx?t1,t2(c1).</li>
 * 					<li>Push the value t2.const c2 to the stack.</li>
 * 				</ol>
 * 		</li>
 * 		<li>Else:
 *			<ol type="a">
 *   			<li>Trap.</li>
 *   		</ol>
 *		</li>
 *	</ol>
 */

public class I64_extend_I32 {


}
