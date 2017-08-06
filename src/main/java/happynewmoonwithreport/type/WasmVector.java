package happynewmoonwithreport.type;


import java.util.ArrayList;

/**
 * Vectors are bounded sequences of the form A^n  (or A^*), where
 * the A can either be values or complex constructions. A vector can have at most (2^32)-1
 * elements.
 * <p>
 * vec(A)::=An(if n &lt (2^32))
 * <p>
 * Source: <a href="https://webassembly.github.io/spec/syntax/conventions.html#vectors" > Vectors</a>
 * <p>
 * Note this implementation can only have 2^31 (Integer.MAX_VALUE) elements. Any attempt to store a larger value will
 * throw an Exception.
 */
public class WasmVector<Type> extends ArrayList<Type> {

    public WasmVector() {
    }

    public Type get(DataTypeNumber index) {
        checkIfTooLarge(index);

        return super.get(index.integerValue());
    }

    public Type set(DataTypeNumber index, Type element) {
        checkIfTooLarge(index);

        return super.set(index.integerValue(), element);
    }

    private void checkIfTooLarge(DataTypeNumber index) {
        if (index.isBoundByInteger() == false) {
            throw new RuntimeException("Value to Large for Index.  Index = " + index.longValue());
        }
    }


}
