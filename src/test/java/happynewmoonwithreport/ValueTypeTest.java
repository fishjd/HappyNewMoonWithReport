package happynewmoonwithreport;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by James Haring on 2017-07-18. Copyright 2017 Whole Bean Software Limited
 */
public class ValueTypeTest {

    @Test
    public void constructorPayloadIndexModified() throws Exception {
        byte[] payload = new byte[]{0x7F};
        Integer index = 0;
        ValueType vtc = new ValueType(payload, index);
        index += vtc.getSize();
        assertContains(vtc.toString(), "int32");
        assertEquals(new Integer(1), index);
    }

    @Test
    public void constructorPayload() throws Exception {
        byte[] payload = new byte[]{0x7F};
        Integer index = 0;
        ValueType vtc = new ValueType(payload, index);
        assertContains(vtc.toString(), "int32");
    }


    @Test
    public void valueOf() throws Exception {
        ValueType vtc = new ValueType(-0x01);
        assertContains(vtc.getValue(), "int32");

        vtc = new ValueType(-0x20);
        assertContains(vtc.getValue(), "func");

    }

    private void assertContains(String aaa, String bbb) {
        assertTrue(aaa.contains(bbb));
    }

}