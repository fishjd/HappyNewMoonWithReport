package happynewmoonwithreport.type;


import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.Validation;

/**
 * Memory Type,
 *
 * <p>
 * Source:  <a href="http://webassembly.org/docs/binary-encoding/#memory_type" target="_top">
 * http://webassembly.org/docs/binary-encoding/#memory_type
 * </a>
 *
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/syntax/types.html#memory-types" target="_top">
 * https://webassembly.github.io/spec/syntax/types.html#memory-types
 * </a>
 */
public class MemoryType implements Validation {

    private LimitType limit;

    public MemoryType(UInt32 hasMaximum, UInt32 minimum, UInt32 maximum) {
        limit = new LimitType(hasMaximum, minimum, maximum);
    }

    public MemoryType(BytesFile payload) {
        limit = new LimitType(payload);
    }

    /**
     * The limits must be valid.
     * <p>
     * source:  <a href="https://webassembly.github.io/spec/valid/types.html#memory-types" target="_top">
     * https://webassembly.github.io/spec/valid/types.html#memory-types
     * </a>
     *
     * @return true if valid.
     */
    @Override
    public Boolean valid() {
        return limit.valid();
    }


    /**
     * minimum  of the memory in Page Size
     *
     * @return min
     */
    public UInt32 minimum() {
        return limit.minimum();
    }

    /**
     * maximum of the memory in Page Size
     * <p>
     * Usage :
     * <code>
     * if (hasMaximum()) {
     * max = maximum();
     * }
     * </code>
     * <p>
     * Throws RuntimeException is maximum is not set.
     *
     * @return maximum
     */
    public UInt32 maximum() {
        return limit.maximum();
    }

    public UInt32 hasMaximum() {
        return limit.hasMaximum();
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MemoryType{");
        sb.append("678 hasMaximum=").append(hasMaximum());
        sb.append(", minimum=").append(minimum());
        if (limit.hasMaximum().booleanValue()) {
            sb.append(", maximum=").append(maximum());
        }
        sb.append('}');
        return sb.toString();
    }
}
