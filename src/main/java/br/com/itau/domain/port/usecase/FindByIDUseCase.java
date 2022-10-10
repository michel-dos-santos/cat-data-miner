package br.com.itau.domain.port.usecase;

import br.com.itau.domain.entity.Breed;

import java.util.UUID;

public interface FindByIDUseCase {

    Breed findByID(UUID id);

}
