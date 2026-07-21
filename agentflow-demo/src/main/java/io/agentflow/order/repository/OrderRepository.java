package io.agentflow.order.repository;

import io.agentflow.order.model.OrderInfo;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class OrderRepository {

    public OrderInfo findById(String orderId) {
        return new OrderInfo(
                orderId,
                "张三",
                "已发货",
                new BigDecimal("199.00")
        );
    }
}
