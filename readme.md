# Happy New Moon with Report
Run and Test Web Assembly modules (*.wasm) in Java.

## The basic use case is:

```java
Wasm wasm = new Wasm("your Web Asssembly Module.wasm");
wasm.exports().yourFuncion(); 

Wasm wasm = new Wasm("HelloWorld.wasm");
System.out.println(wasm.exports().HelloWorld()); 
```

## For Testing the Web Assembly Module
```java
@Test
public void testHelloWorld throws Exception {
	Wasm wasm = new Wasm("HelloWorld.wasm");
    assertEquals("Hello World", wasm.exports().HelloWorld());
}	
```

This project has nothing to do with reporting or fireworks.  
