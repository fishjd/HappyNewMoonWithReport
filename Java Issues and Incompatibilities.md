#Java Issues and Incompatibilities

## -Nan converted to +Nan. 

The Happy New Moon with Report types F32 and F64 use Java's Float and Double to store the value.  
The values -Nan, "nan:0x200000", "-nan:0x200000", "nan:canonical", "nan:arithmetic" are converted to 
Nan.   
See: F32 and F64 valueOf(String s).  
See: https://docs.oracle.com/javase/7/docs/api/java/lang/Float.html

   