package br.com.itau.infrastructure.service.thecatapi.output;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BreedResponseDTO {

    private String id;
    private String name;
    private String temperament;
    private String origin;
    private String description;
    private ImageResponseDTO image;

}
