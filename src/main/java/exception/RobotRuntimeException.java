package exception;

public class RobotRuntimeException extends Exception {
    public RobotRuntimeException(String message) {
        super(message);
    }

    public RobotRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
