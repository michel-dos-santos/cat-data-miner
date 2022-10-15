package br.com.itau.application.rest;

import br.com.itau.application.output.BreedNameResponseDTO;
import br.com.itau.application.output.BreedResponseDTO;
import br.com.itau.application.output.PageResponse;
import br.com.itau.domain.entity.Breed;
import br.com.itau.domain.entity.Page;
import br.com.itau.domain.exception.BreedNotFoundException;
import br.com.itau.domain.port.repository.LogRepository;
import br.com.itau.domain.port.usecase.FindAllBreedNameUseCase;
import br.com.itau.domain.port.usecase.FindAllByOriginUseCase;
import br.com.itau.domain.port.usecase.FindAllByTemperamentUseCase;
import br.com.itau.domain.port.usecase.FindByIDUseCase;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.lang.reflect.Type;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BreedController.class)
public class BreedControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FindAllBreedNameUseCase findAllBreedNameUseCase;
    @MockBean
    private FindByIDUseCase findByIDUseCase;
    @MockBean
    private FindAllByTemperamentUseCase findAllByTemperamentUseCase;
    @MockBean
    private FindAllByOriginUseCase findAllByOriginUseCase;
    @MockBean
    private ModelMapper modelMapper;
    @MockBean
    private LogRepository logRepository;
    private static EasyRandom easyRandom;

    @BeforeAll
    private static void beforeTests() {
        easyRandom = new EasyRandom();
    }

    @Test
    public void findAllBreedNameTest() throws Exception {
        Page<Breed> breedPage = easyRandom.nextObject(Page.class);
        breedPage.setContent(easyRandom.objects(Breed.class, 10).collect(Collectors.toList()));
        PageResponse<BreedNameResponseDTO> breedPageResponse = easyRandom.nextObject(PageResponse.class);
        breedPageResponse.setContent(easyRandom.objects(BreedNameResponseDTO.class, 10).collect(Collectors.toList()));
        Type type = new TypeToken<PageResponse<BreedNameResponseDTO>>() {}.getType();
        String urlTemplate = "/v1/breed/names";

        Mockito.when(findAllBreedNameUseCase.findAllBreedName(any())).thenReturn(breedPage);
        Mockito.when(modelMapper.map(breedPage, type)).thenReturn(breedPageResponse);

        ResultActions resultActions = mockMvc.perform(get(urlTemplate).accept(APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.page", is(breedPageResponse.getPage())))
                .andExpect(jsonPath("$.rowsPerPage", is(breedPageResponse.getRowsPerPage())))
                .andExpect(jsonPath("$.totalElements", is(breedPageResponse.getTotalElements())))
                .andExpect(jsonPath("$.totalPages", is(breedPageResponse.getTotalPages())));

        resultActions.andExpect(jsonPath("$.content.length()", is(10)));

        for (int i = 0; i < breedPageResponse.getContent().size(); i++) {
            BreedNameResponseDTO breed = breedPageResponse.getContent().get(i);

            resultActions.andExpect(jsonPath(format("$.content[%d].id", i), is(breed.getId().toString())));
            resultActions.andExpect(jsonPath(format("$.content[%d].name", i), is(breed.getName())));
        }

        // Verifica se o serviço recebeu os argumentos da requisição feita ao controller
        verify(findAllBreedNameUseCase).findAllBreedName(any());
    }

    @Test
    public void findByIdTest() throws Exception {
        UUID id = easyRandom.nextObject(UUID.class);
        Breed breed = easyRandom.nextObject(Breed.class);
        BreedResponseDTO breedPageResponse = easyRandom.nextObject(BreedResponseDTO.class);
        String urlTemplate = "/v1/breed/{id}";

        Mockito.when(findByIDUseCase.findByID(id)).thenReturn(breed);
        Mockito.when(modelMapper.map(breed, BreedResponseDTO.class)).thenReturn(breedPageResponse);

        ResultActions resultActions = mockMvc.perform(get(urlTemplate, id.toString()).accept(APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(breedPageResponse.getId().toString())))
                .andExpect(jsonPath("$.name", is(breedPageResponse.getName())))
                .andExpect(jsonPath("$.origin", is(breedPageResponse.getOrigin())))
                .andExpect(jsonPath("$.description", is(breedPageResponse.getDescription())));

        resultActions.andExpect(jsonPath("$.temperaments.length()", greaterThanOrEqualTo(0)));
        resultActions.andExpect(jsonPath("$.images.length()", greaterThanOrEqualTo(0)));

        // Verifica se o serviço recebeu os argumentos da requisição feita ao controller
        verify(findByIDUseCase).findByID(id);
    }

    @Test
    public void findByIdNotFoundTest() throws Exception {
        UUID id = easyRandom.nextObject(UUID.class);
        String urlTemplate = "/v1/breed/{id}";

        Mockito.when(findByIDUseCase.findByID(id)).thenThrow(new BreedNotFoundException("id", id.toString()));

        ResultActions resultActions = mockMvc.perform(get(urlTemplate, id.toString()).accept(APPLICATION_JSON));

        resultActions.andExpect(status().isNotFound());

        resultActions.andExpect(jsonPath("$.code", is("Not Found")));
        resultActions.andExpect(jsonPath("$.description", is("Raça não encontrado")));
        resultActions.andExpect(jsonPath("$.message", is(String.format("Raça não encontrado com base no id: %s", id))));

        // Verifica se o serviço recebeu os argumentos da requisição feita ao controller
        verify(findByIDUseCase).findByID(id);
    }

    @Test
    public void findByIdIllegalArgumentTest() throws Exception {
        String id = "idError";
        String urlTemplate = "/v1/breed/{id}";

        ResultActions resultActions = mockMvc.perform(get(urlTemplate, id).accept(APPLICATION_JSON));

        resultActions.andExpect(status().isBadRequest());

        resultActions.andExpect(jsonPath("$.code", is("Bad Request")));
        resultActions.andExpect(jsonPath("$.description", is("Paramêtro inválido")));
        resultActions.andExpect(jsonPath("$.message", is(String.format("Invalid UUID string: %s", id))));
    }

    @Test
    public void findAllByTemperamentTest() throws Exception {
        Page<Breed> breedPage = easyRandom.nextObject(Page.class);
        breedPage.setContent(easyRandom.objects(Breed.class, 10).collect(Collectors.toList()));
        PageResponse<BreedResponseDTO> breedPageResponse = easyRandom.nextObject(PageResponse.class);
        breedPageResponse.setContent(easyRandom.objects(BreedResponseDTO.class, 10).collect(Collectors.toList()));
        Type type = new TypeToken<PageResponse<BreedResponseDTO>>() {}.getType();
        String urlTemplate = "/v1/breed/temperament/{temperament}";

        Mockito.when(findAllByTemperamentUseCase.findAllByTemperament(any(), anyString())).thenReturn(breedPage);
        Mockito.when(modelMapper.map(breedPage, type)).thenReturn(breedPageResponse);

        ResultActions resultActions = mockMvc.perform(get(urlTemplate, "active").accept(APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.page", is(breedPageResponse.getPage())))
                .andExpect(jsonPath("$.rowsPerPage", is(breedPageResponse.getRowsPerPage())))
                .andExpect(jsonPath("$.totalElements", is(breedPageResponse.getTotalElements())))
                .andExpect(jsonPath("$.totalPages", is(breedPageResponse.getTotalPages())));

        resultActions.andExpect(jsonPath("$.content.length()", is(10)));

        for (int i = 0; i < breedPageResponse.getContent().size(); i++) {
            BreedResponseDTO breed = breedPageResponse.getContent().get(i);

            resultActions.andExpect(jsonPath(format("$.content[%d].id", i), is(breed.getId().toString())));
            resultActions.andExpect(jsonPath(format("$.content[%d].name", i), is(breed.getName())));
            resultActions.andExpect(jsonPath(format("$.content[%d].origin", i), is(breed.getOrigin())));
            resultActions.andExpect(jsonPath(format("$.content[%d].description", i), is(breed.getDescription())));
            resultActions.andExpect(jsonPath(format("$.content[%d].temperaments.length()", i), greaterThanOrEqualTo(0)));
            resultActions.andExpect(jsonPath(format("$.content[%d].images.length()", i), greaterThanOrEqualTo(0)));
        }

        // Verifica se o serviço recebeu os argumentos da requisição feita ao controller
        verify(findAllByTemperamentUseCase).findAllByTemperament(any(), anyString());
    }

    @Test
    public void findAllByOriginTest() throws Exception {
        Page<Breed> breedPage = easyRandom.nextObject(Page.class);
        breedPage.setContent(easyRandom.objects(Breed.class, 10).collect(Collectors.toList()));
        PageResponse<BreedResponseDTO> breedPageResponse = easyRandom.nextObject(PageResponse.class);
        breedPageResponse.setContent(easyRandom.objects(BreedResponseDTO.class, 10).collect(Collectors.toList()));
        Type type = new TypeToken<PageResponse<BreedResponseDTO>>() {}.getType();
        String urlTemplate = "/v1/breed/origin/{origin}";

        Mockito.when(findAllByOriginUseCase.findAllByOrigin(any(), anyString())).thenReturn(breedPage);
        Mockito.when(modelMapper.map(breedPage, type)).thenReturn(breedPageResponse);

        ResultActions resultActions = mockMvc.perform(get(urlTemplate, "Brazil").accept(APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.page", is(breedPageResponse.getPage())))
                .andExpect(jsonPath("$.rowsPerPage", is(breedPageResponse.getRowsPerPage())))
                .andExpect(jsonPath("$.totalElements", is(breedPageResponse.getTotalElements())))
                .andExpect(jsonPath("$.totalPages", is(breedPageResponse.getTotalPages())));

        resultActions.andExpect(jsonPath("$.content.length()", is(10)));

        for (int i = 0; i < breedPageResponse.getContent().size(); i++) {
            BreedResponseDTO breed = breedPageResponse.getContent().get(i);

            resultActions.andExpect(jsonPath(format("$.content[%d].id", i), is(breed.getId().toString())));
            resultActions.andExpect(jsonPath(format("$.content[%d].name", i), is(breed.getName())));
            resultActions.andExpect(jsonPath(format("$.content[%d].origin", i), is(breed.getOrigin())));
            resultActions.andExpect(jsonPath(format("$.content[%d].description", i), is(breed.getDescription())));
            resultActions.andExpect(jsonPath(format("$.content[%d].temperaments.length()", i), greaterThanOrEqualTo(0)));
            resultActions.andExpect(jsonPath(format("$.content[%d].images.length()", i), greaterThanOrEqualTo(0)));
        }

        // Verifica se o serviço recebeu os argumentos da requisição feita ao controller
        verify(findAllByOriginUseCase).findAllByOrigin(any(), anyString());
    }

    @Test
    public void findAllBreedNameExceptionTest() throws Exception {
        String urlTemplate = "/v1/breed/names";

        Mockito.when(findAllBreedNameUseCase.findAllBreedName(any())).thenThrow(new MockitoException("Erro interno"));

        ResultActions resultActions = mockMvc.perform(get(urlTemplate).accept(APPLICATION_JSON));

        resultActions.andExpect(status().isInternalServerError());

        // Verifica se o serviço recebeu os argumentos da requisição feita ao controller
        verify(findAllBreedNameUseCase).findAllBreedName(any());
    }

    @Test
    public void findByIdExceptionTest() throws Exception {
        UUID id = easyRandom.nextObject(UUID.class);
        String urlTemplate = "/v1/breed/{id}";

        Mockito.when(findByIDUseCase.findByID(id)).thenThrow(new MockitoException("Erro interno"));

        ResultActions resultActions = mockMvc.perform(get(urlTemplate, id.toString()).accept(APPLICATION_JSON));

        resultActions.andExpect(status().isInternalServerError());

        // Verifica se o serviço recebeu os argumentos da requisição feita ao controller
        verify(findByIDUseCase).findByID(id);
    }

    @Test
    public void findAllByTemperamentExceptionTest() throws Exception {
        String urlTemplate = "/v1/breed/temperament/{temperament}";

        Mockito.when(findAllByTemperamentUseCase.findAllByTemperament(any(), anyString())).thenThrow(new MockitoException("Erro interno"));

        ResultActions resultActions = mockMvc.perform(get(urlTemplate, "active").accept(APPLICATION_JSON));

        resultActions.andExpect(status().isInternalServerError());

        // Verifica se o serviço recebeu os argumentos da requisição feita ao controller
        verify(findAllByTemperamentUseCase).findAllByTemperament(any(), anyString());
    }

    @Test
    public void findAllByOriginExceptionTest() throws Exception {
        String urlTemplate = "/v1/breed/origin/{origin}";

        Mockito.when(findAllByOriginUseCase.findAllByOrigin(any(), anyString())).thenThrow(new MockitoException("Erro interno"));

        ResultActions resultActions = mockMvc.perform(get(urlTemplate, "Brazil").accept(APPLICATION_JSON));

        resultActions.andExpect(status().isInternalServerError());

        // Verifica se o serviço recebeu os argumentos da requisição feita ao controller
        verify(findAllByOriginUseCase).findAllByOrigin(any(), anyString());
    }
}
