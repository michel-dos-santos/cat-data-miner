package br.com.itau.domain.usecase;

import br.com.itau.domain.entity.Breed;
import br.com.itau.domain.entity.Page;
import br.com.itau.domain.entity.Pageable;
import br.com.itau.domain.port.repository.BreedRepository;
import br.com.itau.domain.port.repository.LogRepository;
import br.com.itau.domain.port.usecase.FindAllByOriginUseCase;

public class FindAllByOriginUseCaseImpl implements FindAllByOriginUseCase {

    private final BreedRepository breedRepository;
    private final LogRepository logRepository;

    public FindAllByOriginUseCaseImpl(BreedRepository breedRepository, LogRepository logRepository) {
        this.breedRepository = breedRepository;
        this.logRepository = logRepository;
    }

    @Override
    public Page<Breed> findAllByOrigin(Pageable pageable, String origin) {
        logRepository.info(String.format("Caso de Uso: findAllByOriginUseCase -> Filtrando a raça pela origem: %s", origin.trim()));
        Page<Breed> page = breedRepository.findAllByOrigin(pageable, origin.trim().toLowerCase());

        logRepository.info(String.format("Caso de Uso: findAllByOriginUseCase -> Paginação -> page: %s, rowsPerPage: %s, totalElements: %s, totalPages: %s", page.getPage(), page.getRowsPerPage(), page.getTotalElements(), page.getTotalPages()));
        return page;
    }
}
