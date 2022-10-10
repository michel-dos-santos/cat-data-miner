package br.com.itau.infrastructure.repository.postgres.repository;

import br.com.itau.domain.entity.Breed;
import br.com.itau.domain.entity.Page;
import br.com.itau.domain.entity.Pageable;
import br.com.itau.domain.port.repository.BreedRepository;
import br.com.itau.infrastructure.repository.postgres.entity.BreedEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.*;

@Component
public class PostgresDBBreedRepository implements BreedRepository {

    private final SpringDataPostgresBreedRepository breedRepository;
    private final ModelMapper modelMapper;

    public PostgresDBBreedRepository(SpringDataPostgresBreedRepository breedRepository, ModelMapper modelMapper) {
        this.breedRepository = breedRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Breed save(Breed breed) {
        BreedEntity breedEntity = modelMapper.map(breed, BreedEntity.class);
        breedEntity.getTemperaments().stream().forEach(temperamentEntity -> temperamentEntity.setBreed(breedEntity));

        if (Objects.nonNull(breedEntity.getImages())) {
            breedEntity.getImages().stream().forEach(imageEntity -> imageEntity.setBreed(breedEntity));
        }

        breedRepository.save(breedEntity);
        breed.setId(breedEntity.getId());
        breed.setExternalId(breedEntity.getExternalId());
        return breed;
    }

    @Override
    public Optional<Breed> findByID(UUID id) {
        Optional<BreedEntity> optionalBreedEntity = breedRepository.findById(id);
        if (optionalBreedEntity.isPresent()) {
            return Optional.of(modelMapper.map(optionalBreedEntity.get(), Breed.class));
        }
        return Optional.empty();
    }

    @Override
    public Page<Breed> findAll(Pageable pageable) {
        Sort.Direction direction = pageable.getSortDir().equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, pageable.getSortBy());
        PageRequest pageRequest = PageRequest.of(Math.max(0, pageable.getPage() - 1), pageable.getRowsPerPage(), sort);
        Type type = new TypeToken<Page<Breed>>() {}.getType();
        org.springframework.data.domain.Page<BreedEntity> page = breedRepository.findAll(pageRequest);
        Page<Breed> pageMap = modelMapper.map(page, type);
        pageMap.setPage(page.getNumber() + 1);
        pageMap.setRowsPerPage(page.getNumberOfElements());
        return pageMap;
    }

    @Override
    public Page<Breed> findAllByTemperament(Pageable pageable, String temperament) {
        Sort.Direction direction = pageable.getSortDir().equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, pageable.getSortBy());
        PageRequest pageRequest = PageRequest.of(Math.max(0, pageable.getPage() - 1), pageable.getRowsPerPage(), sort);
        Type type = new TypeToken<Page<Breed>>() {}.getType();
        org.springframework.data.domain.Page<BreedEntity> page = breedRepository.findAllByTemperaments(pageRequest, temperament);
        Page<Breed> pageMap = modelMapper.map(page, type);
        pageMap.setPage(page.getNumber() + 1);
        pageMap.setRowsPerPage(page.getNumberOfElements());
        return pageMap;
    }

    @Override
    public Page<Breed> findAllByOrigin(Pageable pageable, String origin) {
        Sort.Direction direction = pageable.getSortDir().equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, pageable.getSortBy());
        PageRequest pageRequest = PageRequest.of(Math.max(0, pageable.getPage() - 1), pageable.getRowsPerPage(), sort);
        Type type = new TypeToken<Page<Breed>>() {}.getType();
        org.springframework.data.domain.Page<BreedEntity> page = breedRepository.findAllByOrigin(pageRequest, origin);
        Page<Breed> pageMap = modelMapper.map(page, type);
        pageMap.setPage(page.getNumber() + 1);
        pageMap.setRowsPerPage(page.getNumberOfElements());
        return pageMap;
    }

    @Override
    public boolean existsByExternalId(String externalId) {
        return breedRepository.existsByExternalId(externalId);
    }
}
