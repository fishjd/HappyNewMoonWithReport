# Happy New Moon with Report

[![Join the chat at https://gitter.im/HappyNewMoonWithReport/Lobby](https://badges.gitter.im/HappyNewMoonWithReport/Lobby.svg)](https://gitter.im/HappyNewMoonWithReport/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

**Happy New Moon with Report** is an open-source implementation of [WebAssembly](https://webassembly.org/) written entirely in Java. It is typically used to run or test Web Assembly Modules (*.wasm) in Java.

Happy New Moon with Report doesn't concern itself with the production of the WASM binary files; these files are produced with another tools such as [Emscripten](https://emscripten.org/), [wabt](https://github.com/WebAssembly/wabt) or [binaryen](https://github.com/WebAssembly/binaryen) or any of the number of  languages that can output to WebAssembly such as as [Rust](https://www.rust-lang.org/).   To compile Java to WebAssembly use [ByteCoder](https://mirkosertic.github.io/Bytecoder/)

The primary goal of `Happy New Moon with Report` is to be able to load and run a binary `WebAssembly` module in your Java program.

Think of Happy New Moon With Report as the [Rhino](https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino) of Web Assembly.   Rhino runs JavaScript in Java; Happy New Moon with Report runs WebAssembly in Java.  

Happy New Moon With Report has nothing to do with reporting or fireworks.


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

### Testing the `HelloWorld.wasm' Web Assembly Module in JUnit.
```java
@Test
public void testHelloWorld throws Exception {
	Wasm wasm = new Wasm("HelloWorld.wasm");
	assertEquals("Hello World", wasm.exports().HelloWorld());
}
```



## Happy New Moon With Report Downloads

## Similar Projects

For the 'Go' language: Wagon (https://github.com/go-interpreter/wagon)

A Web Assembly virtual Machine written in C/C++:  WAVM (https://github.com/WAVM/WAVM)

'Life' is a secure & fast WebAssembly VM built for decentralized applications, written in Go (https://github.com/perlin-network/life)

## Progress

Happy New Moon with Report can read from *.wasm file  and run an app that adds two numbers.

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

Code Section:  See Opcodes

Data Section:  To Do

### OpCodes Completed
[Source] (https://webassembly.github.io/spec/core/appendix/index-instructions.html)

- [x] UnReachable 0x00
- [x] NOP 0x01
- [ ] Block 0x02
- [ ] Loop 0x03
- [ ] If 0x04
- [ ] Else 0x05
- [ ] End 0x0B
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
- [ ] f32_load 0x2A
- [ ] f64_load 0x2B
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
- [ ] f32_store 0x38
- [ ] f64_store 0x39
- [x] i32_store-8 0x3A
- [x] i32_store-16 0x3B
- [x] i64_store-8 0x3C
- [x] i64_store-16 0x3D
- [x] i64_store-32 0x3E
- [ ] Current_memory 0x3F
- [ ] Grow_memory 0x40
- [x] i32_const 0x41
- [x] i64_const 0x42
- [ ] f32_const 0x43
- [ ] f64_const 0x44
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
- [ ] f32.eq  0x5B
- [ ] f32.ne 0x5C
- [ ] f32.lt  0x5D
- [ ] f32.gt 0x5E
- [ ] f32.lt  0x5F
- [ ] f32.ge 0x60
- [ ] f64.eq  0x61
- [ ] f64.ne  0x62
- [ ] f64.lt 0x63
- [ ] f64.gt 0x64
- [ ] f64.le  0x65
- [ ] f64.ge 0x66
- [ ] i32.clz  0x67
- [ ] i32.ctx  0x68
- [ ] i32.popcnt  0x69
- [x] i32.add 0x6A
- [x] i32.sub 0x6B
- [x] i32.mul 0x6C
- [ ] i32.div_s 0x6D
- [ ] i32.div_u 0x6E
- [ ] i32.rem_s 0x6F
- [ ] i32.rem_u 0x70
- [ ] i32.and 0x71
- [ ] i32.or 0x72
- [ ] i32.xor 0x73
- [ ] i32.shl 0x74
- [ ] i32.shr_s 0x75
- [ ] i32.shr_u  0x76
- [ ] i32.rotl 0x77
- [ ] i32.rotr 0x78
- [ ] i64.clz   0x79
- [ ] i64.ctz   0x7A
- [ ] i64.popcnt  0x7B
- [ ] i64.add   0x7C
- [ ] i64.sub  0x7D
- [ ] i64.mul  0x7E
- [ ] i64.div_s   0x7F
- [ ] i64.div_u   0x80
- [ ] i64.rem_s 0x81
- [ ] i64.rem_u 0x82
- [ ] i64.and 0x83
- [ ] i64.or 0x84
- [ ] i64.xor 0x85
- [ ] i64.shl 0x86
- [ ] i64.shr_s 0x87
- [ ] i64.shr_u 0x88
- [ ] i64.rotl 0x89
- [ ] i64.rotr 0x8A
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