package ru.kozhevnikov.marketplace.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kozhevnikov.marketplace.dto.ItemCompensationResponseDto;
import ru.kozhevnikov.marketplace.dto.ItemRequestDto;
import ru.kozhevnikov.marketplace.dto.ItemResponseDto;
import ru.kozhevnikov.marketplace.service.ItemService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class MarketplaceController {

    private final ItemService itemService;

    @PostMapping("/reserve")
    public ResponseEntity<ItemResponseDto> reserveItem(@RequestBody ItemRequestDto itemRequestDto){
        log.info("Получен запрос на бронирование товара: {}", itemRequestDto);
        ItemResponseDto itemResponseDto = itemService.reserveItem(itemRequestDto);
        return ResponseEntity.ok(itemResponseDto);
    }

    @PostMapping("/compensation")
    public ResponseEntity<ItemCompensationResponseDto> compensationItem(@RequestBody ItemRequestDto itemRequestDto){
        log.info("Получен запрос на компенсацию бронирования товара: {}", itemRequestDto);
        ItemCompensationResponseDto response = itemService.compensationItem(itemRequestDto);
        return ResponseEntity.ok(response);
    }
}
