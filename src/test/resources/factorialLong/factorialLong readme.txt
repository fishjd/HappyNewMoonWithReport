



Section 10 'Code' the 0x0A at 0x56

0x5c: 01  // function Count
0x5d Code 0x01: B4 80 80 00   // i32 value 52(0x34)    body size of function 0
Code 0x06: 01  // i32 value 1(0x01)  local count
Code 0x07: 02  // i32 value 2(0x02)  local Entry count
Code 0x08: 7F  // valueType "int32"

// Function 1 Start
0x65 Code 0x09:
41 01 21 02 02 40 20 00 41 01 48
0D 00 41 01 21 02 03 40 20 02 20 00 6C 21 02 20
00 41 01 4A 21 01 20 00 41 7F 6A 21 00 20 01 0D
00 0B 0B 20 02
0B // End Byte
// Function 1 End
// End of File

Function 1
0x00: 41 01     // i32 constant 0x01
0x02: 21 02     // set local variable 0x02;
0x04: 02 40     // Block result type "empty block"
0x06  20 00     // get_local 0x00
0x08  41 01     // i32 const 0x01
0x0A: 48        // i32_lts
0x0B: 0D 00     // branch to label 0
0x0C: 41 01     // i32 const 0x01
0x0E: 21 02     // set_local 0x02
0x10: 03 40     // Loop result type "empty block"
0x12: 20 02     // get_local 0x02
0x14: 20 00     // get_local 0x00
0x16: 6C        // i32 Multiply
0x17: 21 02     // set_local 0x02
0x19: 20 00     // get_local 0x00
0x1B: 41 01     // i32 const 0x01
0x1C: 4A        // i32_gt_s
0x1E: 21 01     // set_local 0x01
0x21: 20 00     // get_local 0x00
0x23: 41 7F     // i32.const 0x7F -1
0x25: 6A        // i32.add
0x26: 21 00     // set_local 0x00
0x28: 20 01     // get_local 0x01
0x2A: 0D 00     // branch_if 0x00
0x2D: 0B        // end of block
0x2E: 0B        // end of block
0x30: 20 02     // get local 2
0x31: 0B        // end of block



Function 1
41 01   // i32 constant of 1
B4 80 80
80 00 01 02 7F
41 01   // i32Constant  (1)  location 0x65  Start of function
21 02   // SetLocal (2)
02 40 20 00 41 01 48
0D 00
41 01 21 02 03 40 20 02 20 00 6C 21 02 20
00 41 01 4A 21 01 20 00 41 7F 6A 21 00 20 01 0D
00 0B 0B 20 02 0B