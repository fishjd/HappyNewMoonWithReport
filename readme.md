# Happy New Moon with Report

**Happy New Moon with Report** is an open-source implementation of 
[WebAssembly](https://webassembly.org/) written entirely in Java. It is typically used to run or 
test Web Assembly Modules (*.wasm) in Java.

Happy New Moon with Report doesn't concern itself with the production of the WASM binary files; 
these files are produced with another tools such as [Emscripten](https://emscripten.org/), 
[wabt](https://github.com/WebAssembly/wabt) or [binaryen](https://github.com/WebAssembly/binaryen) 
or any of the number of  languages that can output to WebAssembly such as as 
[Rust](https://www.rust-lang.org/).   

Think of Happy New Moon With Report as the [Rhino](https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino) of Web Assembly.   Rhino runs JavaScript in Java; Happy New Moon with Report runs WebAssembly in Java.

Happy New Moon With Report has nothing to do with reporting or fireworks.

For a list of Web Assembly languages see: [Awesome WASM languages](https://github.com/appcypher/awesome-wasm-langs).

## The basic use case is:

```java
Wasm wasm = new Wasm("your Web Assembly Module.wasm");
wasm.exports().yourFunction();
```

### A more concrete example.  

Load `HelloWorld.wasm` file and call the `HelloWorld()` function in Java; 

```java
Wasm wasm = new Wasm("HelloWorld.wasm");
System.out.println(wasm.exports().HelloWorld());
```

## Goals

1. An interpreter of *.wasm files in the Java Language
1. Code in the Web Assembly Specification language.  For example Happy New Moon With Report creates an web 
assembly full I32 object for the Web Assembly I32 data type.  If there is a object in the Web Assembly 
specification there will be an analogous object in Happy New Moon With Report. Conversion to Java 
is 'pressed down' as far a possible in the code.  This should allow 
causal readers of the code to understand it with only a beginning understanding of Java.
1. Every object has links to the Web Assembly Specification.  This will explain what the code is implementing.    
1. Happy New Moon With Report is only an interpreter.  No conversion from text files (*.wast) to modules (*.wasm). 
1. Easily embeddable in your Java project.     
1. Compatible with the Web Assembly Specification.
1. Well commented to help casual readers. 
1. Tested with extensive unit tests.  


### Where to start your journey in the code

#### Wasm.java
The main object is in [Wasm.java](https://github.com/fishjd/HappyNewMoonWithReport/blob/master/src/main/java/happynewmoonwithreport/Wasm.java).  
This opens the wasm module(*.wasm), parses the web assembly sections such as 
* [Type](https://webassembly.github.io/spec/core/binary/modules.html#type-section) 
* [Function](https://webassembly.github.io/spec/core/binary/modules.html#function-section) 
* [Table](https://webassembly.github.io/spec/core/binary/modules.html#table-section)
* all the other [sections](https://webassembly.github.io/spec/core/binary/modules.html#function-section)...

#### WasmInstance.Java 
The main loop of the interpreter is in [WasmInstance.Java](https://github.com/fishjd/HappyNewMoonWithReport/blob/master/src/main/java/happynewmoonwithreport/WasmInstance.java).
In the `execute(BytesFile)` function an opcode is decoded and executed.  
 
#### A unit test on how to load a Web Assembly module and execute a function.
See [WasmAdd32Test.Java](https://github.com/fishjd/HappyNewMoonWithReport/blob/master/src/test/java/happynewmoonwithreport/loadFromWasm/WasmAdd32Test.java)
This will need to be simplified in future revisions, It is currently a bit of a mess.   

The wasm module is in the [add32 folder](https://github.com/fishjd/HappyNewMoonWithReport/tree/master/src/test/resources/add32).  
This folder also contains the wasm text files and notes on the bytes in the wasm module.    
  
### Testing the `HelloWorld.wasm' Web Assembly Module in JUnit.
```java
@Test
public void testHelloWorld throws Exception {
	Wasm wasm = new Wasm("HelloWorld.wasm");
	assertEquals("Hello World", wasm.exports().HelloWorld());
}
```

## Similar Projects

For the 'Go' language: Wagon (https://github.com/go-interpreter/wagon).

A Web Assembly virtual Machine written in C/C++:  WAVM (https://github.com/WAVM/WAVM).

'Life' is a secure & fast WebAssembly VM built for decentralized applications, written in Go (https://github.com/perlin-network/life).

For a list of Web Assembly Run times at [Awesome WASM runtimes](https://github.com/appcypher/awesome-wasm-runtimes).

To compile Java to WebAssembly use [ByteCoder](https://mirkosertic.github.io/Bytecoder/)

## Progress

Happy New Moon with Report can read from *.wasm file and run an app that adds two numbers.

All I32 and I64 instruction are complete.  

## To do:  
Branching opcodes   ex : If, Block.  11 remaining.  
Floating point opcodes.  About 75 remaining. 

### Sections

[Source] (http://webassembly.org/docs/binary-encoding/#module-structure)

Custom (a.k.a Name) section : Completed

Type Section :  Completed

Import Section:   To Do

Function Section:  Completed

Table Section:  Completed

Memory Section:  Completed

Global Section:  Completed

Export Section:  Completed

Start Section:  Completed

Code Section:  See Opcodes list below. 

Data Section:  To Do

### OpCodes Completed
[Source] (https://webassembly.github.io/spec/core/appendix/index-instructions.html)

- [x] UnReachable 0x00
- [x] NOP 0x01
- [x] Block 0x02
- [ ] Loop 0x03
- [ ] If 0x04
- [ ] Else 0x05
- [x] End 0x0B
- [ ] Br 0x0C
- [ ] Br_if  0x0D
- [ ] Br_table 0x0E
- [ ] Return 0x0F
- [ ] Call 0x10
- [ ] Call_indirect 0x11
- [x] Drop 0x1A
- [x] Select  0x1B
- [x] Get_local 0x20
- [x] Set_local 0x21
- [ ] Tee_local 0x22
- [ ] Get_global 0x23
- [ ] Set_global 0x24
- [x] I32_load 0x28
- [x] I64_load 0x29
- [x] f32_load 0x2A
- [x] f64_load 0x2B
- [x] i32_load8-S 0x2C
- [x] i32_load8-U 0x2D
- [x] i32_load16-S 0x2E
- [x] i32_load16-U 0x2F
- [x] i64_load8-S 0x30
- [x] i64_load8-U 0x31
- [x] i64_load16-S 0x32
- [x] i64_load16-U 0x33
- [x] i64_load32-S 0x34
- [x] i64_load32-U 0x35
- [x] i32_store 0x36
- [x] i64_store 0x37
- [x] f32_store 0x38
- [x] f64_store 0x39
- [x] i32_store-8 0x3A
- [x] i32_store-16 0x3B
- [x] i64_store-8 0x3C
- [x] i64_store-16 0x3D
- [x] i64_store-32 0x3E
- [ ] Current_memory 0x3F
- [ ] Grow_memory 0x40
- [x] i32_const 0x41
- [x] i64_const 0x42
- [x] f32_const 0x43
- [x] f64_const 0x44
- [x] i32_eqz  0x46
- [x] i32_eq 0x47
- [x] i32_ne 0x48
- [x] i32_ls_s 0x49
- [x] i32_gt_s 0x4A
- [x] i32_gt_u 0x4B
- [x] i32_lt_s 0x4C
- [x] i32_lt_u 0x4D
- [x] i32_gt_s 0x4E
- [x] i32_gt_u 0x4F
- [x] i64_eqz  0x50
- [x] i64_eq 0x51
- [x] i64_ne 0x52
- [x] i64_lt_s 0x53
- [x] i64_lt_u 0x54
- [x] i64_gt_s 0x55
- [x] i64_gt_u 0x56
- [x] i64_lt_s 0x57
- [x] i64_lt_u 0x58
- [x] i64_gt_s 0x59
- [x] i64_gt_u 0x5A
- [x] f32.eq  0x5B
- [x] f32.ne 0x5C
- [x] f32.lt  0x5D
- [x] f32.gt 0x5E
- [x] f32.le  0x5F
- [x] f32.ge 0x60
- [x] f64.eq  0x61
- [x] f64.ne  0x62
- [x] f64.lt 0x63
- [x] f64.gt 0x64
- [x] f64.le  0x65
- [x] f64.ge 0x66
- [x] i32.clz  0x67
- [x] i32.ctz  0x68
- [x] i32.popcnt  0x69
- [x] i32.add 0x6A
- [x] i32.sub 0x6B
- [x] i32.mul 0x6C
- [x] i32.div_s 0x6D
- [x] i32.div_u 0x6E
- [x] i32.rem_s 0x6F
- [x] i32.rem_u 0x70
- [x] i32.and 0x71
- [x] i32.or 0x72
- [x] i32.xor 0x73
- [x] i32.shl 0x74
- [x] i32.shr_s 0x75
- [x] i32.shr_u 0x76
- [x] i32.rotl 0x77
- [x] i32.rotr 0x78
- [x] i64.clz 0x79
- [x] i64.ctz 0x7A
- [x] i64.popcnt 0x7B
- [x] i64.add 0x7C
- [x] i64.sub 0x7D
- [x] i64.mul 0x7E
- [x] i64.div_s 0x7F
- [x] i64.div_u 0x80
- [x] i64.rem_s 0x81
- [x] i64.rem_u 0x82
- [x] i64.and 0x83
- [x] i64.or 0x84
- [x] i64.xor 0x85
- [x] i64.shl 0x86
- [x] i64.shr_s 0x87
- [x] i64.shr_u 0x88
- [x] i64.rotl 0x89
- [x] i64.rotr 0x8A
- [ ] f32.abs 0x8B
- [ ] f32.neg 0x8C
- [ ] f32.ceil 0x8D
- [ ] f32.floor 0x8E
- [ ] f32.trunk 0x8F
- [ ] f32.nearest 0x90
- [ ] f32.sqrt 0x91
- [ ] f32.add 0x92
- [ ] f32.sub 0x93
- [ ] f32.mul 0x94
- [ ] f32.div 0x95
- [ ] f32.min 0x96
- [ ] f32.max 0x97
- [ ] f32.copysign 0x98
- [ ] f64.abs 0x99
- [ ] f64.neg 0x 9A
- [ ] f64.ceil 0x9B
- [ ] f64.floor 0x9C
- [ ] f64.trunk 0x9D
- [ ] f64.nearest 0x9E
- [ ] f64.sqrt 0x9F
- [ ] f64.add 0xA0
- [ ] f64.sub 0xA1
- [ ] f64.mul 0xA2
- [ ] f64.div 0xA3
- [ ] f64.min 0xA4
- [ ] f64.max 0xA5
- [ ] f64.copysign 0xA6
- [ ] f32.warp/i64 0xA7
- [ ] f32.trunc_s/f32 0xA8
- [ ] f32.trunc_u/f32 0xA9
- [ ] f32.trunc_s/f64 0xAA
- [ ] f32.trunc_u/f64 0xAB
- [x] i64.extend_s/i32 0xAC
- [x] i64.extend_u/i32 0xAD
- [ ] i64.trunc_s/f32 0xAE
- [ ] i64.trunc_u/f32 0xAF
- [ ] i64.trunc_s/f64 0xB0
- [ ] i64.trunc_u/f64 0xB1
- [ ] f32.convert_s/i32 0xB2
- [ ] f32.convert_u/i32 0xB3
- [ ] f32.convert_u/i64 0xB4
- [ ] f32.convert_u/i64 0xB5
- [ ] f32.demote/f64 0xB6
- [ ] f64.convert_s/i32 0xB7
- [ ] f64.convert_u/i32 0xB8
- [ ] f64.convert_s/i64 0xB9
- [ ] f64.convert_u/i64 0xBA
- [ ] f64.promote/f32 0xBB
- [ ] i32.reinterpert/f32 0xBC
- [ ] i64.reinterpert/f64 0xBD
- [ ] f32.reinterpert/i32 0xBD
- [ ] f64.reinterpert/i64 0xBF
- [x] i32.extend8_s 0xC0
- [x] i32.extend16_s 0xC1
- [x] i64.extend8_s 0xC2
- [x] i64.extend16_s 0xC3
- [x] i64.extend34_s 0xC4
- [ ] i32.trunc_sat_f32_s 0xFC 0x00
- [ ] i32.trunc_sat_f32_u 0xFC 0x01
- [ ] i32.trunc_sat_f64_s 0xFC 0x02
- [ ] i32.trunc_sat_f64_u 0xFC 0x03
- [ ] i64.trunc_sat_f32_s 0xFC 0x04
- [ ] i64.trunc_sat_f32_u 0xFC 0x05
- [ ] i64.trunc_sat_f64_s 0xFC 0x06
- [ ] i64.trunc_sat_f64_u 0xFC 0x07