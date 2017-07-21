package happynewmoonwithreport.type;


import happynewmoonwithreport.BytesFile;

import java.nio.charset.StandardCharsets;

/**
 * A String read from the *.wasm file.
 */
public class WasmString {

    private String value;

    /**
     * Construct using a BytesFile and the length in bytes.
     *
     * @param payload
     * @param sizeInBytes
     */
    public WasmString(BytesFile payload, DataTypeNumber sizeInBytes) {
        Integer size = sizeInBytes.integerValue();
        byte[] byteAll = new byte[size];
        for (Integer i = 0; i < size; i++) {
            byteAll[i] = payload.readByte();
        }
        value = new String(byteAll, StandardCharsets.UTF_8);

    }

    /**
     * Used in unit testing.
     *
     * @param value
     */
    public WasmString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WasmString that = (WasmString) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "WasmString{" +
                "value='" + value + '\'' +
                '}';
    }
}
