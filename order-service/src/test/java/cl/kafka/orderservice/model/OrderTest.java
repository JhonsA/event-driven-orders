package cl.kafka.orderservice.model;

import cl.kafka.orderservice.exception.InvalidOrderException;
import cl.kafka.orderservice.exception.InvalidOrderStateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderTest {

    @ParameterizedTest(name = "should reject id: [{0}]")
    @NullSource
    @EmptySource
    @ValueSource(strings = {" ", "   "})
    void shouldRejectBlackId(String id) {
        assertThrows(
                InvalidOrderException.class,
                () -> new Order(
                        id,
                        "customer-123",
                        "product-123",
                        1,
                        new BigDecimal("19990"),
                        OrderStatus.CREATED
                )
        );
    }

    @ParameterizedTest(name = "should reject customerId: [{0}]")
    @NullSource
    @EmptySource
    @ValueSource(strings = {" ", "   "})
    void shouldRejectBlankCustomerId(String customerId) {
        assertThrows(
                InvalidOrderException.class,
                () -> new Order(
                        "order-123",
                        customerId,
                        "product-123",
                        1,
                        new BigDecimal("19990"),
                        OrderStatus.CREATED
                )
        );
    }

    @ParameterizedTest(name = "should reject productId: [{0}]")
    @NullSource
    @EmptySource
    @ValueSource(strings = {" ", "   "})
    void shouldRejectBlankProductId(String productId) {
        assertThrows(
                InvalidOrderException.class,
                () -> new Order(
                        "order-123",
                        "customer-123",
                        productId,
                        1,
                        new BigDecimal("19990"),
                        OrderStatus.CREATED
                )
        );
    }

    @ParameterizedTest(name = "should reject quantity {0}")
    @ValueSource(ints = {0, -1, -10})
    void shouldRejectNonPositiveQuantity(int quantity) {
        assertThrows(
                InvalidOrderException.class,
                () -> new Order(
                        "order-123",
                        "customer-123",
                        "product-123",
                        quantity,
                        new BigDecimal("19990"),
                        OrderStatus.CREATED
                )
        );
    }

    @Test
    void shouldRejectNullUnitPrice() {
        assertThrows(
                InvalidOrderException.class,
                () -> new Order(
                        "order-123",
                        "customer-123",
                        "product-123",
                        1,
                        null,
                        OrderStatus.CREATED
                )
        );
    }

    @ParameterizedTest(name = "should reject unitPrice: {0}")
    @ValueSource(strings = {"0", "-1", "-19990"})
    void shouldRejectNonPositiveUnitPrice(String unitPrice) {
        assertThrows(
                InvalidOrderException.class,
                () -> new Order(
                        "order-123",
                        "customer-123",
                        "product-123",
                        1,
                        new BigDecimal(unitPrice),
                        OrderStatus.CREATED
                )
        );
    }

    @Test
    void shouldCancelCreatedOrder() {
        // Arrange
        Order order = new Order(
                "order-123",
                "customer-123",
                "product-123",
                1,
                new BigDecimal("19990"),
                OrderStatus.CREATED
        );

        // Act
        order.cancel();

        assertEquals(
                OrderStatus.CANCELLED,
                order.status()
        );
    }

    @Test
    void shouldRejectCancellingAlreadyCancelledOrder() {
        // Arrange
        Order order = new Order(
                "order-123",
                "customer-123",
                "product-123",
                1,
                new BigDecimal("19990"),
                OrderStatus.CREATED
        );

        order.cancel();

        // Act + Assert
        InvalidOrderStateException exception = assertThrows(
                InvalidOrderStateException.class,
                order::cancel
        );

        assertEquals(
                "Order is already cancelled",
                exception.getMessage()
        );
    }

    @Test
    void shouldPayCreatedOrder() {

        // Arrange
        Order order = new Order(
                "order-123",
                "customer-123",
                "product-123",
                1,
                new BigDecimal("19990"),
                OrderStatus.CREATED
        );

        // Act
        order.pay();

        // Assert
        assertEquals(
                OrderStatus.PAID,
                order.status()
        );
    }

    @Test
    void shouldRejectPayingCancelledOrder() {
        Order order = new Order(
                "order-123",
                "customer-123",
                "product-123",
                1,
                new BigDecimal("19990"),
                OrderStatus.CREATED
        );

        order.cancel();

        InvalidOrderStateException exception = assertThrows(
                InvalidOrderStateException.class,
                order::pay
        );

        assertEquals(
                "Cancelled order cannot be paid",
                exception.getMessage()
        );
    }
}