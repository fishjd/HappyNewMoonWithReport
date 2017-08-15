package happynewmoonwithreport.type;


import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.Validation;

/**
 * Memory Type,  also refereed to as resizable_limits
 * <p>
 * <h3 id="resizable_limits"><code class="highlighter-rouge">resizable_limits</code></h3> <p>A packed tuple that
 * describes the limits of a <a href="../semantics/#table">table</a> or <a href="../semantics/#resizing">memory</a>:</p>
 * <p> <table> <thead> <tr> <th>Field</th> <th>Type</th> <th>Description</th> </tr> </thead> <tbody> <tr>
 * <td>flags</td>
 * <td><code class="highlighter-rouge">varuint1</code></td> <td> <code class="highlighter-rouge">1</code> if the
 * maximum
 * field is present, <code class="highlighter-rouge">0</code> otherwise</td> </tr> <tr> <td>initial</td> <td><code
 * class="highlighter-rouge">varuint32</code></td> <td>initial length (in units of table elements or wasm pages)</td>
 * </tr> <tr> <td>maximum</td> <td> <code class="highlighter-rouge">varuint32</code>?</td> <td>only present if
 * specified
 * by <code class="highlighter-rouge">flags</code> </td> </tr> </tbody> </table> <p> <p>Note: In the <a
 * href="../future-features/#threads">future <img class="emoji" title=":unicorn:" alt=":unicorn:"
 * src="https://assets-cdn.github.com/images/icons/emoji/unicode/1f984.png" height="20" width="20"
 * align="absmiddle"></a>, the "flags" field may be changed to <code class="highlighter-rouge">varuint32</code>, e.g.,
 * to include a flag for sharing between threads.</p>
 * <p>
 * <p>
 * Source : <a href= "http://webassembly.org/docs/binary-encoding/#resizable_limits">http://webassembly.org/docs/binary-encoding/#resizable_limits</a>
 * <p>
 * <p>
 * <a href="https://webassembly.github.io/spec/syntax/types.html#syntax-memtype">Memory Types</a>
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
     * source:  <a href="https://webassembly.github.io/spec/valid/types.html#memory-types">
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
