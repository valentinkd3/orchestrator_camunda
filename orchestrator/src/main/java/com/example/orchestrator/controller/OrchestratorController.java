package com.example.orchestrator.controller;

import com.example.orchestrator.constant.ProcessStage;
import com.example.orchestrator.dto.OrderRequestDto;
import com.example.orchestrator.dto.RequestContext;
import com.example.orchestrator.service.CamundaService;
import com.example.orchestrator.util.RequestContextRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.example.orchestrator.constant.ProcessStage.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class OrchestratorController {

    private final CamundaService camundaService;

    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDto orderRequestDto){
      log.info("Запрос попал в метод контроллера. Тело запроса: {}", orderRequestDto);
        RequestContextRegistry.setRequestContext(new RequestContext(orderRequestDto));
        log.info("Положили {} в ThreadLocal процесса", orderRequestDto);
        Map<String, Object> resultCamundaProcess = camundaService.startCamundaProcess(START_PROCESS_KEY.getValue());
        return ResponseEntity.ok(resultCamundaProcess);
    }
}
