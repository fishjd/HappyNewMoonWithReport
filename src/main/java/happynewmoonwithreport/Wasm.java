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

import happynewmoonwithreport.section.*;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.WasmVector;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Start Here, The class to loads a WebAssembly file
 */
public class Wasm {
	public Wasm() {
		super();
		sectionStart = new SectionStartEmpty();
	}

	private WasmModule module;

	private BytesFile bytesFile;
	private UInt32 magicNumber;
	private UInt32 version;
	private Boolean valid;

	private ArrayList<ExportEntry> exportAll;


	/*  Initialize all sections except Start and Code to be empty.
		This may not be to the Wasm specification, but avoids Null pointer exceptions.

		Start Section must be null if not defined.
		Code Section must exist.  A module with out code make no sense.
	 */
	private SectionCustom sectionCustom = new SectionCustom();
	private SectionType sectionType = new SectionType();
	private SectionFunction sectionFunction = new SectionFunction();
	private SectionTable sectionTable = new SectionTable();
	private SectionMemory sectionMemory = new SectionMemory();
	private SectionGlobal sectionGlobal = new SectionGlobal();
	private SectionExport sectionExport = new SectionExport();
	private SectionStart sectionStart = null;
	private SectionCode sectionCode = null;

	/**
	 * <p>
	 * This is a convenience constructor.  Constructors which throw exceptions are
	 * to be used cautiously. Consider using the constructor <code>Wasm(byte[])</code>
	 * </p>
	 * <h2>Use instead</h2>
	 * <pre>
	 * {@code
	 * try {
	 *         WasmFile wasmFile = new WasmFile(fileName);
	 *         byte[] bytesAll = wasmFile.bytes();
	 *         Wasm wasm = new Wasm(bytesAll);
	 *         Boolean valid = wasm.validate();
	 * } catch (IOException ioException) {
	 *
	 * }
	 * }
	 * </pre>
	 *
	 * @param fileName The fileName.  This parameter will be used in <code>new File(fileName)
	 *                 </code>
	 * @throws IOException Thrown if the file does not exist and other reasons.
	 */
	public Wasm(String fileName) throws IOException {
		this();
		WasmFile wasmFile = new WasmFile(fileName);
		byte[] bytesAll = wasmFile.bytes();
		bytesFile = new BytesFile(bytesAll);
	}

	/**
	 * Construct a Wasm module with an array of bytes.
	 *
	 * @param bytesAll An array of bytes that contain the wasm module.
	 */
	public Wasm(byte[] bytesAll) {
		bytesFile = new BytesFile(bytesAll);
	}

	/**
	 * <p>
	 * Source:  <a href="https://github.com/WebAssembly/design/blob/master/JS
	 * .md#user-content-webassemblyinstantiate"
	 * target="_top">
	 * https://github.com/WebAssembly/design/blob/master/JS.md#user-content-webassemblyinstantiate
	 * </a>
	 *
	 * @return Web Assembly Module.
	 */
	public WasmModule instantiate() {

		SectionName sectionName;
		UInt32 u32PayloadLength;
		/*
		 * payloadLength needs to be a java type as it is used in math (+).  Should be Long but
		 * copyOfRange only handles int.
		 */
		Integer payloadLength;

		magicNumber = readMagicNumber();
		checkMagicNumber();
		version = readVersion();

		instantiateSections();

		fillExport(sectionExport);

		fillFunction(sectionType, sectionCode);

		module = new WasmModule(sectionType.getFunctionSignatures(), //
			functionAll, //
			sectionTable.getTables(),//
			sectionMemory.getMemoryTypeAll(),//
			sectionGlobal.getGlobals(),
			// to do element
			// to do data
			sectionStart.getIndex(), sectionExport.getExports()
			// to do import
		);
		return module;
	}

