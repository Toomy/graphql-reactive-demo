package org.toomy.graphqltest.adapter.out.jpa;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.toomy.graphqltest.adapter.out.jpa.pmodel.CoffeeP;
import reactor.core.publisher.Flux;

public interface CoffeeRepository extends R2dbcRepository<CoffeeP, Long> {

    Flux<CoffeeP> findAllBy(Pageable pageable);
}
