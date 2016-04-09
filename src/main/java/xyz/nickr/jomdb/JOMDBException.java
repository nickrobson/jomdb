package xyz.nickr.jomdb;

public class JOMDBException extends RuntimeException {

    private static final long serialVersionUID = -5037027411374317282L;

    public JOMDBException() {
        super();
    }

    public JOMDBException(String message) {
        super(message);
    }

    public JOMDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public JOMDBException(Throwable cause) {
        super(cause);
    }

}
