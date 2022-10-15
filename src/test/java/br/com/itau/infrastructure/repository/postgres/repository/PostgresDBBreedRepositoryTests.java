package br.com.itau.infrastructure.repository.postgres.repository;

import br.com.itau.domain.entity.*;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostgresDBBreedRepositoryTests {

    @Autowired
    private PostgresDBBreedRepository postgresDBBreedRepository;
    private static EasyRandom easyRandom;

    @BeforeAll
    private static void beforeTests() {
        easyRandom = new EasyRandom();
    }

    @Test
    public void saveTest() throws Exception {
        Breed breed = easyRandom.nextObject(Breed.class);
        breed.setId(null);
        breed.setTemperaments(null);
        breed.setImages(null);

        Breed breedWithTemperamentsAndImages = easyRandom.nextObject(Breed.class);
        breedWithTemperamentsAndImages.setId(null);
        breedWithTemperamentsAndImages.setTemperaments(easyRandom.objects(Temperament.class, 2).collect(Collectors.toList()));
        breedWithTemperamentsAndImages.getTemperaments().forEach(temperament -> temperament.setId(null));
        breedWithTemperamentsAndImages.setImages(easyRandom.objects(Image.class, 2).collect(Collectors.toList()));
        breedWithTemperamentsAndImages.getImages().forEach(image -> image.setId(null));

        postgresDBBreedRepository.save(breedWithTemperamentsAndImages);
        Optional<Breed> optionalBreedWithTemperamentsAndImages = postgresDBBreedRepository.findByID(breedWithTemperamentsAndImages.getId());
        assertThat(optionalBreedWithTemperamentsAndImages).isPresent();

        postgresDBBreedRepository.save(breed);
        Optional<Breed> optionalBreed = postgresDBBreedRepository.findByID(breed.getId());
        assertThat(optionalBreed).isPresent();

    }

    @Test
    public void findByIDTest() throws Exception {
        Breed breed = easyRandom.nextObject(Breed.class);
        breed.setId(null);
        breed.setTemperaments(null);
        breed.setImages(null);
        postgresDBBreedRepository.save(breed);

        Optional<Breed> optionalBreedPresent = postgresDBBreedRepository.findByID(breed.getId());
        assertThat(optionalBreedPresent).isPresent();

        Optional<Breed> optionalBreedNotPresent = postgresDBBreedRepository.findByID(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        assertThat(optionalBreedNotPresent).isEmpty();

    }

    @Test
    public void findAllTest() throws Exception {
        Breed breed = easyRandom.nextObject(Breed.class);
        breed.setId(null);
        breed.setTemperaments(easyRandom.objects(Temperament.class, 2).collect(Collectors.toList()));
        breed.getTemperaments().forEach(temperament -> temperament.setId(null));
        breed.setImages(easyRandom.objects(Image.class, 2).collect(Collectors.toList()));
        breed.getImages().forEach(image -> image.setId(null));
        postgresDBBreedRepository.save(breed);

        Page<Breed> all = postgresDBBreedRepository.findAll(new Pageable(1, 100, "name", "desc"));
        assertThat(all.getContent().size()).isGreaterThanOrEqualTo(1);

        Page<Breed> page = postgresDBBreedRepository.findAll(new Pageable(100000, 1, "name", "desc"));
        assertThat(page.getContent().size()).isLessThanOrEqualTo(0);
    }

    @Test
    public void findAllByTemperamentTest() throws Exception {
        Breed breed = easyRandom.nextObject(Breed.class);
        breed.setId(null);
        breed.setTemperaments(easyRandom.objects(Temperament.class, 2).collect(Collectors.toList()));
        breed.getTemperaments().forEach(temperament -> temperament.setId(null));
        breed.setImages(easyRandom.objects(Image.class, 2).collect(Collectors.toList()));
        breed.getImages().forEach(image -> image.setId(null));
        postgresDBBreedRepository.save(breed);

        String temperament = breed.getTemperaments().get(0).getName();
        Page<Breed> all = postgresDBBreedRepository.findAllByTemperament(new Pageable(1, 100, "name", "desc"), temperament);
        assertThat(all.getContent().size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void findAllByOriginTest() throws Exception {
        Breed breed = easyRandom.nextObject(Breed.class);
        breed.setId(null);
        breed.setTemperaments(easyRandom.objects(Temperament.class, 2).collect(Collectors.toList()));
        breed.getTemperaments().forEach(temperament -> temperament.setId(null));
        breed.setImages(easyRandom.objects(Image.class, 2).collect(Collectors.toList()));
        breed.getImages().forEach(image -> image.setId(null));
        postgresDBBreedRepository.save(breed);

        String origin = breed.getOrigin();
        Page<Breed> all = postgresDBBreedRepository.findAllByOrigin(new Pageable(1, 100, "name", "desc"), origin);
        assertThat(all.getContent().size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void existsByExternalIdTest() throws Exception {
        Breed breed = easyRandom.nextObject(Breed.class);
        breed.setId(null);
        breed.setTemperaments(null);
        breed.setImages(null);
        postgresDBBreedRepository.save(breed);

        assertThat(postgresDBBreedRepository.existsByExternalId(breed.getExternalId())).isTrue();
        assertThat(postgresDBBreedRepository.existsByExternalId("testtesttesttesttesttesttesttesttest")).isFalse();
    }
}
