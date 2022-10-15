package br.com.itau.infrastructure.repository.postgres.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "temperament")
@EntityListeners(AuditingEntityListener.class)
public class TemperamentEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createAt;
    @LastModifiedDate
    private Date updatedAt;
    private String name;
    @ManyToOne
    @JoinColumn(name = "fk_breed_id")
    private BreedEntity breed;

}
