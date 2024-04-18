package ru.kozhevnikov.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kozhevnikov.user.dto.DebitRequestDto;
import ru.kozhevnikov.user.dto.DebitResponseDto;
import ru.kozhevnikov.user.service.UserService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/debit")
    public ResponseEntity<DebitResponseDto> debit(@RequestBody DebitRequestDto debitRequestDto){
        log.info("Получен запрос на оплату товара: {}", debitRequestDto);
        DebitResponseDto debitResponseDto = userService.debit(debitRequestDto);
        return ResponseEntity.ok(debitResponseDto);
    }
}
