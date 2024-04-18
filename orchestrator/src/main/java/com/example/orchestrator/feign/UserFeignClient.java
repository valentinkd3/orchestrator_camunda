package com.example.orchestrator.feign;

import com.example.orchestrator.dto.DebitRequestDto;
import com.example.orchestrator.dto.DebitResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user", url = "${feign.user.url}", path = "${feign.user.path}")
public interface UserFeignClient {

    @PostMapping("/debit")
    ResponseEntity<DebitResponseDto> debit(@RequestBody DebitRequestDto debitRequestDto);
}
