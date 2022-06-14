package it.si2001.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Pokemon {
    private Long id;
    private String name;
    private String link;
}
