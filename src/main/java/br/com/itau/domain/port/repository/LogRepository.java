package br.com.itau.domain.port.repository;

public interface LogRepository {

    void debug(String msg);
    void info(String msg);
    void warn(String msg);
    void error(String msg);

}
