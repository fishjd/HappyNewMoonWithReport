package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.Validation;

/**
 * Limit also known as resizable limit.
 * Limits classify the size range of resizeable storage associated with memory types and table types.
 * If no maximum is given, the respective storage can grow to any size.
 * <p>
 * Source : <a href= "http://webassembly.org/docs/binary-encoding/#resizable_limits" target="_top">
 * http://webassembly.org/docs/binary-encoding/#resizable_limits</a>
 * <p>
 * source:  <a href="https://webassembly.github.io/spec/syntax/types.html#limits" target="_top">
 * https://webassembly.github.io/spec/syntax/types.html#limits
 * </a>
 */
public class LimitType implements Validation {

    /**
     * does the limit have max?
     */
    private UInt32 hasMaximum;
    private UInt32 minimum;
    private UInt32 maximum;

    private LimitType() {
        maximum = null;
    }

    public LimitType(UInt32 minimum) {
        this();
        this.hasMaximum = new UInt32(0);  // must be zero.
        this.minimum = minimum;
        this.maximum = null;  // not set!
    }

    public LimitType(UInt32 minimum, UInt32 maximum) {
        this();
        this.hasMaximum = new UInt32(1);
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public LimitType(UInt32 hasMaximum, UInt32 minimum, UInt32 maximum) {
        this();
        this.hasMaximum = hasMaximum;
        this.minimum = minimum;
        this.maximum = maximum;
    }


    public LimitType(BytesFile payload) {
        this();
        hasMaximum = new VarUInt1(payload);
        minimum = new VarUInt32(payload);
        if (hasMaximum.isTrue()) {
            maximum = new VarUInt32(payload);
        }
    }

    /**
     * Does the limit set the maximum value?   Limits without a maximum are legal.
     *
     * @return hasMaximum   nonZero indicates true.
     */
    public UInt32 hasMaximum() {
        return hasMaximum;
    }

    /**
     * Get the Minimum.
     *
     * @return minimum
     */
    public UInt32 minimum() {
        return minimum;
    }

    /* code in Javadoc source: https://stackoverflow.com/questions/541920/multiple-line-code-example-in-javadoc-comment
     */

    /**
     * Get the Maximum
     * <p>
     * Usage:
     * <pre>
     * {@code
     * if (hasMaximum()) {
     *      max = maximum();
     * }
     * }
     * </pre>
     * <p>
     * Throws RuntimeException is maximum is not set.
     *
     * @return maximum
     */
    public UInt32 maximum() {
        assert (maximum != null);
        if (maximum == null) {
            throw new RuntimeException("Calling maximum when it is not set!");
        }
        return maximum;
    }

    /**
     * Limits must have meaningful bounds.
     * <ul>
     * <li>If the maximum m? is not empty, then its value must not be smaller than n</li>
     * <li>Then the limit is valid.</li>
     * </ul>
     * source:  <a target="_top" href="https://webassembly.github.io/spec/valid/types.html#limits">
     * https://webassembly.github.io/spec/valid/types.html#limits</a>
     *
     * @return true if limit is valid.
     */
    @Override
    public Boolean valid() {
        Boolean result = false;

        if (hasMaximum.booleanValue() == true) {
            result = minimum.integerValue() <= maximum.integerValue();
        } else {
            return true;
        }
        return result;
    }
}
