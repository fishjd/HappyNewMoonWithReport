package happynewmoonwithreport;

import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author James
 */
public class Wasm {
    public Wasm() {
        super();
    }

    private BytesFile bytesFile;
    private UInt32 magicNumber;
    private UInt32 version;
    Integer index = 0;

    SectionType sectionType = null;
    SectionFunction sectionFunction = null;
    SectionTable sectionTable = null;
    SectionMemory sectionMemory = null;
    SectionGlobal sectionGlobal = null;
    SectionExport sectionExport = null;

    public Wasm(String fileName) {
        try {
            byte[] bytesAll = readBytesFromFile(fileName);
            bytesFile = new BytesFile(bytesAll);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void instantiate() throws Exception {

        SectionCode sectionCode;
        VarUInt32 u32PayloadLength;
        /**
         * payloadLength needs to be a java type as it is used in math (+).  Should be Long but copyOfRange only handles int.
         */
        Integer payloadLength;
        VarUInt32 nameLength = new VarUInt32(0L);
        Integer sizeOFNameLength = 0;

        magicNumber = readMagicNumber();
        checkMagicNumber();
        version = readVersion();

        while (bytesFile.atEndOfFile() == false) {
            // Section Code
            sectionCode = readSectionCode();

            // Payload Length
            u32PayloadLength = new VarUInt32(bytesFile);

            payloadLength = u32PayloadLength.integerValue();
            // ¿ Named Section ?
            if (sectionCode.getType() == 0) {
                // TODO
            }
            payloadLength = payloadLength - nameLength.integerValue() - sizeOFNameLength;
            BytesFile payload = bytesFile.copy(payloadLength);
            switch (sectionCode.getValue()) {
                case SectionCode.TYPE:
                    sectionType = new SectionType();
                    sectionType.instantiate(payload);
                    break;
                case SectionCode.FUNCTION:
                    sectionFunction = new SectionFunction();
                    sectionFunction.instantiate(payload);
                    break;
                case SectionCode.TABLE:
                    sectionTable = new SectionTable();
                    sectionTable.instantiate(payload);
                    break;
                case SectionCode.MEMORY:
                    sectionMemory = new SectionMemory();
                    sectionMemory.instantiate(payload);
                    break;
                case SectionCode.GLOBAL:
                    sectionGlobal = new SectionGlobal();
                    sectionGlobal.instantiate(payload);
                    break;
                case SectionCode.EXPORT:
                    sectionExport = new SectionExport();
                    sectionExport.instantiate(payload);
            }
        }
        assert bytesFile.atEndOfFile() : "File length is not correct";
    }


    private SectionCode readSectionCode() {
        SectionCode result = new SectionCode(bytesFile);

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

    /**
     * copied from : https://www.mkyong.com/java/how-to-convert-file-into-an-array-of-bytes/
     *
     * @param filePath
     * @return
     */
    private static byte[] readBytesFromFile(String filePath) throws IOException {
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

    public UInt32 getVersion() {
        return version;
    }

    public UInt32 getMagicNumber() {
        return magicNumber;
    }

    public SectionType getFunctionSignatures() {

        return sectionType;
    }
}
