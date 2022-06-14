package it.si2001.client.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PokemonResponseDto {
    private Integer count;
    private String next;
    private String previous;
    private List<PokemonDto> results;
}
