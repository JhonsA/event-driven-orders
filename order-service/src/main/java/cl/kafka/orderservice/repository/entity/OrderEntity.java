package cl.kafka.orderservice.repository.entity;

import cl.kafka.orderservice.model.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    private String id;

    private String customerId;
    private String productId;
    private int quantity;
    private BigDecimal unitPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    protected OrderEntity() {
    }

    public OrderEntity(
            String id,
            String customerId,
            String productId,
            int quantity,
            BigDecimal unitPrice,
            OrderStatus status
    ) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.status = status;
    }

}
