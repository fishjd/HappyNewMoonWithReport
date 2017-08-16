package happynewmoonwithreport;

import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt32;

import java.util.ArrayList;

/**
 * FunctionBody Bodies
 * <p>
 * FunctionBody bodies consist of a sequence of local variable declarations followed by bytecode instructions.
 * Instructions are encoded as an opcode followed by zero or more immediates as defined by the tables below. Each
 * function body must end with the end opcode.
 * </p>
 *
 */

public class FunctionBody {

    private UInt32 bodySize;
    private UInt32 localCount;
    private ArrayList<ValueType> localAll;
    private byte[] code;
    private byte end;

    public FunctionBody() {

    }

    public FunctionBody(BytesFile payload) {
        //* Body Size
        bodySize = new VarUInt32(payload);

        final Integer start = payload.getIndex();

        //* Count
        localCount = new VarUInt32(payload);


        //* LocalAll
        localAll = new ArrayList<>(localCount.integerValue());
        for (Integer index = 0; index < localCount.integerValue(); ) {
            LocalEntry localEntry = new LocalEntry(payload);
            for (Integer localIndex = 0; localIndex < localEntry.getCount().integerValue(); localIndex++) {
                localAll.add(index, localEntry.getValueType());
                index++;
            }
        }

        final Integer after = payload.getIndex();

        final Integer consumedByLocals = after - start;

        final Integer codeLength = bodySize.integerValue() - consumedByLocals  - 1;  // minus 1 for end byte.

        //* Code
        code = new byte[codeLength];
        for (Integer i = 0; i < codeLength; i++) {
            code[i] = payload.readByte();
        }

        //* Byte
        end = payload.readByte();
        assert (end == (byte) 0x0B);
    }

    public UInt32 getBodySize() {
        return bodySize;
    }

    public UInt32 getLocalCount() {
        return localCount;
    }

    public ArrayList<ValueType> getLocalAll() {
        return localAll;
    }

    public byte[] getCode() {
        return code;
    }

    public byte getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "FunctionBody{" +
                "bodySize=" + bodySize +
                ", localCount=" + localCount +
                '}';
    }
}
