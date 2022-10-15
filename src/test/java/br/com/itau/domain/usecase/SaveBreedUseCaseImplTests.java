package br.com.itau.domain.usecase;

import br.com.itau.domain.entity.Breed;
import br.com.itau.domain.port.repository.BreedRepository;
import br.com.itau.domain.port.repository.LogRepository;
import br.com.itau.domain.port.service.GetExternalBreedService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaveBreedUseCaseImplTests {

    @InjectMocks
    private SaveBreedUseCaseImpl saveBreedUseCase;
    @Mock
    private BreedRepository breedRepository;
    @Mock
    private GetExternalBreedService externalBreedService;
    @Mock
    private LogRepository logRepository;
    private static EasyRandom easyRandom;

    @BeforeAll
    private static void beforeTests() {
        easyRandom = new EasyRandom();
    }

    @Test
    public void saveTest() throws Exception {
        List<Breed> breeds = easyRandom.objects(Breed.class, 10).collect(Collectors.toList());

        Mockito.when(externalBreedService.getAllBreeds()).thenReturn(breeds);
        Mockito.when(breedRepository.existsByExternalId(any())).thenReturn(false);

        saveBreedUseCase.save();

        breeds.forEach(breed -> verify(breedRepository).save(breed));
        verify(breedRepository, atLeast(10)).existsByExternalId(any());
        verify(breedRepository, atLeast(10)).save(any());

    }

    @Test
    public void saveNotBreedsTest() throws Exception {
        Mockito.lenient().when(externalBreedService.getAllBreeds()).thenReturn(Collections.emptyList());

        verify(breedRepository, never()).existsByExternalId(any());
        verify(breedRepository, never()).save(any());

    }

}
