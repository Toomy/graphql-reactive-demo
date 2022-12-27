package org.toomy.graphqltest.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.toomy.graphqltest.model.Coffee;
import org.toomy.graphqltest.model.Size;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICoffeeService {

    Flux<Coffee> findAll();

    Mono<Coffee> findOne(@Argument Long id);

    Mono<Coffee> create(@Argument String name, @Argument Size size);

    Mono<Coffee> update(@Argument Long id, @Argument String name, @Argument Size size);

    Mono<Coffee> delete(@Argument Long id);

    Mono<Page<Coffee>> findAllPaged(PageRequest pageRequest);

}
