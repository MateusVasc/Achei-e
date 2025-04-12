package com.upe.br.auth.dtos;

public record ErrorResponse(String error, String cause) {

    public ErrorResponse(Exception e) {
        this(e.getMessage(), e.getCause() != null ? e.getCause().toString() : "");
    }
}
