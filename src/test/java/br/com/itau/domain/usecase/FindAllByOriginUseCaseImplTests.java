package br.com.itau.domain.usecase;

import br.com.itau.domain.entity.Breed;
import br.com.itau.domain.entity.Page;
import br.com.itau.domain.entity.Pageable;
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

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FindAllByOriginUseCaseImplTests {

    @InjectMocks
    private FindAllByOriginUseCaseImpl findAllByOriginUseCase;
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
    public void findAllByOriginTest() throws Exception {
        Page<Breed> breedPage = easyRandom.nextObject(Page.class);
        breedPage.setContent(easyRandom.objects(Breed.class, 10).collect(Collectors.toList()));
        Pageable pageable = easyRandom.nextObject(Pageable.class);
        String origin = "Brazil";

        Mockito.when(breedRepository.findAllByOrigin(pageable, origin.trim().toLowerCase())).thenReturn(breedPage);

        Page<Breed> allBreedName = findAllByOriginUseCase.findAllByOrigin(pageable, origin);

        assertThat(allBreedName).isNotNull();
        assertThat(allBreedName.getPage()).isEqualTo(breedPage.getPage());
        assertThat(allBreedName.getTotalElements()).isEqualTo(breedPage.getTotalElements());
        assertThat(allBreedName.getRowsPerPage()).isEqualTo(breedPage.getRowsPerPage());
        assertThat(allBreedName.getTotalPages()).isEqualTo(breedPage.getTotalPages());
        assertThat(allBreedName.getContent()).containsExactlyElementsOf(breedPage.getContent());

    }

}
