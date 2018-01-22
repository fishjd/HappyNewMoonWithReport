/*
 *  Copyright 2018 Whole Bean Software, LTD.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package happynewmoonwithreport.opcode;

import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.type.I32;
import happynewmoonwithreport.util.converter.StringToIntegerConverter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created on 2018-01-20.
 */
public class I32_eqTest5 {

    private WasmInstanceInterface instance;
    private I32_eq function;


    @BeforeEach
    void setUp() {
        instance = new WasmInstanceStub();
        function = new I32_eq(instance);
    }

    @Test
    void execute() {
        int val1 = 3;
        int val2 = 3;
        instance.stack().push(new I32(val1));
        instance.stack().push(new I32(val2));
        function.execute();
        I32 result = (I32) instance.stack().pop();
        assertEquals(new I32(1), result);
    }


    @CsvSource({
            // val1         , val2         , expected
            "  3            , 3            , 1"
            , "3            , 0            , 0"
            , "0x0FFF_FFFF  , 0x0FFF_FFFE  , 0"
            , "0x0FFF_FFFF  , 0x0FFF_FFFF  , 1"
            , "-0x0FFF_FFFF , -0x0FFF_FFFF , 1"
            , "0x7FFF_FFFF  , 0x7FFF_FFFF  , 1"
            , "-0x7FFF_FFFF , -0x7FFF_FFFF , 1"
    })
    @ParameterizedTest(name = "index = {index} execute( val1 = {0}, val2 = {1}, expected = {2} )")
    void execute(
            @ConvertWith(StringToIntegerConverter.class) Integer val1, // Note: StringToIntegerConverter only needed if using hexadecimal or octal constants.
            @ConvertWith(StringToIntegerConverter.class) Integer val2, // Note: StringToIntegerConverter only needed if using hexadecimal or octal constants.
            Integer expected) {

        // setup push two operands on stack
        instance.stack().push(new I32(val1));
        instance.stack().push(new I32(val2));

        // run
        function.execute();

        // validate
        I32 result = (I32) instance.stack().pop();

        // jUnit Format
        assertEquals(new I32(expected), result);
        // AssertJ Format
        assertThat(new I32(expected)).isEqualTo(result);
    }

    @AfterEach
    void tearDown() {
    }
}