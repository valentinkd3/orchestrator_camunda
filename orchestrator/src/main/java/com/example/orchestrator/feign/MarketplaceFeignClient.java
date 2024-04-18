package com.example.orchestrator.feign;

import com.example.orchestrator.dto.ItemCompensationResponseDto;
import com.example.orchestrator.dto.ItemRequestDto;
import com.example.orchestrator.dto.ItemResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "marketplace", url = "${feign.marketplace.url}", path = "${feign.marketplace.path}")
public interface MarketplaceFeignClient {

    @PostMapping("/reserve")
    ResponseEntity<ItemResponseDto> reserveItem(@RequestBody ItemRequestDto itemRequestDto);

    @PostMapping("/compensation")
    ResponseEntity<ItemCompensationResponseDto> compensationItem(@RequestBody ItemRequestDto itemRequestDto);
}
