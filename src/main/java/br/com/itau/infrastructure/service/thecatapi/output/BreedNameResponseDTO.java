package br.com.itau.infrastructure.service.thecatapi.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreedNameResponseDTO {

    private long id;
    private String name;

}
