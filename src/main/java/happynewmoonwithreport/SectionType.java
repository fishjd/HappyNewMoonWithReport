package happynewmoonwithreport;

/*
 * 
 Source 20170709 http://webassembly.org/docs/binary-encoding/#high-level-structure 
Type 		1 	Function signature declarations
Import 		2 	Import declarations
Function 	3 	Function declarations
Table 		4 	Indirect function table and other tables
Memory 		5 	Memory attributes
Global 		6 	Global declarations
Export 		7 	Exports
Start 		8 	Start function declaration
Element 	9 	Elements section
Code 		10 	Function bodies (code)
Data 		11
 */

import happynewmoonwithreport.type.VarUInt7;

/**
 * Enum for the defined section names.
 */
public enum SectionType {
    type(1), //
    importSection(2), // import is a reserved word in Java
    function(3), //
    table(4), //
    memeory(5), //
    global(6), //
    export(7), //
    start(8), //
    element(9), //
    code(10), //
    data(11) //
    ; // <-- the terminating semicolon.

    private Integer value;

    private SectionType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public VarUInt7 getUInt7() {
        return new VarUInt7(value);
    }

}
