package ru.kozhevnikov.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kozhevnikov.user.dto.DebitRequestDto;
import ru.kozhevnikov.user.dto.DebitResponseDto;
import ru.kozhevnikov.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public DebitResponseDto debit(DebitRequestDto debitRequestDto) {
        DebitResponseDto debitResponseDto = new DebitResponseDto();
        userRepository.findById(debitRequestDto.getUserId())
                .ifPresent(user -> {
                    Double price = debitRequestDto.getPrice();
                    Double balance = user.getBalance();
                    if (price <= balance){
                        balance -= price;
                        user.setBalance(balance);
                        debitResponseDto.setDebit(true);
                        log.info("Оплата товара прошла успешно");
                    } else {
                        log.error("Оплата не прошла");
                    }
                });
        return debitResponseDto;
    }
}
