#Java Issues and Incompatibilities

1)  Java Float and Double types do not support -Nan.   -Nan is represented as Nan. 

The Happy New Moon with Report types F32 and F64 use Float and Double to store the value.  
The values -Nan will be lost. 

3)   