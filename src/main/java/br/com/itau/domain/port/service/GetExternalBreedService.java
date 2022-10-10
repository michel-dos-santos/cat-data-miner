package br.com.itau.domain.port.service;

import br.com.itau.domain.entity.Breed;

import java.util.List;

public interface GetExternalBreedService {

    List<Breed> getAllBreeds();

}
