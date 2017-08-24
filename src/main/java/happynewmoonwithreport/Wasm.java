package happynewmoonwithreport;

import happynewmoonwithreport.section.*;
import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.WasmVector;

import java.io.IOException;
import java.util.ArrayList;

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


    private SectionType sectionType = null;
    private SectionFunction sectionFunction = null;
    private SectionTable sectionTable = null;
    private SectionMemory sectionMemory = null;
    private SectionGlobal sectionGlobal = null;
    private SectionExport sectionExport = null;
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
     * @param fileName The fileName.  This parameter will be used in <code>new File(fileName)</code>
     *
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
     * Source:  <a href="https://github.com/WebAssembly/design/blob/master/JS.md#user-content-webassemblyinstantiate" target="_top">
     * https://github.com/WebAssembly/design/blob/master/JS.md#user-content-webassemblyinstantiate
     * </a>
     * @throws Exception
     */
    public WasmModule instantiate() throws Exception {

        SectionName sectionName;
        UInt32 u32PayloadLength;
        /*
         * payloadLength needs to be a java type as it is used in math (+).  Should be Long but copyOfRange only handles int.
         */
        Integer payloadLength;

        magicNumber = readMagicNumber();
        checkMagicNumber();
        version = readVersion();

        instantiateSections();

        fillExport(sectionExport);

        fillFunction(sectionType, sectionCode);

        module = new WasmModule(
                sectionType.getFunctionSignatures(), //
                functionAll, //
                sectionTable.getTables(),//
                sectionMemory.getMemoryTypeAll(),//
                sectionGlobal.getGlobals(),
                // to do element
                // to do data
                sectionStart.getIndex(),
                sectionExport.getExports()
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
            // ¿ Named Section ?
            if (sectionName.getType() == 0) {
                // TODO
            }
            payloadLength = payloadLength - nameLength.integerValue();
            BytesFile payload = bytesFile.copy(payloadLength);
            switch (sectionName.getValue()) {
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
            }
        }
        assert bytesFile.atEndOfFile() : "File length is not correct";
    }

    /**
     * Returns true if module is valid.   Called after <code>instantiate();</code>
     *
     * <p>
     * Source:  <a href="https://github.com/WebAssembly/design/blob/master/JS.md#user-content-webassemblyvalidate" target="_top">
     * https://github.com/WebAssembly/design/blob/master/JS.md#user-content-webassemblyvalidate
     * </a>
     * <p>
     * Source:  <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/WebAssembly/validate" target="_top">
     * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/WebAssembly/validate
     * </a>
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

        UInt32 magicNumberExpected = new UInt32((byte) 0x00, (byte) 0x61, (byte) 0x73, (byte) 0x6D);
        Boolean result = magicNumber.equals(magicNumberExpected);
        if (result == false) {
            throw new RuntimeException("Magic Number does not match.  May not be a *.wasm file");
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
        functionAll = new WasmVector<>(type.getSize());
        for (Integer index = 0; index < type.getSize(); index++) {
            WasmFunction function = new WasmFunction(new UInt32(index), code.getFunctionAll().get(index));
            functionAll.add(function);
        }
    }

    public UInt32 getVersion() {
        return version;
    }

    public UInt32 getMagicNumber() {
        return magicNumber;
    }

    /**
     * Did the module pass all validation checks?
     * <p>
     * Source:  <a href="https://webassembly.github.io/spec/valid/index.html" target="_top">
     * https://webassembly.github.io/spec/valid/index.html
     * </a>
     *
     * @return true if all validity checks pass.
     */
    public Boolean getValid() {
        return valid;
    }

    public SectionType getFunctionSignatures() {

        return sectionType;
    }

    public ArrayList<ExportEntry> exports() {
        return exportAll;
    }


}
