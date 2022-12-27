package org.toomy.graphqltest.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.toomy.graphqltest.domain.ICoffeeService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class RestCoffeController {

    private final ICoffeeService coffeeService;
    private final CoffeeDtoMapper dtoMapper;

    @GetMapping("/all")
    public Flux<CoffeeDto> findAll() {
        return coffeeService.findAll()
                .map(dtoMapper::asCoffeeDto);
    }

    @GetMapping("/query/{id}")
    public Mono<CoffeeDto> findOne(@PathVariable Long id) {
        return coffeeService.findOne(id)
                .map(dtoMapper::asCoffeeDto);
    }

    @PutMapping
    public Mono<CoffeeDto> create(@RequestBody CreateCoffeeDto coffeeDto) {
        return coffeeService.create(coffeeDto.name(), dtoMapper.asSize(coffeeDto))
                .map(dtoMapper::asCoffeeDto);
    }

    @PostMapping
    public Mono<CoffeeDto> update(@RequestBody CoffeeDto coffeeDto) {
        return coffeeService.update(coffeeDto.id(), coffeeDto.name(), dtoMapper.asSize(coffeeDto))
                .map(dtoMapper::asCoffeeDto);
    }

    @DeleteMapping("/{id}")
    public Mono<CoffeeDto> delete(@PathVariable Long id) {
        return coffeeService.delete(id)
                .map(dtoMapper::asCoffeeDto);
    }

    @GetMapping("allPaged")
    public Mono<Page<CoffeeDto>> findAllPaged(@RequestParam("page") int page, @RequestParam("size") int size) {
        return coffeeService.findAllPaged(PageRequest.of(page, size))
                .map(v -> v.map(dtoMapper::asCoffeeDto));
    }
}
