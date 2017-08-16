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
     * @throws  IOException on Error.
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
