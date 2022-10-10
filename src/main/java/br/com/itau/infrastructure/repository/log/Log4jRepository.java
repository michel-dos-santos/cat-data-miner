package br.com.itau.infrastructure.repository.log;

import br.com.itau.domain.port.repository.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Log4jRepository implements LogRepository {

    @Override
    public void debug(String msg) {
        log.debug(msg);
    }

    @Override
    public void info(String msg) {
        log.info(msg);
    }

    @Override
    public void warn(String msg) {
        log.warn(msg);
    }

    @Override
    public void error(String msg) {
        log.error(msg);
    }
}
