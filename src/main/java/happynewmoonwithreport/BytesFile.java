package happynewmoonwithreport;

import happynewmoonwithreport.type.utility.Hex;

import java.util.Arrays;

/**
 * An byte array with an index. Created by James Haring on 2017-07-18.
 */
public class BytesFile {
    private byte[] bytesAll;
    private Integer index = 0;

    public BytesFile(byte[] bytesAll) {
        this(bytesAll, 0);
    }

//    public BytesFile(byte... bytes) {
//        this.bytesAll = bytes;
//    }

    public BytesFile(byte[] bytesAll, Integer index) {
        this.bytesAll = bytesAll;
        this.index = index;
    }

    public byte readByte() {
        byte result = bytesAll[index];
        index++;
        return result;
    }

    public BytesFile copy(Integer length) {
        byte[] tempBytesAll = Arrays.copyOfRange(bytesAll, index, index + length);
        index += length;
        return new BytesFile(tempBytesAll);
    }

    public byte[] getBytesAll() {
        return bytesAll;
    }

    public void setBytesAll(byte[] bytesAll) {
        this.bytesAll = bytesAll;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public void addToIndex(Integer size) {
        this.index += size;
    }

    public Boolean isNotEmpty() {
        return (index < bytesAll.length);
    }

    public Boolean atEndOfFile() {
        return (index == bytesAll.length);
    }

    public Boolean longEnough(Integer size) {
        return (index + size <= bytesAll.length);
    }

    @Override
    public String toString() {
        return "BytesFile{" +
                "current byte = " +  Hex.byteToHex(bytesAll[index]) +
                ", index = " + index +
                ", bytesAll = " + Arrays.toString(bytesAll) +
                ", bytesAll in Hex = " + Hex.bytesToHex(bytesAll) +
                '}';
    }
}
