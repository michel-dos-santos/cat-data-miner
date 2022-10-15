package br.com.itau.infrastructure.repository.postgres.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "breed")
@EntityListeners(AuditingEntityListener.class)
public class BreedEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createAt;
    @LastModifiedDate
    private Date updatedAt;
    private String externalId;
    private String name;
    private String origin;
    private String description;
    @OneToMany(mappedBy="breed", cascade = CascadeType.ALL)
    private List<TemperamentEntity> temperaments = new ArrayList<>();
    @OneToMany(mappedBy="breed", cascade = CascadeType.ALL)
    private List<ImageEntity> images = new ArrayList<>();

}
