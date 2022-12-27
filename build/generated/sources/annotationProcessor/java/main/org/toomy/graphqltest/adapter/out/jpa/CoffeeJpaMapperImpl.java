package org.toomy.graphqltest.adapter.out.jpa;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.toomy.graphqltest.adapter.out.jpa.pmodel.CoffeeP;
import org.toomy.graphqltest.model.Coffee;
import org.toomy.graphqltest.model.Size;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-26T13:10:01+0100",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.jar, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class CoffeeJpaMapperImpl implements CoffeeJpaMapper {

    @Override
    public Coffee asCoffee(CoffeeP coffee) {
        if ( coffee == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        Size size = null;

        id = coffee.getId();
        name = coffee.getName();
        if ( coffee.getSize() != null ) {
            size = asSize( coffee.getSize().name() );
        }

        Coffee coffee1 = new Coffee( id, name, size );

        return coffee1;
    }

    @Override
    public CoffeeP asCoffeeP(String name, Size size) {
        if ( name == null && size == null ) {
            return null;
        }

        CoffeeP.CoffeePBuilder coffeeP = CoffeeP.builder();

        coffeeP.name( name );
        coffeeP.size( sizeToSize( size ) );

        return coffeeP.build();
    }

    protected CoffeeP.Size sizeToSize(Size size) {
        if ( size == null ) {
            return null;
        }

        CoffeeP.Size size1;

        switch ( size ) {
            case SHORT: size1 = CoffeeP.Size.SHORT;
            break;
            case TALL: size1 = CoffeeP.Size.TALL;
            break;
            case GRANDE: size1 = CoffeeP.Size.GRANDE;
            break;
            case VENTI: size1 = CoffeeP.Size.VENTI;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + size );
        }

        return size1;
    }
}
