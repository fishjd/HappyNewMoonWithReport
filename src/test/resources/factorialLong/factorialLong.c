/* Used for input to WasmFiddle.
source  https://wasdk.github.io/WasmFiddle/?12atat
**/


/**
 *  Called factorialLong because it returns a Long (Int32) type.
 *  The example factorial functions return a 'Long Long' (Int64) type.
*/
long factorialLong(int i) {
  long  n = 1;
  for (;0 < i; i--) {
    n *= i;
  }
  return n;
}