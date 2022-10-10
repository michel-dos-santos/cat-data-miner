package br.com.itau.domain.entity;

import java.util.UUID;

public class Image {

    private UUID id;
    private String externalId;
    private String url;

    public Image() {
    }

    public Image(UUID id, String externalId, String url) {
        this.id = id;
        this.externalId = externalId;
        this.url = url;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
