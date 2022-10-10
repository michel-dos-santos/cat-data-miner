package br.com.itau.domain.port.usecase;

import br.com.itau.domain.entity.Breed;
import br.com.itau.domain.entity.Page;
import br.com.itau.domain.entity.Pageable;

public interface FindAllByTemperamentUseCase {

    Page<Breed> findAllByTemperament(Pageable pageable, String temperament);

}
