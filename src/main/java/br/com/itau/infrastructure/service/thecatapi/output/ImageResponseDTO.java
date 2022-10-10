package br.com.itau.infrastructure.service.thecatapi.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponseDTO {

    private String id;
    private String url;

}
