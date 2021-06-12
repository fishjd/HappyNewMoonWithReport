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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * Class to read a file and return an Array of bytes.
 */
public class WasmFile {

	String filePath;

	public WasmFile() {
		super();
	}

	public WasmFile(String filePath) {
		this();
		this.filePath = filePath;
	}

	/**
	 * copied from : https://www.mkyong.com/java/how-to-convert-file-into-an-array-of-bytes/
	 *
	 * @return The file as an array of bytes.
	 * @throws IOException on Error.
	 */
	public byte[] bytes() throws IOException {
		FileInputStream fileInputStream = null;
		byte[] bytesArray = null;
		try {
			File file = new File(filePath);
			bytesArray = new byte[(int) file.length()];

			// read file into bytes[]
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bytesArray);
		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}
		return bytesArray;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
