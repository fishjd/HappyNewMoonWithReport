```wasm
       0  1  2  3  4  5  6  7  8  9  0A 0B 0C 0D 0E 0F
       -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
0000 | 00 61 73 6D 01 00 00 00 01 05 01 60 00 01 7F 03  
0010 | 02 01 00 07 0C 01 08 73 69 6E 67 75 6C 61 72 00  
0020 | 00 0A 09 01 07 00 02 7F 41 0F 0B 0B 00 17 04 6E  
0030 | 61 6D 65 01 0B 01 00 08 73 69 6E 67 75 6C 61 72  
0040 | 02 03 01 00 00                                   

Addr    Dec Hec     // Comment
0x00    00  0x00     // Magic Number
0x01    97  0x61     // Magic Number 
0x02    115 0x73     // Magic Number   
0x03    109 0x6D     // Magic Number
0x04    01  0x01     // Version Number 1  byte 4   00 00 00 01
0x05    00  0x00	// Version Number 1  byte 3       
0x06    00  0x00	// Version Number 1  byte 2
0x07    00  0x00	// Version Number 1  byte 1
// Type Section Header
0x08    01  0x01    // Section = 1 (Type) 
0x09    05  0x05    // Payload Length = 5
// Type Section      
0x0A    01  0x01    // count = 1
0x0B    96  0x60    // Function Type
0x0C    00  0x00    // Parameter Count = 0 
0x0D    01  0x01    // Return Count = 1
0x0E    127 0x7F    // Return Type = -127 Int32
// End Type Section        

// Function Section header
0x0F    03  0x03    // Section = 3 (Function) 
0x10    02  0x02    // Payload Length = 2
// Function Section 
0x11    01  0x01    // count = 1
0x12    00  0x00    // Type = 0   
// End Function Section 

// Begin Export Section Header
0x13    07  0x07    // Section = 7 (Export)
0x14    12  0x0c    // Payload Length = 12 
// Begin Export Section
0x15    01  0x01    // Count
0x16    08  0x08    // Field Length
0x17    115 0x73    // s    
0x18    105 0x69    // i  
0x19    110 0x6E    // n
0x1a    103 0x67    // g
0x1B    117 0x75    // u
0x1C    108 0x6C    // l
0x1D    97  0x61    // a
0x1E    114 0x72    // r
0x1F    0   0x00    // external kind = 0
0x20    0   0x00    // index = 0
// End Export section
 
// Start Code Section header
0x21	10	(0x0A)	// Code Section
0x22	9	(0x09)	// Code Size  9 bytes
// Start Code Section
0x23	1	(0x01)	// Count of Functions
0x24	7	(0x07)	// Function Body: Size   7 bytes. 
0x25	00	(0x00)	// Function Body: local function Count.  
					// No local functions
```
// Code Section of function 'singular'
// Starting at location 0x26
```wasm
0x26    2   (0x02)  // Branch Opcode
0x27    127 (0x7F)  // Data Type of return value I32  -1
0x28    65  (0x41)  // I32 Constant opcode. Places constant on the stack
0x29    15  (0x0F)  // Value of the I32 constant  '15' 
0x2A    11  (0x0B)  // End opcode.  End of function¿
0x2B    11  (0x0B)  // End opcode.  End of Code Section¿
// End Code Section

// Start Custom Section Header
0x2C    00  (0x00)	// Custom Section 
0x2D	 23  (0x17)	// Custom Section Length = 23

// Start of Custom Section 
0x2E	0      4	(0X04) // Size of Name = 4
0x2F	1      110	(0x6E)	// 'n'
0x30	2      97	(0x61)	// 'a'
0x31	3      109	(0x6D)	// 'm'
0x32	4      101	(0x65) // 'e'
```
The rest of the file is not parsed in the custom section of Happy Blue Moon with Report. 
```
0x33	5      1	(0X01)	
0x34	6      11	(0X0B)	// ¿Call Indirect x?
0x35	7      1	(0X01)	
0x36	8      0	(0X00)	
0x37	9      8	(0X08)	// ¿length?
0x38	10     115	(0X73)	// 's'
0x39	11     105	(0X69)	// 'i' 
0x3A	12     110	(0X6E)	// 'n'
0x3B	13     103	(0X67)	// 'g'
0x3C	14     117	(0X75)	// 'u'
0x3D	15     109	(0X6C)	// 'l'
0x3E	16     97	(0X61)	// 'a'
0x3F	17     114	(0X72)	// 'r'
0x40	18     2	(0X02)	
0x41	19     3	(0X03)	
0x42	20     1	(0X01)	
0x43	21     0	(0X00)	
0x44	22     0	(0x00)	// Last byte of file.   

       0  1  2  3  4  5  6  7  8  9  0A 0B 0C 0D 0E 0F
       -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
0000 | 00 61 73 6D 01 00 00 00 01 05 01 60 00 01 7F 03  
0010 | 02 01 00 07 0C 01 08 73 69 6E 67 75 6C 61 72 00  
0020 | 00 0A 09 01 07 00 02 7F 41 0F 0B 0B 00 17 04 6E  
0030 | 61 6D 65 01 0B 01 00 08 73 69 6E 67 75 6C 61 72  
0040 | 02 03 01 00 00  

