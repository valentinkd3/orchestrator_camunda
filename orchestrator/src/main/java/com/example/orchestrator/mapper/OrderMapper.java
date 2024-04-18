package com.example.orchestrator.mapper;

import com.example.orchestrator.domain.Order;
import com.example.orchestrator.dto.OrderRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "status", constant = "CREATED")
    Order mapToEntity(OrderRequestDto dto);

}
