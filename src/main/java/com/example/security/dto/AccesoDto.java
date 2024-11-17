package com.example.security.dto;

public class AccesoDto {
	private Long id;
    private String accesos;
    private char estadoAccesos;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccesos() {
        return accesos;
    }

    public void setAccesos(String accesos) {
        this.accesos = accesos;
    }

    public char getEstadoAccesos() {
        return estadoAccesos;
    }

    public void setEstadoAccesos(char estadoAccesos) {
        this.estadoAccesos = estadoAccesos;
    }
}