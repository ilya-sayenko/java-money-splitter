package exception;

import exception.global.GlobalAppException;

public class InputDataException extends GlobalAppException {
    public InputDataException(String message) {
        super(message);
    }
}
