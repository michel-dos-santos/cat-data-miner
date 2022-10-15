package br.com.itau.application.output;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class BreedNameResponseDTO {

    private UUID id;
    private String name;

}
