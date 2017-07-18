package happynewmoonwithreport;

import happynewmoonwithreport.type.VarInt7;
import happynewmoonwithreport.type.VarUInt1;
import happynewmoonwithreport.type.VarUInt32;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProcessTypeSectionTest {

    FunctionSignature functionSignature;

    @Before
    public void setUp() throws Exception {
        functionSignature = new FunctionSignature();
        assertNotNull(functionSignature);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCount() {
        byte[] payload = {(byte) 0x01, (byte) 0x60, (byte) 0x02, (byte) 0x7F, (byte) 0x7F, (byte) 0x01, (byte) 0x7F};
        functionSignature.instantiate(payload);
        assertEquals(1,functionSignature.getFunctionSignitures().size());
        final ArrayList<FunctionType> functionSignatureAll = functionSignature.getFunctionSignitures();
        FunctionType functionType = functionSignatureAll.get(0);
        assertEquals(ValueType.func,  functionType.getForm());
        assertEquals(new VarUInt32(2),  functionType.getParamCount());
        assertEquals(new VarUInt1(1),  functionType.getReturnCount());

        assertEquals( new VarUInt32(2), functionType.getParamCount());
        assertEquals( new VarUInt1(1), functionType.getReturnCount());

        assertEquals(2, functionType.getParamTypeAll().size());
        assertEquals(1, functionType.getReturnTypeAll().size());

        assertEquals( ValueType.int32  , functionType.getParamTypeAll().get(0));
        assertEquals( ValueType.int32  , functionType.getParamTypeAll().get(1));
        assertEquals( ValueType.int32  , functionType.getReturnTypeAll().get(0));


    }

}
