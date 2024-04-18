package com.example.orchestrator.event;

import com.example.orchestrator.dto.OrderRequestDto;
import com.example.orchestrator.util.RequestContextRegistry;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

import static com.example.orchestrator.constant.ProcessStage.REQUEST_BODY;

@Slf4j
@Component
public class RequestExecutionListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        if (!delegateExecution.getVariables().containsKey(REQUEST_BODY.getValue()) &&
            RequestContextRegistry.getRequestContext() != null){
            OrderRequestDto requestBody = (OrderRequestDto) RequestContextRegistry.getRequestContext().getRequestBody();
            delegateExecution.setVariable(REQUEST_BODY.getValue(), requestBody);
            log.info("Запрос попал в ExecutionListener. Положили объект {} в Variable процесса", requestBody);
        }
    }
}
