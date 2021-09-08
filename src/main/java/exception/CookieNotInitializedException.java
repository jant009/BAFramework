package exception;

public class CookieNotInitializedException extends RobotRuntimeException {
    public CookieNotInitializedException(String message) {
        super("cookie '" + message + "' is not initialized");
    }

    public CookieNotInitializedException(String message, Throwable cause) {
        super("cookie '" + message + "' is not initialized", cause);
    }
}
