package br.com.itau.infrastructure.configuration;

import br.com.itau.domain.port.repository.BreedRepository;
import br.com.itau.domain.port.repository.LogRepository;
import br.com.itau.domain.port.service.GetExternalBreedService;
import br.com.itau.domain.port.usecase.*;
import br.com.itau.domain.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BreedBeanConfiguration {

    @Bean
    FindAllBreedNameUseCase findAllBreedNameUseCase(BreedRepository breedRepository, LogRepository logRepository) {
        return new FindAllBreedNameUseCaseImpl(breedRepository, logRepository);
    }

    @Bean
    FindAllByOriginUseCase findAllByOriginUseCase(BreedRepository breedRepository, LogRepository logRepository) {
        return new FindAllByOriginUseCaseImpl(breedRepository, logRepository);
    }

    @Bean
    FindAllByTemperamentUseCase findAllByTemperamentUseCase(BreedRepository breedRepository, LogRepository logRepository) {
        return new FindAllByTemperamentUseCaseImpl(breedRepository, logRepository);
    }

    @Bean
    FindByIDUseCase findByIDUseCase(BreedRepository breedRepository, LogRepository logRepository) {
        return new FindByIDUseCaseImpl(breedRepository, logRepository);
    }

    @Bean
    SaveBreedUseCase saveBreedUseCase(BreedRepository breedRepository, GetExternalBreedService externalBreedService, LogRepository logRepository) {
        return new SaveBreedUseCaseImpl(breedRepository, externalBreedService, logRepository);
    }

}
