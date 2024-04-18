package com.example.orchestrator.bpmn;

import com.example.orchestrator.dto.ItemRequestDto;
import com.example.orchestrator.dto.OrderRequestDto;
import com.example.orchestrator.feign.MarketplaceFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static com.example.orchestrator.constant.ProcessStage.REQUEST_BODY;

@Component
@RequiredArgsConstructor
@Slf4j
public class CompensationDelegation implements JavaDelegate {

    private final MarketplaceFeignClient marketplaceFeignClient;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("Инициирована компенсирующая транзакция для возврата товара");
        OrderRequestDto orderRequestDto = (OrderRequestDto) delegateExecution.getVariable(REQUEST_BODY.getValue());
        ItemRequestDto itemRequestDto = ItemRequestDto.builder()
                .itemId(orderRequestDto.getItemId())
                .quantity(orderRequestDto.getQuantity())
                .build();
        marketplaceFeignClient.compensationItem(itemRequestDto);
    }
}
