package br.com.itau.domain.usecase;

import br.com.itau.domain.entity.Breed;
import br.com.itau.domain.exception.BreedNotFoundException;
import br.com.itau.domain.port.repository.BreedRepository;
import br.com.itau.domain.port.repository.LogRepository;
import br.com.itau.domain.port.usecase.FindByIDUseCase;

import java.util.UUID;

public class FindByIDUseCaseImpl implements FindByIDUseCase {

    private final BreedRepository breedRepository;
    private final LogRepository logRepository;

    public FindByIDUseCaseImpl(BreedRepository breedRepository, LogRepository logRepository) {
        this.breedRepository = breedRepository;
        this.logRepository = logRepository;
    }

    @Override
    public Breed findByID(UUID id) {
        logRepository.info(String.format("Caso de Uso: findByIDUseCase -> Filtrando a raça pelo id: %s", id.toString()));
        Breed breed = breedRepository.findByID(id).orElseThrow(() -> new BreedNotFoundException("id", id.toString()));

        logRepository.info(String.format("Caso de Uso: findByIDUseCase -> Recuperado -> id: %s, name: %s, origin: %s, externalId: %s", breed.getExternalId(), breed.getName(), breed.getOrigin(), breed.getExternalId()));
        return breed;
    }
}
