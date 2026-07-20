package cl.kafka.orderservice.model;

import cl.kafka.orderservice.exception.InvalidOrderException;
import cl.kafka.orderservice.exception.InvalidOrderStateException;

import java.math.BigDecimal;

public class Order {

    private final String id;
    private final String customerId;
    private final String productId;
    private final int quantity;
    private final BigDecimal unitPrice;
    private OrderStatus status;

    public Order(String id, String customerId, String productId, int quantity, BigDecimal unitPrice, OrderStatus status) {
        if (id == null || id.isBlank()) {
            throw new InvalidOrderException("ID must not be blank");
        }

        if (customerId == null || customerId.isBlank()) {
            throw new InvalidOrderException("Customer ID must not be blank");
        }

        if (productId == null || productId.isBlank()) {
            throw new InvalidOrderException("Product ID must not be blank");
        }

        if (quantity <= 0) {
            throw new InvalidOrderException("Quantity must be greater than zero");
        }

        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidOrderException("Unit Price must be greater than zero");
        }

        if (status == null) {
            throw new InvalidOrderException("Order status must not be null");
        }

        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.status = status;
    }

    public void cancel() {
        if (status == OrderStatus.CANCELLED) {
            throw new InvalidOrderStateException(
                    "Order is already cancelled"
            );
        }

        this.status = OrderStatus.CANCELLED;
    }

    public void pay() {
        if (status == OrderStatus.CANCELLED) {
            throw new InvalidOrderStateException(
                    "Cancelled order cannot be paid"
            );
        }

        this.status = OrderStatus.PAID;
    }

    public String id() {
        return id;
    }

    public String customerId() {
        return customerId;
    }

    public String productId() {
        return productId;
    }

    public int quantity() {
        return quantity;
    }

    public BigDecimal unitPrice() {
        return unitPrice;
    }

    public OrderStatus status() {
        return status;
    }

}
