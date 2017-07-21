package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VarInt7Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    public void assertArrayEqualsJDH(byte[] expected, byte[] actual) {
        Integer length = Math.min(expected.length, actual.length);
        Boolean equal = true;
        for (int i = 0; i < length; i++) {
            if (expected[i] == 0 || actual[i] == 0) {
                break;
            }
            if (expected[i] != actual[i]) {
                equal = false;
                throw new AssertionError("Array not equals");
            }
        }
    }




    /**
     * Similarly, either of<br>
     * 0x7e<br>
     * 0xFE 0x7F <br>
     * 0xFE 0xFF 0x7F <br>
     * are well-formed encodings of the value -2.
     * <p>
     * source : https://webassembly.github.io/spec/binary/values.html#integers
     */
    @Test
    public void testReadSignedPaddedNeg2() {
        BytesFile payload = new BytesFile(new byte[]{(byte) 0x7E});
        NumberHelper.assertEqualHex(new VarInt7(-2).longValue(), new VarInt7(payload).longValue());
    }

    @Test
    public void testReadSignedPaddedPositive2() {
        BytesFile payload = new BytesFile(new byte[]{(byte) 0x02});
        NumberHelper.assertEqualHex(new VarInt7(2).longValue(), new VarInt7(payload).longValue());

    }

    @Test
    public void testSize() {
        BytesFile payload = new BytesFile(new byte[]{(byte) 0x03});
    }

	/* @formatter:off	
                        -624485 Decimal
	     1001_10000111_01100101 624485 In raw binary
	     0110_01111000_10011010 Ones Complement (NOT)
	     0110_01111000_10011011 Twos Complement +1
	   011001__1110001__0011011 Split into 7 bit groups 
	  1011001__1110001__0011011 Sign extend to 21 bits.   
	 01011001_11110001_10011011 Add high 1 bits on all but most significant  group to form bytes
	     0x59     0xF1     0x9B Hexadecimal
	     0x9B     0xF1     0x59 Convert to little Endian.   
	 @formatter:on
	**/
}
