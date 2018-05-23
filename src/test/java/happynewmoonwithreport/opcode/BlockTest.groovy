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
package happynewmoonwithreport.opcode

import happynewmoonwithreport.BytesFile
import happynewmoonwithreport.ValueType
import happynewmoonwithreport.WasmLabel
import spock.lang.Specification
/**
 * Created on 2017-09-23.
 */
class BlockTest extends Specification {



	def "Execute"() {
		setup: "A instance with the code set "

		WasmInstanceStub instance = new WasmInstanceStub();
		byte[] byteAll = [
				(byte) 0x40,  //  result type 'empty'
				// everything else is filler
				(byte) 0x20, (byte) 0x00, (byte) 0x41, (byte) 0x01, (byte) 0x48];
		BytesFile code = new BytesFile(byteAll);
		instance.setCode(code);

		when: "run the opcode"
		Block block = new Block(instance);
		block.execute();

		then: "The stack contains a Label "
		instance.stack().peek().getClass() == WasmLabel;

		WasmLabel label = instance.stack().pop()
		1 == label.returnTypeAll.size()
		ValueType.emptyBlock == label.returnTypeAll.get(0).value
	}
}
