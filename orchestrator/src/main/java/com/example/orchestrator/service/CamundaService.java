package com.example.orchestrator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class CamundaService {

    private final RuntimeService runtimeService;

    public Map<String, Object> startCamundaProcess(String processDefinitionKey) {
        log.info("Запустили процесс Camunda: {}", processDefinitionKey);
        ProcessInstanceWithVariables processInstance = runtimeService.createProcessInstanceByKey(processDefinitionKey).executeWithVariablesInReturn();
        Map<String, Object> resultCamundaProcess = new ConcurrentHashMap<>();
        processInstance.getVariables().entrySet().stream()
                .filter(entry -> !entry.getKey().contains("Error"))
                .forEach(entry -> {
                    resultCamundaProcess.put(entry.getKey(), entry.getValue());
                });
        log.info("Завершили процесс Camunda. Получили набор параметров: {}", resultCamundaProcess);
        return resultCamundaProcess;
    }
}
