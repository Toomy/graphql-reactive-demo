package org.toomy.graphqltest.adapter.out.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.toomy.graphqltest.domain.ICoffeeService;
import org.toomy.graphqltest.model.Coffee;
import org.toomy.graphqltest.model.Size;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * https://www.vinsguru.com/r2dbc-pagination/
 * https://www.youtube.com/watch?v=oq-c3D67WqM
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CoffeeDbAdapter implements ICoffeeService {

    private final CoffeeRepository repo;
    private final CoffeeJpaMapper jpaMapper;


    /**
     * To Stream the JSON, add the following header to the request:
     * <pre>
     *     Accept: application/x-ndjson
     * </pre>
     *
     * @return
     */
    @Override
    public Flux<Coffee> findAll() {
        return repo.findAll()
                .map(jpaMapper::asCoffee);
    }

    @Override
    public Mono<Coffee> findOne(Long id) {
        return repo.findById(id)
                .map(jpaMapper::asCoffee);
    }

    @Override
    public Mono<Coffee> create(String name, Size size) {
        return repo.save(jpaMapper.asCoffeeP(name, size))
                .map(jpaMapper::asCoffee);
    }

    @Override
    public Mono<Coffee> update(Long id, String name, Size size) {
        return repo.save(jpaMapper.asCoffeeP(name, size))
                .map(jpaMapper::asCoffee);
    }

    @Override
    public Mono<Coffee> delete(Long id) {
        return repo.deleteById(id)
                .then(Mono.empty());
    }

    @Override
    public Mono<Page<Coffee>> findAllPaged(PageRequest pageRequest) {
        return repo.findAllBy(pageRequest)
                .map(jpaMapper::asCoffee)
                .collectList()
                .zipWith(repo.count())
                .map(t -> new PageImpl<>(t.getT1(), pageRequest, t.getT2()));
    }
}
