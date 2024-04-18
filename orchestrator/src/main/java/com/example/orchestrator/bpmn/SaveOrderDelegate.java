package com.example.orchestrator.bpmn;

import com.example.orchestrator.dto.OrderRequestDto;
import com.example.orchestrator.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static com.example.orchestrator.constant.ProcessStage.ORDER_ID;
import static com.example.orchestrator.constant.ProcessStage.REQUEST_BODY;

@Component
@RequiredArgsConstructor
@Slf4j
public class SaveOrderDelegate implements JavaDelegate {

    private final OrderService orderService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("SaveOrderDelegate: Сохранение заказа в БД");
        OrderRequestDto orderRequestDto = (OrderRequestDto) delegateExecution.getVariable(REQUEST_BODY.getValue());
        Long orderId = orderService.createOrder(orderRequestDto);
        delegateExecution.setVariable(ORDER_ID.getValue(), orderId);
    }
}
