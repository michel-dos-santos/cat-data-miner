package br.com.itau.infrastructure.service.thecatapi;

import br.com.itau.domain.entity.Breed;
import br.com.itau.domain.port.repository.LogRepository;
import br.com.itau.domain.port.service.GetExternalBreedService;
import br.com.itau.infrastructure.service.thecatapi.output.BreedResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class TheCatAPIServiceImpl implements GetExternalBreedService {

    @Value("${api.cats.key}")
    private String apiKey;
    @Autowired
    private TheCatAPIServiceFeignClient theCatAPIServiceFeignClient;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LogRepository logRepository;

    @Override
    public List<Breed> getAllBreeds() {
        logRepository.info("Caso de Uso: saveBreedUseCase -> Obtendo todas as raças da The Cat API");
        List<BreedResponseDTO> allBreeds = theCatAPIServiceFeignClient.getAllBreeds(apiKey);

        if (Objects.nonNull(allBreeds) && !allBreeds.isEmpty()) {
            logRepository.info(String.format("Caso de Uso: saveBreedUseCase -> Obtido %s raças da The Cat API", allBreeds.size()));
            return Arrays.asList(modelMapper.map(allBreeds, Breed[].class));
        }

        logRepository.info("Caso de Uso: saveBreedUseCase -> Não foi recuperado nenhuma raça da The Cat API");
        return new ArrayList<>();
    }

}