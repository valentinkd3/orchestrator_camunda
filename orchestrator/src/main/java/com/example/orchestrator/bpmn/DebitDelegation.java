package com.example.orchestrator.bpmn;

import com.example.orchestrator.domain.Status;
import com.example.orchestrator.dto.DebitRequestDto;
import com.example.orchestrator.dto.DebitResponseDto;
import com.example.orchestrator.dto.ItemResponseDto;
import com.example.orchestrator.dto.OrderRequestDto;
import com.example.orchestrator.feign.UserFeignClient;
import com.example.orchestrator.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static com.example.orchestrator.constant.ProcessStage.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class DebitDelegation implements JavaDelegate {

    private final UserFeignClient userFeignClient;

    private final OrderService orderService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        OrderRequestDto orderRequestDto = (OrderRequestDto) delegateExecution.getVariable(REQUEST_BODY.getValue());
        Double price = (Double) delegateExecution.getVariable(PRICE.getValue());
        Long orderId = (Long) delegateExecution.getVariable(ORDER_ID.getValue());
        DebitRequestDto debitRequestDto = DebitRequestDto.builder()
                .userId(orderRequestDto.getUserId())
                .price(price)
                .build();
        log.info("Направлен запрос в сервис User для оплаты товара");
        ResponseEntity<DebitResponseDto> response = userFeignClient.debit(debitRequestDto);
        if (!(response.getStatusCode().is2xxSuccessful() && response.getBody().isDebit())) {
            log.error("Произошел сбой при оплате товара");
            orderService.saveOrder(orderId,price, Status.FAIL);
            throw new BpmnError(ERROR.getValue());
        }
        log.info("Товар оплачен в сервисе User");
        orderService.saveOrder(orderId, price, Status.SUCCESS);
    }
}
