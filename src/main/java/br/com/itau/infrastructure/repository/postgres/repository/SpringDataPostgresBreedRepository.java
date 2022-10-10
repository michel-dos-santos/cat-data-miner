package br.com.itau.infrastructure.repository.postgres.repository;

import br.com.itau.infrastructure.repository.postgres.entity.BreedEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface SpringDataPostgresBreedRepository extends PagingAndSortingRepository<BreedEntity, UUID> {

    @Query("select b from BreedEntity b inner join b.temperaments t where lower(t.name) = :temperament")
    Page<BreedEntity> findAllByTemperaments(PageRequest pageRequest, @Param("temperament") String temperament);
    @Query("select b from BreedEntity b where lower(b.origin) = :origin")
    Page<BreedEntity> findAllByOrigin(PageRequest pageRequest, @Param("origin") String origin);

    boolean existsByExternalId(String externalId);
}