	private void instantiateSections() {
		UInt32 nameLength = new UInt32(0L);
		SectionName sectionName;
		UInt32 u32PayloadLength;
		Integer payloadLength;
		while (bytesFile.atEndOfFile() == false) {
			// Section Code
			sectionName = readSectionName();

			// Payload Length
			u32PayloadLength = new VarUInt32(bytesFile);

			payloadLength = u32PayloadLength.integerValue();

			payloadLength = payloadLength - nameLength.integerValue();
			BytesFile payload = bytesFile.copy(payloadLength);
			switch (sectionName.getValue()) {
				case SectionName.CUSTOM:
					sectionCustom = new SectionCustom();
					sectionCustom.instantiate(payload);
					break;
				case SectionName.TYPE:
					sectionType = new SectionType();
					sectionType.instantiate(payload);
					break;
				case SectionName.FUNCTION:
					sectionFunction = new SectionFunction();
					sectionFunction.instantiate(payload);
					break;
				case SectionName.TABLE:
					sectionTable = new SectionTable();
					sectionTable.instantiate(payload);
					break;
				case SectionName.MEMORY:
					sectionMemory = new SectionMemory();
					sectionMemory.instantiate(payload);
					break;
				case SectionName.GLOBAL:
					sectionGlobal = new SectionGlobal();
					sectionGlobal.instantiate(payload);
					break;
				case SectionName.EXPORT:
					sectionExport = new SectionExport();
					sectionExport.instantiate(payload);
					break;
				case SectionName.START:
					sectionStart = new SectionStart();
					sectionStart.instantiate(payload);
					break;
				case SectionName.CODE:
					sectionCode = new SectionCode();
					sectionCode.instantiate(payload);
					break;
				default:
					throw new WasmRuntimeException(
						UUID.fromString("e737f67f-5935-4c61-a14f-eeb97e393178"),
						"Unknown Section in Module. Section = " + sectionName.getValue());

			}
		}
		assert bytesFile.atEndOfFile() : "File length is not correct";
	}

	/**
	 * Returns true if module is valid.   Call only after <code>instantiate();</code>
	 * <p>
	 * Source:
	 * <a href="https://github.com/WebAssembly/design/blob/master/JS.md#user-content-webassemblyvalidate"
	 * target="_top">
	 * https://github.com/WebAssembly/design/blob/master/JS.md#user-content-webassemblyvalidate
	 * </a>
	 * <p>
	 * Source:
	 * <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/WebAssembly/validate"
	 * target="_top">
	 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects
	 * /WebAssembly/validate
	 * </a>
	 *
	 * @return true if valid.
	 */
	public Boolean validate() {
		valid = module.validation();
		return valid;
	}

	private SectionName readSectionName() {
		SectionName result = new SectionName(bytesFile);
		return result;
	}

	private UInt32 readVersion() {
		UInt32 version = new UInt32(bytesFile);
		return version;
	}

	private UInt32 readMagicNumber() {
		UInt32 magicNumber = new UInt32(bytesFile);
		return magicNumber;
	}

	private void checkMagicNumber() {
		// magicNumberExpected ‘\0asm’ = 1836278016

		UInt32 magicNumberExpected = new UInt32((byte) 0x00, (byte) 0x61, (byte) 0x73,
			(byte) 0x6D);
		Boolean result = magicNumber.equals(magicNumberExpected);
		if (result == false) {
			throw new WasmRuntimeException(UUID.fromString("402c27fb-0633-4191-bb86-8e5b44479230"),
				"Magic Number does not match.  May not be a *.wasm file");
		}
	}

	private void fillExport(SectionExport sectionExport) {
		exportAll = new ArrayList<>(sectionExport.getCount().integerValue());
		final ArrayList<ExportEntry> exportEntryAll = sectionExport.getExports();

		Integer count = 0;
		for (ExportEntry exportEntry : exportEntryAll) {
			exportAll.add(count, exportEntry);
			count++;
		}

	}

	private WasmVector<WasmFunction> functionAll;

	private void fillFunction(SectionType type, SectionCode code) {
		functionAll = new WasmVector<>(type.getCount().integerValue());
		for (Integer index = 0; index < type.getCount().integerValue(); index++) {
			WasmFunction function =
				new WasmFunction(new UInt32(index), code.getFunctionAll().get(index));
			functionAll.add(function);
		}
	}

	public UInt32 getVersion() {
		return version;
	}

	public UInt32 getMagicNumber() {
		return magicNumber;
	}

	public SectionType getFunctionSignatures() {

		return sectionType;
	}

	public ArrayList<ExportEntry> exports() {
		return exportAll;
	}


}
