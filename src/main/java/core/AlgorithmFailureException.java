package core;

public class AlgorithmFailureException extends RuntimeException {
    AlgorithmFailureException(String description) {
        super(description);
    }
}
