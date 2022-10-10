package br.com.itau.domain.entity;

import java.util.List;

public class Page<T> {

    private List<T> content;
    private int page;
    private int rowsPerPage;
    private long totalElements;
    private int totalPages;

    public Page() {
    }

    public Page(List<T> content, int page, int rowsPerPage, long totalElements, int totalPages) {
        this.content = content;
        this.page = page;
        this.rowsPerPage = rowsPerPage;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
