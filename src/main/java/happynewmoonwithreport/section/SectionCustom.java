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
package happynewmoonwithreport.section;

import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.type.UInt8;


/**
 * The encoding of the Custon or 'Named' section:
 * <p>
 * Source <a href="https://webassembly.org/docs/binary-encoding/#name-section" target="_top">
 * http://webassembly.org/docs/binary-encoding/#memory-section
 * </a>
 *
 * <p>
 * Source:
 * <a href="https://webassembly.github.io/spec/core/binary/modules.html#custom-section" target="_top">
 * https://webassembly.github.io/spec/core/binary/modules.html#global-section
 * </a>
 */
public class SectionCustom implements Section {

	public SectionCustom() {
		name = "";
	}

	String name;

	/**
	 * @param payload the input BytesFile.
	 */
	@Override
	public void instantiate(BytesFile payload) {

		try {
			UInt8 sizeOfName = new UInt8(payload);

			final byte[] byteAllName = payload.getBytes(sizeOfName.integerValue());

			name = new String(byteAllName, StandardCharsets.UTF_8);
		} catch (Exception exception) {
			// The wasm spec requires that an error does not cause failure of the module
			// <p>
			// <b>Source:</b>  <a href="https://webassembly.github.io/spec/core/binary/modules
			// .html#custom-section" target="_top">
			// https://webassembly.github.io/spec/core/binary/modules.html#custom-section
			// </a>
			Logger.getLogger(SectionCustom.class.getName())
				.log(Level.WARNING, "552775a5-ca14-459c-8911-20669e7e1f87 "
									+ "Custom Section: unknown Exception.  This does not "
									+ "invalidate the module.",
					exception);

		}

	}

}
