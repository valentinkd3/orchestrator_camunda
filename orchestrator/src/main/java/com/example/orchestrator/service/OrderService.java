package com.example.orchestrator.service;

import com.example.orchestrator.domain.Order;
import com.example.orchestrator.domain.Status;
import com.example.orchestrator.dto.ItemRequestDto;
import com.example.orchestrator.dto.OrderRequestDto;
import com.example.orchestrator.feign.MarketplaceFeignClient;
import com.example.orchestrator.mapper.OrderMapper;
import com.example.orchestrator.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.orchestrator.constant.ProcessStage.REQUEST_BODY;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    private final MarketplaceFeignClient marketplaceFeignClient;

    private final OrderMapper mapper;

    @Transactional
    public Long createOrder(OrderRequestDto orderRequestDto){
        Order order = mapper.mapToEntity(orderRequestDto);
        return orderRepository.saveAndFlush(order).getId();
    }

    @Transactional
    public void saveOrder(Long orderId, Double price, Status status) {
        orderRepository.findById(orderId)
                .ifPresent(order -> {
                    order.setStatus(status);
                    order.setPrice(price);
                });
    }
}
