public class GameAlreadyEndedException extends RuntimeException {
    public GameAlreadyEndedException(String errorMessage) {
        super(errorMessage);
    }
}
