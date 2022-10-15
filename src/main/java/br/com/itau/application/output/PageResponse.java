package br.com.itau.application.output;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PageResponse<T> {

    private List<T> content;
    private int page;
    private int rowsPerPage;
    private long totalElements;
    private int totalPages;

}