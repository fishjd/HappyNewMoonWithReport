/*
 *  Copyright 2017 Whole Bean Software, LTD.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package happynewmoonwithreport;

import java.util.UUID;

/**
 * Thrown when the Wasm crashes.
 */
public class WasmRuntimeException extends RuntimeException {

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
