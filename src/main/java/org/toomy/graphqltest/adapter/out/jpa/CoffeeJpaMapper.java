package org.toomy.graphqltest.adapter.out.jpa;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.toomy.graphqltest.adapter.out.jpa.pmodel.CoffeeP;
import org.toomy.graphqltest.model.Coffee;
import org.toomy.graphqltest.model.Size;

@Mapper(componentModel = "spring")
public interface CoffeeJpaMapper {

    Coffee asCoffee(CoffeeP coffee);

    @Mapping(target = "id", ignore = true)
    CoffeeP asCoffeeP(String name, Size size);

    default Size asSize(String size) {
        return Optional.ofNullable(size)
                .map(Size::valueOf)
                .orElseThrow(() -> new IllegalArgumentException("Size must not be null"));
    }
}
