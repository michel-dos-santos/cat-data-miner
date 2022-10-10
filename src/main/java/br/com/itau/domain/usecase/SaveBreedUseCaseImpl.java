package br.com.itau.domain.usecase;

import br.com.itau.domain.port.repository.BreedRepository;
import br.com.itau.domain.port.repository.LogRepository;
import br.com.itau.domain.port.service.GetExternalBreedService;
import br.com.itau.domain.port.usecase.SaveBreedUseCase;

public class SaveBreedUseCaseImpl implements SaveBreedUseCase {

    private final BreedRepository breedRepository;
    private final GetExternalBreedService externalBreedService;
    private final LogRepository logRepository;

    public SaveBreedUseCaseImpl(BreedRepository breedRepository, GetExternalBreedService externalBreedService, LogRepository logRepository) {
        this.breedRepository = breedRepository;
        this.externalBreedService = externalBreedService;
        this.logRepository = logRepository;
    }

    @Override
    public void save() {
        logRepository.info("Caso de Uso: saveBreedUseCase -> Iniciando o processo de mineração dos dados de raças de gatos");
        externalBreedService.getAllBreeds().stream().forEach(breedName -> {
            boolean existBreed = breedRepository.existsByExternalId(breedName.getExternalId());
            String exists = existBreed ? "SIM" : "NÃO";
            logRepository.info(String.format("Caso de Uso: saveBreedUseCase -> Validando a existência do External id: %s - Existe: %s", breedName.getExternalId(), exists));
            if (!existBreed) {
                breedRepository.save(breedName);
                logRepository.info(String.format("Caso de Uso: saveBreedUseCase -> Salvando External id: %s - Breed name: %s", breedName.getExternalId(), breedName.getName()));
            }
        });
    }
}
