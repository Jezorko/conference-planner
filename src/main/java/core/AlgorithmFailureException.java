package core;

class AlgorithmFailureException extends RuntimeException {
    AlgorithmFailureException(String description) {
        super(description);
    }
}
