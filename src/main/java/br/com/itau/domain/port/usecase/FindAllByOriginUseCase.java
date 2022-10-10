package br.com.itau.domain.port.usecase;

import br.com.itau.domain.entity.Breed;
import br.com.itau.domain.entity.Page;
import br.com.itau.domain.entity.Pageable;

public interface FindAllByOriginUseCase {

    Page<Breed> findAllByOrigin(Pageable pageable, String origin);

}
