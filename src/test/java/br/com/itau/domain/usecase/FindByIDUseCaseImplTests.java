package br.com.itau.domain.usecase;

import br.com.itau.domain.entity.Breed;
import br.com.itau.domain.exception.BreedNotFoundException;
import br.com.itau.domain.port.repository.BreedRepository;
import br.com.itau.domain.port.repository.LogRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class FindByIDUseCaseImplTests {

    @InjectMocks
    private FindByIDUseCaseImpl findByIDUseCase;
    @Mock
    private BreedRepository breedRepository;
    @Mock
    private LogRepository logRepository;
    private static EasyRandom easyRandom;

    @BeforeAll
    private static void beforeTests() {
        easyRandom = new EasyRandom();
    }

    @Test
    public void findByIDTest() throws Exception {
        Breed breed = easyRandom.nextObject(Breed.class);
        Optional<Breed> optionalBreed = Optional.of(breed);
        UUID id = UUID.randomUUID();

        Mockito.when(breedRepository.findByID(id)).thenReturn(optionalBreed);

        Breed breedByID = findByIDUseCase.findByID(id);

        assertThat(breedByID).isNotNull();
        assertThat(breedByID.getId()).isEqualTo(breed.getId());
        assertThat(breedByID.getName()).isEqualTo(breed.getName());
        assertThat(breedByID.getOrigin()).isEqualTo(breed.getOrigin());
        assertThat(breedByID.getExternalId()).isEqualTo(breed.getExternalId());
        assertThat(breedByID.getDescription()).isEqualTo(breed.getDescription());
        assertThat(breedByID.getImages()).containsExactlyElementsOf(breed.getImages());
        assertThat(breedByID.getTemperaments()).containsExactlyElementsOf(breed.getTemperaments());

    }

    @Test
    public void findByIDNotFoundExceptionTest() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(breedRepository.findByID(id)).thenThrow(new BreedNotFoundException("id", id.toString()));

        assertThatThrownBy(() -> findByIDUseCase.findByID(id))
                .isInstanceOf(BreedNotFoundException.class)
                .hasMessage(String.format("Raça não encontrado com base no id: %s", id));

    }

}
