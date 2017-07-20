package happynewmoonwithreport;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by James Haring on 2017-07-20. Copyright 2017 Whole Bean Software Limited
 */
public class ElementTypeTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test that the String constructor works.
     * @throws Exception
     */
    @Test
    public void constructorWithString() throws Exception {
        ElementType elementType = new ElementType("anyFunc");
        assertNotNull(elementType);
        assertEquals(new Integer((byte)-0x10), elementType.getType());
        assertEquals("anyFunc", elementType.getValue());

    }

}