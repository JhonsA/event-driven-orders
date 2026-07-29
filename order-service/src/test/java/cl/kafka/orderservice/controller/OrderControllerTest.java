package cl.kafka.orderservice.controller;

import cl.kafka.orderservice.dto.CreateOrderRequest;
import cl.kafka.orderservice.model.Order;
import cl.kafka.orderservice.model.OrderStatus;
import cl.kafka.orderservice.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderService orderService;

    @Test
    void createOrderShouldReturnCreatedOrderWithHttp201() throws Exception {
        // Arrange
        CreateOrderRequest request = new CreateOrderRequest(
                "customer-123",
                "product-456",
                2,
                new BigDecimal("19990")
        );

        Order createdOrder = new Order(
                "order-123",
                "customer-123",
                "product-456",
                2,
                new BigDecimal("19990"),
                OrderStatus.CREATED
        );

        when(orderService.createOrder(request))
                .thenReturn(createdOrder);

        // Act + Assert
        mockMvc.perform(
                post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isCreated())
                .andExpect(header().string(
                        "Location",
                        "/orders/order-123"
                ))
                .andExpect(jsonPath("$.id").value("order-123"))
                .andExpect(jsonPath("$.customerId").value("customer-123"))
                .andExpect(jsonPath("$.productId").value("product-456"))
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.unitPrice").value(19990))
                .andExpect(jsonPath("$.status").value("CREATED"));
    }

}
