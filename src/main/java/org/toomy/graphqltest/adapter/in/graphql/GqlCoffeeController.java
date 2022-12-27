package org.toomy.graphqltest.adapter.in.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.toomy.graphqltest.domain.ICoffeeService;
import org.toomy.graphqltest.model.Coffee;
import org.toomy.graphqltest.model.Size;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class GqlCoffeeController {

    private final ICoffeeService coffeeService;

    @QueryMapping
    public Flux<Coffee> findAll() {
        return coffeeService.findAll();
    }

    @QueryMapping
    public Mono<Coffee> findOne(@Argument Long id) {
        return coffeeService.findOne(id);
    }

    @MutationMapping
    public Mono<Coffee> create(@Argument String name, @Argument Size size) {
        return coffeeService.create(name, size);
    }

    @MutationMapping
    public Mono<Coffee> update(@Argument Long id, @Argument String name, @Argument Size size) {
        return coffeeService.update(id, name, size);
    }

    @MutationMapping
    public Mono<Coffee> delete(@Argument Long id) {
        return coffeeService.delete(id);
    }
}
