package br.com.itau.application.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreedNameResponseDTO {

    private UUID id;
    private String name;

}
