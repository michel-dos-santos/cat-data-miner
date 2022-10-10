package br.com.itau.domain.entity;

import java.util.UUID;

public class Temperament {

    private UUID id;
    private String name;

    public Temperament() {
    }

    public Temperament(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
