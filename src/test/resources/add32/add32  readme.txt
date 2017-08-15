Add32.wasm

Section 1
Location in file = 0x0E to 0x14
Payload Length = 7
Pay Load =
01 60 02 7F 7F 01 7F
(byte) 0x01, (byte) 0x60, (byte) 0x02, (byte) 0x7F, (byte) 0x7F, (byte) 0x01, (byte) 0x7F

Section 2
Not present.

Section 3  the 3 at 0x15
Payload Length = 2    0x16  to 0x1B
Payload =
0x01  0x00

Section 4 the 4 at 0x1D



Section 5 the 5 at 0x27


Section 10 'Code' the 0x0A at 0x  0x4F
8D 80 80 80 00 01 87 80 80 80 00 00 20 01 20 00 6A 0B

8D 80 80 80 00            // Payload Count 13
//* Payload
01                        // count of functions
//** function Body
87 80 80 80 00            // Body Size 7
00                        // Local Count 0
// *** Code
20 01 20 00 6A
0B                        // End Byte 0x0B
