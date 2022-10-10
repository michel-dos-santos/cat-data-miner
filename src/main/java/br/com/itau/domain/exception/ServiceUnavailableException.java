package br.com.itau.domain.exception;

public class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException(String serviceName) {
        super(String.format("Não foi possível acessar o serviço %s para obter os dados das raças", serviceName));
    }

}