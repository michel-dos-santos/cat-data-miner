package br.com.itau.infrastructure.service.thecatapi;

import br.com.itau.infrastructure.service.thecatapi.output.BreedResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Component
@FeignClient(value = "theCatAPIServiceFeignClient", url = "${api.cats.url}", fallbackFactory = TheCatAPIServiceFallback.class)
public interface TheCatAPIServiceFeignClient {

    @GetMapping(value = "/v1/breeds")
    List<BreedResponseDTO> getAllBreeds(@RequestHeader("x-api-key") String apiKey);

}