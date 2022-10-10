package br.com.itau.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Breed {

    private UUID id;
    private String name;
    private String externalId;
    private String origin;
    private String description;
    private List<Temperament> temperaments = new ArrayList<>();
    private List<Image> images = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Temperament> getTemperaments() {
        return temperaments;
    }

    public void setTemperaments(List<Temperament> temperaments) {
        this.temperaments = temperaments;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
