package io.agentflow.order.service;

import io.agentflow.order.model.OrderInfo;
import io.agentflow.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderInfo findById(String orderId){

        return orderRepository.findById(orderId);
    }
}
