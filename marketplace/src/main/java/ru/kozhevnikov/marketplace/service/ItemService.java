package ru.kozhevnikov.marketplace.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kozhevnikov.marketplace.dto.ItemCompensationResponseDto;
import ru.kozhevnikov.marketplace.dto.ItemRequestDto;
import ru.kozhevnikov.marketplace.dto.ItemResponseDto;
import ru.kozhevnikov.marketplace.repository.ItemRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public ItemResponseDto reserveItem(ItemRequestDto itemRequestDto) {
        Long itemId = itemRequestDto.getItemId();
        ItemResponseDto itemResponseDto = new ItemResponseDto();
        itemRepository.findById(itemId)
                .ifPresent(item -> {
                    Integer amount = item.getQuantity();
                    Double price = item.getPrice();
                    Integer quantityToReserve = itemRequestDto.getQuantity();
                    if (amount >= quantityToReserve) {
                        amount -= quantityToReserve;
                        Double resultPrice = quantityToReserve * price;
                        item.setQuantity(amount);
                        itemResponseDto.setPrice(resultPrice);
                        itemResponseDto.setReserved(true);
                        log.info("Товар забронирован");
                    } else {
                        log.error("Товара недостаточно на складе");
                    }
                });
        return itemResponseDto;
    }

    @Transactional
    public ItemCompensationResponseDto compensationItem(ItemRequestDto itemRequestDto) {
        ItemCompensationResponseDto itemCompensationResponseDto = new ItemCompensationResponseDto();
        Long itemId = itemRequestDto.getItemId();
        itemRepository.findById(itemId)
                .ifPresent(item -> {
                            Integer amount = item.getQuantity();
                            Integer quantityToCompensation = itemRequestDto.getQuantity();
                            amount += quantityToCompensation;
                            item.setQuantity(amount);
                            itemCompensationResponseDto.setComp(true);
                            log.info("Оформлен возврат товара");
                        }
                );
        return itemCompensationResponseDto;
    }
}
