package br.com.itau.application.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreedResponseDTO {

    private UUID id;
    private String name;
    private String origin;
    private String description;
    private List<TemperamentResponseDTO> temperaments;
    private List<ImageResponseDTO> images;

}
