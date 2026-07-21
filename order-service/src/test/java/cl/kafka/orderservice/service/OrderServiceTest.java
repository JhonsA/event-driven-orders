package cl.kafka.orderservice.service;

import cl.kafka.orderservice.dto.CreateOrderRequest;
import cl.kafka.orderservice.exception.OrderNotFoundException;
import cl.kafka.orderservice.model.Order;
import cl.kafka.orderservice.model.OrderStatus;
import cl.kafka.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    private OrderRepository orderRepository;
    private OrderIdGenerator orderIdGenerator;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderIdGenerator = mock(OrderIdGenerator.class);

        when(orderIdGenerator.generate())
                .thenReturn("order-123");

        orderService = new OrderService(
                orderRepository,
                orderIdGenerator
        );
    }

    @Test
    void shouldCreateValidOrder() {
        // Arrange: preparar datos (ahora lo hace el setUp)

        CreateOrderRequest request = new CreateOrderRequest(
                "customer-123",
                "product-456",
                2,
                new BigDecimal("19990")
        );

        // Act: ejecutar lo que se quiere probar
        Order order = orderService.createOrder(request);

        // Assert: verificar resultado
        assertEquals(OrderStatus.CREATED, order.status());
        assertEquals("customer-123", order.customerId());
        assertEquals("product-456", order.productId());
        assertEquals(2, order.quantity());
        assertEquals(new BigDecimal("19990"), order.unitPrice());
    }

    @Test
    void shouldStoreCreatedOrder() {
        CreateOrderRequest request = new CreateOrderRequest(
                "customer-1",
                "product-1",
                2,
                new BigDecimal("19990")
        );

        Order createdOrder = orderService.createOrder(request);

        verify(orderRepository).save(createdOrder);
    }

    @Test
    void shouldCreateOrderWithId() {
        CreateOrderRequest request = new CreateOrderRequest(
                "customer-1",
                "product-1",
                2,
                new BigDecimal("19990")
        );

        when(orderIdGenerator.generate())
                .thenReturn("order-1234");

        Order order = orderService.createOrder(request);

        assertEquals("order-1234", order.id());

        verify(orderIdGenerator).generate();
    }

    @Test
    void shouldFindOrderById() {
        // Arrange
        String orderId = "order-123";

        Order storedOrder = new Order(
                orderId,
                "customer-123",
                "product-123",
                1,
                new BigDecimal("19990"),
                OrderStatus.CREATED
        );

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(storedOrder));

        // Act
        Order foundOrder = orderService.findById(orderId);

        // Assert
        assertEquals(storedOrder, foundOrder);

        verify(orderRepository).findById(orderId);
    }

    @Test
    void shouldThrowExceptionWhenOrderDoesNotExist() {
        // Arrange
        String orderId = "order-123";

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.empty());

        // Act + Assert
        OrderNotFoundException exception = assertThrows(
                OrderNotFoundException.class,
                () -> orderService.findById(orderId)
        );

        assertEquals(
                "Order with id 'order-123' was not found.",
                exception.getMessage()
        );
    }

    @Test
    void shouldSaveCancelledOrder() {
        // Arrange
        String orderId = "order-123";

        Order storedOrder = new Order(
                orderId,
                "customer-123",
                "product-123",
                1,
                new BigDecimal("19990"),
                OrderStatus.CREATED
        );

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(storedOrder));

        ArgumentCaptor<Order> orderCaptor =
                ArgumentCaptor.forClass(Order.class);

        // Act
        orderService.cancelOrder(orderId);

        // Assert
        verify(orderRepository).save(orderCaptor.capture());

        Order savedOrder = orderCaptor.getValue();

        assertEquals(
                OrderStatus.CANCELLED,
                savedOrder.status()
        );
    }

    @Test
    void shouldThrowExceptionWhenCancellingNonExistingOrder() {
        // Arrange
        String orderId = "order-123";

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                OrderNotFoundException.class,
                () -> orderService.cancelOrder(orderId)
        );

        verify(orderRepository, never())
                .save(any(Order.class));
    }

    @Test
    void shouldSavePaidOrder() {
        // Arrange
        String orderId = "order-123";

        Order storedOrder = new Order(
                orderId,
                "customer-123",
                "product-123",
                1,
                new BigDecimal("19990"),
                OrderStatus.CREATED
        );

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(storedOrder));

        // Act
        orderService.payOrder(orderId);

        // Assert
        verify(orderRepository).save(storedOrder);
    }

    @Test
    void shouldThrowExceptionWhenPayingNonExistingOrder() {
        // Arrange
        String orderId = "order-123";

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                OrderNotFoundException.class,
                () -> orderService.payOrder(orderId)
        );

        verify(orderRepository, never())
                .save(any(Order.class));
    }

}
