package br.com.itau.domain.port.repository;

import br.com.itau.domain.entity.Breed;
import br.com.itau.domain.entity.Page;
import br.com.itau.domain.entity.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface BreedRepository {

    Breed save(Breed breed);
    Optional<Breed> findByID(UUID id);
    Page<Breed> findAll(Pageable pageable);
    Page<Breed> findAllByTemperament(Pageable pageable, String temperament);
    Page<Breed> findAllByOrigin(Pageable pageable, String origin);

    boolean existsByExternalId(String externalId);

}
