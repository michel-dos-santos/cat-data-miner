package br.com.itau.domain.exception;

public class BreedNotFoundException extends RuntimeException {

    public BreedNotFoundException(String campo, String conteudo) {
        super(String.format("Raça não encontrado com base no %s: %s", campo, conteudo));
    }

}