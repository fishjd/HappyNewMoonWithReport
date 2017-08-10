
package happynewmoonwithreport.type;

/**
 * Limits classify the size range of resizeable storage associated with memory types and table types.
 * <p>
 * If no maximum is given, the respective storage can grow to any size.
 * <p>
 * Source: <a href="https://webassembly.github.io/spec/syntax/types.html#syntax-limits" > limits</a>
 */
public interface Limit {

    UInt32 minimum();

    UInt32 maximum();

    VarUInt1 hasMaximum();

}
