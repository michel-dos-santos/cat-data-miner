package br.com.itau.domain.entity;

public class Pageable {

    private int page;
    private int rowsPerPage;
    private String sortBy;
    private String sortDir;

    public Pageable(int page, int rowsPerPage, String sortBy, String sortDir) {
        this.page = page;
        this.rowsPerPage = rowsPerPage;
        this.sortBy = sortBy;
        this.sortDir = sortDir;
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

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }
}
