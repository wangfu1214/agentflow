package io.agentflow.order.model;

import java.math.BigDecimal;

public record OrderInfo(
        //订单ID
        String orderId,
        //客户名称
        String customerName,
        //订单状态
        String status,
        //订单金额
        BigDecimal amount
) {
}
