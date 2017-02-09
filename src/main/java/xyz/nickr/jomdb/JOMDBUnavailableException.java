package xyz.nickr.jomdb;

/**
 * @author Nick Robson
 */
public class JOMDBUnavailableException extends JOMDBException {

    public JOMDBUnavailableException(Throwable t) {
        super("JOMDB is unavailable. This means it is unable to service requests.", t);
    }

    public JOMDBUnavailableException() {
        this(null);
    }

}
