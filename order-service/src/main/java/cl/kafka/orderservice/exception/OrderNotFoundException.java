package cl.kafka.orderservice.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String orderId) {
        super("Order with id '" + orderId + "' was not found.");
    }
}
