package com.upe.br.acheie.dtos;

public record ErrorDTO(String error, String cause) {

	  public ErrorDTO(Exception e) {
	    this(e.getMessage(), e.getCause() != null ? e.getCause().toString() : "");
	  }
}