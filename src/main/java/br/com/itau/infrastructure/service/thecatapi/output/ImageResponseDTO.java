package br.com.itau.infrastructure.service.thecatapi.output;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageResponseDTO {

    private String id;
    private String url;

}
