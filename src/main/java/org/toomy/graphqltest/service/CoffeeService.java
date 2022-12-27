package org.toomy.graphqltest.service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import org.toomy.graphqltest.model.Coffee;
import org.toomy.graphqltest.model.Size;

@Service
public class CoffeeService {

    private final Map<Long, Coffee> coffees = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(0);

    public List<Coffee> findAll() {
        return List.copyOf(coffees.values());
    }

    public Optional<Coffee> findOne(Integer id) {
        return Optional.ofNullable(coffees.get(id));
    }

    public Coffee create(String name, Size size) {
        var coffee = new Coffee(id.incrementAndGet(), name, size);
        coffees.put(coffee.id(), coffee);
        return coffee;
    }

    public Coffee update(Long id, String name, Size size) {
        var updatedCoffee = coffees.compute(id, (k, v) ->
                Optional.ofNullable(v)
                        .map(oV -> new Coffee(oV.id(), name, size))
                        .orElseThrow(() -> new IllegalArgumentException("Invalid coffee"))
        );
        return updatedCoffee;
    }

    public Integer delete(Integer id) {
        coffees.remove(id);
        return id;
    }

    @PostConstruct
    private void init() {
        coffees.compute(id.incrementAndGet(), (k, v) -> new Coffee(k, "Caffè Americano", Size.GRANDE));
        coffees.compute(id.incrementAndGet(), (k, v) -> new Coffee(k, "Caffè Latte", Size.VENTI));
        coffees.compute(id.incrementAndGet(), (k, v) -> new Coffee(k, "Caffè Caramel Macchiato", Size.TALL));
    }
}
