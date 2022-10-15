package br.com.itau.application.rest;

import br.com.itau.domain.port.usecase.SaveBreedUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MinerBreedTheCatAPIController.class)
public class MinerBreedTheCatAPIControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SaveBreedUseCase saveBreedUseCase;

    @Test
    public void saveTest() throws Exception {
        String urlTemplate = "/v1/miner/the-cat";
        Mockito.doNothing().when(saveBreedUseCase).save();

        ResultActions resultActions = mockMvc.perform(post(urlTemplate).accept(APPLICATION_JSON));

        resultActions.andExpect(status().isOk());

        // Verifica se o serviço recebeu os argumentos da requisição feita ao controller
        verify(saveBreedUseCase).save();
    }

    @Test
    public void saveExceptionTest() throws Exception {
        String urlTemplate = "/v1/miner/the-cat";

        Mockito.doThrow(new MockitoException("Erro interno")).when(saveBreedUseCase).save();

        ResultActions resultActions = mockMvc.perform(post(urlTemplate).accept(APPLICATION_JSON));

        resultActions.andExpect(status().isInternalServerError());

        // Verifica se o serviço recebeu os argumentos da requisição feita ao controller
        verify(saveBreedUseCase).save();
    }
}
