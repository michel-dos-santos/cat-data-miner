package br.com.itau.domain.port.usecase;

import br.com.itau.domain.entity.Breed;
import br.com.itau.domain.entity.Page;
import br.com.itau.domain.entity.Pageable;

public interface FindAllBreedNameUseCase {

    Page<Breed> findAllBreedName(Pageable pageable);

}
