package br.com.itau.infrastructure.repository.postgres.log;

import br.com.itau.infrastructure.repository.log.Log4jRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OutputCaptureExtension.class)
@ExtendWith(MockitoExtension.class)
public class Log4jRepositoryTests {

    @InjectMocks
    private Log4jRepository log4jRepository;
    private static EasyRandom easyRandom;

    @BeforeAll
    private static void beforeTests() {
        easyRandom = new EasyRandom();
    }

    @Test
    public void debugTest(CapturedOutput output) throws Exception {
        String loggers = easyRandom.nextObject(String.class);
        log4jRepository.debug(loggers);

        assertThat(output).contains(loggers);
    }

    @Test
    public void infoTest(CapturedOutput output) throws Exception {
        String loggers = easyRandom.nextObject(String.class);
        log4jRepository.info(loggers);

        assertThat(output).contains(loggers);
    }

    @Test
    public void warnTest(CapturedOutput output) throws Exception {
        String loggers = easyRandom.nextObject(String.class);
        log4jRepository.warn(loggers);

        assertThat(output).contains(loggers);
    }

    @Test
    public void errorTest(CapturedOutput output) throws Exception {
        String loggers = easyRandom.nextObject(String.class);
        log4jRepository.error(loggers);

        assertThat(output).contains(loggers);
    }

}


