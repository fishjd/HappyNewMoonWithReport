package happynewmoonwithreport;

import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;
import happynewmoonwithreport.type.VarUInt7;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author James
 */
public class Wasm {
    public Wasm() {
        super();
    }

    private byte[] bytesAll;
    private UInt32 version;
    Integer index = 0;

    FunctionSigniture functionSigniture = null;

    public Wasm(String fileName) {
        bytesAll = readBytesFromFile(fileName);
    }

    public void instantiate() throws Exception {

        VarUInt32 leb32NameLength = new VarUInt32(0L);
        Integer sizeOFNameLenth = 0;
        checkMagicNumber();
        readVersion();

        while (index < bytesAll.length) {
            Integer sectionCode = getSectionCode();
            VarUInt32 leb32PayloadLength = new VarUInt32(bytesAll, index);
            Integer payloadLength = leb32PayloadLength.IntegerValue();
            index += leb32PayloadLength.size();
            if (sectionCode == 0) {
                // name Length
                leb32NameLength = new VarUInt32(bytesAll, index);
                index += leb32NameLength.size();
                // name
                String name = new String(bytesAll, index, leb32NameLength.IntegerValue());
                index += leb32NameLength.value().intValue();
            }
            payloadLength = payloadLength - leb32NameLength.value().intValue() - sizeOFNameLenth;
            byte[] payload = Arrays.copyOfRange(bytesAll, index, index + payloadLength);
            index += payloadLength;
            if (sectionCode == SectionType.type.getValue()) {
                functionSigniture = new FunctionSigniture();
                functionSigniture.instantiate(payload);
            }
        }
        assert index == bytesAll.length : "File length is not correct";
    }

    /**
     * Source:
     * https://github.com/WebAssembly/design/blob/master/BinaryEncoding.md#user-content-module-structure
     *
     * @return
     */
    private Integer getSectionCode() {
        VarUInt7 leb7Result = new VarUInt7(bytesAll[index]);
        Integer result = leb7Result.value();
        index += leb7Result.size();
        return result;
    }

    private void readVersion() {
        version = new UInt32(bytesAll, index);
        index += version.size();
    }

    private void checkMagicNumber() throws Exception {
        Boolean result = true;
        result &= bytesAll[0] == 0x00; // 0x00
        result &= bytesAll[1] == 0x61; // a
        result &= bytesAll[2] == 0x73; // s
        result &= bytesAll[3] == 0x6d; // m
        if (result == false) {
            throw new Exception("Magic Number does not match.  May not be a *.wasm file");
        }
        index += 4;
    }

    /**
     * copied from :
     * https://www.mkyong.com/java/how-to-convert-file-into-an-array-of-bytes/
     *
     * @param filePath
     * @return
     */
    private static byte[] readBytesFromFile(String filePath) {
        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;
        try {

            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];

            // read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytesArray;
    }

    public UInt32 getVersion() {
        return version;
    }

    public FunctionSigniture getFunctionSignitures() {

        return functionSigniture;
    }
}
