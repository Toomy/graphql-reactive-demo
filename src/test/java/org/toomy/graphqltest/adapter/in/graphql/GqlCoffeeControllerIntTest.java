package org.toomy.graphqltest.adapter.in.graphql;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.toomy.graphqltest.domain.ICoffeeService;
import org.toomy.graphqltest.model.Coffee;
import org.toomy.graphqltest.model.Size;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@GraphQlTest(GqlCoffeeController.class)
class GqlCoffeeControllerIntTest {

    @Autowired
    GraphQlTester tester;

    @MockBean
    ICoffeeService coffeeService;

    private static final Map<Long, Coffee> COFFEES = Map.of(
            1L, new Coffee(1L, "Caffè Americano", Size.GRANDE),
            2L, new Coffee(2L, "Caffè Latte", Size.VENTI),
            3L, new Coffee(3L, "Caffè Caramel Macchiato", Size.TALL)
    );

    @Test
    void setTestFindAllCoffeesShouldReturnAllCoffies() {
        Mockito.when(coffeeService.findAll()).thenReturn(Flux.fromIterable(COFFEES.values()));

        // language=GraphQL
        var document = """
                query {
                    findAll {
                        id
                        name
                        size                    
                   }
                }
                """;

        tester.document(document)
                .execute()
                .path("findAll")
                .entityList(Coffee.class)
                .hasSize(3);
    }

    @Test
    void testValidIdShouldReturnCoffee() {
        Mockito.when(coffeeService.findOne(Mockito.anyLong()))
                .thenAnswer(invocation -> {
                    var id = invocation.getArgument(0, Long.class);
                    return Mono.just(id).map(COFFEES::get);
                });

        // language=GraphQL
        var document = """
                query findOne($id: ID){
                    findOne(id: $id){
                    id
                    name
                    size
                    }
                }        
                """;

        var testId = 2L;
        tester.document(document)
                .variable("id", testId)
                .execute()
                .path("findOne")
                .entity(Coffee.class)
                .satisfies(coffee ->
                        assertEquals(COFFEES.get(testId), coffee)
                );
    }

    @Test
    void shouldCreateNewCoffee() {
        final var coffees = new LinkedList<Coffee>();
        Mockito.when(coffeeService.create(Mockito.anyString(), Mockito.any(Size.class)))
                .thenAnswer(invocation -> {
                    var name = invocation.getArgument(0, String.class);
                    var size = invocation.getArgument(1, Size.class);
                    return Mono.just(new Coffee(0L, name, size))
                            .doOnSuccess(coffees::add);
                });

        // language=GraphQL
        var document = """
                mutation create($name: String, $size: Size){
                    create(name: $name, size: $size){
                    id
                    name
                    size
                    }
                }        
                """;

        tester.document(document)
                .variable("name", "Caffe Speziale")
                .variable("size", Size.VENTI)
                .execute()
                .path("create")
                .entity(Coffee.class)
                .satisfies(coffee -> assertEquals(coffees.getFirst(), coffee));
    }
}