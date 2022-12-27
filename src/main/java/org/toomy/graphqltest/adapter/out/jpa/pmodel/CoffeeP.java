package org.toomy.graphqltest.adapter.out.jpa.pmodel;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("coffee")
public class CoffeeP {

    @Id
    Long id;
    String name;
    Size size;

    public enum Size {
        SHORT,
        TALL,
        GRANDE,
        VENTI
    }
}
