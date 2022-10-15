package br.com.itau.domain.usecase;

import br.com.itau.domain.entity.Breed;
import br.com.itau.domain.entity.Page;
import br.com.itau.domain.entity.Pageable;
import br.com.itau.domain.port.repository.BreedRepository;
import br.com.itau.domain.port.repository.LogRepository;
import br.com.itau.domain.port.usecase.FindAllBreedNameUseCase;

public class FindAllBreedNameUseCaseImpl implements FindAllBreedNameUseCase {

    private final BreedRepository breedRepository;
    private final LogRepository logRepository;

    public FindAllBreedNameUseCaseImpl(BreedRepository breedRepository, LogRepository logRepository) {
        this.breedRepository = breedRepository;
        this.logRepository = logRepository;
    }

    @Override
    public Page<Breed> findAllBreedName(Pageable pageable) {
        logRepository.info("Caso de Uso: findAllBreedNameUseCase -> Filtrando todas as raças e retornando apenas seu identificador e nome");
        Page<Breed> page = breedRepository.findAll(pageable);

        logRepository.info(String.format("Caso de Uso: findAllBreedNameUseCase -> Paginação -> page: %s, rowsPerPage: %s, totalElements: %s, totalPages: %s", page.getPage(), page.getRowsPerPage(), page.getTotalElements(), page.getTotalPages()));
        return page;
    }
}
