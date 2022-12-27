package org.toomy.graphqltest.adapter.in.rest;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.toomy.graphqltest.model.Coffee;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-26T13:10:00+0100",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.jar, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class CoffeeDtoMapperImpl implements CoffeeDtoMapper {

    @Override
    public CoffeeDto asCoffeeDto(Coffee coffee) {
        if ( coffee == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String size = null;

        id = coffee.id();
        name = coffee.name();
        if ( coffee.size() != null ) {
            size = coffee.size().name();
        }

        CoffeeDto coffeeDto = new CoffeeDto( id, name, size );

        return coffeeDto;
    }
}
