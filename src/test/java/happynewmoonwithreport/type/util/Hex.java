package happynewmoonwithreport.type.util;

/**
 * Created by James Haring on 2017-07-19. Copyright 2017 Whole Bean Software Limited
 */

public class Hex {

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            sb.append("0x");
            sb.append(hexArray[v >>> 4]);
            sb.append(hexArray[v & 0x0F]);
            sb.append(" ");
        }
        return  sb.toString();
    }
}
