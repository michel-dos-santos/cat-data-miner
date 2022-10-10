package br.com.itau.application.rest;


import br.com.itau.application.output.BreedNameResponseDTO;
import br.com.itau.application.output.BreedResponseDTO;
import br.com.itau.application.output.PageResponse;
import br.com.itau.application.rest.exception.APIException;
import br.com.itau.domain.entity.Breed;
import br.com.itau.domain.entity.Page;
import br.com.itau.domain.entity.Pageable;
import br.com.itau.domain.exception.BreedNotFoundException;
import br.com.itau.domain.exception.ErrorResponse;
import br.com.itau.domain.port.repository.LogRepository;
import br.com.itau.domain.port.usecase.FindAllBreedNameUseCase;
import br.com.itau.domain.port.usecase.FindAllByOriginUseCase;
import br.com.itau.domain.port.usecase.FindAllByTemperamentUseCase;
import br.com.itau.domain.port.usecase.FindByIDUseCase;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.UUID;

@Tag(name = "Endpoint Breeds")
@RestController
@RequestMapping(path = "/v1/breed", produces = MediaType.APPLICATION_JSON_VALUE)
public class BreedController {
    @Autowired
    private FindAllBreedNameUseCase findAllBreedNameUseCase;

    @Autowired
    private FindByIDUseCase findByIDUseCase;
    @Autowired
    private FindAllByTemperamentUseCase findAllByTemperamentUseCase;
    @Autowired
    private FindAllByOriginUseCase findAllByOriginUseCase;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LogRepository logRepository;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Indica que a busca foi executada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Indica que houve algum erro inesperado ao buscar as raças", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @Operation(summary = "Busca na base os nomes das raças")
    @Counted(value = "execution.count.findAllBreedName")
    @Timed(value = "execution.time.findAllBreedName", longTask = true)
    @GetMapping(value = "/names")
    public PageResponse<BreedNameResponseDTO> findAllBreedName(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "rowsPerPage", required = false, defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) throws APIException {
        try {
            logRepository.info(String.format("Caso de Uso: findAllBreedNameUseCase -> Recebendo requisição para %s", "/v1/breed/names"));
            Page<Breed> allBreedName = findAllBreedNameUseCase.findAllBreedName(new Pageable(page, size, sortBy, sortDir));
            Type type = new TypeToken<PageResponse<BreedNameResponseDTO>>() {}.getType();

            return modelMapper.map(allBreedName, type);
        } catch (Exception e) {
            throw APIException.internalError("Erro interno inesperado ao buscar as raças", e.getMessage());
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Indica que a busca foi executada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Indica que houve um erro na requisição"),
            @ApiResponse(responseCode = "404", description = "Indica que a busca foi executada com sucesso, porém não foi encontrado nenhum resultado"),
            @ApiResponse(responseCode = "500", description = "Indica que houve algum erro inesperado ao buscar as raças", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @Operation(summary = "Busca na base os dados da raça com base no ID informado")
    @Counted(value = "execution.count.findById")
    @Timed(value = "execution.time.findById", longTask = true)
    @GetMapping(value = "/{id}")
    public BreedResponseDTO findById(@PathVariable(value = "id") String id) throws APIException {
        try {
            logRepository.info(String.format("Caso de Uso: findByIDUseCase -> Recebendo requisição para %s", "/v1/breed/{id}"));
            Breed breed = findByIDUseCase.findByID(UUID.fromString(id));
            return modelMapper.map(breed, BreedResponseDTO.class);
        } catch (BreedNotFoundException e) {
            throw APIException.notFound("Raça não encontrado", e.getMessage());
        } catch (IllegalArgumentException e) {
            throw APIException.badRequest("Paramêtro inválido", e.getMessage());
        } catch (Exception e) {
            throw APIException.internalError("Erro interno inesperado ao buscar as raças", e.getMessage());
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Indica que a busca foi executada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Indica que houve algum erro inesperado ao buscar as raças pelo temperamento", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @Operation(summary = "Busca na base os dados da raça com base no temperamento informado")
    @Counted(value = "execution.count.findAllByTemperament")
    @Timed(value = "execution.time.findAllByTemperament", longTask = true)
    @GetMapping(value = "/temperament/{temperament}")
    public PageResponse<BreedResponseDTO> findAllByTemperament(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "rowsPerPage", required = false, defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
            @PathVariable(value = "temperament") String temperament) throws APIException {
        try {
            logRepository.info(String.format("Caso de Uso: findAllByTemperamentUseCase -> Recebendo requisição para %s", "/v1/breed/temperament/{temperament}"));
            Page<Breed> allBreed = findAllByTemperamentUseCase.findAllByTemperament(new Pageable(page, size, sortBy, sortDir), temperament);
            Type type = new TypeToken<PageResponse<BreedResponseDTO>>() {}.getType();

            return modelMapper.map(allBreed, type);
        } catch (Exception e) {
            throw APIException.internalError("Erro interno inesperado ao buscar as raças", e.getMessage());
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Indica que a busca foi executada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Indica que houve algum erro inesperado ao buscar as raças pela origem", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @Operation(summary = "Busca na base os dados da raça com base na origem informado")
    @Counted(value = "execution.count.findAllByOrigin")
    @Timed(value = "execution.time.findAllByOrigin", longTask = true)
    @GetMapping(value = "/origin/{origin}")
    public PageResponse<BreedResponseDTO> findAllByOrigin(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "rowsPerPage", required = false, defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
            @PathVariable(value = "origin") String origin) throws APIException {
        try {
            logRepository.info(String.format("Caso de Uso: findAllByOriginUseCase -> Recebendo requisição para %s", "/v1/breed/origin/{origin}"));
            Page<Breed> allBreed = findAllByOriginUseCase.findAllByOrigin(new Pageable(page, size, sortBy, sortDir), origin);
            Type type = new TypeToken<PageResponse<BreedResponseDTO>>() {}.getType();

            return modelMapper.map(allBreed, type);
        } catch (Exception e) {
            throw APIException.internalError("Erro interno inesperado ao buscar as raças", e.getMessage());
        }
    }

}
