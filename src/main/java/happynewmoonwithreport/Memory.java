package happynewmoonwithreport;

import happynewmoonwithreport.type.Int32;
import happynewmoonwithreport.type.UInt16;
import happynewmoonwithreport.type.UInt32;

import java.util.Arrays;

/**
 * The web assembly memory.
 * A big array of bytes.
 */
public class Memory {

    public UInt16 kib_64 = new UInt16(64 * 1024);
    public UInt16 page_size = new UInt16(kib_64);
    public UInt16 maxPage = new UInt16(1 << 16);
    /**
     * The current size of memory in page size.   1 = 64 KiBytes;
     */
    public UInt32 size;

    private byte[] memory;

    public Memory(UInt32 initialSize) {
        assert (0 < initialSize.value());
        size = initialSize;
        memory = new byte[initialSize.integerValue() * page_size.integerValue()];
    }

    public void set(UInt32 index, byte input) {
        memory[index.integerValue()] = input;
    }

    public byte get(UInt32 index) {
        return memory[index.integerValue()];
    }

    /**
     * The grow_memory instruction grows memory by a given delta and returns the previous size, or
     * <code>-1</code> if enough memory cannot be allocated.
     *
     * @param additionalSize in page_size.
     *
     * @return "previous size" in page size on success;   -1 on failure;
     */
    public Int32 grow(UInt32 additionalSize) {
        assert (memory != null);
        Int32 failure = new Int32(-1);
        Int32 previousSizeInPages = new Int32(size);

        Integer sizeNewInPages = size.integerValue() + additionalSize.integerValue();

        // trap on too large.
        if (maxPage.integerValue() < sizeNewInPages) {
            throw new MemoryException();
        }
        try {
            // increase the memory.
            byte[] memoryNew = Arrays.copyOfRange(memory, 0, sizeNewInPages * page_size.integerValue());
            memory = memoryNew;
        } catch (Exception exception) {
            return failure;
        }

        return previousSizeInPages;
    }

    public class MemoryException extends RuntimeException {
        MemoryException() {
            super("Memory Failure too large");
        }
    }


}
