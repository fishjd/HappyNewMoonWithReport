package happynewmoonwithreport.type;


import happynewmoonwithreport.BytesFile;

/**
 * Memory Type,  also refereed to as resizable_limits
 *
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
 *
 * <a href="https://webassembly.github.io/spec/syntax/types.html#syntax-memtype">Memory Types</a>
 */
public class MemoryType implements Limit {


    /**
     * does the limit have max?
     */
    private VarUInt1 hasMaximum;
    /**
     * length in wasm pages (64k)
     */
    private UInt32 minimum;
    /**
     * length in wasm pages (64k)
     * <p>
     * usage:
     * <code>
     * if (flag == true) {
     * max = getMaximumLength(); {
     * else {
     * // There is no Maximum  (infinity)
     * <p>
     * }
     * }</code>
     */
    private UInt32 maximum;

    public MemoryType(VarUInt1 hasMaximum, UInt32 minimum, UInt32 maximum) {
        this.hasMaximum = hasMaximum;
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public MemoryType(BytesFile payload) {
        hasMaximum = new VarUInt1(payload);
        minimum = new VarUInt32(payload);
        if (hasMaximum.isTrue()) {
            maximum = new VarUInt32(payload);
        }
    }

    /**
     * minimum  of the memory in Page Size
     *
     * @return min
     */
    @Override
    public UInt32 minimum() {
        return minimum;
    }

    /**
     * maximum of the memory in Page Size
     *
     * @return max
     */
    @Override
    public UInt32 maximum() {
        return maximum;
    }

    @Override
    public VarUInt1 hasMaximum() {
        return hasMaximum;
    }
}
