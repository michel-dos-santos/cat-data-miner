package br.com.itau.domain.usecase;

import br.com.itau.domain.entity.Breed;
import br.com.itau.domain.entity.Page;
import br.com.itau.domain.entity.Pageable;
import br.com.itau.domain.port.repository.BreedRepository;
import br.com.itau.domain.port.repository.LogRepository;
import br.com.itau.domain.port.usecase.FindAllByTemperamentUseCase;

public class FindAllByTemperamentUseCaseImpl implements FindAllByTemperamentUseCase {

    private final BreedRepository breedRepository;
    private final LogRepository logRepository;

    public FindAllByTemperamentUseCaseImpl(BreedRepository breedRepository, LogRepository logRepository) {
        this.breedRepository = breedRepository;
        this.logRepository = logRepository;
    }

    @Override
    public Page<Breed> findAllByTemperament(Pageable pageable, String temperament) {
        logRepository.info(String.format("Caso de Uso: findAllByTemperamentUseCase -> Filtrando a raça pelo temperamento: %s", temperament.trim()));
        Page<Breed> page = breedRepository.findAllByTemperament(pageable, temperament.trim().toLowerCase());

        logRepository.info(String.format("Caso de Uso: findAllByTemperamentUseCase -> Paginação -> page: %s, rowsPerPage: %s, totalElements: %s, totalPages: %s", page.getPage(), page.getRowsPerPage(), page.getTotalElements(), page.getTotalPages()));
        return page;
    }
}
