package br.com.itau.infrastructure.service.thecatapi;

import br.com.itau.domain.exception.ServiceUnavailableException;
import br.com.itau.domain.port.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class TheCatAPIServiceFallback implements FallbackFactory<TheCatAPIServiceFeignClient> {

    @Autowired
    private LogRepository logRepository;

    @Override
    public TheCatAPIServiceFeignClient create(Throwable cause) {
        logRepository.info(cause.getMessage());

        throw new ServiceUnavailableException("The Cat API");
    }
}