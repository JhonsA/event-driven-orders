package cl.kafka.orderservice;

import cl.kafka.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class OrderServiceApplicationTests {

	@MockitoBean
	private OrderService orderService;

	@Test
	void contextLoads() {
	}

}
