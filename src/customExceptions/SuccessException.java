package customExceptions;

public class SuccessException extends RuntimeException {
    public SuccessException() {
        super("Cannot find disconnected node, all nodes working fine");
    }
}
