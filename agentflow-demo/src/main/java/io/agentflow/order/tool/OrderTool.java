package io.agentflow.order.tool;

import io.agentflow.ai.tool.annotation.AgentTool;
import io.agentflow.order.model.OrderInfo;
import io.agentflow.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;

@RequiredArgsConstructor
@AgentTool(name = "order", description = "订单查询工具", category = "business")
public class OrderTool {

    private final OrderService orderService;

    @Tool(description = """
            查询订单信息。
            
            适用场景：
            - 查询订单详情
            - 查询订单状态
            - 查询物流状态
            - 查询订单金额
            
            参数：
            - orderId：订单编号
            """)
    public OrderInfo getOrderInfo(String orderId) {
        return orderService.findById(orderId);
    }
}
