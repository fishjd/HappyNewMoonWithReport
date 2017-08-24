package happynewmoonwithreport;

import java.util.UUID;

/**
 * Thrown when the Wasm crashes.
 */
class WasmRuntimeException extends RuntimeException {

    /**
     * a uuid to help when searching the internet
     */
    private UUID uuid;

    public WasmRuntimeException(UUID uuid, String message) {
        super(message);
        this.uuid = uuid;
    }


    public WasmRuntimeException(UUID uuid, String message, String possibleSolutions) {
        super(message + " Possible Solutions " + possibleSolutions);
        this.uuid = uuid;
    }


    public WasmRuntimeException(UUID uuid, String message, Throwable cause) {
        super(message, cause);
        this.uuid = uuid;
    }

    public WasmRuntimeException(UUID uuid, Throwable cause) {
        super(cause);
        this.uuid = uuid;
    }

    public WasmRuntimeException(UUID uuid, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.uuid = uuid;
    }

    private String formatMessage(String message) {
        String result = "Uuid = " + uuid + " message = " + message;
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WasmRuntimeException{");
        sb.append("Uuid='").append(uuid).append('\'');
        sb.append(", Message='").append(getMessage()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
