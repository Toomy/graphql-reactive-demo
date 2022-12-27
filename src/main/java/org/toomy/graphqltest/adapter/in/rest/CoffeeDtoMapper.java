package org.toomy.graphqltest.adapter.in.rest;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.toomy.graphqltest.model.Coffee;
import org.toomy.graphqltest.model.Size;

@Mapper(componentModel = "spring")
public interface CoffeeDtoMapper {

    CoffeeDto asCoffeeDto(Coffee coffee);

    default Size asSize(String dto) {
        return Optional.ofNullable(dto)
                .map(Size::valueOf)
                .orElseThrow(() -> new IllegalArgumentException("Size must not be null"));
    }

    default Size asSize(CreateCoffeeDto dto) {
        return Optional.ofNullable(dto)
                .map(v -> asSize(v.size()))
                .orElseThrow(() -> new IllegalArgumentException("CreateCoffeeDto must not be null"));
    }

    default Size asSize(CoffeeDto dto) {
        return Optional.ofNullable(dto)
                .map(v -> asSize(v.size()))
                .orElseThrow(() -> new IllegalArgumentException("CoffeeDto must not be null"));
    }
}
