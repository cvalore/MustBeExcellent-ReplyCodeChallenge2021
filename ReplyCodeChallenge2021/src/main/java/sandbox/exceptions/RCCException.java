package sandbox.exceptions;

import sandbox.exceptions.errors.ErrorCode;
import lombok.Getter;
import lombok.ToString;

@ToString
public class RCCException extends Exception {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -7304125117553589057L;

    @Getter
    private ErrorCode errorCode;

    public RCCException(ErrorCode errorCode) {
        this(errorCode, errorCode.description());
    }

    public RCCException(ErrorCode errorCode, String message) {
        this(errorCode, message, null);
    }

    public RCCException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public RCCException(String message) {
        super(message);
        this.errorCode = null;
    }
}

