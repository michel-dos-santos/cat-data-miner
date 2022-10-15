package br.com.itau.infrastructure.service.thecatapi;

import br.com.itau.domain.entity.Breed;
import br.com.itau.domain.exception.ServiceUnavailableException;
import br.com.itau.domain.port.repository.LogRepository;
import br.com.itau.infrastructure.service.thecatapi.output.BreedResponseDTO;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class TheCatAPIServiceImplTests {

    @InjectMocks
    private TheCatAPIServiceImpl theCatAPIService;
    @Mock
    private TheCatAPIServiceFeignClient theCatAPIServiceFeignClient;
    @Mock
    private TheCatAPIServiceFallback theCatAPIServiceFallback;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private LogRepository logRepository;
    private static EasyRandom easyRandom;

    @BeforeAll
    private static void beforeTests() {
        easyRandom = new EasyRandom();
    }

    @Test
    public void getAllBreedsTest() throws Exception {
        List<BreedResponseDTO> breedResponseDTOS = easyRandom.objects(BreedResponseDTO.class, 10).collect(Collectors.toList());
        List<Breed> breeds = easyRandom.objects(Breed.class, 10).collect(Collectors.toList());

        Mockito.when(theCatAPIServiceFeignClient.getAllBreeds(any())).thenReturn(breedResponseDTOS);
        Mockito.when(modelMapper.map(breedResponseDTOS, Breed[].class)).thenReturn(breeds.toArray(Breed[]::new));

        List<Breed> allBreeds = theCatAPIService.getAllBreeds();

        assertThat(allBreeds).isNotNull();
        assertThat(allBreeds).isNotEmpty();
        assertThat(allBreeds).containsExactlyElementsOf(breeds);

    }

    @Test
    public void getAllBreedsEmptyTest() throws Exception {
        Mockito.when(theCatAPIServiceFeignClient.getAllBreeds(any())).thenReturn(Collections.emptyList());

        List<Breed> allBreeds = theCatAPIService.getAllBreeds();

        assertThat(allBreeds).isEmpty();

    }

    @Test
    public void getAllBreedsNullTest() throws Exception {
        Mockito.when(theCatAPIServiceFeignClient.getAllBreeds(any())).thenReturn(null);

        List<Breed> allBreeds = theCatAPIService.getAllBreeds();

        assertThat(allBreeds).isEmpty();

    }

    @Test
    public void getAllBreedsServiceUnavailableTest() throws Exception {
        Mockito.when(theCatAPIServiceFeignClient.getAllBreeds(any())).thenThrow(new ServiceUnavailableException("The Cat API"));

        assertThatThrownBy(() -> theCatAPIService.getAllBreeds())
                .isInstanceOf(ServiceUnavailableException.class)
                .hasMessage("Não foi possível acessar o serviço The Cat API para obter os dados das raças");

    }

    @Test
    public void createFallbackFactoryTest() throws Exception {
        Mockito.when(theCatAPIServiceFallback.create(any())).thenThrow(new ServiceUnavailableException("The Cat API"));

        assertThatThrownBy(() -> theCatAPIServiceFallback.create(any()))
                .isInstanceOf(ServiceUnavailableException.class)
                .hasMessage("Não foi possível acessar o serviço The Cat API para obter os dados das raças");

    }

}
