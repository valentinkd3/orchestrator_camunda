package com.example.orchestrator.bpmn;

import com.example.orchestrator.domain.Status;
import com.example.orchestrator.dto.ItemRequestDto;
import com.example.orchestrator.dto.ItemResponseDto;
import com.example.orchestrator.dto.OrderRequestDto;
import com.example.orchestrator.feign.MarketplaceFeignClient;
import com.example.orchestrator.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static com.example.orchestrator.constant.ProcessStage.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ItemReservationDelegation implements JavaDelegate {

    private final MarketplaceFeignClient marketplaceFeignClient;

    private final OrderService orderService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        OrderRequestDto orderRequestDto = (OrderRequestDto) delegateExecution.getVariable(REQUEST_BODY.getValue());
        Double price = (Double) delegateExecution.getVariable(PRICE.getValue());
        ItemRequestDto itemRequestDto = ItemRequestDto.builder()
                .itemId(orderRequestDto.getItemId())
                .quantity(orderRequestDto.getQuantity())
                .build();
        log.info("Направлен запрос в сервис Marketplace для бронирования товара");
        ResponseEntity<ItemResponseDto> response = marketplaceFeignClient.reserveItem(itemRequestDto);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody().isReserved()) {
            delegateExecution.setVariable(PRICE.getValue(), response.getBody().getPrice());
            log.info("Товар забронирован в сервисе Marketplace");
        } else {
            log.error("Произошел сбой при броинровании товара");
            Long orderId = (Long) delegateExecution.getVariable(ORDER_ID.getValue());
            orderService.saveOrder(orderId,price, Status.FAIL);
            throw new BpmnError(ERROR.getValue());
        }
    }
}
