package ru.kozhevnikov.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebitRequestDto {

    private Long userId;
    private Double price;
}
