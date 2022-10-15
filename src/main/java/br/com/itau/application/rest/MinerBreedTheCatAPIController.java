package br.com.itau.application.rest;


import br.com.itau.application.rest.exception.APIException;
import br.com.itau.domain.port.usecase.SaveBreedUseCase;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Endpoint Miner from provider The Cat API")
@RestController
@RequestMapping(path = "/v1/miner/the-cat", produces = MediaType.APPLICATION_JSON_VALUE)
public class MinerBreedTheCatAPIController {

    @Autowired
    private SaveBreedUseCase saveBreedUseCase;

    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Indica que o processo de miniração dos dados foi iniciado") })
    @Operation(summary = "Persiste os dados minerados oriundos do provedor The Cat API")
    @Counted(value = "execution.count.save")
    @Timed(value = "execution.time.save", longTask = true)
    @PostMapping
    public void save() throws APIException {
        try {
            saveBreedUseCase.save();
        } catch (Exception e) {
            throw APIException.internalError("Erro interno inesperado ao minerar dados do the cat api", e.getMessage());
        }
    }

}
