package cl.kafka.orderservice.exception;

public class InvalidOrderException extends IllegalArgumentException {

    public InvalidOrderException(String message) {
        super(message);
    }
}
